package org.kobic.gwt.smart.closha.client.configuration.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.configuration.record.ModuleConfigurationListGridRecord;
import org.kobic.gwt.smart.closha.client.configuration.record.PipelineConfigurationListGridRecord;
import org.kobic.gwt.smart.closha.client.controller.RegisterPipelineController;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.module.register.RegisterModuleModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.PipelineModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ConfigurationPresenter implements Presenter {

	private Display display;
	@SuppressWarnings("unused")
	private Map<String, String> config;
	
	public interface Display {
		VLayout asWidget();
		ListGrid getModuleListGrid();
		ListGrid getPipelineListGrid();
		ToolStripButton getModuleDeleteButton();
		ToolStripButton getPipelineDeleteButton();
		ToolStripButton getModuleCheckButton();
		ToolStripButton getModuleRefreshButton();
		ToolStripButton getModuleUnCheckToolStripButton();
	}

	public ConfigurationPresenter(HandlerManager eventBus, Map<String, String> config, UserDto userDto, Display view) {
		this.display = view;
		this.config = config;
	}

	@Override
	public void init() {
		setModuleListGridDataBind();
		setPipelineListGridDataBind();
		bind();
	}

	@Override
	public void bind() {
		setModuleDeleteButton();
		setPipelineDeleteButton();
		setModuleCheckButtonEvent();
		setModuleRefreshButtonEvent();
		setModuleUnCheckButtonEvent();
	}
	
	
	private void setPipelineListGridDataBind(){
		RegisterPipelineController.Util.getInstance().getRegisterPipelinesList(
				new AsyncCallback<List<PipelineModel>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(List<PipelineModel> list) {
				// TODO Auto-generated method stub
				PipelineConfigurationListGridRecord records[] = new PipelineConfigurationListGridRecord[list.size()];
				
				for(int i = 0; i < list.size(); i++){
					PipelineModel pm = list.get(i);
					records[i] = new PipelineConfigurationListGridRecord(
							pm.getRegisterID(), pm.getPipelineName(), 
							"<p align=\"justify\">" + pm.getPipelineDesc() + "</p>", 
							pm.getRegisterDate(),  pm.getUpdateDate(), 
							pm.getVersion(), pm.getPipelineAuthor(), pm.getPipelineType());
				}
				
				display.getPipelineListGrid().setData(records);
			}
		});
		
	}
	
	private void setModuleListGridDataBind(){
		RegisterPipelineController.Util.getInstance().getRegisterPipelineModuleList(true, new AsyncCallback<List<RegisterModuleModel>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			
			@Override
			public void onSuccess(List<RegisterModuleModel> list) {
				// TODO Auto-generated method stub
				ModuleConfigurationListGridRecord records[] = new ModuleConfigurationListGridRecord[list.size()];
				
				for(int i = 0; i < list.size(); i++){
					RegisterModuleModel rm = list.get(i);
					
					records[i] = new ModuleConfigurationListGridRecord(rm.getModuleID(), rm.getModuleName(), 
							"<p align=\"justify\">" + rm.getModuleDesc() + "</p>", 
							rm.getRegisterDate(), rm.getUpdateDate(), rm.getVersion(), 
							rm.getScriptPath(), rm.getModuleAuthor(), rm.getIcon(), rm.isOpen(), rm.isMulti(), rm.isAdmin(), rm.isCheck());
				}	
				display.getModuleListGrid().setData(records);
			}
		});
	}
	
	private void setPipelineDeleteButton(){
		display.getPipelineDeleteButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				List<String> registerIDs = new ArrayList<String>();
				ListGridRecord[] records = display.getPipelineListGrid().getSelectedRecords();
				
				if(records.length != 0){
					for(ListGridRecord record : records){
						PipelineConfigurationListGridRecord pr = (PipelineConfigurationListGridRecord) record;
						registerIDs.add(pr.getRegisterID());
					}
					
					RegisterPipelineController.Util.getInstance().deleteRegisterPipeline(registerIDs, new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println(caught.getCause() + ":" + caught.getMessage());
						}
						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							setPipelineListGridDataBind();
						}
					});
				}
			}
		});
	}
	
	private void setModuleDeleteButton(){
		display.getModuleDeleteButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				List<String> moduleIDs = new ArrayList<String>();
				ListGridRecord[] records = display.getModuleListGrid().getSelectedRecords();
				
				if(records.length != 0){
					for(ListGridRecord record : records){
						ModuleConfigurationListGridRecord mr = (ModuleConfigurationListGridRecord) record;
						moduleIDs.add(mr.getModuleID());
					}
					
					RegisterPipelineController.Util.getInstance().deleteRegisterPipelineModule(moduleIDs, new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println(caught.getCause() + ":" + caught.getMessage());
						}
						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							setModuleListGridDataBind();
						}
					});
				}
			}
		});
	}
	
	private void setModuleCheckButtonEvent(){
		display.getModuleCheckButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				List<String> moduleIDs = new ArrayList<String>();
				ListGridRecord[] records = display.getModuleListGrid().getSelectedRecords();
				
				if(records.length != 0){
					for(ListGridRecord record : records){
						ModuleConfigurationListGridRecord mr = (ModuleConfigurationListGridRecord) record;
						moduleIDs.add(mr.getModuleID());
					}
					
					RegisterPipelineController.Util.getInstance().updateUserModuleCheck(moduleIDs, true, new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
						}
						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							setModuleListGridDataBind();
						}
					});
				}
				
			}
		});
	}
	
	private void setModuleUnCheckButtonEvent(){
		display.getModuleUnCheckToolStripButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				List<String> moduleIDs = new ArrayList<String>();
				ListGridRecord[] records = display.getModuleListGrid().getSelectedRecords();
				
				if(records.length != 0){
					for(ListGridRecord record : records){
						ModuleConfigurationListGridRecord mr = (ModuleConfigurationListGridRecord) record;
						moduleIDs.add(mr.getModuleID());
					}
					
					RegisterPipelineController.Util.getInstance().updateUserModuleCheck(moduleIDs, false, new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
						}
						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							setModuleListGridDataBind();
						}
					});
				}
			}
		});
	}
	
	private void setModuleRefreshButtonEvent(){
		display.getModuleRefreshButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				setModuleListGridDataBind();
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