package org.kobic.gwt.smart.closha.client.section.job.history.presenter;

import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.controller.InstancePipelineController;
import org.kobic.gwt.smart.closha.client.frame.right.event.JobHistoryDataLoadEvent;
import org.kobic.gwt.smart.closha.client.frame.right.event.JobHistoryDataLoadEventHandler;
import org.kobic.gwt.smart.closha.client.frame.right.event.JobHistoryFilteringEvent;
import org.kobic.gwt.smart.closha.client.frame.right.event.JobHistoryFilteringEventHandler;
import org.kobic.gwt.smart.closha.client.instantce.pipeline.record.InstancePipelineListGridRecord;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.section.job.history.component.JobHistoryListGrid;
import org.kobic.gwt.smart.closha.client.section.job.history.event.JobHistoryStackDataRefreshEvent;
import org.kobic.gwt.smart.closha.client.section.job.history.event.JobHistoryStackDataReflashEventHandler;
import org.kobic.gwt.smart.closha.shared.Messages;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class JobHistoryPresenter implements Presenter{

	private final HandlerManager eventBus;
	private final Display display;
	private final UserDto userDto;
	private Map<String, String> config;
	
	private ListGrid historyListGrid;
	private Label label;
	
	public interface Display{
		VLayout asLayout();
	}
	
	public JobHistoryPresenter(HandlerManager eventBus, Map<String, String> config, UserDto userDto, Display view){
		this.eventBus = eventBus;
		this.display = view;
		this.userDto = userDto;
		this.config = config;
	}
	
	@Override
	public void init(){
		
		label = new Label(Messages.HISTORY_LABEL_MESSAGE);  
        label.setParentCanvas(display.asLayout());  
        label.setWidth100();
        label.setHeight(200);
        label.setMaxHeight(500);
        label.setBackgroundColor("#FFFFD0");  
        label.setVisibility(Visibility.HIDDEN);  
        label.setValign(VerticalAlignment.CENTER);  
        label.setAlign(Alignment.CENTER);  
        label.setAnimateTime(500);

        ListGridField pipelineNameField = new ListGridField("instanceName", "Project");  
        pipelineNameField.setAlign(Alignment.LEFT);
        
        ListGridField statusField = new ListGridField("stateImg", "Status");
        statusField.setType(ListGridFieldType.IMAGE);
        statusField.setAlign(Alignment.CENTER);
        statusField.setWidth(70);
        statusField.setShowDefaultContextMenu(false);
        
        ListGridField reportField = new ListGridField("report", "Report");
        reportField.setAlign(Alignment.CENTER);
        reportField.setWidth(70);   
        reportField.setShowDefaultContextMenu(false);

        ListGridField projectPathField = new ListGridField("projectPath", "Project Path");
        projectPathField.setAlign(Alignment.CENTER);
        projectPathField.setHidden(true);
        
        historyListGrid = new JobHistoryListGrid(eventBus);
		historyListGrid.setWidth100();
		historyListGrid.setHeight("50%");
        historyListGrid.setFields(pipelineNameField, statusField, 
        		reportField, projectPathField);  
        
        display.asLayout().addMember(historyListGrid);
        display.asLayout().addMember(label);
		
		bind();
	}
	
	@Override
	public void bind(){
		getHistoryListData();
		cellClickHistoryListGridContent();
		historyLabelResetFireEvent();
		stateChangedDataFireEvent();
		reflashHistoryDataFireEvent();
	}
	
	private void reflashHistoryDataFireEvent(){
		eventBus.addHandler(JobHistoryStackDataRefreshEvent.TYPE, new JobHistoryStackDataReflashEventHandler() {
			@Override
			public void historyStackDataRefreshEvent(JobHistoryStackDataRefreshEvent event) {
				// TODO Auto-generated method stub
				getHistoryListData();
			}
		});
	}
	
	private void stateChangedDataFireEvent(){
		eventBus.addHandler(JobHistoryFilteringEvent.TYPE, new JobHistoryFilteringEventHandler() {
			@Override
			public void stateChangedEvent(JobHistoryFilteringEvent event) {
				// TODO Auto-generated method stub
				InstancePipelineController.Util.getInstance().getUserInstancePipelines(config, userDto.getUserId(), event.getState(), 
						new AsyncCallback<List<InstancePipelineModel>>() {
					@Override
					public void onSuccess(List<InstancePipelineModel> list) {
						// TODO Auto-generated method stub
						setHistoryListGridDataBinding(list);
					}
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println(caught.getCause() + ":" + caught.getMessage());
					}
				});
			}
		});
	}
	
	private void historyLabelResetFireEvent(){
		eventBus.addHandler(JobHistoryDataLoadEvent.TYPE, new JobHistoryDataLoadEventHandler() {
			@Override
			public void resetHistoryLabel(JobHistoryDataLoadEvent event) {
				// TODO Auto-generated method stub
				label.setContents(Messages.HISTORY_LABEL_MESSAGE);
				getHistoryListData();
			}
		});
	}
	
	private String getInstancePipelineDesc(String instanceDesc){
		return  "<p align=\"justify\" style=\"font-size:12px;\">" + instanceDesc + "</p>";
	}
	
	private void cellClickHistoryListGridContent(){
		historyListGrid.addCellClickHandler(new CellClickHandler() {
			
			@Override
			public void onCellClick(CellClickEvent event) {
				// TODO Auto-generated method stub
				
				InstancePipelineListGridRecord record = (InstancePipelineListGridRecord) event.getRecord();
				String instancePipelineDesc = record.getAttributeAsString("instanceDesc");
				
				if(label.isVisible()){
					label.animateHide(AnimationEffect.SLIDE);
				}
				label.setContents(getInstancePipelineDesc(instancePipelineDesc));
				label.animateShow(AnimationEffect.SLIDE);
				
				Timer timer = new Timer() {
					@Override
					public void run() {
						label.animateHide(AnimationEffect.FADE);
					}
				};
				timer.schedule(5000);
			}
		});
	}
	
	private void setHistoryListGridDataBinding(List<InstancePipelineModel> list){
		InstancePipelineListGridRecord[] records = new InstancePipelineListGridRecord[list.size()];
		
		for(int i = 0; i < list.size(); i++){
			InstancePipelineModel iModel = list.get(i);
			
			records[i] = new InstancePipelineListGridRecord(iModel.getInstanceID(), iModel.getPipelineID(), 
					iModel.getExeID(), iModel.getPipelineName(), iModel.getInstanceOwner(), iModel.getOwnerEmail(), 
					iModel.getInstanceName(), iModel.getInstanceDesc(), iModel.getInstanceXML(), iModel.getStatus(), 
					iModel.getExeCount(), iModel.getCreateDate(), CommonUtilsGwt.getStatusImgSrc(iModel.getStatus()), 
					iModel.getProjectPath());
		}
		historyListGrid.setData(records);
	}
	
	private void getHistoryListData(){
		InstancePipelineController.Util.getInstance().getUserInstancePipelines(config, userDto.getUserId(), new AsyncCallback<List<InstancePipelineModel>>() {
			@Override
			public void onSuccess(List<InstancePipelineModel> list) {
				// TODO Auto-generated method stub
				setHistoryListGridDataBinding(list);
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
		});
	}
	
	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.asLayout());
		init();
	}
}