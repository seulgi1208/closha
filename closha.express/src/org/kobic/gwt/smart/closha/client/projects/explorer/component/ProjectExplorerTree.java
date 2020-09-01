package org.kobic.gwt.smart.closha.client.projects.explorer.component;

import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.tree.Tree;

public class ProjectExplorerTree extends Tree{
	
	public ProjectExplorerTree(){
		setModelType(TreeModelType.CHILDREN);
		setNameProperty("projectName");
		setIdField("projectID");
		setParentIdField("parentID");
		setAttribute("pipelineID", "pipelineID", true);
		setAttribute("projectDesc", "projectDesc", true);
		setAttribute("projectOwner", "projectOwner", true);
		setAttribute("createDate", "createDate", true);
		setShowRoot(false);
	}

}
