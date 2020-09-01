package org.kobic.gwt.smart.closha.client.section.ontology.module.component;

import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.tree.Tree;

public class ModuleOntologyTree extends Tree {
	
	public ModuleOntologyTree(){
		setModelType(TreeModelType.PARENT);
		setNameProperty("moduleTreeName");
		setIdField("moduleID");
		setParentIdField("ontologyID");
		setShowRoot(false);
	}

}
