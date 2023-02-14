package com.douzone.mysite.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Spring-Servlet bean설정들
@Configuration
public class FileuploadConfig implements WebMvcConfigurer {
	
	// Multipart Resolver
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartresolver = new CommonsMultipartResolver();
		multipartresolver.setMaxUploadSize(52428800);
		multipartresolver.setMaxInMemorySize(52428800);
		multipartresolver.setDefaultEncoding("utf-8");
		
		return multipartresolver;
	}

	// MVC url-resource mapping
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/assets/upload-images/**")
			.addResourceLocations("file:/mysite-uploads/");
	}
	
}
