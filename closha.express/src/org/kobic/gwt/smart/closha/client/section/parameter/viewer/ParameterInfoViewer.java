package org.kobic.gwt.smart.closha.client.section.parameter.viewer;

import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.client.section.parameter.presenter.ParameterInfoPresenter;
import org.kobic.gwt.smart.closha.client.section.parameter.record.ParameterInfoRecord;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;

public class ParameterInfoViewer extends VLayoutWidget implements ParameterInfoPresenter.Display{

	private ListGrid parameterListGird;
	
	public ParameterInfoViewer(){
		
		parameterListGird = new ListGrid();  
        parameterListGird.setWidth100();  
        parameterListGird.setHeight100();
        parameterListGird.setShowAllRecords(true);  
        parameterListGird.setGroupStartOpen(GroupStartOpen.ALL);  
        parameterListGird.setGroupByField("name");  
        parameterListGird.setEmptyMessage("There are no data.");
        
        ListGridField cacheImgField = new ListGridField("cache", "Status", 30);  
        cacheImgField.setFrozen(true);
        cacheImgField.setAlign(Alignment.CENTER);  
        cacheImgField.setType(ListGridFieldType.IMAGE);  
        
        ListGridField typeImgField = new ListGridField("type", "Type", 60);  
        typeImgField.setFrozen(true);
        typeImgField.setAlign(Alignment.CENTER);  
        typeImgField.setType(ListGridFieldType.IMAGE);  
        
        ListGridField parameterTypeImgField = new ListGridField("parameter_type", "Parameter Type", 60);  
        
        ListGridField parameterField = new ListGridField("parameter", "Parameter");  
        ListGridField valueFiled = new ListGridField("value", "Value");
        valueFiled.setShowHover(true);
        valueFiled.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				// TODO Auto-generated method stub
				ParameterInfoRecord parameterInfoRecord = (ParameterInfoRecord) record;
				return parameterInfoRecord.getAttribute("value");
			}
		});
        
        ListGridField descFiled = new ListGridField("desc", "Description");  
        ListGridField moduleNameField = new ListGridField("name", "Program Name");
        moduleNameField.setHidden(true);
        
        ListGridField keyField = new ListGridField("key", "Register Number");
        keyField.setHidden(true);
        
        parameterListGird.setFields(new ListGridField[] {cacheImgField, typeImgField, parameterTypeImgField, parameterField, valueFiled, descFiled, moduleNameField, keyField});  
        
        addMember(parameterListGird);
	}
	
	@Override
	public VLayout asLayout() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ListGrid getParameterListGrid() {
		// TODO Auto-generated method stub
		return parameterListGird;
	}
}