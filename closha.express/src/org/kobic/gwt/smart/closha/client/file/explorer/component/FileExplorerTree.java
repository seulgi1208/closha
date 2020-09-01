package org.kobic.gwt.smart.closha.client.file.explorer.component;

import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.tree.Tree;

public class FileExplorerTree extends Tree{
	
	public FileExplorerTree(){
		
		setModelType(TreeModelType.PARENT);
		setNameProperty("fileName");
		setIdField("fileId");
		setParentIdField("parentId");
		setAttribute("fileSize", "fileSize", true);
		setAttribute("fileModifyDate", "fileModifyDate", true);
		setAttribute("fileType", "fileType", true);
		setShowRoot(false);
	}
}
