package com.douzone.mysite.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Spring-Servlet bean설정들
@Configuration
@PropertySource("classpath:com/douzone/mysite/web/fileupload.properties")
public class FileuploadConfig implements WebMvcConfigurer {
	@Autowired
	private Environment env;
	
	// Multipart Resolver
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartresolver = new CommonsMultipartResolver();
		multipartresolver.setMaxUploadSize(env.getProperty("fileupload.maxUploadSize", Long.class));
		multipartresolver.setMaxInMemorySize(env.getProperty("fileupload.maxInMemorySize", Integer.class));
		multipartresolver.setDefaultEncoding(env.getProperty("fileupload.defaultEncoding"));
		
		return multipartresolver;
	}

	// MVC url-resource mapping
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler(env.getProperty("fileupload.resourceUrl") + "/**")
			.addResourceLocations("file:" + env.getProperty("fileupload.uploadLocation") + "/");
	}
	
}
