package org.kobic.gwt.smart.closha.client.preference.viewer;

import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.client.preference.component.PreferencesListGrid;
import org.kobic.gwt.smart.closha.client.preference.component.SelectPreferencePipelineListGrid;
import org.kobic.gwt.smart.closha.client.preference.presenter.PreferencePresenter;
import org.kobic.gwt.smart.closha.shared.Constants;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class PreferenceViewer extends VLayoutWidget implements PreferencePresenter.Display{

	private ListGrid preferencesListGrid;
	private ListGrid transferListGrid;
	private ToolStripButton totalCountButton;
	private ToolStripButton runCountButton;
	private ToolStripButton waitCountButton;
	private ToolStripButton completeCountButton;
	private ToolStripButton errorCountButton;
	private ToolStripButton deleteButton;
	private ToolStripButton shareButton;
	private ToolStripButton clearButton;
	private ToolStripButton reflashButton;
	
	
	public PreferenceViewer(){
		
		setPadding(5);
		setMembersMargin(10);
		
		VLayout preferenceLayout = new VLayout();
		preferenceLayout.setHeight100();
		preferenceLayout.setWidth100();
		
		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.addFill();

		totalCountButton = new ToolStripButton();
		totalCountButton.setIcon("closha/icon/number.png");
		totalCountButton.setTitle(Constants.TOTAL_COUNT);
		toolStrip.addButton(totalCountButton);
		
		runCountButton = new ToolStripButton();
		runCountButton.setIcon("closha/icon/history_running.png");
		runCountButton.setTitle(Constants.RUN_COUNT);
		toolStrip.addButton(runCountButton);
		
		waitCountButton = new ToolStripButton();
		waitCountButton.setIcon("closha/icon/history_wait.png");
		waitCountButton.setTitle(Constants.WAIT_COUNT);
		toolStrip.addButton(waitCountButton);
		
		completeCountButton = new ToolStripButton();
		completeCountButton.setIcon("closha/icon/history_done.png");
		completeCountButton.setTitle(Constants.COMPLETE_COUNT);
		toolStrip.addButton(completeCountButton);
		
		errorCountButton = new ToolStripButton();
		errorCountButton.setIcon("closha/icon/history_error.png");
		errorCountButton.setTitle(Constants.ERROR_COUNT);
		toolStrip.addButton(errorCountButton);
		
		ListGridField instanceStatusField = new ListGridField("stateImg", "Status");  
		instanceStatusField.setAlign(Alignment.CENTER);
		instanceStatusField.setType(ListGridFieldType.IMAGE);
		instanceStatusField.setWidth(50);
		
		ListGridField instanceIDField = new ListGridField("instanceID", "Pipeline ID");  
		instanceIDField.setAlign(Alignment.CENTER);
		instanceIDField.setHidden(true);
		instanceIDField.setWidth(250);
		
		ListGridField pipelineNameField = new ListGridField("pipelineName", "Register Pipeline Name");
		pipelineNameField.setAlign(Alignment.CENTER);
		pipelineNameField.setHidden(true);
		pipelineNameField.setWidth(150);
        
        ListGridField instanceNameField = new ListGridField("instanceName", "Project Name");  
        instanceNameField.setAlign(Alignment.CENTER);
        instanceNameField.setWidth(150);
        
        ListGridField ownerEmailField = new ListGridField("ownerEmail", "Email");  
        ownerEmailField.setAlign(Alignment.CENTER);
        ownerEmailField.setHidden(true);
        
        ListGridField instanceDescField = new ListGridField("instanceDesc", "Description");  
        instanceDescField.setAlign(Alignment.CENTER);
		
        ListGridField exeCountField = new ListGridField("exeCount", "Execution Count");  
        exeCountField.setAlign(Alignment.CENTER);
        exeCountField.setWidth(120);
        
        ListGridField createDateField = new ListGridField("createDate", "Created Date");  
        createDateField.setAlign(Alignment.CENTER);
        createDateField.setWidth(120);
        
		preferencesListGrid = new PreferencesListGrid();
		
		preferencesListGrid.setFields(instanceStatusField, instanceIDField, 
				pipelineNameField, instanceNameField, ownerEmailField, instanceDescField, 
				exeCountField, createDateField);
		
		preferenceLayout.addMember(toolStrip);
		preferenceLayout.addMember(preferencesListGrid);
		
		/*******************************************************************************************/
		
		VLayout transferLayout = new VLayout();
		transferLayout.setHeight("40%");
		transferLayout.setWidth100();
		
		ToolStrip functionTooltip = new ToolStrip();
		functionTooltip.setWidth100();
		functionTooltip.addFill();

		shareButton = new ToolStripButton();
		shareButton.setIcon("closha/icon/cluster16.gif");
		shareButton.setTitle("Project Share");
		functionTooltip.addButton(shareButton);
		
		deleteButton = new ToolStripButton();
		deleteButton.setIcon("closha/icon/application_delete.png");
		deleteButton.setTitle("Delete Project");
		functionTooltip.addButton(deleteButton);
		
		clearButton = new ToolStripButton();
		clearButton.setIcon("closha/icon/basket_remove.png");
		clearButton.setTitle("Deselect");
		functionTooltip.addButton(clearButton);
		
		reflashButton = new ToolStripButton();
		reflashButton.setIcon("closha/icon/new_refresh.png");
		reflashButton.setTitle("Refresh");
		functionTooltip.addButton(reflashButton);
		
		transferListGrid = new SelectPreferencePipelineListGrid();
		
		transferLayout.addMember(functionTooltip);
		transferLayout.addMember(transferListGrid);
		
		addMember(preferenceLayout);
		addMember(transferLayout);
	}
	
	@Override
	public VLayout getBottomLayout() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ListGrid getPreferencesListGrid() {
		// TODO Auto-generated method stub
		return preferencesListGrid;
	}

	@Override
	public ListGrid getTransferListGrid() {
		// TODO Auto-generated method stub
		return transferListGrid;
	}

	@Override
	public ToolStripButton getClearButton() {
		// TODO Auto-generated method stub
		return clearButton;
	}

	@Override
	public ToolStripButton getShareButton() {
		// TODO Auto-generated method stub
		return shareButton;
	}

	@Override
	public ToolStripButton getTotalCountButton() {
		// TODO Auto-generated method stub
		return totalCountButton;
	}

	@Override
	public ToolStripButton getRunCountButton() {
		// TODO Auto-generated method stub
		return runCountButton;
	}

	@Override
	public ToolStripButton getWaitCountButton() {
		// TODO Auto-generated method stub
		return waitCountButton;
	}

	@Override
	public ToolStripButton getCompleteButton() {
		// TODO Auto-generated method stub
		return completeCountButton;
	}

	@Override
	public ToolStripButton getErrorButton() {
		// TODO Auto-generated method stub
		return errorCountButton;
	}

	@Override
	public ToolStripButton getReflashButton() {
		// TODO Auto-generated method stub
		return reflashButton;
	}
}