package org.kobic.gwt.smart.closha.server.service;

import java.util.HashMap;
import java.util.List;

import org.kobic.gwt.smart.closha.server.dao.ProjectDao;
import org.kobic.gwt.smart.closha.shared.model.project.ProjectModel;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

public class ProjectService implements ProjectDao {

	private SqlMapClientTemplate sqlMapClientTemplate;
	private HashMap<String, Object> valueMap = new HashMap<String, Object>();

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
	@Override
	public Object makeNewProject(ProjectModel projectModel) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.insert("project.makeNewProject", projectModel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectModel> getProjectList(String userID) {
		// TODO Auto-generated method stub
		valueMap.put("projectOwner", userID);
		return sqlMapClientTemplate.queryForList("project.getProjectList", valueMap);
	}
	
	@Override
	public ProjectModel getProject(String projectID) {
		// TODO Auto-generated method stub
		valueMap.put("projectID", projectID);
		return (ProjectModel) sqlMapClientTemplate.queryForObject("project.getProject", valueMap);
	}

	@Override
	public boolean existUserProject(String userID, String projectName) {
		// TODO Auto-generated method stub
		
		valueMap.put("projectOwner", userID);
		valueMap.put("projectName", projectName);
		ProjectModel pModel = (ProjectModel) sqlMapClientTemplate.queryForObject("project.checkProjectExist", valueMap);
		
		if(pModel == null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public int deleteUserProject(String projectID) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.delete("project.deleteUserProject", projectID);
	}
}
