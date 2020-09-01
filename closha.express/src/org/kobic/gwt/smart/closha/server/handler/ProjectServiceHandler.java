package org.kobic.gwt.smart.closha.server.handler;

import org.kobic.gwt.smart.closha.server.service.ProjectService;
import org.springframework.context.ApplicationContext;

public class ProjectServiceHandler {

private static String PROJECT_SERVICE = "projectService";
	
	public static ProjectService getService(){
		ApplicationContext factory = CloshaApplicationContextService.getApplicationContext();
		ProjectService service = factory.getBean(PROJECT_SERVICE, ProjectService.class);
		return service;
	}
	
}
