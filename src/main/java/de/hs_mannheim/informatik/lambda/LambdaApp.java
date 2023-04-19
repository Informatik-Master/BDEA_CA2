package de.hs_mannheim.informatik.lambda;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LambdaApp {

	public static void main(String[] args) {
		SpringApplication.run(LambdaApp.class, args);
		
		System.out.println("Ausführungsort: " + new File(".").getAbsolutePath());
	}


}
