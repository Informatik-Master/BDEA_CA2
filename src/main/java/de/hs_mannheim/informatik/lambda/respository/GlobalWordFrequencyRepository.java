package de.hs_mannheim.informatik.lambda.respository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.hs_mannheim.informatik.lambda.entities.GlobalWordFrequency;

@Repository
public interface GlobalWordFrequencyRepository extends JpaRepository<GlobalWordFrequency, Long>{
    
    public Optional<GlobalWordFrequency> findOneByWord(String word);
}
