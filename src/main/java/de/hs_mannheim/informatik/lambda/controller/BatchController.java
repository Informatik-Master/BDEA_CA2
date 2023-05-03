package de.hs_mannheim.informatik.lambda.controller;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hs_mannheim.informatik.lambda.entities.Document;
import de.hs_mannheim.informatik.lambda.entities.GlobalWordFrequency;
import de.hs_mannheim.informatik.lambda.respository.DocumentRepository;
import de.hs_mannheim.informatik.lambda.respository.GlobalWordFrequencyRepository;
import de.hs_mannheim.informatik.lambda.services.DocumentFrequencyService;
import de.hs_mannheim.informatik.lambda.services.FSService;
import de.hs_mannheim.informatik.lambda.services.WordCloudService;

@RestController()
@RequestMapping("api/batch")
public class BatchController {

  
    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    GlobalWordFrequencyRepository globalWordFrequencyRepository;

    @Autowired
    SparkSession sparkSession;

    @Autowired
    WordCloudService wordCloudService;

    @Autowired
    DocumentFrequencyService documentFrequencyService;

    @Autowired
    FSService fsService;

    ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @PostMapping("wordcloud")
    public void batchCreateAWordCloudFromAllDocuments() {
        this.executor.submit(() -> {
            var filename = UUID.randomUUID().toString() + ".PNG";
            var target = fsService.getTargetDirectory().resolve(filename).toString();
            var wordCloud = wordCloudService.generateWordCloud(fsService.getSourceDirectory().toString());

            wordCloud.writeToFile(target);

            var document = new Document();
            document.setFilename(filename);
            document.setDocumentType(Document.DocumentType.OVERALL_TARGET);
            document.setWarehouseFilename(target);
            document.setContentType("image/png");
            documentRepository.save(document);
        });
    }

    @PostMapping("documentfrequency")
    public void batchCreateDokumentFrequence() {
        this.executor.submit(() -> {
            var frequencies = documentFrequencyService.generateDocumentFrequency(fsService.getSourceDirectory().toString());

            var dbEntires = frequencies.stream().map(tuple -> {
                var globalWordFrequency = new GlobalWordFrequency();
                globalWordFrequency.setWord(tuple._1());
                globalWordFrequency.setFrequency(tuple._2());
                return globalWordFrequency;
            }).collect(Collectors.toList());
    
            globalWordFrequencyRepository.deleteAll();
            globalWordFrequencyRepository.saveAll(dbEntires);
        });
       
    }
}
