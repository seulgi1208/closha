package org.kobic.gwt.smart.closha.server.handler;

import org.kobic.gwt.smart.closha.server.service.InstancePipelineService;
import org.springframework.context.ApplicationContext;

public class InstancePipelineServiceHandler {
	
	private static String INSTANCE_PIPELINE_SERVICE = "instanceService";
	
	public static InstancePipelineService getService(){
		ApplicationContext factory = CloshaApplicationContextService.getApplicationContext();
		InstancePipelineService service = factory.getBean(INSTANCE_PIPELINE_SERVICE, InstancePipelineService.class);
		return service;
	}
	
}
