package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class JobHistoryListGrid extends ListGrid{

	public JobHistoryListGrid(){
	
		setShowAllRecords(true);
		setShowRowNumbers(true);
		setShowRollOver(false);
		setCanReorderRecords(true);
		setSelectionAppearance(SelectionAppearance.CHECKBOX);
		setWidth100();
		setHeight100();
		setEmptyMessage("There are no data.");
		
        ListGridField exeIDField = new ListGridField("exeID", "Job ID");  
        exeIDField.setAlign(Alignment.CENTER);
        
        ListGridField startDateField = new ListGridField("startDate", "Start Time");
        startDateField.setAlign(Alignment.CENTER);
        
        ListGridField endDateField = new ListGridField("endDate", "End Time");
        endDateField.setAlign(Alignment.CENTER);
        
        setFields(exeIDField, startDateField, endDateField); 
	}
}
