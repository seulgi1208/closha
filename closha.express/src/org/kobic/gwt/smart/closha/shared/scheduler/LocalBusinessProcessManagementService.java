package org.kobic.gwt.smart.closha.shared.scheduler;

import java.util.Collection;
import java.util.List;

import org.kobic.gwt.smart.closha.shared.model.design.XmlConnectLinkedModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;

public interface LocalBusinessProcessManagementService {
	void activityAction(List<String> targetIDs);

	void terminate();
	
	List<String> init();
	
	List<String> executeStep(Collection<XmlConnectLinkedModel> connections);
	
	List<String> executeStep(List<String> targetIDs);
	
	void addSourceID(String targetID);
	
	List<String> getSoruceID();
	
	void clearSourceID();
	
	boolean checkUp(String key);
	
	void jobStart(String stepKey);
	
	void gwtStateDataEventService(String state, List<XmlModuleModel> xmlModulesModelList);
	
	XmlDispatchModel getCompleteModuleList(XmlDispatchModel xm, List<String> targetIDs);
	
	XmlDispatchModel getRunningModuleList(XmlDispatchModel xm, List<String> targetIDs);
	
	XmlDispatchModel terminateModuleList(XmlDispatchModel xm);
	
	XmlDispatchModel getXMLDispatchModel();
	
	boolean checkUp(List<String> keys);
}
