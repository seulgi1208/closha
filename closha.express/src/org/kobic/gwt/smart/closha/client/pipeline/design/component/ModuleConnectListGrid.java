package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import org.kobic.gwt.smart.closha.client.event.draw.ConnectionLinkedStraightEvent;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ModuleConnectListGrid extends ListGrid{
	
	private HandlerManager eventBus;
	private String projectName;
	
	@Override  
    protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {  

        String fieldName = this.getFieldName(colNum);  
        final String startID = record.getAttributeAsString("startKey");
        final String endID = record.getAttributeAsString("endKey");
        
        if (fieldName.equals("buttonField")) {  
            IButton button = new IButton();  
            button.setHeight(18);  
            button.setWidth(25);                      
            button.setIcon("closha/icon/arrow_refresh_small.png");  
            
            button.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					eventBus.fireEvent(new ConnectionLinkedStraightEvent(projectName, startID, endID));
				}
			});
            
            return button;  
        } else {  
            return null;  
        }  
	}

	public ModuleConnectListGrid(HandlerManager eventBus, String projectName){
	
		this.eventBus = eventBus;
		this.projectName = projectName;
		
		setShowAllRecords(true);
		setShowRowNumbers(true);
		setShowRollOver(true);
		setCanReorderRecords(true);
		setWidth100();
		setHeight100();
		setSelectionAppearance(SelectionAppearance.CHECKBOX);
		setEmptyMessage("There are no data.");
		setShowRecordComponents(true);          
        setShowRecordComponentsByCell(true);  
          
  
        
        ListGridField iconField = new ListGridField("linked", "Link Status");  
        iconField.setAlign(Alignment.CENTER);
        iconField.setType(ListGridFieldType.IMAGE);
        
        ListGridField sourceField = new ListGridField("startModuleName", "Start Module");
        sourceField.setAlign(Alignment.CENTER);
        
        ListGridField targetField = new ListGridField("endModuleName", "End Module");
        targetField.setAlign(Alignment.CENTER);
        
        ListGridField buttonField = new ListGridField("buttonField", "Reset Link");  
        buttonField.setAlign(Alignment.CENTER);  
        
        setFields(iconField, sourceField, targetField, buttonField); 
	}
}
