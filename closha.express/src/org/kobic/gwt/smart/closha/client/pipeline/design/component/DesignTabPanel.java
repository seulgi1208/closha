package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import java.util.Map;

import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;

public class DesignTabPanel {
	
	private DiagramController currentController;
	private WindowController windowController;
	
	public DesignTabPanel(Canvas pane, HandlerManager eventBus, 
			int width, int height, InstancePipelineModel iModel, Map<String, String> config, Window progressWindow){
	
		currentController = new DiagramController(width, height);
		currentController.showGrid(false);
		currentController.getContextMenu().setAnimationEnabled(false);
		currentController.getContextMenu().setVisible(false);
		
		windowController = new WindowController(currentController.getView());
		
		PipelineDesignWidget application = new PipelineDesingCanvasWidget();
		application.setDiagramController(currentController);
		application.setEventBus(eventBus);
		application.setWindowController(windowController);
		application.setHeight(height);
		application.setWidth(width);
		application.setInstancePipelineModel(iModel);
		application.setLeft(pane.getAbsoluteLeft());
		application.setTop(pane.getAbsoluteTop());
		application.setConfig(config);
		
		Widget widget = application.asWidget();
		widget.getElement().getStyle().setZIndex(10);
		pane.addChild(widget);
		
		application.init();
		
		progressWindow.close();
		progressWindow.destroy();
		
	}	
}
