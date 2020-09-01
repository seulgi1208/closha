package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import java.util.ArrayList;
import java.util.List;

import org.kobic.gwt.smart.closha.client.event.draw.CloseFunctionWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.DeleteConnectionEvent;
import org.kobic.gwt.smart.closha.client.event.draw.DeleteModuleEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ReloadConnectionWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ReloadConnectionWindowEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.RemoveRegisterEvents;
import org.kobic.gwt.smart.closha.client.event.draw.RemoveRegisterEventsHandler;
import org.kobic.gwt.smart.closha.client.pipeline.design.record.ModuleConnectionListGridRecord;
import org.kobic.gwt.smart.closha.client.pipeline.design.record.ModuleLinkedListGridRecord;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.design.IntegrationModuleConnectModel;
import org.kobic.gwt.smart.closha.shared.model.design.ModuleInfomModel;
import org.kobic.gwt.smart.closha.shared.model.design.ModuleConnectionInfomModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ModuleLinkedWindow extends Window{
	
	private HandlerManager eventBus;
	private HandlerRegistration registerHandler[];
	private String projectName;
	
	private ToolStripButton removeModuleButton;
	private ToolStrip moduleToolStrip;
	
	private ToolStripButton removeConnectButton;	
	private ToolStrip connectorToolStrip;
	
	private ListGrid moduleListGrid;
	private ListGrid connectionLinkedListGrid;
	
	private void init(IntegrationModuleConnectModel integrationLinkedModel){
		setListGridsDataBinding(integrationLinkedModel);
        bind();
	}

	private void bind(){
		
		this.addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				// TODO Auto-generated method stub
				closed();
			}
		});
		
		this.addDoubleClickHandler(new DoubleClickHandler() {
			
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				// TODO Auto-generated method stub
				centerInPage();
			}
		});
		
		registerHandler[0] = eventBus.addHandler(RemoveRegisterEvents.TYPE, new RemoveRegisterEventsHandler() {
			@Override
			public void removeRegisterEventHandler(RemoveRegisterEvents event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					for(int i = 0; i < registerHandler.length; i++){
						registerHandler[i].removeHandler();
					}
				}
			}
		});
		
		registerHandler[1] = eventBus.addHandler(ReloadConnectionWindowEvent.TYPE, new ReloadConnectionWindowEventHandler() {
			@Override
			public void reLoadConnectionWindowData(ReloadConnectionWindowEvent event) {
				// TODO Auto-generated method stub
				if(event.getProjectName().equals(projectName)){
					setListGridsDataBinding(event.getIntegrationLinkedModel());
				}
			}
		});
		
		removeConnectButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				List<String> keys = new ArrayList<String>();
				
				for(ListGridRecord records : connectionLinkedListGrid.getSelectedRecords()){
					ModuleConnectionListGridRecord record = (ModuleConnectionListGridRecord) records;
					
					String sKey = record.getAttributeAsString("startKey");
					String eKey = record.getAttributeAsString("endKey");
					
					keys.add(sKey + "&" + eKey);
					
					eventBus.fireEvent(new DeleteConnectionEvent(keys));
				}
			}
		});
		
		removeModuleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub				
				List<String> keys = new ArrayList<String>();
				List<String> checkKeys = new ArrayList<String>();
				String key = "";

				for(ListGridRecord records : moduleListGrid.getSelectedRecords()){
					ModuleLinkedListGridRecord record = (ModuleLinkedListGridRecord) records;
					key = record.getAttributeAsString("identifier");
					
					if(Integer.parseInt(key) == Constants.MODULE_END_KEY || 
							Integer.parseInt(key) == Constants.MODULE_START_KEY){
						checkKeys.add(record.getAttributeAsString("name"));
					}else{
						keys.add(key);
					}
				}
				
				if(checkKeys.size() > 0){
					String waringSentence = "";
					for(String moduleName : checkKeys){
						waringSentence += moduleName + Constants.SPLIT_SAPCE;
					}
					SC.say("[" + waringSentence + "]You cannot delete START and END module.");
				}else{
					eventBus.fireEvent(new DeleteModuleEvent(keys));
				}
			}
		});
	}
	
	private void closed(){
		if(registerHandler.length != 0){
			for(int i = 0; i < registerHandler.length; i++){
				registerHandler[i].removeHandler();
			}
		}
		
		eventBus.fireEvent(new CloseFunctionWindowEvent(projectName, Constants.CONNECTER_MODULE_WINDOW_ID));
		
		destroy();
	}

	
	private void setListGridsDataBinding(IntegrationModuleConnectModel integrationLinkedModel){
		List<ModuleInfomModel> functionList = integrationLinkedModel.getModuleFunctionModel();
		ModuleLinkedListGridRecord[] moduleLinkedListGridRecord = new ModuleLinkedListGridRecord[functionList.size()];
		
		for(int i = 0; i < functionList.size(); i++){
			ModuleInfomModel model = functionList.get(i);
			moduleLinkedListGridRecord[i] = new ModuleLinkedListGridRecord(model.getLeft(), model.getTop(), 
					model.getId(), model.getIdentifier(), model.getName(), "module");
		}
		moduleListGrid.setData(moduleLinkedListGridRecord);
		
		List<ModuleConnectionInfomModel> linksList = integrationLinkedModel.getModuleLinksModel();
		ModuleConnectionListGridRecord[] connectionLinkedListGridRecord = new ModuleConnectionListGridRecord[linksList.size()];
		
		int x = 0;
		int y = 0;
		
		for(int i = 0; i < linksList.size(); i++){
			ModuleConnectionInfomModel model = linksList.get(i);
			
			for(int[] p : model.getPoints()){
				x = p[0];
				y = p[1];
			}
			
			connectionLinkedListGridRecord[i] = new ModuleConnectionListGridRecord(model.getStartID(), model.getEndID(), 
					model.getStartModuleName(), model.getEndModuleName(), model.getStartKey(), model.getEndKey(), 
					x, y);
		}
		
		connectionLinkedListGrid.setData(connectionLinkedListGridRecord);
	}
	
	public ModuleLinkedWindow(HandlerManager eventBus, String projectName, IntegrationModuleConnectModel integrationLinkedModel){
		
		this.eventBus = eventBus;
		this.projectName = projectName;
		
		registerHandler = new HandlerRegistration[2];
        moduleListGrid = new ModuleLinkedListGrid();
        
        removeModuleButton = new ToolStripButton("Delete");
        removeModuleButton.setIcon("closha/icon/delete.png");
		
        moduleToolStrip = new ToolStrip();
        moduleToolStrip.addFill();
        moduleToolStrip.addButton(removeModuleButton);
		
		VLayout moduleLayout = new VLayout();
        moduleLayout.setHeight100();
        moduleLayout.setWidth100();
        moduleLayout.addMember(moduleListGrid);
        moduleLayout.addMember(moduleToolStrip);
        
        Tab tTab1 = new Tab("Analysis Program List", "closha/icon/application_view_list.png");  
        tTab1.setWidth(200);
        tTab1.setPane(moduleLayout);
       
        connectionLinkedListGrid = new ModuleConnectListGrid(eventBus, projectName);
        
        removeConnectButton = new ToolStripButton("Disconnect");
        removeConnectButton.setIcon("closha/icon/disconnect.png");
        
        connectorToolStrip = new ToolStrip();
        connectorToolStrip.addFill();
        connectorToolStrip.addButton(removeConnectButton);
        
        VLayout connectorLayout = new VLayout();
        connectorLayout.setHeight100();
        connectorLayout.setWidth100();
        connectorLayout.addMember(connectionLinkedListGrid);
        connectorLayout.addMember(connectorToolStrip);
        
        Tab tTab2 = new Tab("Connected Information", "closha/icon/connect.png");
        tTab2.setWidth(200);
        tTab2.setPane(connectorLayout);
        
        final TabSet tabSet = new TabSet();  
        tabSet.setTabBarPosition(Side.TOP);  
        tabSet.setWidth100();  
        tabSet.setHeight100();
        tabSet.addTab(tTab1);  
        tabSet.addTab(tTab2);
        
    	setTitle("Analysis Pipeline Connected Information");
        setHeaderIcon("closha/icon/connect.png");
        setWidth(600);  
        setHeight(300);  
        setCanDragReposition(true);  
        setCanDragResize(true);
        setShowCloseButton(true);
        setShowMinimizeButton(false);
        addItem(tabSet);
        
        init(integrationLinkedModel);
	}
}
