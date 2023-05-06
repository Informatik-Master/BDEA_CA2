package de.hs_mannheim.informatik.lambda.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.hs_mannheim.informatik.lambda.entities.Document;
import de.hs_mannheim.informatik.lambda.entities.GlobalWordFrequency;
import de.hs_mannheim.informatik.lambda.entities.TermFrequency;

@Repository
public interface TermFrequencyRepository extends JpaRepository<TermFrequency, Long> {

    public Optional<GlobalWordFrequency> findOneByWordAndSource(String word, Document source);
}
