package org.kobic.gwt.smart.closha.client.section.ontology.pipeline.component;

import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.tree.Tree;

public class PipelineOntologyTree extends Tree{
	
	public PipelineOntologyTree(){
		setModelType(TreeModelType.PARENT);
		setNameProperty("pipelineName");
		setIdField("registerID");
		setParentIdField("ontologyID");
		setShowRoot(false);
	}
}
