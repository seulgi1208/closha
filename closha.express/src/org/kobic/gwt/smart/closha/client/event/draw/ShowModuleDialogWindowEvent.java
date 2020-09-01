package org.kobic.gwt.smart.closha.client.event.draw;

import java.util.List;

import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;

import com.google.gwt.event.shared.GwtEvent;

public class ShowModuleDialogWindowEvent extends GwtEvent<ShowModuleDialogWindowEventHandler>{

	public static Type<ShowModuleDialogWindowEventHandler> TYPE = new Type<ShowModuleDialogWindowEventHandler>();
	
	private int xPoint;
	private int yPoint;
	
	private String projectName;	
	private XmlModuleModel xmlModuleModel;
	private List<XmlParameterModel> parameter;
	
	public int getXPoint(){
		return xPoint;
	}
	
	public int getYPoint(){
		return yPoint;
	}
	
	public String getProjectName(){
		return projectName;
	}
	
	public XmlModuleModel getXMLModuleModel(){
		return xmlModuleModel;
	}
	
	public List<XmlParameterModel> getParameter() {
		return parameter;
	}

	public ShowModuleDialogWindowEvent(int xPoint, int yPoint,
			String projectName, XmlModuleModel xmlModuleModel,
			List<XmlParameterModel> parameter) {
		
		this.xPoint = xPoint;
		this.yPoint = yPoint;
		this.projectName = projectName;
		this.xmlModuleModel = xmlModuleModel;
		this.parameter = parameter;
	}
	
	public ShowModuleDialogWindowEvent(String projectName, 
			XmlModuleModel xmlModuleModel,
			List<XmlParameterModel> parameter) {

		this.projectName = projectName;
		this.xmlModuleModel = xmlModuleModel;
		this.parameter = parameter;
	}
	
	@Override
	public Type<ShowModuleDialogWindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ShowModuleDialogWindowEventHandler handler) {
		// TODO Auto-generated method stub
		handler.showModuleDialogWindow(this);
	}
}