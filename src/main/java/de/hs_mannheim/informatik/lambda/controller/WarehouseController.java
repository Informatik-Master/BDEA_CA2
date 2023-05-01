package de.hs_mannheim.informatik.lambda.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hs_mannheim.informatik.lambda.entities.Document;
import de.hs_mannheim.informatik.lambda.entities.GlobalWordFrequency;
import de.hs_mannheim.informatik.lambda.respository.DocumentRepository;
import de.hs_mannheim.informatik.lambda.respository.GlobalWordFrequencyRepository;

@RestController()
@RequestMapping("api/warehouse")
public class WarehouseController {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    GlobalWordFrequencyRepository globalWordFrequencyRepository;

    @GetMapping("/documents")
    public List<Document> doc() {
        return documentRepository.findAll();
    }

    @GetMapping("/globalWordFrequency")
    public List<GlobalWordFrequency> globalWordFrequency() {
        return globalWordFrequencyRepository.findAll();
    }

    @GetMapping("/globalWordFrequency/{word}")
    public GlobalWordFrequency globalWordFrequency(@PathVariable String word) {
        return globalWordFrequencyRepository.findOneByWord(word);
    }

    @GetMapping("/documents/{id}")
    public void doc(HttpServletResponse response, @PathVariable Long id) throws IOException {
        var document = documentRepository.findById(id);
        if (document.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        var fileStream = new FileInputStream(document.get().getWarehouseFilename());
        fileStream.transferTo(response.getOutputStream());
        response.addHeader(HttpHeaders.CONTENT_TYPE, document.get().getContentType());

        var contentDisposition = ContentDisposition.builder("inline")
                .filename(document.get().getFilename())
                .build();

        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
    }

}
