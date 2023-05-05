package de.hs_mannheim.informatik.lambda.respository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.hs_mannheim.informatik.lambda.entities.Document;


// TODO: find only latest

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>{
    public Optional<Document> findOneBySource(Document source);

    public Optional<Document> findOneByDocumentType(Document.DocumentType type);

    public List<Document> findByDocumentType(Document.DocumentType type);
}
