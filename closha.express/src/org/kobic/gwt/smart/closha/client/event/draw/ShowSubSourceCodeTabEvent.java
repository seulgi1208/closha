package org.kobic.gwt.smart.closha.client.event.draw;

import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;

import com.google.gwt.event.shared.GwtEvent;

public class ShowSubSourceCodeTabEvent extends GwtEvent<ShowSubSourceCodeTabEventHandler>{

	public static Type<ShowSubSourceCodeTabEventHandler> TYPE = new Type<ShowSubSourceCodeTabEventHandler>();
	
	private String projectName;
	private XmlModuleModel xmlModuleModel;
	
	
	public String getProjectName() {
		return projectName;
	}
	
	public XmlModuleModel getXmlModuleModel() {
		return xmlModuleModel;
	}

	public ShowSubSourceCodeTabEvent(XmlModuleModel xmlModuleModel, String projectName){
		this.xmlModuleModel = xmlModuleModel;
		this.projectName = projectName;
	}

	@Override
	public Type<ShowSubSourceCodeTabEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ShowSubSourceCodeTabEventHandler handler) {
		// TODO Auto-generated method stub
		handler.showSubSourceCodeTab(this);
	}

}
