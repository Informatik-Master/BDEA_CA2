package de.hs_mannheim.informatik.lambda.controller;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hs_mannheim.informatik.lambda.entities.Document;
import de.hs_mannheim.informatik.lambda.respository.DocumentRepository;

@RestController
public class FController {

    private static final String UPLOAD_DIRECTORY = "./upload";

    @Autowired
    DocumentRepository documentRepository;
    
    public FController() { }

    // upload a text file and write it to the filesystem
    // then generate a Tag cloud per file (TF-IDF = Term Frequency)
    // Store the image file and show it in the frontend (not directly but select it)
    // remove stopwords
    public void uploadAndCreateTermImage() {

    }

    // store requence un a db
    public void batchCreateDokumentFrequence    () {

    }


    @GetMapping("/doc")
    public List<Document> doc() {
        return documentRepository.findAll();
    }
}
