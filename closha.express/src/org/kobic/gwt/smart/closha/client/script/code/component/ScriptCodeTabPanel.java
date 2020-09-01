package org.kobic.gwt.smart.closha.client.script.code.component;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.script.code.presenter.ScriptCodePresenter;
import org.kobic.gwt.smart.closha.client.script.code.viewer.ScriptCodeViewer;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class ScriptCodeTabPanel extends Tab{
	
	private Presenter sourceCodePresenter;
	
	public ScriptCodeTabPanel(HandlerManager eventBus, Map<String, String> config,
			String projectName, XmlModuleModel xmlModuleModel) {
		
		setID(Constants.SOURCE_CODE_VIEWER_WINDOW_ID);
		setTitle(Constants.SOURCE_CODE_VIEWER_WINDOW_TITLE);
		setIcon("silk/page_white_code_red.png");
		setWidth(200);
		
		HLayout layout = new HLayout();
		layout.setBorder("solid 1px #DCDCDC");
		layout.setHeight100();
		layout.setWidth100();
		layout.setAlign(Alignment.CENTER);
		
		sourceCodePresenter = new ScriptCodePresenter(eventBus, config,
				projectName, xmlModuleModel, new ScriptCodeViewer());
		sourceCodePresenter.go(layout);
		
		setPane(layout);
		
	}

}
