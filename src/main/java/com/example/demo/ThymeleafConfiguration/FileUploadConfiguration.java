package com.example.demo.ThymeleafConfiguration;

import jakarta.servlet.MultipartConfigElement;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class FileUploadConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadConfiguration.class);

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();

        DataSize maxFileSize = DataSize.ofGigabytes(3);
        factory.setMaxFileSize(maxFileSize);
        logger.info("Maximum file size set to: {}");

        DataSize maxRequestSize = DataSize.ofGigabytes(3);
        factory.setMaxRequestSize(maxRequestSize);
        logger.info("Maximum request size set to: {}");

        return factory.createMultipartConfig();
    }

}



