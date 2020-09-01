package org.kobic.gwt.smart.closha.client.file.explorer.viewer;

import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.client.file.explorer.presenter.FileExplorerPresenter;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class FileExplorerViewer extends VLayoutWidget implements FileExplorerPresenter.Display{
	
	private TreeGridField fileNameField; 
	private TreeGridField modifyDateField;
	private TreeGrid fileBrowserTreeGrid;
	
	public FileExplorerViewer(){
		
		fileNameField =  new TreeGridField("fileName", "File Name");
		modifyDateField = new TreeGridField("lastModifyDate", "Last Modified Date");
		modifyDateField.setAlign(Alignment.CENTER);
		
		fileBrowserTreeGrid = new TreeGrid();          
		fileBrowserTreeGrid.setWidth100(); 
		fileBrowserTreeGrid.setHeight100();
		fileBrowserTreeGrid.setShowHeader(true);
		fileBrowserTreeGrid.setSelectionType(SelectionStyle.SINGLE);
		fileBrowserTreeGrid.setLeaveScrollbarGap(false);
		fileBrowserTreeGrid.setEmptyMessage("There are no data.");
		fileBrowserTreeGrid.setCanAcceptDroppedRecords(true);
		fileBrowserTreeGrid.setCanReparentNodes(false);
		fileBrowserTreeGrid.setShowConnectors(true);
		fileBrowserTreeGrid.setCanDragRecordsOut(false);
		fileBrowserTreeGrid.setFields(fileNameField, modifyDateField);
		
		addMember(fileBrowserTreeGrid);
	}

	@Override
	public VLayout asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public TreeGrid getFileBrowserTreeGrid() {
		// TODO Auto-generated method stub
		return fileBrowserTreeGrid;
	}
}