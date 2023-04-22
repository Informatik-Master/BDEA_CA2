package de.hs_mannheim.informatik.lambda.respository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.hs_mannheim.informatik.lambda.entities.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>{
    
}