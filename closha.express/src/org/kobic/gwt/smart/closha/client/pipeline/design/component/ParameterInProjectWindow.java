package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.file.browser.event.SelectFileWindowEvent;
import org.kobic.gwt.smart.closha.client.pipeline.design.record.InnerProjectParameterListGridRecord;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.DropCompleteEvent;
import com.smartgwt.client.widgets.events.DropCompleteHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ParameterInProjectWindow extends Window {

	private String formName;
	
	@SuppressWarnings("unused")
	private String userId;
	private String key;
	private String parameterType;
	private String parameterKey;
	@SuppressWarnings("unused")
	private String projectName;
	private String type;
	private String extensions;
	
	private HandlerManager eventBus;

	private ToolStripButton resetButton;
	private ToolStripButton selectButton;

	@SuppressWarnings("unused")
	private Map<String, String> config;
	
	private ListGrid inputParameterListGrid;
	private ListGrid outputParameterListGrid;
	private ListGrid setParameterListGrid;
	
	private List<XmlParameterModel> parameterList;
	
	private String getImg(String type, String parameterType) {
		
		System.out.println(type + " : "  + parameterType);

		String parameterTypeIcon = "";

		if (type.toLowerCase().equals("file")) {

			if (parameterType.toLowerCase().equals("input")) {
				parameterTypeIcon = "closha/icon/input_files_1.png";
			} else if (parameterType.toLowerCase().equals("output")) {
				parameterTypeIcon = "closha/icon/output_files_1.png";
			}

		} else if (type.toLowerCase().equals("folder")) {
			parameterTypeIcon = "closha/icon/folder.png";
		} else if(type.toLowerCase().equals("reference")){
			parameterTypeIcon = "closha/icon/project_folder.png";
		}

		return parameterTypeIcon;
	}
	
	@Override
	protected void onInit() {
		
		List<XmlParameterModel> inputList = new ArrayList<XmlParameterModel>();
		List<XmlParameterModel> outputList = new ArrayList<XmlParameterModel>();
		
		for(XmlParameterModel pm : parameterList){
			if(pm.getType().equals("FILE") || pm.getType().equals("FOLDER") || pm.getType().equals("REFERENCE")){
				
//				if(pm.getParameterType().equals("INPUT")){
//					inputList.add(pm);
//				}else if(pm.getParameterType().equals("OUTPUT") && CommonUtilsGwt.isExtension(extensions, pm.getExtensions())){
//					outputList.add(pm);
//				}
				
				if(pm.getParameterType().equals("INPUT")){
					inputList.add(pm);
				}else if(pm.getParameterType().equals("OUTPUT")){
					outputList.add(pm);
				}
			}
		}
		
		InnerProjectParameterListGridRecord inputRecords[] = new InnerProjectParameterListGridRecord[inputList.size()];
		
		for(int i = 0; i < inputList.size(); i++){
			XmlParameterModel fm = inputList.get(i);
			inputRecords[i] = new InnerProjectParameterListGridRecord(getImg(
					fm.getType(), fm.getParameterType()), fm.getModule(),
					fm.getName(), fm.getType(), fm.getSetupValue(),
					CommonUtilsGwt.getBold(fm.getDescription()));
		}
		
		inputParameterListGrid.setData(inputRecords);
		
		InnerProjectParameterListGridRecord outputRecords[] = new InnerProjectParameterListGridRecord[outputList.size()];
		
		for(int i = 0; i < outputList.size(); i++){
			XmlParameterModel fm = outputList.get(i);
			outputRecords[i] = new InnerProjectParameterListGridRecord(getImg(
					fm.getType(), fm.getParameterType()), fm.getModule(),
					fm.getName(), fm.getType(), fm.getSetupValue(),
					CommonUtilsGwt.getBold(fm.getDescription()));
		}
		
		outputParameterListGrid.setData(outputRecords);
	}
	
	private void bind(){
		
		this.addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				// TODO Auto-generated method stub
				destroy();
			}
		});
		
		resetButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				setParameterListGrid.removeSelectedData();
			}
		});
		
		selectButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
			
				ListGridRecord[] records = setParameterListGrid.getSelectedRecords();
				
				if(records.length > 1){
					SC.warn("You cannot set more than one parameter at the same time.");
				}else if(records.length == 0){
					SC.warn("It is required to set parameters.");
				}else if(records.length == 1){
					
					if(setParameterListGrid.getSelectedRecord().getAttributeAsString("type").equals(type)){
						String fileName = setParameterListGrid.getSelectedRecord().getAttributeAsString("value");
						String filePath = setParameterListGrid.getSelectedRecord().getAttributeAsString("value");
						
						eventBus.fireEvent(new SelectFileWindowEvent(formName, fileName, filePath, parameterType, parameterKey, key));
						destroy();
					}else{
						if(type.equals("FILE")){
							SC.warn("The setting value of the file type is required.");
						}else if(type.equals("REFERENCE")){
							SC.warn("The setting value of the reference type is required.");
						}else{
							SC.warn("The setting value of the directory type is required.");
						}
					}
				}
			}
		});
		
		setParameterListGrid.addDropCompleteHandler(new DropCompleteHandler() {
			
			@Override
			public void onDropComplete(DropCompleteEvent event) {
				// TODO Auto-generated method stub
				setParameterListGrid.selectAllRecords();
			}
		});
	}

	public ParameterInProjectWindow(Map<String, String> config, String userID,
			String formName, String parameterType, String parameterKey,
			List<XmlParameterModel> parameterList, String key,
			String projectName, String type, String extensions, HandlerManager eventBus) {

		this.formName = formName;
		this.eventBus = eventBus;
		this.userId = userID;
		this.config = config;
		this.parameterType = parameterType;
		this.parameterKey = parameterKey;
		this.parameterList = parameterList;
		this.key = key;
		this.projectName = projectName;
		this.type = type;
		this.extensions = extensions;
		
		ListGridField imgField = new ListGridField("format", "Format", 50);
		imgField.setAlign(Alignment.CENTER);
		imgField.setType(ListGridFieldType.IMAGE);
		
		ListGridField moduleNameField = new ListGridField("moduleName", "Program Name");
		ListGridField parameterNameField = new ListGridField("parameterName", "Parameter Name");
		ListGridField parameterTypeField = new ListGridField("type", "Type");
		parameterTypeField.setHidden(true);
		ListGridField parameterDescField = new ListGridField("desc", "Description");
		ListGridField parameterValueField = new ListGridField("value", "Value");

		inputParameterListGrid = new ListGrid();
		inputParameterListGrid.setWidth100();
		inputParameterListGrid.setHeight100();
		inputParameterListGrid.setShowAllRecords(true);
		inputParameterListGrid.setCanReorderRecords(true);
		inputParameterListGrid.setCanDragRecordsOut(true);
		inputParameterListGrid.setCanAcceptDroppedRecords(true);
		inputParameterListGrid.setDragDataAction(DragDataAction.COPY);
		inputParameterListGrid.setEmptyMessage("Drag and drop, please.");
		inputParameterListGrid.setCanGroupBy(true);
		inputParameterListGrid.groupBy("moduleName");
		inputParameterListGrid.setGroupStartOpen(GroupStartOpen.ALL); 
		inputParameterListGrid.setFields(imgField, moduleNameField, parameterNameField, parameterTypeField, parameterValueField, parameterDescField);
		
		ListGridField imgField2 = new ListGridField("format", "Format", 50);
		imgField2.setAlign(Alignment.CENTER);
		imgField2.setType(ListGridFieldType.IMAGE);
		
		ListGridField moduleNameField3 = new ListGridField("moduleName", "Program Name");
		ListGridField parameterNameField3 = new ListGridField("parameterName", "Parameter Name");
		ListGridField parameterTypeField3 = new ListGridField("type", "Type");
		parameterTypeField3.setHidden(true);
		ListGridField parameterDescField3 = new ListGridField("desc", "Description");
		ListGridField parameterValueField3 = new ListGridField("value", "Value");

		outputParameterListGrid = new ListGrid();
		outputParameterListGrid.setWidth100();
		outputParameterListGrid.setHeight100();
		outputParameterListGrid.setShowAllRecords(true);
		outputParameterListGrid.setCanReorderRecords(true);
		outputParameterListGrid.setCanDragRecordsOut(true);
		outputParameterListGrid.setCanAcceptDroppedRecords(true);
		outputParameterListGrid.setDragDataAction(DragDataAction.COPY);
		outputParameterListGrid.setEmptyMessage("No selectable arguments exist.");
		outputParameterListGrid.setCanGroupBy(true);
		outputParameterListGrid.groupBy("moduleName");		
		outputParameterListGrid.setGroupStartOpen(GroupStartOpen.ALL); 
		outputParameterListGrid.setFields(imgField2, moduleNameField3, parameterNameField3, parameterTypeField3, parameterValueField3, parameterDescField3);

		/**
		 * Tab 추가
		 */
	
        Tab inputTab = new Tab("Input");  
        inputTab.setPane(inputParameterListGrid);
        inputTab.setID("input_tab");
        inputTab.setIcon("closha/icon/input_files_1.png");
        
        Tab outputTab = new Tab("Output");  
        outputTab.setPane(outputParameterListGrid);
        outputTab.setID("output_tab");
        outputTab.setIcon("closha/icon/output_files_1.png");
        
        final TabSet tabSet = new TabSet();  
        tabSet.setTabBarPosition(Side.TOP);  
        tabSet.setTabBarAlign(Side.LEFT);  
        tabSet.setWidth100();
        tabSet.setHeight100();
        tabSet.addTab(outputTab);
        tabSet.addTab(inputTab);  
         
        VLayout topLayout = new VLayout();
        topLayout.setShowResizeBar(true);
        topLayout.addMember(tabSet);
        topLayout.setWidth100();
        topLayout.setHeight("80%");
        
        /**
         * Tab 완료
         */
		
    	ListGridField imgField3 = new ListGridField("format", "Format", 50);
		imgField3.setAlign(Alignment.CENTER);
		imgField3.setType(ListGridFieldType.IMAGE);
		
		ListGridField moduleNameField2 = new ListGridField("moduleName", "Program Name");
		ListGridField parameterNameField2 = new ListGridField("parameterName", "Parameter Name");
		ListGridField parameterValueField2 = new ListGridField("value", "Value");
		
		setParameterListGrid = new ListGrid();
		setParameterListGrid.setWidth100();
		setParameterListGrid.setHeight100();
		setParameterListGrid.setShowAllRecords(true);
		setParameterListGrid.setEmptyMessage("Drag and drop, please.");
		setParameterListGrid.setCanReorderFields(true);
		setParameterListGrid.setCanDragRecordsOut(false);
		setParameterListGrid.setCanAcceptDroppedRecords(true);
		setParameterListGrid.setFields(imgField3, moduleNameField2, parameterNameField2, parameterValueField2);
		setParameterListGrid.setSelectionType(SelectionStyle.SIMPLE);
		setParameterListGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		setParameterListGrid.setSelectOnEdit(true);
		
		/*
		setParameterListGrid.addCellClickHandler(new CellClickHandler() {
			@Override
			public void onCellClick(CellClickEvent event) {
				// TODO Auto-generated method stub
				
                boolean checked = event.getRecord().getAttributeAsBoolean("checked");
                
                if(checked){
                	event.getRecord().setAttribute("checked", !checked);
                	setParameterListGrid.deselectRecord(event.getRecord());
                }else{
                	event.getRecord().setAttribute("checked", !checked);
                	setParameterListGrid.selectRecord(event.getRecord());
                }
                setParameterListGrid.saveAllEdits();
                setParameterListGrid.refreshFields();
			}
		});
		*/
		
		VLayout buttomLayout = new VLayout();
		buttomLayout.addMember(setParameterListGrid);
		buttomLayout.setWidth100();
		buttomLayout.setHeight("*");
		
		resetButton = new ToolStripButton();
		resetButton.setTitle("Delete");
		resetButton.setIcon("closha/icon/delete.png");

		selectButton = new ToolStripButton();
		selectButton.setTitle("Confirm");
		selectButton.setIcon("closha/icon/accept.png");

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.addFill();
		toolStrip.addButton(resetButton);
		toolStrip.addButton(selectButton);

		setIsModal(true);
		setShowModalMask(true);
		setWidth(900);
		setHeight(500);
		setTitle("Select a project parameter");
		setHeaderIcon("closha/icon/debugt_obj.gif");
		setShowMaximizeButton(true);
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setCanDragReposition(true);
		setCanDragResize(true);
		setShowShadow(false);
		centerInPage();
		setAutoCenter(true);
		addItem(topLayout);
		addItem(buttomLayout);
		addItem(toolStrip);
		
		bind();
	}
}