package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import java.util.Map;

import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;

public abstract class PipelineDesignWidget {
	
	protected DiagramController controller;
	protected HandlerManager eventBus;
	protected WindowController windowController;
	protected int height;
	protected int width;
	protected int left;
	protected int top;
	protected String projectName;
	protected InstancePipelineModel iModel;
	protected Map<String, String> config;
	
	public abstract void init();
	public abstract void draw(XmlDispatchModel xmlDispatchBean);
	public abstract Widget asWidget();
	
	public void setInstancePipelineModel(InstancePipelineModel iModel){
		this.iModel = iModel;
		this.projectName = iModel.getInstanceName();
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void setLeft(int left){
		this.left = left;
	}
	
	public void setTop(int top){
		this.top = top;
	}
	
	public void setEventBus(HandlerManager eventBus){
		this.eventBus = eventBus;
	}
	
	public void setDiagramController(DiagramController controller){
		this.controller = controller;
	}

	public void setWindowController(WindowController windowController){
		this.windowController = windowController;
	}
	
	public void setConfig(Map<String, String> config){
		this.config = config;
	}

}
