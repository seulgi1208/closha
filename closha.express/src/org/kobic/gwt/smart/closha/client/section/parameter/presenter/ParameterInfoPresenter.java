package org.kobic.gwt.smart.closha.client.section.parameter.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.controller.CacheLiteClientController;
import org.kobic.gwt.smart.closha.client.event.draw.RemoveRegisterEvents;
import org.kobic.gwt.smart.closha.client.event.draw.RemoveRegisterEventsHandler;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.TransmitParameterDataEvent;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.TransmitParameterDataEventHandler;
import org.kobic.gwt.smart.closha.client.section.parameter.record.ParameterInfoRecord;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;
import org.kobic.gwt.smart.closha.shared.model.parameter.ParameterModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ParameterInfoPresenter implements Presenter{

	private final HandlerManager eventBus;
	private final Display display;
	private HandlerRegistration HandlerRegistration[];
	private String projectName;
	
	public interface Display{
		VLayout asLayout();
		ListGrid getParameterListGrid();
	}
	
	public ParameterInfoPresenter(HandlerManager eventBus, Display view, String projectName){
		this.eventBus = eventBus;
		this.display = view;
		this.projectName = projectName;
	}
	
	@Override
	public void bind(){
		removeRegisterFireEvent();
		reciveParameterDataFireEvent();
	}
	
	private void reciveParameterDataFireEvent(){
		HandlerRegistration[1] = eventBus.addHandler(TransmitParameterDataEvent.TYPE, new TransmitParameterDataEventHandler() {
			@Override
			public void parameterDataSendEvent(TransmitParameterDataEvent event) {
				// TODO Auto-generated method stub
				
				setSelectProjectParameterDtatBinding(event.getParameters());
			}
		});
	}
	
	private void removeRegisterFireEvent(){
		
		HandlerRegistration[0] = eventBus.addHandler(RemoveRegisterEvents.TYPE, new RemoveRegisterEventsHandler() {
			@Override
			public void removeRegisterEventHandler(RemoveRegisterEvents event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					
					for(int i = 0; i < HandlerRegistration.length; i++){
						HandlerRegistration[i].removeHandler();
					}
				}
			}
		});
	}
	
	private void setSelectProjectParameterDtatBinding(List<XmlParameterModel> parameters){
		
		ParameterInfoRecord records[] = new ParameterInfoRecord[parameters.size()];
		
		String typeImg = null;
		String cacheImg = null;
		
		final Map<String, ParameterModel> pMap = new HashMap<String, ParameterModel>();
		
		for(int i = 0; i < parameters.size(); i++){
			
			XmlParameterModel pm = parameters.get(i);
			
			if(pm.getParameterType().equals("INPUT") && (pm.getType().equals("FOLDER") || pm.getType().equals("FILE")) ){
				
				if(pm.getSetupValue().length() < 10 || pm.getSetupValue().equals("/")){
					cacheImg = "closha/icon/history_error.png";
				}else{
					cacheImg = "closha/icon/icon_loading.gif";
					
					ParameterModel param = new ParameterModel();
					param.setKey(pm.getId());
					param.setSetupValue(pm.getSetupValue());
					param.setExistCache(false);
					
					pMap.put(pm.getId(), param);
				}
			}else{
				cacheImg = "closha/icon/history_running.png";
			}
			
			if(pm.getType().equals("FOLDER")){
				typeImg = "closha/icon/folder.png";
			}else if(pm.getType().equals("FILE")){
				typeImg = "closha/icon/file.png";
			}else if(pm.getType().equals("STRING")){
				typeImg = "closha/icon/textfield_rename.png";
			}else{
				typeImg = "closha/icon/ui-list-box-blue.png";
			}
			
			records[i] = new ParameterInfoRecord(pm.getName(), pm.getSetupValue(), pm.getDescription(), pm.getModule(), typeImg, pm.getParameterType(), cacheImg, pm.getId());
		}
		
		display.getParameterListGrid().setData(records);
		
		Timer t = new Timer() {
			public void run() {
				
				CacheLiteClientController.Util.getInstance().isExistCache(pMap, new AsyncCallback<Map<String, ParameterModel>>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onSuccess(Map<String, ParameterModel> result) {
						// TODO Auto-generated method stub
						
						ParameterInfoRecord records[] = new ParameterInfoRecord[display.getParameterListGrid().getRecords().length];
						
						for(int i = 0; i < display.getParameterListGrid().getRecords().length; i++){
							
							final ParameterInfoRecord pr = (ParameterInfoRecord) display.getParameterListGrid().getRecords()[i];
							
							ParameterModel pm = result.get(pr.getKey());
							
							if(pm != null){
								if(pm.isExistCache()){
									pr.setCache("closha/icon/history_done.png");
								}else{
									pr.setCache("closha/icon/history_wait.png");
								}
							}
							
							records[i] = pr;
						}
						
						display.getParameterListGrid().setData(records);
					}
				});
			}
		};
		t.schedule(1000);
	}
	
	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.asLayout());
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
		HandlerRegistration = new HandlerRegistration[2];
		
		bind();
	}
}
