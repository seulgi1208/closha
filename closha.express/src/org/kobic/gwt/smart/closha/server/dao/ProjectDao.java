package org.kobic.gwt.smart.closha.server.dao;

import java.util.List;

import org.kobic.gwt.smart.closha.shared.model.project.ProjectModel;

public interface ProjectDao {

	Object makeNewProject(ProjectModel projectModel);

	List<ProjectModel> getProjectList(String userID);
	
	ProjectModel getProject(String projectID);

	boolean existUserProject(String userID, String projectName);
	
	int deleteUserProject(String projectID);
}
