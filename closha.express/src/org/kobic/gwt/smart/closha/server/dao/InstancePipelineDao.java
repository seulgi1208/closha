package org.kobic.gwt.smart.closha.server.dao;

import java.util.List;

import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineExeModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;

public interface InstancePipelineDao {

	Object newInstancePipeline(InstancePipelineModel instancePipelineModel);
	
	Object newInstancePipelineJob(InstancePipelineExeModel instancePipelineJobModel);

	InstancePipelineModel getInstancePipelineFromProject(String instanceID);
	
	List<InstancePipelineModel> getOwnerInstancePipelines(String instanceOwner);
	
	List<InstancePipelineModel> getOwnerInstancePipelines(String instanceOwner, int status);
	
	int getInstancePipelineState(String instanceID);
	
	int getInstancePipelineExecuteCount(String instanceID);
	
	int updateInstancePipelineExecuteCount(String instanceID);
	
	int updateInstancePipelineXML(String instanceOwner, String instanceID,
			String instanceName, String instanceXML);
	
	int updateInstancePielineState(String instanceOwner, String instanceID,
			String instanceName, int state);
	
	int updateInstancePipelineRegister(String instanceID, boolean register);
	
	int updateInstancePielineState(String instanceOwner, String instanceID,
			String exeID, String instanceName, int state);
	
	int updateInstancePipelineEndDate(String instanceID, String exeID, String endDate);
	
	int deleteInstancePipeline(String instanceID);
	
	int deleteInstancePipelineJob(String instanceID);
	
	String getInstancePipelineXML(String instanceID);
	
	List<InstancePipelineExeModel> getInstancePielineJobHistory(String instanceID);
	
	void deleteInstancePipelineExecuteHistory(String instanceID, List<String> executeIDs);
	
	boolean isInstancePipelineExist(String userID, String instancePipeline);

}
