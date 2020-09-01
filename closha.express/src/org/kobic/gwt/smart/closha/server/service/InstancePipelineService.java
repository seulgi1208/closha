package org.kobic.gwt.smart.closha.server.service;

import java.util.HashMap;
import java.util.List;

import org.kobic.gwt.smart.closha.server.dao.InstancePipelineDao;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineExeModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

public class InstancePipelineService implements InstancePipelineDao{

	private SqlMapClientTemplate sqlMapClientTemplate;
	private HashMap<String, Object> valueMap = new HashMap<String, Object>();

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
	@Override
	public Object newInstancePipeline(
			InstancePipelineModel instancePipelineModel) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.insert("instance.newInstancePipeline", instancePipelineModel);
	}

	@Override
	public InstancePipelineModel getInstancePipelineFromProject(
			String instanceID) {
		// TODO Auto-generated method stub
		valueMap.put("instanceID", instanceID);
		return (InstancePipelineModel) sqlMapClientTemplate.queryForObject("instance.getInstancePipelineFromProject", valueMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstancePipelineModel> getOwnerInstancePipelines(
			String instanceOwner) {
		// TODO Auto-generated method stub
		valueMap.put("instanceOwner", instanceOwner);
		return sqlMapClientTemplate.queryForList("instance.getOwnerInstancePipeline", valueMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InstancePipelineModel> getOwnerInstancePipelines(
			String instanceOwner, int status) {
		// TODO Auto-generated method stub
		valueMap.put("instanceOwner", instanceOwner);
		valueMap.put("status", status);
		return sqlMapClientTemplate.queryForList("instance.getOwnerInstancePipelineWithStatus", valueMap);
	}

	@Override
	public int updateInstancePipelineXML(String instanceOwner, String instanceID,
			String instanceName, String instanceXML) {
		// TODO Auto-generated method stub
		valueMap.put("instanceOwner", instanceOwner);
		valueMap.put("instanceID", instanceID);
		valueMap.put("instanceName", instanceName);
		valueMap.put("instanceXML", instanceXML);
		return sqlMapClientTemplate.update("instance.updateInstancePipelineXML", valueMap);
	}
	
	@Override
	public int deleteInstancePipeline(String instanceID) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.delete("instance.delteInstancePipeline", instanceID);
	}

	@Override
	public int deleteInstancePipelineJob(String instanceID) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.delete("instance.delteInstancePipelineJob", instanceID);
	}

	@Override
	public int updateInstancePielineState(String instanceOwner,
			String instanceID, String instanceName, int state) {
		// TODO Auto-generated method stub
		valueMap.put("instanceOwner", instanceOwner);
		valueMap.put("instanceID", instanceID);
		valueMap.put("instanceName", instanceName);
		valueMap.put("status", state);
		return sqlMapClientTemplate.update("instance.updateInstancePipelineStatus", valueMap);
	}
	
	@Override
	public int updateInstancePielineState(String instanceOwner,
			String instanceID, String exeID, String instanceName, int state) {
		// TODO Auto-generated method stub
		valueMap.put("instanceOwner", instanceOwner);
		valueMap.put("instanceID", instanceID);
		valueMap.put("exeID", exeID);
		valueMap.put("instanceName", instanceName);
		valueMap.put("status", state);
		return sqlMapClientTemplate.update("instance.updateInstancePipelineStatusWithExeID", valueMap);
	}

	@Override
	public int updateInstancePipelineRegister(String instanceID, boolean register) {
		// TODO Auto-generated method stub
		valueMap.put("register", register);
		return sqlMapClientTemplate.update("instance.updateInstancePipelineRegister", valueMap);
	}

	@Override
	public int getInstancePipelineExecuteCount(String instanceID) {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClientTemplate.queryForObject("instance.getInstancePipelineExecuteCount", instanceID);
	}

	@Override
	public int updateInstancePipelineExecuteCount(String instanceID) {
		// TODO Auto-generated method stub
		int exeCount = getInstancePipelineExecuteCount(instanceID) + 1;
		valueMap.put("instanceID", instanceID);
		valueMap.put("exeCount", exeCount);
		return sqlMapClientTemplate.update("instance.updateInstancePipelineExecuteCount", valueMap);	
	}

	@Override
	public int getInstancePipelineState(String instanceID) {
		// TODO Auto-generated method stub
		return (Integer)sqlMapClientTemplate.queryForObject("instance.getInstancePipelineState", instanceID);
	}

	@Override
	public Object newInstancePipelineJob(
			InstancePipelineExeModel instancePipelineJobModel) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.insert("instance.newInstancePipelineJob", instancePipelineJobModel);
	}

	@Override
	public int updateInstancePipelineEndDate(String instanceID, String exeID,
			String endDate) {
		// TODO Auto-generated method stub
		valueMap.put("instanceID", instanceID);
		valueMap.put("exeID", exeID);
		valueMap.put("endDate", endDate);
		return sqlMapClientTemplate.update("instance.updateInstancePipelineEndDate", valueMap);
	}

	@Override
	public String getInstancePipelineXML(String instanceID){
		// TODO Auto-generated method stub
		return (String) sqlMapClientTemplate.queryForObject("instance.getInstancePipelineXMLData", instanceID);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InstancePipelineExeModel> getInstancePielineJobHistory(
			String instanceID) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("instance.getInstancePipelineJob", instanceID);
	}

	@Override
	public void deleteInstancePipelineExecuteHistory(String instanceID,
			List<String> executeIDs) {
		// TODO Auto-generated method stub
		for(String executeID : executeIDs){
			valueMap.put("instanceID", instanceID);
			valueMap.put("exeID", executeID);
			sqlMapClientTemplate.delete("instance.delteInstancePipelineExecuteHistory", valueMap);
		}
	}

	@Override
	public boolean isInstancePipelineExist(String userID, String instancePipeline) {
		// TODO Auto-generated method stub
		valueMap.put("instanceOwner", userID);
		valueMap.put("instanceName", instancePipeline);
		
		InstancePipelineModel iModel = (InstancePipelineModel) sqlMapClientTemplate.queryForObject("instance.isInstancePipelineExist", valueMap);
		
		if(iModel == null){
			return true;
		}else{
			return false;
		}
	}
}