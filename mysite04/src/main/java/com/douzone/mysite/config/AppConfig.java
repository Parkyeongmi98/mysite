package com.douzone.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.douzone.mysite.config.app.DBConfig;
import com.douzone.mysite.config.app.MyBatisConfig;

// ApplicationContext bean설정들
@Configuration
// auto proxy
@EnableAspectJAutoProxy 
// component-scan
@ComponentScan({"com.douzone.mysite.service","com.douzone.mysite.repository", "com.douzone.mysite.exception", "com.douzone.mysite.aspect"})
// Import 받기
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {
	
}
