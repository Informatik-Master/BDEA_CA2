package de.hs_mannheim.informatik.lambda.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import de.hs_mannheim.informatik.lambda.entities.Document;
import de.hs_mannheim.informatik.lambda.entities.GlobalWordFrequency;
import de.hs_mannheim.informatik.lambda.respository.DocumentRepository;
import de.hs_mannheim.informatik.lambda.respository.GlobalWordFrequencyRepository;
import de.hs_mannheim.informatik.lambda.respository.TermFrequencyRepository;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController()
@RequestMapping("api/warehouse")
public class WarehouseController {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    GlobalWordFrequencyRepository globalWordFrequencyRepository;

    @Autowired
    TermFrequencyRepository termFrequencyRepository;

    @GetMapping("/documents")
    public List<Document> doc() {
        return documentRepository.findAll();
    }

    @GetMapping("/globalWordFrequency")
    public List<GlobalWordFrequency> globalWordFrequency() {
        return globalWordFrequencyRepository.findAll();
    }

    @GetMapping("/globalWordFrequency/{word}")
    public Optional<GlobalWordFrequency> globalWordFrequency(@PathVariable String word) {
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

    @GetMapping("/documents/overall_target")
    public void overallTarget(HttpServletResponse response) throws IOException {
        var document = documentRepository.findFirstByDocumentTypeOrderByIdDesc(Document.DocumentType.OVERALL_TARGET);
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

    @GetMapping("/documents/{id}/wordcloud")
    public void wordcloud(HttpServletResponse response, @PathVariable Long id) throws IOException {
        var document = documentRepository.findById(id);
        if (document.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        var target = documentRepository.findFirstBySourceOrderByIdDesc(document.get());
        if (target.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        var fileStream = new FileInputStream(target.get().getWarehouseFilename());
        fileStream.transferTo(response.getOutputStream());
        response.addHeader(HttpHeaders.CONTENT_TYPE, document.get().getContentType());

        var contentDisposition = ContentDisposition.builder("inline")
                .filename(document.get().getFilename())
                .build();

        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
    }

    @GetMapping("/idf/{word}")
    public Double idf(HttpServletResponse response, @PathVariable String word) throws IOException {
        var document = documentRepository.findByDocumentTypeOrderByIdDesc(Document.DocumentType.SOURCE);
        if (document.isEmpty()) {
            return 1D;
        }

        var dbEntry = globalWordFrequencyRepository.findOneByWord(word);
        var amountOfDocs = 0L;
        if (!dbEntry.isEmpty()) {
            amountOfDocs = dbEntry.get().getFrequency();
        }

        var idf = Math.log(document.size() / (double) amountOfDocs);
        return idf;
    }

    @GetMapping("/documents/{id}/tfidf/{word}")
    public Double tfidf(HttpServletResponse response, @PathVariable String id, @PathVariable String word)
            throws IOException {
        var allDocuments = documentRepository.findByDocumentTypeOrderByIdDesc(Document.DocumentType.SOURCE);
        if (allDocuments.isEmpty()) {
            return 1D;
        }

        var dbEntry = globalWordFrequencyRepository.findOneByWord(word);
        var amountOfDocs = 0L;
        if (!dbEntry.isEmpty()) {
            amountOfDocs = dbEntry.get().getFrequency();
        }

        var idf = Math.log(allDocuments.size() / (double) amountOfDocs);

        var document = documentRepository.findById(Long.parseLong(id));
        var tf = 1D;
        if (!document.isEmpty()) {
            var f = termFrequencyRepository.findOneByWordAndSource(word, document.get());
            if (!f.isEmpty()) {
                tf = f.get().getFrequency();
            }
        }
        return tf * idf;
    }

}
