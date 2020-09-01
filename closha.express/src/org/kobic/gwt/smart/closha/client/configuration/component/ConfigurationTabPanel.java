package org.kobic.gwt.smart.closha.client.configuration.component;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.configuration.presenter.ConfigurationPresenter;
import org.kobic.gwt.smart.closha.client.configuration.viewer.ConfigurationViewer;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class ConfigurationTabPanel extends Tab{
	
	private Presenter configurationPresenter;

	public ConfigurationTabPanel(HandlerManager evnetBus, Map<String, String> config, UserDto userDto){
		
		setID(Constants.CONFIGURATION_WINDOW_ID);
		setTitle(Constants.CONFIGURATION_WINDOW_TITLE);
		setIcon("closha/icon/cog.png", 16, 16);
		setWidth(200);
		
		HLayout configurationLayout = new HLayout();
		configurationLayout.setBorder("solid 1px #DCDCDC");
		configurationLayout.setHeight100();
		configurationLayout.setWidth100();
		configurationLayout.setAlign(Alignment.CENTER);
		
		configurationPresenter = new ConfigurationPresenter(evnetBus, config, userDto, new ConfigurationViewer());
		configurationPresenter.go(configurationLayout);
		
		setPane(configurationLayout);
		
	}
}
