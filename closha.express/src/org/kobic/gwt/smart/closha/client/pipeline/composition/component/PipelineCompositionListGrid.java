package org.kobic.gwt.smart.closha.client.pipeline.composition.component;

import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.event.draw.ShowModuleDialogWindowEvent;
import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;

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

public class PipelineCompositionListGrid extends ListGrid{
	
	private HandlerManager eventBus;
	
	private XmlModuleModel xmlModuleModel;
	private Map<String, XmlModuleModel> xmlModuleModelMap;
	private List<XmlParameterModel> paramList;
	private InstancePipelineModel iModel;
	
	@Override  
    protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {  

		String fieldName = this.getFieldName(colNum);  
    	
    	if (fieldName.equals("buttonField")) {  
            IButton button = new IButton();  
            button.setHeight(18);  
            button.setWidth(65);                      
            button.setIcon("closha/icon/wrench.png");  
            button.setTitle("Setting");  
            button.addClickHandler(new ClickHandler() {  
                public void onClick(ClickEvent event) {  
                    
                	String id = record.getAttributeAsString("id");
    				
    				xmlModuleModel = xmlModuleModelMap.get(id);
    				
    				eventBus.fireEvent(new ShowModuleDialogWindowEvent(
    						iModel.getInstanceName(), xmlModuleModel, paramList));
                	
                }  
            });  
            return button;  
        } else {  
            return null;  
        }  
	}

	public PipelineCompositionListGrid(HandlerManager eventBus, XmlModuleModel xmlModuleModel, List<XmlParameterModel> paramList, InstancePipelineModel iModel){
	
		this.eventBus = eventBus;
		this.xmlModuleModel = xmlModuleModel;
		this.paramList = paramList;
		this.iModel = iModel;
		
		setShowAllRecords(true);
		setShowRowNumbers(true);
		setShowRollOver(true);
		setCanReorderRecords(true);
		setWidth100();
		setHeight100();
		setSelectionAppearance(SelectionAppearance.ROW_STYLE);
		setEmptyMessage("There are no data.");
		setShowRecordComponents(true);          
        setShowRecordComponentsByCell(true);  
  
        ListGridField iconField = new ListGridField("icon", "Icon", 50);  
		iconField.setFrozen(true);
        iconField.setAlign(Alignment.CENTER);  
        iconField.setType(ListGridFieldType.IMAGE);  
        
		ListGridField nameField =  new ListGridField("name", "Program Name", 200);
		nameField.setAlign(Alignment.CENTER);
		
		ListGridField idField =  new ListGridField("id", "Program ID", 300);
		idField.setAlign(Alignment.CENTER);
		idField.setHidden(true);
		
		ListGridField typeField =  new ListGridField("type", "Type", 70);
		typeField.setAlign(Alignment.CENTER);
		
		ListGridField descField =  new ListGridField("desc", "Description");
		descField.setWidth("*");
		descField.setAlign(Alignment.LEFT);
		
		ListGridField statusField =  new ListGridField("status", "Status", 70);
		statusField.setAlign(Alignment.CENTER);
		statusField.setHidden(true);
		
		ListGridField versionField =  new ListGridField("version", "Version", 70);
		versionField.setAlign(Alignment.CENTER);
		versionField.setHidden(true);
		
		ListGridField scriptField =  new ListGridField("script", "Script Path", 250);
		scriptField.setAlign(Alignment.CENTER);
		scriptField.setHidden(true);
		
		ListGridField buttonField = new ListGridField("buttonField", "Setting");  
        buttonField.setAlign(Alignment.CENTER); 
        
        setFields(iconField, nameField, idField, typeField, descField,
				statusField, versionField, scriptField, buttonField);
	}
}
