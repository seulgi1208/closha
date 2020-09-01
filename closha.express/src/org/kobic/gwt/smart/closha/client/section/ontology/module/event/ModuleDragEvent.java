package org.kobic.gwt.smart.closha.client.section.ontology.module.event;

import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;

import com.google.gwt.event.shared.GwtEvent;

public class ModuleDragEvent extends GwtEvent<ModuleEventHandler>{

	public static Type<ModuleEventHandler> TYPE = new Type<ModuleEventHandler>(); 
	
	private XmlModuleModel xmlModuleModel;
	
	public XmlModuleModel getModuleModel(){
		return xmlModuleModel;
	}
	
	public ModuleDragEvent(XmlModuleModel xmlModuleModel){
		this.xmlModuleModel = xmlModuleModel;
	}

	@Override
	public Type<ModuleEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ModuleEventHandler handler) {
		// TODO Auto-generated method stub
		handler.closhaModuleDragEvent(this);
	}

}
