package org.kobic.gwt.smart.closha.client.section.ontology.module.presenter;

import java.util.List;

import org.kobic.gwt.smart.closha.client.controller.OntologyController;
import org.kobic.gwt.smart.closha.client.controller.RegisterPipelineController;
import org.kobic.gwt.smart.closha.client.frame.right.event.ModuleOntologyDataEvent;
import org.kobic.gwt.smart.closha.client.frame.right.event.ModuleOntologyDataEventHandler;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.section.ontology.module.component.ModuleOntologyTree;
import org.kobic.gwt.smart.closha.client.section.ontology.module.event.ModuleDragEvent;
import org.kobic.gwt.smart.closha.client.section.ontology.module.record.ModuleOntologyRecord;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.module.register.RegisterModuleModel;
import org.kobic.gwt.smart.closha.shared.model.ontology.OntologyModel;
import org.kobic.gwt.smart.closha.shared.utils.common.UUID;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.events.DragStopEvent;
import com.smartgwt.client.widgets.events.DragStopHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;

public class ModuleOntologyPresenter implements Presenter{

	private final HandlerManager eventBus;
	private final Display display;
	private final UserDto userDto;
	
	public interface Display{
		VLayout asLayout();
		TreeGrid getModuleTreeGrid();
	}
	
	public ModuleOntologyPresenter(HandlerManager eventBus, Display view, UserDto userDto){
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
		setModuleExplorerDragDropEvent();
		setOntologyChageFireEvent();
	}
	
	private void setModuleExplorerDragDropEvent(){

		display.getModuleTreeGrid().addDragStopHandler(new DragStopHandler() {
			@Override
			public void onDragStop(DragStopEvent event) {
				// TODO Auto-generated method stub
				
				ModuleOntologyRecord record = (ModuleOntologyRecord)display.getModuleTreeGrid().getSelectedRecord();

				XmlModuleModel xm = new XmlModuleModel();
				xm.setType(record.getAttributeAsString("type"));
				xm.setModuleID(UUID.uuid());
				xm.setOntologyID(record.getAttributeAsString("ontologyID"));
				xm.setAppFormat(record.getAttributeAsString("appFormat"));
				xm.setLanguage(record.getAttributeAsString("language"));
				xm.setScriptPath(record.getAttributeAsString("scriptPath"));
				xm.setCmd(record.getAttributeAsString("cmd"));
				xm.setModuleName(record.getAttributeAsString("moduleName"));
				xm.setModuleDesc(record.getAttributeAsString("moduleDesc"));
				xm.setModuleAuthor(record.getAttributeAsString("moduleAuthor"));
				xm.setRegisterDate(record.getAttributeAsString("registerDate"));
				xm.setUpadteDate(record.getAttributeAsString("updateDate"));
								
				xm.setParameter(record.getAttributeAsString("parameter"));
				xm.setParameterNumber(record.getAttributeAsString(""));
				
				xm.setLinkedKey(record.getAttributeAsString("linkedKey"));
				xm.setVersion(record.getAttributeAsString("version"));
				//이부분도 나이스 하게 수정
//				xm.setImgSrc(record.getAttributeAsString("iconSrc"));
				xm.setImgSrc("images/closha/module/_" + record.getAttributeAsString("iconSrc").split("/")[record.getAttributeAsString("iconSrc").split("/").length - 1]);
				
				xm.setImgWidth(Constants.IMG_DEFAULT_SIZE);
				xm.setImgHeight(Constants.IMG_DEFAULT_SIZE);
				xm.setX(event.getX());
				xm.setY(event.getY());
				xm.setKey(null);
				xm.setStatus(Constants.WAITING);
				xm.setTerminate(false);
				xm.setUrl(record.getAttributeAsString("url"));
				
				xm.setOpen(record.getAttributeAsBoolean("open"));
				xm.setMulti(record.getAttributeAsBoolean("multi"));
				xm.setAdmin(record.getAttributeAsBoolean("admin"));
				xm.setCheck(record.getAttributeAsBoolean("check"));
				xm.setParallel(record.getAttributeAsBoolean("parallel"));
				xm.setAlignment(record.getAttributeAsBoolean("alignment"));
				
				eventBus.fireEvent(new ModuleDragEvent(xm));
			}
		});		
	}
	
