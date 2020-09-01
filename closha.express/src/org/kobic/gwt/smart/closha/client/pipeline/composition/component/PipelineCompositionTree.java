package org.kobic.gwt.smart.closha.client.pipeline.composition.component;

import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.tree.Tree;

public class PipelineCompositionTree extends Tree{
	
	public PipelineCompositionTree(){
		setModelType(TreeModelType.PARENT);
		setNameProperty("name");
		setIdField("id");
		setParentIdField("pId");
		setAttribute("id", "ID", true);
		setAttribute("type", "Type", true);
		setAttribute("desc", "Desc", true);
		setAttribute("status", "Status", true);
		setAttribute("version", "Version", true);
		setAttribute("script", "Script", true);	
	}
}
