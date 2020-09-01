package org.kobic.gwt.smart.closha.client.section.ontology.pipeline.presenter;

import java.util.List;

import org.kobic.gwt.smart.closha.client.controller.OntologyController;
import org.kobic.gwt.smart.closha.client.controller.RegisterPipelineController;
import org.kobic.gwt.smart.closha.client.frame.right.event.PipelineOntologyDataEvent;
import org.kobic.gwt.smart.closha.client.frame.right.event.PipelineOntologyDataEventHandler;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.section.ontology.pipeline.component.PipelineOntologyTree;
import org.kobic.gwt.smart.closha.client.section.ontology.pipeline.record.PipelineOntologyRecord;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.ontology.OntologyModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.PipelineModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;

public class PipelineOntologyPresenter implements Presenter{

	private final HandlerManager eventBus;
	private final Display display;
	private UserDto userDto;
	
	public interface Display{
		VLayout asLayout();
		TreeGrid getModuleTreeGrid();
	}
	
	public PipelineOntologyPresenter(HandlerManager eventBus, Display view, UserDto userDto){
		this.eventBus = eventBus;
		this.display = view;
		this.userDto = userDto;
	}
	
	@Override
	public void init(){
		getOntologyList();
		bind();
	}
	
	@Override
	public void bind(){		
		setOntologyChageFireEvent();
	}
	
	private void setOntologyChageFireEvent(){
		
		eventBus.addHandler(PipelineOntologyDataEvent.TYPE, new PipelineOntologyDataEventHandler() {
			@Override
			public void pipelineOntologyDataReload(PipelineOntologyDataEvent event) {
				// TODO Auto-generated method stub
				getOntologyList();
			}
		});
	}
	
	private void getOntologyList(){
		
		OntologyController.Util.getInstance().getOntologyList(Constants.PIPELINE_TYPE, 
				new AsyncCallback<List<OntologyModel>>() {
			@Override
			public void onSuccess(List<OntologyModel> ontologyList) {
				// TODO Auto-generated method stub
				getRegisterPipelineList(ontologyList);
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
		});
	}
	
	private void getRegisterPipelineList(final List<OntologyModel> ontologyList){
		RegisterPipelineController.Util.getInstance().getRegisterPipelinesList(userDto, new AsyncCallback<List<PipelineModel>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(List<PipelineModel> registerPipelineList) {
				// TODO Auto-generated method stub
				setProjectTreeDataBind(ontologyList, registerPipelineList);
			}
		});
	}
	
	private void setProjectTreeDataBind(List<OntologyModel> ontologyList, List<PipelineModel> registerPipelineList){
		
//		for (Iterator<PipelineModel> it = registerPipelineList.iterator(); it.hasNext();) {
//
//			PipelineModel p = it.next();
//
//			if (p.getPipelineType() == 1 || p.getPipelineType() == 2) {
//				if (!userDto.isAdmin())
//					it.remove();
//			}
//		}
		
		int size = ontologyList.size() + registerPipelineList.size();
		
		PipelineOntologyRecord[] records = new PipelineOntologyRecord[size];
		
		String pipeline_icon = null;
		String pipeline_name = null;
		
		for(int i = 0; i < ontologyList.size(); i++){
			
			OntologyModel oModel = ontologyList.get(i);
			
			pipeline_name = userDto.isAdmin() ? oModel.getOntologyName() + "(" + oModel.getCount() + ")" : oModel.getOntologyName();
			
			records[i] = new PipelineOntologyRecord(null,
					oModel.getOntologyID(), pipeline_name, null,
					null, null, null, null, null, Constants.ROOT_ID, null,
					"closha/icon/project_folder.gif");
		}
		
		for(int i = 0; i < registerPipelineList.size(); i++){
			PipelineModel rModel = registerPipelineList.get(i);
			
			if(rModel.getPipelineType() == 1){
				pipeline_icon = "closha/icon/pipeline.png";
			}else if(rModel.getPipelineType() == 2){
				pipeline_icon = "closha/icon/hosting.png";
			}else{
				pipeline_icon = "closha/icon/chart_organisation_opened.png";
			}
			
			records[ontologyList.size() + i] = new PipelineOntologyRecord(
					rModel.getPipelineID(), rModel.getRegisterID(),
					rModel.getPipelineName(), rModel.getPipelineDesc(),
					rModel.getPipelineAuthor(), rModel.getRegisterDate(),
					rModel.getUpdateDate(), rModel.getPipelineXML(),
					rModel.getLinkedKey(), rModel.getOntologyID(),
					rModel.getVersion(), pipeline_icon);
		}
		
		Tree tree = new PipelineOntologyTree();
		tree.setData(records);
		
		display.getModuleTreeGrid().setData(tree);
		display.getModuleTreeGrid().getData().closeAll();
	}
	
	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.asLayout());
		init();
	}
}