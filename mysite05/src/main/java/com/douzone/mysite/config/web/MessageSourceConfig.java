package com.douzone.mysite.config.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

// Spring-Servlet bean설정들
@Configuration
public class MessageSourceConfig {
	
	// MessageSource
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("com/douzone/mysite/web/messages/messages_ko");
		messageSource.setDefaultEncoding("utf-8");
		
		return messageSource;
	}
}