	private void setOntologyChageFireEvent(){
		eventBus.addHandler(ModuleOntologyDataEvent.TYPE, new ModuleOntologyDataEventHandler() {
			@Override
			public void moduleOntologyDataReload(ModuleOntologyDataEvent event) {
				// TODO Auto-generated method stub
				getOntologyList();
			}
		});
	}
	
	private void getOntologyList(){
		
		OntologyController.Util.getInstance().getOntologyList(Constants.MODULE_TYPE,
				new AsyncCallback<List<OntologyModel>>() {
			@Override
			public void onSuccess(List<OntologyModel> list) {
				// TODO Auto-generated method stub
				getRegisterPipelineList(list);
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
		});
	}
	
	private void getRegisterPipelineList(final List<OntologyModel> ontologyList){
		
		RegisterPipelineController.Util.getInstance().getRegisterPipelineModuleList(true, userDto,
				new AsyncCallback<List<RegisterModuleModel>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(List<RegisterModuleModel> registerPipelineModuleList) {
				// TODO Auto-generated method stub
				setPipelineModulesTreeDataBind(ontologyList, registerPipelineModuleList);
			}
		});
	}
	
	private void setPipelineModulesTreeDataBind(
			List<OntologyModel> ontologyList, List<RegisterModuleModel> registerPipelineModuleList){
		
		int size = ontologyList.size() + registerPipelineModuleList.size();
		
		ModuleOntologyRecord[] records = new ModuleOntologyRecord[size];
		
		String ontologyName = null;
				
		for(int i = 0; i < ontologyList.size(); i++){
			
			OntologyModel oModel = ontologyList.get(i);
			
			ontologyName = userDto.isAdmin() ? oModel.getOntologyName() + "(" + oModel.getCount() + ")" : oModel.getOntologyName();
			
			records[i] = new ModuleOntologyRecord(null, oModel.getOntologyID(),
					Constants.ROOT_ID, null, null, null, null, null, ontologyName,
					null, null, null, null, null, null, null, null, "closha/icon/project_folder.gif", null, false, false, false, false, false, false);
		}
		
		String tmp = "";
		
		for(int i = 0; i < registerPipelineModuleList.size(); i++){
			
			RegisterModuleModel mModel = registerPipelineModuleList.get(i);
			
			if(mModel.isOpen()){
				tmp = "closha/module/" + mModel.getIcon().split("/")[mModel.getIcon().split("/").length - 1];
			}else{
				//비공개 모듈 아이콘 설정
				tmp = "closha/module/module_8.png";
			}
			
			records[ontologyList.size() + i] = new ModuleOntologyRecord(
					mModel.getType(), mModel.getModuleID(),
					mModel.getOntologyID(), mModel.getAppFormat(),
					mModel.getLanguage(), mModel.getScriptPath(),
					mModel.getCmd(), mModel.getModuleName(), 
					mModel.getModuleName() + ".v." + mModel.getVersion() + "(" + mModel.isParallel() + "," + mModel.isAlignment()+  ")", 
					mModel.getModuleDesc(), mModel.getModuleAuthor(), 
					mModel.getRegisterDate(), mModel.getUpdateDate(), 
					mModel.getParameter(), mModel.getLinkedKey(), 
					mModel.getVersion(), mModel.getIcon(), 
					tmp, mModel.getUrl(), mModel.isOpen(), mModel.isMulti(), mModel.isAdmin(), mModel.isCheck(), mModel.isParallel(), mModel.isAlignment());
		}
		
		Tree tree = new ModuleOntologyTree();
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