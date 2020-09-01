package org.kobic.gwt.smart.closha.client.file.browser.viewer;

import java.util.LinkedHashMap;

import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.client.file.browser.presenter.FileBrowserPresenter;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class FileBrowserViewer extends VLayoutWidget implements
		FileBrowserPresenter.Display {
	
	private ListGrid fileListGrid;
	
	private ToolStripButton homeButton;
	private ToolStripButton downloadButton;
	private ToolStripButton refreshButton;
	private ToolStripButton makeButton;
	private ToolStripButton deleteFileButton;
	private ToolStripButton editFileButton;
	private ToolStripButton copyButton;
	
	private SelectItem item;
	private SelectItem selectItem;
	
	public FileBrowserViewer(){
		
        item = new SelectItem("itemID");  
        item.setWidth(400);  
        item.setPickListWidth(800);
        item.setShowTitle(false);  
        
        LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("person", "<b>Personal Data</b>");
		valueMap.put("public", "<b>Public Data</b>");
		
		LinkedHashMap<String, String> valueIcons = new LinkedHashMap<String, String>();
		valueIcons.put("person", "closha/icon/user.png");
		valueIcons.put("public", "closha/icon/group.png");
		
		selectItem = new SelectItem();  
	    selectItem.setWidth(150);  
	    selectItem.setShowTitle(false);  
	    selectItem.setDefaultValue("person");
	    selectItem.setValueMap(valueMap);
		selectItem.setValueIcons(valueIcons);
		
        final DynamicForm form = new DynamicForm();  
        form.setWidth(750);  
        form.setFields(selectItem, item);  
		
        HLayout top = new HLayout();
		top.setWidth100();
		top.setAlign(Alignment.LEFT);
		top.addMember(form);
		top.setMargin(20);
        
        ListGridField typeField = new ListGridField("icon", "Type");
        typeField.setAlign(Alignment.CENTER);
        typeField.setType(ListGridFieldType.IMAGE);  
        typeField.setWidth(80);
  
        ListGridField nameField = new ListGridField("fileName", "File Name");          
        nameField.setAlign(Alignment.LEFT);
        nameField.setWidth(300);
        
        ListGridField sizeFiled = new ListGridField("fileSize", "File Size");
        sizeFiled.setAlign(Alignment.CENTER);
        sizeFiled.setWidth(120);
        
        ListGridField userField = new ListGridField("user", "User");
        userField.setAlign(Alignment.CENTER);
        userField.setHidden(true);
        
        ListGridField groupField = new ListGridField("group", "Group");
        groupField.setAlign(Alignment.CENTER);
        groupField.setHidden(true);
        
        ListGridField permissionField = new ListGridField("permissions", "Permission");
        permissionField.setAlign(Alignment.CENTER);
        permissionField.setHidden(true);
        
        ListGridField modifiedField = new ListGridField("lastModifyDate", "Last Modified Date");
        modifiedField.setAlign(Alignment.LEFT);
        
        ListGridField accessField = new ListGridField("lastAccessDate", "Last Access Date");
        accessField.setAlign(Alignment.CENTER);
        accessField.setHidden(true);
        
		fileListGrid = new ListGrid();  
        fileListGrid.setWidth100();
        fileListGrid.setHeight100();
        fileListGrid.setShowAllRecords(true);
        fileListGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX); 
        fileListGrid.setEmptyMessage("There are no data.");
		fileListGrid.setFields(typeField, nameField, sizeFiled, userField,
				groupField, permissionField, modifiedField, accessField);

        homeButton = new ToolStripButton("Home Directory");
		homeButton.setIcon("silk/house.png");
		
		downloadButton = new ToolStripButton("Download");
		downloadButton.setIcon("silk/page_white_put.png");

		makeButton = new ToolStripButton("New Folder");
		makeButton.setIcon("silk/folder_add.png");

		deleteFileButton = new ToolStripButton("Delete");
		deleteFileButton.setIcon("silk/page_white_delete.png");

		editFileButton = new ToolStripButton("Edit");
		editFileButton.setIcon("silk/page_white_edit.png");
		
		refreshButton = new ToolStripButton("Refresh");
		refreshButton.setIcon("closha/icon/new_refresh.png");
		
		copyButton = new ToolStripButton("Copy");
		copyButton.setIcon("silk/page_white_add.png");
		
		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.addFill();
		toolStrip.addButton(homeButton);
		toolStrip.addButton(downloadButton);
		toolStrip.addButton(makeButton);
		toolStrip.addButton(deleteFileButton);
		toolStrip.addButton(copyButton);
		toolStrip.addButton(editFileButton);
		toolStrip.addButton(refreshButton);

        addMember(top);
        addMember(fileListGrid);
        addMember(toolStrip);
	}

	@Override
	public VLayout asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ListGrid getFileBrowserListGrid() {
		// TODO Auto-generated method stub
		return fileListGrid;
	}

	@Override
	public ToolStripButton getMyHomeButton() {
		// TODO Auto-generated method stub
		return homeButton;
	}
	
	@Override
	public ToolStripButton getMakeButton() {
		// TODO Auto-generated method stub
		return makeButton;
	}

	@Override
	public SelectItem getItem() {
		// TODO Auto-generated method stub
		return item;
	}

	@Override
	public ToolStripButton getDeleteButton() {
		// TODO Auto-generated method stub
		return deleteFileButton;
	}

	@Override
	public ToolStripButton getEditButton() {
		// TODO Auto-generated method stub
		return editFileButton;
	}

	@Override
	public ToolStripButton getDownloadButton() {
		// TODO Auto-generated method stub
		return downloadButton;
	}

	@Override
	public ToolStripButton getRefreshButton() {
		// TODO Auto-generated method stub
		return refreshButton;
	}

	@Override
	public ToolStripButton getCopyButton() {
		// TODO Auto-generated method stub
		return copyButton;
	}

	@Override
	public SelectItem getSelectItem() {
		// TODO Auto-generated method stub
		return selectItem;
	}
}