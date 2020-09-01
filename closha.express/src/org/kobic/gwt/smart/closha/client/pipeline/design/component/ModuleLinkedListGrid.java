package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class ModuleLinkedListGrid extends ListGrid{

	public ModuleLinkedListGrid(){
	
		setShowAllRecords(true);
		setShowRowNumbers(true);
		setShowRollOver(true);
		setCanReorderRecords(true);
		setWidth100();
		setHeight100();
		setSelectionAppearance(SelectionAppearance.CHECKBOX); 
		setEmptyMessage("There are no data.");
		
        ListGridField iconField = new ListGridField("module", "Module");  
        iconField.setAlign(Alignment.CENTER);
        iconField.setType(ListGridFieldType.IMAGE);
        
        ListGridField nameField = new ListGridField("name", "Program Name");
        nameField.setAlign(Alignment.CENTER);
        
        ListGridField typeField = new ListGridField("type", "Type");
        typeField.setAlign(Alignment.CENTER);
        
        setFields(iconField, nameField, typeField); 
	}
}
