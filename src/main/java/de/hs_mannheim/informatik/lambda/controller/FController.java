package de.hs_mannheim.informatik.lambda.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.hs_mannheim.informatik.lambda.entities.Document;
import de.hs_mannheim.informatik.lambda.respository.DocumentRepository;
import de.hs_mannheim.informatik.lambda.services.DocumentFrequencyService;
import de.hs_mannheim.informatik.lambda.services.WordCloudService;
import scala.Tuple2;

@CrossOrigin(origins = { "http://localhost:3000"})
@RestController()
@RequestMapping("f")
public class FController {

    private final Path SOURCE_DIRECTORY;
    private final Path WORD_CLOUD_DIRECTORY;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    SparkSession sparkSession;

    @Autowired
    WordCloudService wordCloudService;

    @Autowired
    DocumentFrequencyService documentFrequencyService;

    ExecutorService executor = Executors.newFixedThreadPool(10);

    public FController() throws IOException {
        SOURCE_DIRECTORY = Files.createTempDirectory("SOURCE");
        SOURCE_DIRECTORY.toFile().deleteOnExit();

        WORD_CLOUD_DIRECTORY = Files.createTempDirectory("WORD_CLOUD");
        WORD_CLOUD_DIRECTORY.toFile().deleteOnExit();
    }

    // upload a text file and write it to the filesystem
    // then generate a Tag cloud per file (TF-IDF = Term Frequency)
    // Store the image file and show it in the frontend (not directly but select it)
    // remove stopwords
    @PostMapping("/upload")
    public long uploadAndCreateTermImage(@RequestParam MultipartFile uploadMultipart) throws IOException {
        var output = SOURCE_DIRECTORY.resolve(uploadMultipart.getOriginalFilename());
        uploadMultipart.transferTo(output);

        this.executor.submit(() -> {
            var target = WORD_CLOUD_DIRECTORY.resolve(uploadMultipart.getOriginalFilename()).toString() + ".PNG";

            var wordCloud = wordCloudService.generateWordCloud(output.toString());

            wordCloud.writeToFile(target);

            var document = new Document();
            document.setFilename(target);
            documentRepository.save(document);
        });

        var source = new Document();
        source.setFilename(output.toString());
        return documentRepository.save(source).getId();
    }

    @PostMapping("batch/all")
    public void batchCreateAWordCloudFromAllDocuments() {

        this.executor.submit(() -> {
            var target = WORD_CLOUD_DIRECTORY.resolve("WHOLE.PNG").toString();
            var wordCloud = wordCloudService.generateWordCloud(SOURCE_DIRECTORY.toString());

            wordCloud.writeToFile(target);

            var document = new Document();
            document.setFilename(target);
            documentRepository.save(document);
        });
    }

    // TODO: store requence un a db
    @PostMapping("batch/df")
    public List<Tuple2<String, Long>> batchCreateDokumentFrequence() {
        return documentFrequencyService.generateDocumentFrequency(SOURCE_DIRECTORY.toString());
    }

    @GetMapping("/doc")
    public List<Document> doc() {
        return documentRepository.findAll();
    }
}
