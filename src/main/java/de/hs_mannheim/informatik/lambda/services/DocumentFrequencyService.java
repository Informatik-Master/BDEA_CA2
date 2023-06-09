package de.hs_mannheim.informatik.lambda.services;

import java.util.List;

import scala.Tuple2;
import java.util.Arrays;
import java.util.stream.StreamSupport;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentFrequencyService {

    @Autowired
    SparkSession sparkSession;

    @Autowired
    StopwordService stopwordService;

    public List<Tuple2<String, Long>> generateDocumentFrequency(final String path) {

        var clonedWords = stopwordService.getStopwords();

        var r = sparkSession.sparkContext().wholeTextFiles(path, 1)
                .toJavaRDD()
                .flatMap((FlatMapFunction<Tuple2<String, String>, Tuple2<String, String>>) file -> Arrays
                        .asList(file._2.split("\\W+")).stream().map(v -> new Tuple2<String, String>(v, file._1))
                        .iterator())
                .map(v -> new Tuple2<String, String>(v._1.trim(), v._2.trim()))
                .filter(v -> v._1.length() > 3)
                .map(v -> new Tuple2<String, String>(v._1.toUpperCase(), v._2))
                .filter(v -> !clonedWords.contains(v._1))
                .distinct()
                .groupBy(f -> f._1)
                .map(f -> new Tuple2<String, Long>(f._1, StreamSupport.stream(f._2.spliterator(), false).count()))
                .collect();
        return r;
    }
}
