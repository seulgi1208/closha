package org.kobic.gwt.smart.closha.client.file.browser.component;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.file.browser.presenter.FileBrowserPresenter;
import org.kobic.gwt.smart.closha.client.file.browser.viewer.FileBrowserViewer;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class FileBrowserTabPanel extends Tab{
	
	private Presenter fileBrowserListGridPresenter;
	
	public FileBrowserTabPanel(HandlerManager evnetBus, Map<String, String> config, UserDto userDto){
		
		setID(Constants.FILE_WINDOW_ID);
		setTitle(Constants.FILE_WINDOW_TITLE);
		setIcon("closha/icon/folder_magnify.png");
		setWidth(200);
		
		HLayout hdsfLayout = new HLayout();
		hdsfLayout.setBorder("solid 1px #DCDCDC");
		hdsfLayout.setHeight100();
		hdsfLayout.setWidth100();
		hdsfLayout.setAlign(Alignment.CENTER);
		
		fileBrowserListGridPresenter = new FileBrowserPresenter(evnetBus, config, userDto, new FileBrowserViewer());
		fileBrowserListGridPresenter.go(hdsfLayout);
		
		setPane(hdsfLayout);
	}
}
