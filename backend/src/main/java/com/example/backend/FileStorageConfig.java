package com.example.backend;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileStorageConfig implements WebMvcConfigurer{

    private String uploadPath = Paths
                .get("backend/uploads")
                .toAbsolutePath()
                .normalize()
                .toString();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("Upload path: " + uploadPath);
        registry
            .addResourceHandler("/images/**")
            .addResourceLocations("file:" + uploadPath + "/");

        /*
        to protect folders from access
        */
        registry
            .addResourceHandler("/images/products/**")
            .addResourceLocations("file:" + uploadPath + "/products/");

        registry
            .addResourceHandler("/images/users/**")
            .addResourceLocations("file:" + uploadPath + "/users/");

        registry
            .addResourceHandler("/images/categories/**")
            .addResourceLocations("file:" + uploadPath + "/categories/");
    }
}
