package org.kobic.gwt.smart.closha.shared.utils;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringContextHelper {
	
	public static ApplicationContext getContext( ServletContext servletContext){
		return WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	}
	
	public static Object getBean(ServletContext servletContext, final String beanRef) {
        return getContext(servletContext).getBean(beanRef);
    }   
}
