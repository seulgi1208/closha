package org.kobic.gwt.smart.closha.server.dao;

import java.util.List;

import org.kobic.gwt.smart.closha.shared.model.module.register.RegisterModuleModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.PipelineModel;

public interface RegisterPipelineDao {
	
	Object registerPipeline(PipelineModel registerPipelineModel);
	Object registerPipelineModules(RegisterModuleModel registerPipelineModulesModel);
	List<PipelineModel> getRegisterPipelinesList();
	List<PipelineModel> getSelectRegisterPipelinesList(int type);
	List<RegisterModuleModel> getRegisterPipelineModuleList();
	List<RegisterModuleModel> getSelectRegisterPipelineModuleList(String userID);
	PipelineModel getRegisterPipelineXML(String pipelineID);
	void deleteRegisterPipeline(String registerID);
	void deleteRegisterPipelineModule(String moduleID);
	void updateUserModuleCheck(String moduleID, boolean isCheck);
}
