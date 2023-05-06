package de.hs_mannheim.informatik.lambda.respository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.hs_mannheim.informatik.lambda.entities.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>{
    public Optional<Document> findFirstBySourceOrderByIdDesc(Document source);

    public Optional<Document> findFirstByDocumentTypeOrderByIdDesc(Document.DocumentType type);

    public List<Document> findByDocumentTypeOrderByIdDesc(Document.DocumentType type);

}
