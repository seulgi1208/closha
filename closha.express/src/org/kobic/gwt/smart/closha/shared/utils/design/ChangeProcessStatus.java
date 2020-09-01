package org.kobic.gwt.smart.closha.shared.utils.design;

import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;

public class ChangeProcessStatus {

	public static XmlDispatchModel setModuleStatusComplete(XmlDispatchModel xmlDispatchModel, Integer key){
		
		for(XmlModuleModel xm : xmlDispatchModel.getModulesBeanList()){
			if(Integer.parseInt(xm.getKey()) == key){
				xm.setStatus("Complete");
			}
		}
		
		return xmlDispatchModel;
	}
	
	public static XmlDispatchModel setModuleStatusRunning(XmlDispatchModel xmlDispatchModel, Integer key){
		
		for(XmlModuleModel xm : xmlDispatchModel.getModulesBeanList()){
			if(Integer.parseInt(xm.getKey()) == key){
				xm.setStatus("Running");
			}
		}
		
		return xmlDispatchModel;
	}
	
}
