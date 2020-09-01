package org.kobic.gwt.smart.closha.client.frame.right.presenter;

import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.controller.OntologyController;
import org.kobic.gwt.smart.closha.client.frame.right.component.AddOntologyWindow;
import org.kobic.gwt.smart.closha.client.frame.right.event.JobHistoryDataLoadEvent;
import org.kobic.gwt.smart.closha.client.frame.right.event.JobHistoryFilteringEvent;
import org.kobic.gwt.smart.closha.client.frame.right.event.ModuleOntologyDataEvent;
import org.kobic.gwt.smart.closha.client.frame.right.event.PipelineOntologyDataEvent;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.section.job.history.presenter.JobHistoryPresenter;
import org.kobic.gwt.smart.closha.client.section.job.history.viewer.JobHistoryViewer;
import org.kobic.gwt.smart.closha.client.section.ontology.module.presenter.ModuleOntologyPresenter;
import org.kobic.gwt.smart.closha.client.section.ontology.module.viewer.ModuleOntologyViewer;
import org.kobic.gwt.smart.closha.client.section.ontology.pipeline.presenter.PipelineOntologyPresenter;
import org.kobic.gwt.smart.closha.client.section.ontology.pipeline.viewer.PipelineOntologyViewer;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.ontology.OntologyModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class RightPresenter implements Presenter{
	
	private final HandlerManager eventBus;
	private final Display display; 
	private UserDto userDto;
	private Map<String, String> config;
	
	private Presenter jobHistoryPresenter;
	private Presenter pipelineOntologyPresenter;
	private Presenter moduleOntologyPresenter;
	private Window addOntologyWindow;
	
	public interface Display{
		VLayout asWidget();
		VLayout getRegisterPipelineLayout();
		VLayout getParameterViewLayout();
		VLayout getHistoryLayout();
		VLayout getModuleExplorerLayout();
		
		ImgButton getHistoryReLoadButton();
		ImgButton getAddModuleOntologyButton();
		ImgButton getAddPipelineOntologyButton();
		
		ImgButton getRefreshPipelineOntologyButton();
		ImgButton getRefreshModuleOntologyButton();
		
		SelectItem getSelectItem();
	}
	
	public RightPresenter(HandlerManager eventBus, Map<String, String> config, UserDto userDto, Display view){
		this.eventBus = eventBus;
		this.display = view;
		this.userDto = userDto;
		this.config = config;
	}
	
	private void setAddPipelineOntologyBttuonEvent(){
		display.getAddPipelineOntologyButton().addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				showAddOntologyWindow(Constants.PIPELINE_TYPE);
			}
		});
	}
	
	private void setAddModuleOntologyButtonEvent(){
		display.getAddModuleOntologyButton().addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				showAddOntologyWindow(Constants.MODULE_TYPE);
			}
		});
	}
	
	private void showAddOntologyWindow(final int TYPE){
		OntologyController.Util.getInstance().getOntologyList(TYPE, 
				new AsyncCallback<List<OntologyModel>>() {
			
			@Override
			public void onSuccess(List<OntologyModel> result) {
				// TODO Auto-generated method stub
				addOntologyWindow = new AddOntologyWindow(result, TYPE, eventBus);
				addOntologyWindow.show();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
		});
	}
	
	private void clickHistoryReLoadButtonEvent(){
		display.getHistoryReLoadButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new JobHistoryDataLoadEvent());
			}
		});
	}
	
	private void stateMenuChangeEvent(){
		display.getSelectItem().addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				
				int state = -1;
				
				if(event.getValue().equals("Standby")){
					state = 0;
				}else if(event.getValue().equals("Running")){
					state = 1;
				}else if(event.getValue().equals("Complete")){
					state = 2;
				}else{
					state = 3;
				}
				
				eventBus.fireEvent(new JobHistoryFilteringEvent(state));
			}
		});
	}
	
	private void setRefreshModuleOntologyEvent(){
		display.getRefreshModuleOntologyButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new ModuleOntologyDataEvent());
			}
		});
	}
	
	private void setRefreshPipelineOntologyEvent(){
		display.getRefreshPipelineOntologyButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new PipelineOntologyDataEvent());
			}
		});
	}
	
	@Override
	public void bind(){
		clickHistoryReLoadButtonEvent();
		stateMenuChangeEvent();
		setAddPipelineOntologyBttuonEvent();
		setAddModuleOntologyButtonEvent();
		setRefreshModuleOntologyEvent();
		setRefreshPipelineOntologyEvent();
	}
	
	@Override
	public void init(){
		bind();
		
		if(userDto.isAdmin()){
			display.getAddModuleOntologyButton().setVisible(true);
			display.getAddPipelineOntologyButton().setVisible(true);
		}
		
	}
	
	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.asWidget());
		
		jobHistoryPresenter = new JobHistoryPresenter(eventBus, config, userDto, new JobHistoryViewer());
		jobHistoryPresenter.go(display.getHistoryLayout());
		
		pipelineOntologyPresenter = new PipelineOntologyPresenter(eventBus, new PipelineOntologyViewer(), userDto);
		pipelineOntologyPresenter.go(display.getRegisterPipelineLayout());
		
		moduleOntologyPresenter = new ModuleOntologyPresenter(eventBus, new ModuleOntologyViewer(), userDto);
		moduleOntologyPresenter.go(display.getModuleExplorerLayout());
		
		init();
	}
}
