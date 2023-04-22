package de.hs_mannheim.informatik.lambda.services;

import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.palette.ColorPalette;

import de.hs_mannheim.informatik.lambda.entities.Document;
import de.hs_mannheim.informatik.lambda.respository.DocumentRepository;

import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;

import java.awt.Color;
import java.awt.Dimension;

import scala.Tuple2;

@Service
public class WordCloudService {

    @Autowired
    SparkSession sparkSession;

    @Autowired
    DocumentRepository documentRepository;

    public WordCloud generateWordCloud(final String source) {
        var wordFrequenciesTup = sparkSession.read().textFile(source)
                .javaRDD().flatMap(
                        s -> Arrays.asList(s.split("\\W+")).iterator())
                .map(String::trim)
                .filter(v -> v.length() > 3)
                .map(String::toUpperCase)
                .mapToPair(
                        token -> new Tuple2<>(token, 1))
                .reduceByKey((x, y) -> x + y)
                .collect();// normalizer
        // TF-IDF

        // TODO: WordFrequency is not serializable
        var wordFrequencies = wordFrequenciesTup.stream().map(t -> new WordFrequency(t._1, t._2))
                .collect(Collectors.toList());

        final var dimension = new Dimension(600, 600);
        final var wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1),
                new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new SqrtFontScalar(8, 50));
        wordCloud.build(wordFrequencies);
        return wordCloud;
    }
}
