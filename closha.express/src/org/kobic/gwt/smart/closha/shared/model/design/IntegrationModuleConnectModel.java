package org.kobic.gwt.smart.closha.shared.model.design;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class IntegrationModuleConnectModel implements IsSerializable{
	
	private List<ModuleConnectionInfomModel> moduleLinksModel;
	private List<ModuleInfomModel> moduleFunctionModel;
	
	public List<ModuleConnectionInfomModel> getModuleLinksModel() {
		return moduleLinksModel;
	}
	public void setModuleLinksModel(List<ModuleConnectionInfomModel> moduleLinksModel) {
		this.moduleLinksModel = moduleLinksModel;
	}
	public List<ModuleInfomModel> getModuleFunctionModel() {
		return moduleFunctionModel;
	}
	public void setModuleFunctionModel(List<ModuleInfomModel> moduleFunctionModel) {
		this.moduleFunctionModel = moduleFunctionModel;
	}
}
