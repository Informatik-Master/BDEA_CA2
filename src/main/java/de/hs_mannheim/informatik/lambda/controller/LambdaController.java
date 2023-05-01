package de.hs_mannheim.informatik.lambda.controller;

import java.io.IOException;
import java.util.UUID;

import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.hs_mannheim.informatik.lambda.entities.Document;
import de.hs_mannheim.informatik.lambda.entities.Document.DocumentType;
import de.hs_mannheim.informatik.lambda.respository.DocumentRepository;
import de.hs_mannheim.informatik.lambda.services.DocumentFrequencyService;
import de.hs_mannheim.informatik.lambda.services.FSService;
import de.hs_mannheim.informatik.lambda.services.WordCloudService;

@RestController()
@RequestMapping("lambda")
public class LambdaController {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    SparkSession sparkSession;

    @Autowired
    WordCloudService wordCloudService;

    @Autowired
    DocumentFrequencyService documentFrequencyService;

    @Autowired
    FSService fsService;

    @PostMapping("/upload")
    public long uploadAndCreateTermImage(@RequestParam MultipartFile uploadMultipart) throws IOException {
        var fullStorageLocationOfSourceFile = fsService.getSourceDirectory().resolve(UUID.randomUUID().toString());
        uploadMultipart.transferTo(fullStorageLocationOfSourceFile);
        var wordCloud = wordCloudService.generateWordCloud(fullStorageLocationOfSourceFile.toString());


        var source = new Document();
        source.setFilename(uploadMultipart.getOriginalFilename());
        source.setContentType(uploadMultipart.getContentType());
        source.setWarehouseFilename(fullStorageLocationOfSourceFile.toString());
        source.setDocumentType(DocumentType.SOURCE);
        var sourceDoc = documentRepository.save(source);


        var targetFileName = UUID.randomUUID().toString() + ".PNG";
        var fullStorageLocationOfTargetFile = fsService.getTargetDirectory()
                .resolve(targetFileName); // The fileending is important as it is used by the
                                                                 // libary to determine the file format
        wordCloud.writeToFile(fullStorageLocationOfTargetFile.toString());

        var document = new Document();
        document.setFilename(targetFileName);
        document.setContentType("image/png");
        document.setWarehouseFilename(fullStorageLocationOfTargetFile.toString());
        document.setDocumentType(DocumentType.TARGET);
        document.setSource(sourceDoc);
        documentRepository.save(document);

        return sourceDoc.getId();
    }
}
