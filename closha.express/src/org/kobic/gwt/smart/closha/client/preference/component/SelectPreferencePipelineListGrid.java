package org.kobic.gwt.smart.closha.client.preference.component;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class SelectPreferencePipelineListGrid extends ListGrid {

	public SelectPreferencePipelineListGrid() {
		setShowAllRecords(true);
		setShowRowNumbers(true);
		setWrapCells(true);  
		setFixedRecordHeights(true); 
		setCellHeight(32);
		setEmptyMessage("There are no data.");
		
		ListGridField instanceIDField = new ListGridField("instanceID", "Pipeline ID");
		instanceIDField.setAlign(Alignment.CENTER);
		instanceIDField.setHidden(true);
		
		ListGridField pipelineNameField = new ListGridField("pipelineName", "Pipeline Name"); 
		pipelineNameField.setAlign(Alignment.CENTER);
		pipelineNameField.setHidden(true);
		
		ListGridField instanceNameField = new ListGridField("instanceName", "Instance Name");
		instanceNameField.setAlign(Alignment.CENTER);
		
		ListGridField instanceOwnerField = new ListGridField("instanceOwner", "Owner");
		instanceOwnerField.setAlign(Alignment.CENTER);
		instanceOwnerField.setWidth(120);
		
		ListGridField ownerEmailField = new ListGridField("ownerEmail", "Owner Email");
		ownerEmailField.setAlign(Alignment.CENTER);
		ownerEmailField.setWidth(120);
		
		ListGridField createDateField = new ListGridField("createDate", "Created Date");
		createDateField.setAlign(Alignment.CENTER);
		createDateField.setWidth(120);
		
		setFields(instanceIDField, pipelineNameField, instanceNameField,
				instanceOwnerField, ownerEmailField, createDateField);
	}
}