package org.kobic.gwt.smart.closha.client.preference.component;

import com.smartgwt.client.widgets.grid.ListGrid;

public class PreferencesListGrid  extends ListGrid {

	public PreferencesListGrid() {
		setShowAllRecords(true);
		setShowRowNumbers(true);
		setWrapCells(true);  
		setFixedRecordHeights(false); 
		setCellHeight(32);		
		setEmptyMessage("There are no data.");
	}
}