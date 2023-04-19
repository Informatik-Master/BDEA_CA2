package de.hs_mannheim.informatik.lambda.web;

import java.io.File;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import de.hs_mannheim.informatik.lambda.controller.LambdaController;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		File path = new File(LambdaController.CLOUD_PATH);
		
		if (!path.exists())
			path.mkdir();
			
		registry.addResourceHandler("/" + LambdaController.CLOUD_PATH + "**").addResourceLocations("file:" + LambdaController.CLOUD_PATH);
	}
	
}