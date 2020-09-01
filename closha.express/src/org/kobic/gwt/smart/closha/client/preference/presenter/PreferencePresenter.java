package org.kobic.gwt.smart.closha.client.preference.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.controller.InstancePipelineController;
import org.kobic.gwt.smart.closha.client.event.content.ClearTransferListGridEvent;
import org.kobic.gwt.smart.closha.client.event.content.ClearTransferListGridEventHandler;
import org.kobic.gwt.smart.closha.client.event.content.PreferencesRemoveRegisterEvents;
import org.kobic.gwt.smart.closha.client.event.content.PreferencesRemoveRegisterEventsHandler;
import org.kobic.gwt.smart.closha.client.instantce.pipeline.record.InstancePipelineListGridRecord;
import org.kobic.gwt.smart.closha.client.preference.component.SharePipelineUserWindow;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;
import org.kobic.gwt.smart.closha.shared.model.preference.ShareInstancePipelineModel;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class PreferencePresenter implements Presenter{

	private final HandlerManager eventBus;
	private HandlerRegistration registerHandler[];
	
	private final Display display;
	private final UserDto userDto;
	private Window shareWindow;
	private Map<String, String> config;
	
	private int TOTAL_COUNT = 0;
	private int RUN_COUNT = 0;
	private int WAIT_COUNT = 0;
	private int COMPLETE_COUNT = 0;
	private int ERROR_COUNT = 0;
	
	public interface Display{
		VLayout getBottomLayout();
		
		ListGrid getPreferencesListGrid();
		ListGrid getTransferListGrid();
		
		ToolStripButton getClearButton();
		ToolStripButton getShareButton();
		ToolStripButton getReflashButton();
		
		ToolStripButton getTotalCountButton();
		ToolStripButton getRunCountButton();
		ToolStripButton getWaitCountButton();
		ToolStripButton getCompleteButton();
		ToolStripButton getErrorButton();
	}
	
	public PreferencePresenter(HandlerManager eventBus, Map<String, String> config, UserDto userDto, Display view){
		this.eventBus = eventBus;
		this.display = view;
		this.userDto = userDto;
		this.config = config;
	}
	
	@Override
	public void init(){
		registerHandler = new HandlerRegistration[2];
		bind();
	}
	
	@Override
	public void bind(){
		setPreferencesListGridDataBinding();
		cellClickPreferencesListGrid();
		clickClearButtonEvent();
		clickShareButtonEvent();
		clearTransferListGridFireEvent();
		removeRegisterFireEvent();
		reflashButtonEvent();
	}
	
	private void reflashButtonEvent(){
		display.getReflashButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				setPreferencesListGridDataBinding();
			}
		});
	}
	
	private void removeRegisterFireEvent(){
		registerHandler[0] = eventBus.addHandler(PreferencesRemoveRegisterEvents.TYPE, new PreferencesRemoveRegisterEventsHandler() {
			@Override
			public void preferencesRemoveRegisterEvents(
					PreferencesRemoveRegisterEvents event) {
				// TODO Auto-generated method stub
				if(registerHandler.length != 0){
					for(int i = 0; i < registerHandler.length; i++){
						registerHandler[i].removeHandler();
					}
				}
			}
		});
	}
	
	private void clearTransferListGridFireEvent(){
		registerHandler[1] = eventBus.addHandler(ClearTransferListGridEvent.TYPE, new ClearTransferListGridEventHandler() {
			@Override
			public void clearTransferListGridEvent(ClearTransferListGridEvent event) {
				// TODO Auto-generated method stub
				clearTransferListGrid();
			}
		});
	}
	
	private void clickShareButtonEvent(){
		display.getShareButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				List<ShareInstancePipelineModel> list = new ArrayList<ShareInstancePipelineModel>();
				
				RecordList records = display.getTransferListGrid().getDataAsRecordList();
				for(int i = 0; i < records.getLength(); i++){
					ShareInstancePipelineModel model = new ShareInstancePipelineModel();
					model.setInstanceID(records.get(i).getAttributeAsString("instanceID"));
					model.setInstanceName(records.get(i).getAttributeAsString("instanceName"));
					
					list.add(model);
				}
				
				shareWindow = new SharePipelineUserWindow(eventBus, config, userDto, list);
				shareWindow.show();
			}
		});
	}
	
	private void clickClearButtonEvent(){
		display.getClearButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				clearTransferListGrid();
			}
		});
	}
	
	private void clearTransferListGrid(){
		RecordList records = display.getTransferListGrid().getDataAsRecordList();
		
		for(int i = 0; i < records.getLength(); i++){
			display.getTransferListGrid().removeData((InstancePipelineListGridRecord)records.get(i));
		}
	}
	
	private void cellClickPreferencesListGrid(){
		display.getPreferencesListGrid().addSelectionChangedHandler(new SelectionChangedHandler() {
			
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				// TODO Auto-generated method stub
				display.getTransferListGrid().setData(display.getPreferencesListGrid().getSelectedRecords());
			}
		});
	}
	
	private void setPreferencesListGridDataBinding(){

		InstancePipelineController.Util.getInstance().getUserInstancePipelines(config, userDto.getUserId(), new AsyncCallback<List<InstancePipelineModel>>() {
			@Override
			public void onSuccess(List<InstancePipelineModel> list) {
				// TODO Auto-generated method stub
				TOTAL_COUNT = list.size();
				countingInit();
				
				InstancePipelineListGridRecord[] records = new InstancePipelineListGridRecord[list.size()];
				
				for(int i = 0; i < list.size(); i++){
					InstancePipelineModel iModel = list.get(i);
					stateCounting(iModel.getStatus());
					records[i] = new InstancePipelineListGridRecord(iModel.getInstanceID(), iModel.getPipelineID(), 
							iModel.getExeID(), iModel.getPipelineName(), iModel.getInstanceOwner(), iModel.getOwnerEmail(), 
							iModel.getInstanceName(), iModel.getInstanceDesc(), iModel.getInstanceXML(), iModel.getStatus(), 
							iModel.getExeCount(), iModel.getCreateDate(), CommonUtilsGwt.getStatusImgSrc(iModel.getStatus()),
							iModel.getProjectPath());
				}
				
				setProjectCounting();
				display.getPreferencesListGrid().setData(records);
				
				for (InstancePipelineListGridRecord record : records) {  
		            if (record.getStatus() == 1) {  
		                record.setEnabled(false);  
		            }  
		        }
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
		});
	}
	
	private void countingInit(){
		WAIT_COUNT = 0;
		RUN_COUNT = 0;
		COMPLETE_COUNT = 0;
		ERROR_COUNT = 0;	
	}
	
	private void stateCounting(int state){
		if(state == 0){
			WAIT_COUNT++;
		}else if(state == 1){
			RUN_COUNT++;
		}else if(state == 2){
			COMPLETE_COUNT++;
		}else{
			ERROR_COUNT++;	
		}
	}
	
	private void setProjectCounting(){
		display.getTotalCountButton().setTitle(Constants.TOTAL_COUNT + TOTAL_COUNT);
		display.getRunCountButton().setTitle(Constants.RUN_COUNT + RUN_COUNT);
		display.getWaitCountButton().setTitle(Constants.WAIT_COUNT + WAIT_COUNT);
		display.getCompleteButton().setTitle(Constants.COMPLETE_COUNT + COMPLETE_COUNT);
		display.getErrorButton().setTitle(Constants.ERROR_COUNT + ERROR_COUNT);
	}
	
	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.getBottomLayout());
		init();
	}
}
