package org.kobic.gwt.smart.closha.client.event.draw;

import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;

import com.google.gwt.event.shared.GwtEvent;

public class ShowSourceCodeTabEvent extends GwtEvent<ShowSourceCodeTabEventHandler>{

	public static Type<ShowSourceCodeTabEventHandler> TYPE = new Type<ShowSourceCodeTabEventHandler>();
	
	private String projectName;
	private XmlModuleModel xmlModuleModel;
	
	
	public String getProjectName() {
		return projectName;
	}
	
	public XmlModuleModel getXmlModuleModel() {
		return xmlModuleModel;
	}

	public ShowSourceCodeTabEvent(XmlModuleModel xmlModuleModel, String projectName){
		this.xmlModuleModel = xmlModuleModel;
		this.projectName = projectName;
	}

	@Override
	public Type<ShowSourceCodeTabEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ShowSourceCodeTabEventHandler handler) {
		// TODO Auto-generated method stub
		handler.showSourceCodeViewer(this);
	}

}
