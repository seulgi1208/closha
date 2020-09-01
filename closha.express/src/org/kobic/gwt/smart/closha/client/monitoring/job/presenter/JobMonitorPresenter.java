package org.kobic.gwt.smart.closha.client.monitoring.job.presenter;

import java.util.ArrayList;
import java.util.List;

import org.kobic.gwt.smart.closha.client.controller.BatchSystemContoller;
import org.kobic.gwt.smart.closha.client.monitoring.job.record.HostInformationListGridRecord;
import org.kobic.gwt.smart.closha.client.monitoring.job.record.JobInformationListGridRecord;
import org.kobic.gwt.smart.closha.client.monitoring.job.record.JobStatusListGridRecord;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.model.job.scheduler.HostsModel;
import org.kobic.gwt.smart.closha.shared.model.job.scheduler.JobStatusModel;
import org.kobic.gwt.smart.closha.shared.model.job.scheduler.JobInformationModel;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class JobMonitorPresenter implements Presenter{

	@SuppressWarnings("unused")
	private HandlerManager eventBus;
	private Display display; 
	
	public interface Display{
		VLayout asWidget();
		ListGrid getJobListGrid();
		ListGrid getHostListGrid();
		ListGrid getInfoGrid();
		
		ImgButton getDeleteButton();
		ImgButton getJobRefreshButton();
		ImgButton getHostRefreshButton();
	}
	
	public JobMonitorPresenter(HandlerManager eventBus, Display view){
		this.eventBus = eventBus;
		this.display = view;
	}
	
	@Override
	public void init(){
		bind();
	}
	
	@Override
	public void bind(){
		getSGEStateData();
		getClusterHostData();
		jobStateListGridCellClickEvent();
		refreshButtonClickEvent();
		jobDeleteButtonClickEvent();
	}
	
	private List<JobInformationModel> getJobMonitoringCheckList(){
		ListGridRecord[] records = new JobInformationListGridRecord[]{};
		records = display.getJobListGrid().getSelectedRecords();
		List<JobInformationModel> list = new ArrayList<JobInformationModel>();
		
		for(int i = 0; i < records.length; i++){
			JobInformationListGridRecord record = (JobInformationListGridRecord) records[i];
			
			JobInformationModel sm = new JobInformationModel();
			sm.setJobID(record.getJobID());
			sm.setName(record.getName());
			sm.setPriority(record.getPriority());
			sm.setQueue(record.getQueue());
			sm.setSlots(record.getSlots());
			sm.setState(record.getState());
			sm.setSubmissionDate(record.getSubmissionTime());
	
			list.add(sm);
		}
		return list;
	}
	
	private void jobDeleteButtonClickEvent(){
		display.getDeleteButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
			
				final List<String> jobIDs = new ArrayList<String>();
				
				List<JobInformationModel> list = getJobMonitoringCheckList();
				
				if(list.size() == 0){
					SC.say("Please choose at least 1 job.");
				}else{
					for(JobInformationModel sm : list){
						jobIDs.add(sm.getJobID());
					}
					
					SC.confirm("Warning", CommonUtilsGwt.join(jobIDs, ",", "[]") + "Are you sure you want to stop analyzing the job? <br/>" +
							"Stopped job cannot be restored.", new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							// TODO Auto-generated method stub
							if(value){
								jobDeletesRPC(jobIDs);
							}
						}
					});
				}
			}
		});
	}
	
	private void jobDeletesRPC(List<String> jobIDs){
		BatchSystemContoller.Util.getInstance().jobDeletes(jobIDs, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				
				Timer timer = new Timer() {
					@Override
					public void run() {
						getSGEStateData();
					}
				};
				timer.schedule(3000);
			}
		});
	}

	private void refreshButtonClickEvent(){
		display.getJobRefreshButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				getSGEStateData();
			}
		});
		
		display.getHostRefreshButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				getClusterHostData();
			}
		});
	}
	
	private void setHostListGridDataBind(List<HostsModel> list){
		HostInformationListGridRecord[] records = new HostInformationListGridRecord[list.size()];
		
		for(int i = 0; i < list.size(); i++){
			HostsModel sh = list.get(i);
			
			records[i] = new HostInformationListGridRecord(sh.getHost(), sh.getLoad(), 
					sh.getCpu(), sh.getMemTo(), sh.getMemUse(), sh.getSwapTo(), sh.getSwapUse());
			
		}
		display.getHostListGrid().setData(records);
	}
	
	private void getClusterHostData(){
		BatchSystemContoller.Util.getInstance().getClusterHostInfo(new AsyncCallback<List<HostsModel>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(List<HostsModel> list) {
				// TODO Auto-generated method stub
				setHostListGridDataBind(list);
			}
		});
	}
	
	private void jobStateListGridCellClickEvent(){
		display.getJobListGrid().addCellClickHandler(new CellClickHandler() {
			@Override
			public void onCellClick(CellClickEvent event) {
				// TODO Auto-generated method stub
				JobInformationListGridRecord record = (JobInformationListGridRecord) event.getRecord();
				String jobID = record.getAttributeAsString("jobID");
				
				BatchSystemContoller.Util.getInstance().getJobDetailInfo(jobID, new AsyncCallback<List<JobStatusModel>>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println(caught.getCause() + ":" + caught.getMessage());
					}
					@Override
					public void onSuccess(List<JobStatusModel> list) {
						// TODO Auto-generated method stub
						JobStatusListGridRecord[] records = new JobStatusListGridRecord[list.size()];
						
						for(int i = 0; i < list.size(); i++){
							JobStatusModel sj = list.get(i);
							records[i] = new JobStatusListGridRecord(sj.getKey(), sj.getValue());
						}
						display.getInfoGrid().setData(records);
					}
				});
			}
		});
	}
	
	private void setSGEStateDataBind(List<JobInformationModel> list){
		JobInformationListGridRecord records[] = new JobInformationListGridRecord[list.size()];
		
		for(int i = 0; i < list.size(); i++){
			JobInformationModel sm = list.get(i);
			
			records[i] = new JobInformationListGridRecord(sm.getQueue(), sm.getSlots(), sm.getJobID(), 
					sm.getName(), sm.getPriority(), sm.getState(), sm.getSubmissionDate());
		}
		display.getJobListGrid().setData(records);
	}
	
	private void getSGEStateData(){
		BatchSystemContoller.Util.getInstance().getJobStates(new AsyncCallback<List<JobInformationModel>>() {
			@Override
			public void onSuccess(List<JobInformationModel> list) {
				// TODO Auto-generated method stub
				setSGEStateDataBind(list);
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
		container.addMember(display.asWidget());
		init();
	}
}
