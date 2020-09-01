package org.kobic.gwt.smart.closha.server.service;

import java.util.HashMap;
import java.util.List;

import org.kobic.gwt.smart.closha.server.dao.RegisterPipelineDao;
import org.kobic.gwt.smart.closha.shared.model.module.register.RegisterModuleModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.PipelineModel;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

public class RegisterPipelineService implements RegisterPipelineDao {
	
	private SqlMapClientTemplate sqlMapClientTemplate;
	private HashMap<String, Object> valueMap = new HashMap<String, Object>();

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Object registerPipeline(PipelineModel registerPipelineModel) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.insert("register.registerPipeline", registerPipelineModel);
	}

	@Override
	public Object registerPipelineModules(
			RegisterModuleModel registerPipelineModulesModel) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.insert("register.registerModules", registerPipelineModulesModel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PipelineModel> getRegisterPipelinesList() {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("register.getRegisterPipelines");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PipelineModel> getSelectRegisterPipelinesList(int type) {
		// TODO Auto-generated method stub
		valueMap.put("type", type);
		return sqlMapClientTemplate.queryForList("register.getSelectRegisterPipelines", valueMap);
	}

	@Override
	public PipelineModel getRegisterPipelineXML(String pipelineID) {
		// TODO Auto-generated method stub
		valueMap.put("pipelineID", pipelineID);
		return (PipelineModel) sqlMapClientTemplate.queryForObject("register.getRegisterPipelinesXML", valueMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterModuleModel> getRegisterPipelineModuleList() {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("register.getRegisterPipelineModules");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterModuleModel> getSelectRegisterPipelineModuleList(String userID) {
		// TODO Auto-generated method stub
		valueMap.put("userID", userID);
		return sqlMapClientTemplate.queryForList("register.getSelectRegisterPipelineModules", valueMap);
	}

	@Override
	public void deleteRegisterPipeline(String registerID) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.delete("register.deleteRegisterPipeline", registerID);
	}

	@Override
	public void deleteRegisterPipelineModule(String moduleID) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.delete("register.deleteRegisterPipelineModule", moduleID);
	}

	@Override
	public void updateUserModuleCheck(String moduleID, boolean isCheck) {
		// TODO Auto-generated method stub
		valueMap.put("module_id", moduleID);
		valueMap.put("is_check", isCheck);
		sqlMapClientTemplate.delete("register.updateUserModuleCheck", valueMap);
	}
}
