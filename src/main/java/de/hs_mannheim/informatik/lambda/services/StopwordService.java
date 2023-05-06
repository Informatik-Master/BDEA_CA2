package de.hs_mannheim.informatik.lambda.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class StopwordService {

    private Set<String> stopwords = new HashSet<>();

    public StopwordService() {
        try {
            var inputStream = getClass().getClassLoader().getResourceAsStream("stopwords.txt");
            var reader = new BufferedReader(new InputStreamReader(inputStream));
            while (reader.ready())
                stopwords.add(reader.readLine().trim().toUpperCase());
            reader.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<String> getStopwords() {
        var clonedWords = new HashSet<String>(); // A clone is needed as otherwise this instance would be needed to be
                                                 // serialized
        clonedWords.addAll(stopwords);
        return clonedWords;
    }
}
