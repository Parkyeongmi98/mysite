package com.douzone.mysite.event;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;

@Component
public class ApplicationContextEventListener {
	@Autowired
	private ApplicationContext applicationContext;
	
	
	@EventListener({ContextRefreshedEvent.class})
	public void handleApplicationContextRefreshedEvent() {
		System.out.println((Object)applicationContext.getClass());
		System.out.println(System.identityHashCode(applicationContext));
		System.out.println("--- Context Refresh Event Received --- : " + applicationContext);
		
		SiteService service = applicationContext.getBean(SiteService.class);
		SiteVo site = service.getSite();
		
		AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry)factory;
		
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("title", site.getTitle());
		propertyValues.add("profile", site.getProfile());
		propertyValues.add("welcome", site.getWelcome());
		propertyValues.add("description", site.getDescription());
		
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(SiteVo.class);
		beanDefinition.setPropertyValues(propertyValues);
		
		registry.registerBeanDefinition("site", beanDefinition);
	}
}
