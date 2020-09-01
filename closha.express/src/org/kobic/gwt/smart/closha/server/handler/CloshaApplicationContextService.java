package org.kobic.gwt.smart.closha.server.handler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CloshaApplicationContextService {
	
	public static ApplicationContext getApplicationContext(){
		ApplicationContext factory = new ClassPathXmlApplicationContext("/conf/applicationContext.xml");
		return factory;
	}
	
}
