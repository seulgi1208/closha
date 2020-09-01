package org.kobic.gwt.smart.closha.client.projects.explorer.presenter;

import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.controller.InstancePipelineController;
import org.kobic.gwt.smart.closha.client.controller.WorkFlowController;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.projects.explorer.component.ProjectExplorerTree;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.NewProjectEvent;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.NewProjectEventHandler;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.OpenProjectEvent;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.TransmitParameterDataEvent;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.ProjectExplorerDataLoadEvent;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.ProjectExplorerDataLoadEventHandler;
import org.kobic.gwt.smart.closha.client.projects.explorer.record.ProjectExplorerRecord;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;

public class ProjectsExplorerPresenter implements Presenter{

	private final HandlerManager eventBus;
	private final Display display; 
	private final UserDto userDto;
	private Map<String, String> config;
	
	public interface Display{
		VLayout asWidget();
		TreeGrid getProjectTreeGrid();
	}
	
	public ProjectsExplorerPresenter(HandlerManager eventBus, Display view, UserDto userDto, Map<String, String> config){
		this.eventBus = eventBus;
		this.display = view;
		this.userDto = userDto;
		this.config = config;
	}
	
	@Override
	public void init(){
		getProjectTreeData();
		bind();
	}
	
	@Override
	public void bind(){
		sendSelectProjectFireEvent();
		makeNewProjectFireEvent();
		projectExplorerDataReloadFireEvent();
	}
	
	private void projectExplorerDataReloadFireEvent(){
		eventBus.addHandler(ProjectExplorerDataLoadEvent.TYPE, new ProjectExplorerDataLoadEventHandler() {
			@Override
			public void projectExplorerDataReload(ProjectExplorerDataLoadEvent event) {
				// TODO Auto-generated method stub
				getProjectTreeData();
			}
		});
	}
	
	private void makeNewProjectFireEvent(){
		eventBus.addHandler(NewProjectEvent.TYPE, new NewProjectEventHandler() {
			@Override
			public void makeNewProject(NewProjectEvent event) {
				// TODO Auto-generated method stub
				getProjectTreeData();
				eventBus.fireEvent(new OpenProjectEvent(userDto.getUserId(), event.getInstancePipelineModel()));
			}
		});
	}
	
	private void sendSelectProjectFireEvent(){
		display.getProjectTreeGrid().addNodeClickHandler(new NodeClickHandler() {
			@Override
			public void onNodeClick(final NodeClickEvent event) {
				// TODO Auto-generated method stub
				final String projectName = event.getNode().getAttributeAsString("projectName");
				String pipelineID = event.getNode().getAttributeAsString("projectID");
				
				InstancePipelineController.Util.getInstance().getInstancePipeline(pipelineID, new AsyncCallback<InstancePipelineModel>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println(caught.getCause() + ":" + caught.getMessage());
					}
					@Override
					public void onSuccess(InstancePipelineModel instancePipelineModel) {
						// TODO Auto-generated method stub
						String userID = event.getNode().getAttributeAsString("projectOwner");
						
						eventBus.fireEvent(new OpenProjectEvent(userID, instancePipelineModel));
						sendProjectParameters(projectName, instancePipelineModel);
					}
				});
			}
		});
	}
	
	private void sendProjectParameters(final String projectName, InstancePipelineModel iModel){
		WorkFlowController.Util.getInstance().drawParserXML(iModel.getInstanceXML(), new AsyncCallback<XmlDispatchModel>() {
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
	
	private void getProjectTreeData(){
//		ProjectController.Util.getInstance().getUserProjectList(userDto.getUserId(), new AsyncCallback<List<ProjectModel>>() {
//			@Override
//			public void onSuccess(List<ProjectModel> projects) {
//				// TODO Auto-generated method stub
//				setProjectTreeDataBind(projects);
//			}
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				System.out.println(caught.getCause() + ":" + caught.getMessage());
//			}
//		});
		
		InstancePipelineController.Util.getInstance().getUserInstancePipelines(
				config, userDto.getUserId(),
				new AsyncCallback<List<InstancePipelineModel>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}

			@Override
			public void onSuccess(List<InstancePipelineModel> instancepipeline) {
				// TODO Auto-generated method stub
				setProjectTreeDataBind(instancepipeline);
			}
		});
	}
	
	private void setProjectTreeDataBind(List<InstancePipelineModel> instancepipeline){
		ProjectExplorerRecord[] records = new ProjectExplorerRecord[instancepipeline.size()];
		
		String icon = null;
		String name = null;
		
		for(int i = 0; i < instancepipeline.size(); i++){
			
			InstancePipelineModel pModel = instancepipeline.get(i);
			
			icon = pModel.getInstanceName().endsWith("_share") ? "closha/icon/share3.png" : "closha/icon/application.png";
			name = pModel.getInstanceName().endsWith("_share") ? CommonUtilsGwt.getExplorerFont(pModel.getInstanceName(), "#6ca54b") : pModel.getInstanceName();
			
			records[i] = new ProjectExplorerRecord(Constants.ROOT_ID,
					pModel.getInstanceID(), pModel.getPipelineID(),
					name, pModel.getInstanceDesc(),
					pModel.getInstanceOwner(), pModel.getCreateDate(), pModel.getStatus(),
					icon);
		}
		
		Tree tree = new ProjectExplorerTree();
		tree.setData(records);
		
		display.getProjectTreeGrid().addDrawHandler(new DrawHandler() {
			@Override
			public void onDraw(DrawEvent event) {
				// TODO Auto-generated method stub
				display.getProjectTreeGrid().getData().openAll();
			}
		});
		display.getProjectTreeGrid().setData(tree);
	}
	
	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.asWidget());
		init();
	}
}
