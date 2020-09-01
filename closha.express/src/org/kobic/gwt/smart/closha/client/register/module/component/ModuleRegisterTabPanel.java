package org.kobic.gwt.smart.closha.client.register.module.component;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.register.module.presenter.ModuleRegisterPresenter;
import org.kobic.gwt.smart.closha.client.register.module.viewer.ModuleRegisterViewer;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class ModuleRegisterTabPanel extends Tab{
	
	private Presenter registrationPresenter;
	
	public ModuleRegisterTabPanel(HandlerManager eventBus, Map<String, String> config, UserDto userDto){
		
		setID(Constants.REGISTRATION_WINDOW_ID);
		setTitle(Constants.REGISTRATION_WINDOW_TITLE);
		setIcon("silk/application_form_add.gif");
		setWidth(200);
		
		HLayout layout = new HLayout();
		layout.setBorder("solid 1px #DCDCDC");
		layout.setHeight100();
		layout.setWidth100();
		layout.setAlign(Alignment.CENTER);
		
		registrationPresenter = new ModuleRegisterPresenter(eventBus, config, userDto, new ModuleRegisterViewer());
		registrationPresenter.go(layout);
		
		setPane(layout);
	}
}
