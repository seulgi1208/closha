package org.kobic.gwt.smart.closha.client.preference.component;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.preference.presenter.PreferencePresenter;
import org.kobic.gwt.smart.closha.client.preference.viewer.PreferenceViewer;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class PreferenceTabPanel extends Tab{
	
	private Presenter userPreferencesPresenter;
	
	public PreferenceTabPanel(HandlerManager evnetBus, Map<String, String> config, UserDto userDto){
	
		setID(Constants.PREFERENCES_WINDOW_ID);
		setTitle(Constants.PREFERENCES_WINDOW_TITLE);
		setIcon("closha/icon/cluster16.gif", 16, 16);
		setWidth(200);
		
		HLayout preferencesLayout = new HLayout();
		preferencesLayout.setBorder("solid 1px #DCDCDC");
		preferencesLayout.setHeight100();
		preferencesLayout.setWidth100();
		preferencesLayout.setAlign(Alignment.CENTER);
		
		userPreferencesPresenter = new PreferencePresenter(evnetBus, config, userDto, new PreferenceViewer());
		userPreferencesPresenter.go(preferencesLayout);
		
		setPane(preferencesLayout);
	}
}