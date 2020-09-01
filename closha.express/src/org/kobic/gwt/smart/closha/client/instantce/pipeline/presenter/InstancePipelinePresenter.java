package org.kobic.gwt.smart.closha.client.instantce.pipeline.presenter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.controller.InstancePipelineController;
import org.kobic.gwt.smart.closha.client.controller.ProjectController;
import org.kobic.gwt.smart.closha.client.controller.RegisterPipelineController;
import org.kobic.gwt.smart.closha.client.controller.WorkFlowController;
import org.kobic.gwt.smart.closha.client.file.explorer.controller.HadoopFileSystemController;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.NewProjectEvent;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.TransmitParameterDataEvent;
import org.kobic.gwt.smart.closha.client.section.job.history.event.JobHistoryStackDataRefreshEvent;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.Messages;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.PipelineModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;
import org.kobic.gwt.smart.closha.shared.model.project.ProjectModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class InstancePipelinePresenter implements Presenter{

	private final HandlerManager eventBus;
	private final UserDto userDto;
	private final Display display;
	private LinkedHashMap<String, String> pipelineMap = new LinkedHashMap<String, String>();
	private LinkedHashMap<String, String> pipelineIconMap = new LinkedHashMap<String, String>();
	private Map<String, String> descMap = new HashMap<String, String>();
	private Map<String, String> config;
	
	private Window window;
	
	public interface Display{
		Window asWidget();
		ToolStripButton getSubmintButton();
		ToolStripButton getResetButton();
		DynamicForm getProjectForm();
		TextItem getProjectNameTextItem();
		SelectItem getPipelineSelectITem();
		Label getDescLabel();
	}
	
	public InstancePipelinePresenter(HandlerManager eventBus, Map<String, String> config, UserDto userDto, Display view){
		this.eventBus = eventBus;
		this.display = view;
		this.userDto = userDto;
		this.config = config;
	}
	
	private void setPipelineRadioButtonEvent(){
		display.getPipelineSelectITem().addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				display.getDescLabel().setContents("<p align=\"justify\"><font face=\"Times\" color=\"#A39990\" size=\"2\">" +
						descMap.get((String)event.getValue()) + "</font></p>");
			}
		});
	}
	
	private void setResetButtonEvent(){
		display.getResetButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				display.getProjectForm().reset();
			}
		});
	}
	
	private void makeProjectFolder(String userId, String projectName){
		HadoopFileSystemController.Util.getInstance().makeProjectDirectory(config, userId, projectName, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	private void sendProjectParameters(final String instanceXML, final String projectName){
		WorkFlowController.Util.getInstance().drawParserXML(instanceXML, new AsyncCallback<XmlDispatchModel>() {
			@Override
			public void onSuccess(XmlDispatchModel xmlDispatchModel) {
				// TODO Auto-generated method stub
				//parameter data send parameter list grid fire event
				eventBus.fireEvent(new TransmitParameterDataEvent(projectName, xmlDispatchModel.getParametersBeanList()));
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
		});
	}
	
	private void setSubmitButtonEvent(){
		display.getSubmintButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(display.getProjectForm().validate()){
					final ProjectModel pModel = new ProjectModel();

					pModel.setProjectName(display.getProjectForm().getValueAsString("pName").replaceAll("\\p{Space}", "_"));
					pModel.setPipelineID(display.getProjectForm().getValueAsString("pipeline"));
					pModel.setProjectDesc(display.getProjectForm().getValueAsString("pDesc"));
					pModel.setProjectOwner(userDto.getUserId());
					
					InstancePipelineController.Util.getInstance().isInstancePiplineExist(userDto.getUserId(), pModel.getProjectName(), new AsyncCallback<Boolean>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println(caught.getCause() + ":" + caught.getMessage());
						}
						@Override
						public void onSuccess(Boolean check) {
							// TODO Auto-generated method stub
							if(check){
								ProjectController.Util.getInstance().makeNewProject(pModel, userDto.getEmailAdress(), new AsyncCallback<InstancePipelineModel>() {
									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub
										System.out.println(caught.getCause() + ":" + caught.getMessage());
									}
									@Override
									public void onSuccess(InstancePipelineModel instancePipelineModel) {
										// TODO Auto-generated method stub
										
										makeProjectFolder(userDto.getUserId(), instancePipelineModel.getInstanceName());
										eventBus.fireEvent(new NewProjectEvent(instancePipelineModel));
										eventBus.fireEvent(new JobHistoryStackDataRefreshEvent());
										sendProjectParameters(instancePipelineModel.getInstanceXML(), pModel.getProjectName());
										
										window.destroy();
									}
								});
							}else{
								SC.say(pModel.getProjectName() + " is already existing project name. Please set a different project name.");
								display.getProjectNameTextItem().setValue("");
								display.getProjectNameTextItem().focusInItem();
							}
						}
					});
				}
			}
		});
	}
	
	private void setRegisterPipelineReLoadData(){
		PickerIcon reFreshPicker = new PickerIcon(PickerIcon.REFRESH, 
				new FormItemClickHandler() {
			
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				// TODO Auto-generated method stub
				pipelineMap.clear();
				pipelineIconMap.clear();
				descMap.clear();
				setRegisterPipelineDataBind();
			}
		});
		
		display.getPipelineSelectITem().setIcons(reFreshPicker);
	}
	
	private void setRegisterPipelineDataBind(){		
		RegisterPipelineController.Util.getInstance().getRegisterPipelinesList(userDto, new AsyncCallback<List<PipelineModel>>() {
			@Override
			public void onSuccess(List<PipelineModel> list) {
				// TODO Auto-generated method stub
				
				pipelineMap.put(Constants.NEW_PROJECT_DESIGN_ID, Constants.NEW_PROJECT_DESIGN);
				pipelineIconMap.put(Constants.NEW_PROJECT_DESIGN_ID, "closha/icon/chart_organisation_opened.png");
				descMap.put(Constants.NEW_PROJECT_DESIGN_ID, Messages.NEW_PROJECT_DESIGN_DESC);
				
				for(int i = 0; i < list.size(); i++){
					PipelineModel register = list.get(i);

					pipelineMap.put(register.getPipelineID(), register.getPipelineName());
					pipelineIconMap.put(register.getPipelineID(), "closha/icon/chart_organisation_opened.png");
					descMap.put(register.getPipelineID(), register.getPipelineDesc());
				}
				
				display.getPipelineSelectITem().setValueMap(pipelineMap);
				display.getPipelineSelectITem().setValueIcons(pipelineIconMap);
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
		});
	}
	
	private void setCloseEvent(){
		window.addCloseClickHandler(new CloseClickHandler() {
			
			@Override
			public void onCloseClick(CloseClickEvent event) {
				// TODO Auto-generated method stub
				window.destroy();
			}
		});
	}
	
	@Override
	public void init(){
		setRegisterPipelineDataBind();
		bind();
	}

	@Override
	public void bind(){
		setSubmitButtonEvent();
		setResetButtonEvent();
		setPipelineRadioButtonEvent();
		setRegisterPipelineReLoadData();
		setCloseEvent();
	}
	
	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.asWidget());
		init();
	}
	
	public void go() {
		// TODO Auto-generated method stub
		
		if(window == null){
			window = display.asWidget();
			window.show();
			init();
		}else{
			SC.warn("Create Project Window is already running.");
		}		
	}
}