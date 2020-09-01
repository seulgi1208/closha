package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.controller.ToolkitBarWindowController;
import org.kobic.gwt.smart.closha.client.event.draw.ConnectionDataWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ConsolLogWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.DrawXMLResetEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ExecuteProjectEvent;
import org.kobic.gwt.smart.closha.client.event.draw.HistoryDataWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.PipelineRegistrationEvent;
import org.kobic.gwt.smart.closha.client.event.draw.PipelineXMLSaveEvent;
import org.kobic.gwt.smart.closha.client.event.draw.RemoveRegisterEvents;
import org.kobic.gwt.smart.closha.client.event.draw.RemoveRegisterEventsHandler;
import org.kobic.gwt.smart.closha.client.event.draw.SettingRunButtonRegisterEvent;
import org.kobic.gwt.smart.closha.client.event.draw.SettingRunButtonRegisterHandler;
import org.kobic.gwt.smart.closha.client.event.draw.TabChangeStateReloadEvent;
import org.kobic.gwt.smart.closha.client.event.draw.TabChangeStateReloadEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.ToolBoxParameterWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ToolBoxWorkflowStatusEvent;
import org.kobic.gwt.smart.closha.shared.Messages;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ToolkitBoxWindow extends Window{
	
	private class stateBean{
		
		private int status;
		
		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}
	
	private HandlerManager eventBus;
	
	private IButton executeButton;
	private IButton saveButton;
	private IButton connectorButton;
	private IButton resetButton;
	private IButton parameterButton;
	private IButton stateButton;
	private IButton uploadButton;
	private IButton historyButton;
	private IButton registerButton;
	private IButton logButton;
	
	private String projectName;
	private String instanceID;
	
	private boolean isCheckup = true;
	
	private HandlerRegistration registration[];
	
	private stateBean stateBean;
	
	public ToolkitBoxWindow(HandlerManager eventBus, String projectName,
			String instanceID, String userID, Map<String, String> config) {
		
		this.eventBus = eventBus;
		this.projectName = projectName;
		this.instanceID = instanceID;
		this.registration = new HandlerRegistration[13];
		this.stateBean = new stateBean();
		
		saveButton = new IButton("Save");
		saveButton.setShowRollOver(false);
		saveButton.setIcon("closha/icon/disk.png");  
		saveButton.setActionType(SelectionType.BUTTON);
		
		executeButton = new IButton();
		executeButton.setShowRollOver(false);
		executeButton.setActionType(SelectionType.BUTTON);
		
		connectorButton = new IButton("Connection");
		connectorButton.setShowRollOver(false);  
		connectorButton.setIcon("closha/icon/connect.png");  
		connectorButton.setActionType(SelectionType.BUTTON);
		
		resetButton = new IButton("Reset");
		resetButton.setShowRollOver(false);  
		resetButton.setIcon("closha/icon/new_reset.png");  
		resetButton.setActionType(SelectionType.BUTTON);
		
		parameterButton = new IButton("Parameter");
		parameterButton.setShowRollOver(false);  
		parameterButton.setIcon("closha/icon/textfield_rename.png");  
		parameterButton.setActionType(SelectionType.BUTTON);
		
		stateButton = new IButton("Status");
		stateButton.setShowRollOver(false);  
		stateButton.setIcon("closha/icon/current_work_16.png");  
		stateButton.setActionType(SelectionType.BUTTON);
		
		uploadButton = new IButton("KoDS");
		uploadButton.setShowRollOver(false);  
		uploadButton.setIcon("closha/icon/KoDS_logo16.png");  
		uploadButton.setActionType(SelectionType.BUTTON);
		
		historyButton = new IButton("History");
		historyButton.setShowRollOver(false);  
		historyButton.setIcon("closha/icon/time_opened.png");
		historyButton.setActionType(SelectionType.BUTTON);
		
		registerButton = new IButton("Register");
		registerButton.setShowRollOver(false);  
		registerButton.setIcon("closha/icon/add.png");
		registerButton.setActionType(SelectionType.BUTTON);
		
		logButton = new IButton("Console");
		logButton.setShowRollOver(false);  
		logButton.setIcon("silk/application_osx_terminal.png");
		logButton.setActionType(SelectionType.BUTTON);
		
		VLayout left = new VLayout();
		left.setHeight100();
		left.setWidth100();
		left.setAlign(Alignment.CENTER);
		left.setMembersMargin(3);
		left.addMember(executeButton);
		left.addMember(saveButton);
		left.addMember(uploadButton);
		left.addMember(connectorButton);
		left.addMember(registerButton);
		
		VLayout right = new VLayout();
		right.setHeight100();
		right.setWidth100();
		right.setAlign(Alignment.CENTER);
		right.setMembersMargin(3);
		right.addMember(stateButton);
		right.addMember(resetButton);
		right.addMember(historyButton);
		right.addMember(parameterButton);
		right.addMember(logButton);
		
		HLayout layout = new HLayout();
		layout.setAlign(Alignment.CENTER);
		layout.setPadding(3);
		layout.setHeight100();
		layout.setWidth100();
		layout.setMembersMargin(3);
		layout.addMember(left);
		layout.addMember(right);
		
		setTitle("Tool Box");
        setHeaderIcon("closha/icon/kservices.png");
        setWidth(225);  
        setHeight(175);  
        setCanDragReposition(true);  
        setCanDragResize(false);
        setShowCloseButton(false);
        addItem(layout);
        
        init(true);
	}	
	
	private void init(final boolean isEvent){
		ToolkitBarWindowController.Util.getInstance().getCurrentInstancePipelineState(instanceID, 
				new AsyncCallback<Integer>() {
			@Override
			public void onSuccess(Integer status) {
				// TODO Auto-generated method stub
				if(isEvent){
					setEvent();
				}
				stateBean.setStatus(status);
				setRunButtonView(status);
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
		});
	}
	
	private void setRunButtonView(int status){
		if(status == 0){
			executeButton.setTitle("Run");
			executeButton.setIcon("closha/icon/start.png");
		}else if(status == 1){
			executeButton.setTitle("Stop");
			executeButton.setIcon("closha/icon/stop_7.png");
		}else if(status == 2){
			executeButton.setTitle("Rerun");
			executeButton.setIcon("closha/icon/re_run.png");
		}else{
			executeButton.setTitle("Error");
			executeButton.setIcon("closha/icon/error_delete.png");
		}
	}
	
	private void setEvent(){
		
		registration[0] = eventBus.addHandler(RemoveRegisterEvents.TYPE, new RemoveRegisterEventsHandler() {
			@Override
			public void removeRegisterEventHandler(RemoveRegisterEvents event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					for(int i = 0; i < registration.length; i++){
						registration[i].removeHandler();
					}
				}
			}
		});
		
		registration[1] = executeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new ExecuteProjectEvent(projectName, stateBean.getStatus()));
			}
		});
		
		registration[2] = resetButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new DrawXMLResetEvent(projectName));
			}
		});
		
		registration[3] = saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new PipelineXMLSaveEvent(projectName));
			}
		});
		
		registration[4] = connectorButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new ConnectionDataWindowEvent(projectName));
			}
		});
		
		registration[5] = parameterButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new ToolBoxParameterWindowEvent(projectName));
			}
		});
		
		registration[6] = eventBus.addHandler(SettingRunButtonRegisterEvent.TYPE, new SettingRunButtonRegisterHandler() {
			@Override
			public void settingRegisterRunEventHandler(
					SettingRunButtonRegisterEvent event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					stateBean.setStatus(event.getStatus());
					setRunButtonView(event.getStatus());
				}
			}
		});
		
		registration[7] = eventBus.addHandler(TabChangeStateReloadEvent.TYPE, new TabChangeStateReloadEventHandler() {
			@Override
			public void tabChangeStateReloadEvent(TabChangeStateReloadEvent event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					init(false);
				}
			}
		});
		
		registration[8] = stateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(isCheckup){
					
					isCheckup = false;
					stateButton.setIcon("closha/icon/current_work_16_off.png");
					
					eventBus.fireEvent(new ToolBoxWorkflowStatusEvent(projectName));
					
					Timer timer = new Timer() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							isCheckup = true;
							stateButton.setIcon("closha/icon/current_work_16.png");  
						}
					};
					timer.schedule(60000);
				}else{
					SC.say(Messages.STATUS_UPDATE_MESSAGE);
				}
			}
		});
		
		registration[9] = historyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new HistoryDataWindowEvent(projectName));
			}
		});
		
		registration[10] = uploadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				SC.say("Please use the KoDS software presented by KOBIC.");
			}
		});
		
		registration[11] = registerButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				SC.confirm("Do you want to register this " + projectName + " project?", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						// TODO Auto-generated method stub
						if(value){
							eventBus.fireEvent(new PipelineRegistrationEvent(projectName));
						}
					}
				});
			}
		});
		
		registration[12] = logButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new ConsolLogWindowEvent(projectName));
			}
		});
	}
}
