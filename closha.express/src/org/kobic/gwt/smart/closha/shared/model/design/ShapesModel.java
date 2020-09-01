package org.kobic.gwt.smart.closha.shared.model.design;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ShapesModel implements IsSerializable {
	
	private Object shape;
	private XmlModuleModel xmlModuleModel;
	
	public Object getShape() {
		return shape;
	}
	public void setShape(Object shape) {
		this.shape = shape;
	}
	public XmlModuleModel getXmlModuleModel() {
		return xmlModuleModel;
	}
	public void setXmlModuleModel(XmlModuleModel xmlModuleModel) {
		this.xmlModuleModel = xmlModuleModel;
	}
}
