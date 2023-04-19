package de.hs_mannheim.informatik.lambda.services;

import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SparkService {
    
    @Autowired
    private SparkSession sparkSession;
}
