package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import java.util.ArrayList;
import java.util.List;

import org.kobic.gwt.smart.closha.client.controller.InstancePipelineController;
import org.kobic.gwt.smart.closha.client.event.draw.CloseFunctionWindowEvent;
import org.kobic.gwt.smart.closha.client.instantce.pipeline.record.InstancePipelineExeListGridRecord;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineExeModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class JobHistoryWindow extends Window{

	private ListGrid historyListGrid;
	private ToolStrip toolStrip;
	private ToolStripButton deleteButton;
	private ToolStripButton refreshButton;
	private String instanceID;
	private String projectName;
	private HandlerManager eventBus;
	
	private void closed(){
		
		eventBus.fireEvent(new CloseFunctionWindowEvent(projectName, Constants.EXCUTE_HISTORY_WINDOW_ID));
		
		destroy();
	}
	
	private void init(){
		setExecuteHistoryListGridDataBind();
		bind();
	}
	
	private void bind(){
		
		this.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				// TODO Auto-generated method stub
				centerInPage();
			}
		});
		
		this.addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				// TODO Auto-generated method stub
				closed();
			}
		});
		
		setDeleteButtonEvent();
	}
	
	private void setDeleteButtonEvent(){
		deleteButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				List<String> executeIDs = new ArrayList<String>();
				
				for(ListGridRecord record : historyListGrid.getSelectedRecords()){
					executeIDs.add(record.getAttributeAsString("exeID"));
				}
				
				InstancePipelineController.Util.getInstance().removeInstancePipelineExecuteHistory(
						instanceID, executeIDs, new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println(caught.getCause() + ":" + caught.getMessage());
					}
					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						setExecuteHistoryListGridDataBind();
					}
				});
			}
		});
	}
	
	private void setExecuteHistoryListGridDataBind(){
		InstancePipelineController.Util.getInstance().getInstancePipelineJobHistoryData(instanceID, 
				new AsyncCallback<List<InstancePipelineExeModel>>() {
			
			@Override
			public void onSuccess(List<InstancePipelineExeModel> list) {
				// TODO Auto-generated method stub
				InstancePipelineExeListGridRecord records[] = new InstancePipelineExeListGridRecord[list.size()];
				for(int i = 0; i < list.size(); i++){
					InstancePipelineExeModel model = list.get(i);
					records[i] = new InstancePipelineExeListGridRecord(model.getInstanceID(), 
							model.getPipelineID(), model.getExeID(), 
							model.getStartDate(), model.getEndDate());
					
				}
				
				historyListGrid.setData(records);
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
		});
	}
	
	public JobHistoryWindow(HandlerManager eventBus, String projectName, String instanceID){
		
		this.instanceID = instanceID;
		this.eventBus = eventBus;
		this.projectName = projectName;
		
		historyListGrid = new JobHistoryListGrid();
		
		setTitle(projectName + " Job History");
        setHeaderIcon("closha/icon/clock.png");
        setWidth(600);  
        setHeight(250); 
        setCanDragReposition(true);  
        setCanDragResize(true);
        setShowCloseButton(true);
        setShowMinimizeButton(false);
        
        refreshButton = new ToolStripButton("Refresh");
        refreshButton.setIcon("closha/icon/new_refresh.png");
        
        deleteButton = new ToolStripButton("Delete");
        deleteButton.setIcon("closha/icon/delete.png");
        
		toolStrip = new ToolStrip();
		toolStrip.addFill();
		toolStrip.addButton(refreshButton);
		toolStrip.addButton(deleteButton);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.addMembers(historyListGrid, toolStrip);
        
		addItem(layout);
		
		init();
	}
}