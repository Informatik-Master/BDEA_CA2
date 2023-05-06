package de.hs_mannheim.informatik.lambda.services;

import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.palette.ColorPalette;

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

        @Autowired
        StopwordService stopwordService;

        public List<WordFrequency> getTf(final String source) {

                var clonedWords = stopwordService.getStopwords();

                var wordFrequenciesTup = sparkSession.read().textFile(source)
                                .javaRDD().flatMap(
                                                s -> Arrays.asList(s.split("\\W+")).iterator())
                                .map(String::trim)
                                .filter(v -> v.length() > 3)
                                .map(String::toUpperCase)
                                .filter(v -> !clonedWords.contains(v))
                                .mapToPair(
                                                token -> new Tuple2<>(token, 1))
                                .reduceByKey((x, y) -> x + y)
                                .collect();// normalizer

                return wordFrequenciesTup.stream().map(t -> new WordFrequency(t._1, t._2))
                                .collect(Collectors.toList());
        }

        public WordCloud generateWordCloud(final List<WordFrequency> tf) {
                final var dimension = new Dimension(1200, 1200);
                final var wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
                wordCloud.setPadding(2);
                wordCloud.setBackground(new RectangleBackground(dimension));
                wordCloud.setBackgroundColor(Color.WHITE);
                wordCloud.setColorPalette(
                                new ColorPalette(new Color(0x0C1821), new Color(0x1B2A41), new Color(0x0D6EFD),
                                                new Color(0x228FA8), new Color(0x0B7A75)));
                wordCloud.setFontScalar(new SqrtFontScalar(8, 75));
                wordCloud.build(tf);
                return wordCloud;
        }
}
