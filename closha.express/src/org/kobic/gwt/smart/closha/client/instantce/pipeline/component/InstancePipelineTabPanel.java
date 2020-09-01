package org.kobic.gwt.smart.closha.client.instantce.pipeline.component;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.instantce.pipeline.presenter.InstancePipelinePresenter;
import org.kobic.gwt.smart.closha.client.instantce.pipeline.viewer.InstancePipelineViewer;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class InstancePipelineTabPanel extends Tab{
	
	private Presenter makeProjectPresenter;
	
	public InstancePipelineTabPanel(HandlerManager eventBus, Map<String, String> config, UserDto userDto){
	
		setID(Constants.MAKE_PROJECT_WINDOW_ID);
		setTitle(Constants.MAKE_PROJECT_WINDOW_TITLE);
		setIcon("closha/icon/window_new.png");
		setWidth(200);
		
		HLayout makeProjectLayout = new HLayout();
		makeProjectLayout.setBorder("solid 1px #DCDCDC");
		makeProjectLayout.setHeight100();
		makeProjectLayout.setWidth100();
		makeProjectLayout.setAlign(Alignment.CENTER);
		
		makeProjectPresenter = new InstancePipelinePresenter(eventBus, config, userDto, new InstancePipelineViewer());
		makeProjectPresenter.go(makeProjectLayout);
		
		setPane(makeProjectLayout);
	}

}
	