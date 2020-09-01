package org.kobic.gwt.smart.closha.client.pipeline.composition.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.controller.WorkFlowController;
import org.kobic.gwt.smart.closha.client.event.draw.RemoveRegisterEvents;
import org.kobic.gwt.smart.closha.client.event.draw.RemoveRegisterEventsHandler;
import org.kobic.gwt.smart.closha.client.event.draw.ShowModuleDialogWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.WorkflowSendChangeDataEvent;
import org.kobic.gwt.smart.closha.client.event.draw.WorkflowSendChangeDataEventHandler;
import org.kobic.gwt.smart.closha.client.event.function.SelectModuleEvent;
import org.kobic.gwt.smart.closha.client.event.function.SelectModuleEventHandler;
import org.kobic.gwt.smart.closha.client.pipeline.composition.record.PipelineCompositionTreeGridRecord;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.section.parameter.presenter.ParameterInfoPresenter;
import org.kobic.gwt.smart.closha.client.section.parameter.viewer.ParameterInfoViewer;
import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.types.ListGridFieldType;  
import com.smartgwt.client.widgets.events.ClickEvent;  
import com.smartgwt.client.widgets.events.ClickHandler;    
  

public class PipelineCompositionPresenter implements Presenter{

	private HandlerManager eventBus;
	private Display display; 
	private InstancePipelineModel iModel;
	private XmlDispatchModel xmlDispatchModel;
	private HandlerRegistration HandlerRegistration[];
	
	private Map<String, XmlModuleModel> xmlModuleModelMap;
	private List<XmlParameterModel> xmlParameterModelList;
	
	private String id;
	private XmlModuleModel xmlModuleModel;
	
	private ListGrid pipelineCompositionListGrid;
	
	public XmlDispatchModel getXmlDispatchModel() {
		return xmlDispatchModel;
	}

	public void setXmlDispatchModel(XmlDispatchModel xmlDispatchModel) {
		this.xmlDispatchModel = xmlDispatchModel;
	}
	
	private List<XmlParameterModel> getModuleParameters(String key){
		
		xmlParameterModelList = null;
		
		xmlParameterModelList = new ArrayList<XmlParameterModel>();
		
		for(XmlParameterModel xm : xmlDispatchModel.getParametersBeanList()){
			
			if(xm.getKey().equals(key)){
				xmlParameterModelList.add(xm);
			}
		}
		return  xmlParameterModelList;
	}

	public interface Display{
		HLayout asWidget();
	}
	
	public PipelineCompositionPresenter(HandlerManager eventBus, Display view,
			InstancePipelineModel iModel) {
		this.eventBus = eventBus;
		this.display = view;
		this.iModel = iModel;
	}
	
	@Override
	public void init(){
		
		draw();
		
		HandlerRegistration = new HandlerRegistration[3];
		
		WorkFlowController.Util.getInstance().drawParserXML(
				iModel.getInstanceXML(), new AsyncCallback<XmlDispatchModel>() {
			@Override
			public void onSuccess(XmlDispatchModel xmlDispatchModel) {
				// TODO Auto-generated method stub
				setXmlDispatchModel(xmlDispatchModel);
				
				xmlModuleModelMap = new HashMap<String, XmlModuleModel>();
				
				for(XmlModuleModel moduleBean : xmlDispatchModel.getModulesBeanList()){
					xmlModuleModelMap.put(moduleBean.getModuleID(), moduleBean);
				}

				bind();
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
		});
	}
	
	@Override
	public void bind(){
		setInitDataBind();	
		removeRegisterFireEvent();
		selectModuleFromNetworkGraphFireEvent();
		reciveParameterDataFireEvent();
	}
	
	private void setInitDataBind(){
		setModuleDetailTreeGridDataBind(xmlDispatchModel);
	}
	
	private void setModuleDetailTreeGridDataBind(XmlDispatchModel xmlDispatchModel){
		
		System.out.println("Get a details list table.");
		
		int size = xmlDispatchModel.getModulesBeanList().size();
		String id = xmlDispatchModel.getId();
		
		PipelineCompositionTreeGridRecord[] records = new PipelineCompositionTreeGridRecord[size];
		
		String icon = "";
		
		for(int i = 0; i < xmlDispatchModel.getModulesBeanList().size(); i++){
			
			XmlModuleModel mm = xmlDispatchModel.getModulesBeanList().get(i);
			
			if(mm.getModuleDesc().toLowerCase().equals("start module")){
				icon = "closha/icon/start_module.png";
			}else if(mm.getModuleDesc().toLowerCase().equals("end module")){
				icon = "closha/icon/end_module.png";
			}else{
				icon = mm.getImgSrc().replace("images/", "");
			}
			
			records[i] = new PipelineCompositionTreeGridRecord(mm.getModuleID(),
					id, mm.getModuleName(), mm.getType().toUpperCase(),
					mm.getModuleDesc(), 
					mm.getStatus(), mm.getVersion(),
					mm.getScriptPath(), icon);
		}
		
		pipelineCompositionListGrid.setData(records);
	}
	
	@SuppressWarnings("unused")
	private void setModuleDetailListGridEvent(){
		pipelineCompositionListGrid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			
			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event) {
				// TODO Auto-generated method stub
				
				id = event.getRecord().getAttribute("id"); 
				
				xmlModuleModel = xmlModuleModelMap.get(id);
				
				eventBus.fireEvent(new ShowModuleDialogWindowEvent(
						iModel.getInstanceName(), xmlModuleModel, getModuleParameters(xmlModuleModel.getKey())));
			}
		});
	}
	
	private void reciveParameterDataFireEvent(){
		HandlerRegistration[2] = eventBus.addHandler(WorkflowSendChangeDataEvent.TYPE, new WorkflowSendChangeDataEventHandler() {
			
			@Override
			public void workflowSendChangeData(WorkflowSendChangeDataEvent event) {
				// TODO Auto-generated method stub
				
				setXmlDispatchModel(event.getXmlDispatchModel());
				
				xmlModuleModelMap = null;
				xmlModuleModelMap = new HashMap<String, XmlModuleModel>();
				
				for(XmlModuleModel moduleBean : xmlDispatchModel.getModulesBeanList()){
					xmlModuleModelMap.put(moduleBean.getModuleID(), moduleBean);
				}
				
				setModuleDetailTreeGridDataBind(event.getXmlDispatchModel());
			}
		});
	}
	
	private void selectModuleFromNetworkGraphFireEvent(){
		HandlerRegistration[1] = eventBus.addHandler(SelectModuleEvent.TYPE, new SelectModuleEventHandler() {
			@Override
			public void selectModule(SelectModuleEvent event) {
				// TODO Auto-generated method stub
				String id = event.getLinkedNetworkNodeModel().getAccessID();
				
				for(ListGridRecord record : pipelineCompositionListGrid.getRecords()){
					if(record.getAttributeAsString("id").equals(id)){
						pipelineCompositionListGrid.selectSingleRecord(record);
					}
				}
			}
		});
	}
	
	private void removeRegisterFireEvent(){
		HandlerRegistration[0] = eventBus.addHandler(RemoveRegisterEvents.TYPE, new RemoveRegisterEventsHandler() {
			@Override
			public void removeRegisterEventHandler(RemoveRegisterEvents event) {
				// TODO Auto-generated method stub
				if(iModel.getInstanceName().equals(event.getProjectName())){
					
					for(int i = 0; i < HandlerRegistration.length; i++){
						HandlerRegistration[i].removeHandler();
					}
				}
			}
		});
	}
	
	private ListGrid draw(){
		
		ListGridField iconField = new ListGridField("icon", "Icon", 50);  
        iconField.setAlign(Alignment.CENTER);  
        iconField.setType(ListGridFieldType.IMAGE);  
        
		ListGridField nameField =  new ListGridField("name", "Program Name", 200);
		nameField.setAlign(Alignment.LEFT);
		
		ListGridField idField =  new ListGridField("id", "Program ID", 300);
		idField.setAlign(Alignment.CENTER);
		idField.setHidden(true);
		
		ListGridField typeField =  new ListGridField("type", "Type", 70);
		typeField.setAlign(Alignment.LEFT);
		
		ListGridField descField =  new ListGridField("desc", "Description");
		descField.setWidth("*");
		descField.setAlign(Alignment.LEFT);
		
		ListGridField statusField =  new ListGridField("status", "Status", 70);
		statusField.setAlign(Alignment.CENTER);
		statusField.setHidden(true);
		
		ListGridField versionField =  new ListGridField("version", "Version", 70);
		versionField.setAlign(Alignment.CENTER);
		versionField.setHidden(true);
		
		ListGridField scriptField =  new ListGridField("script", "Script Path", 250);
		scriptField.setAlign(Alignment.CENTER);
		scriptField.setHidden(true);
		
		ListGridField buttonField = new ListGridField("buttonField", "Settings", 80);  
        buttonField.setAlign(Alignment.CENTER); 
		
        pipelineCompositionListGrid = new ListGrid() {  
            @Override  
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {
            	
            	String fieldName = this.getFieldName(colNum);  
            	
				if (fieldName.equals("buttonField")
						&& !record.getAttributeAsString("name").toLowerCase().equals("start")
						&& !record.getAttributeAsString("name").toLowerCase().equals("end")) {  
                    IButton button = new IButton();  
                    button.setWidth(65);
                    button.setHeight(18);
                    button.setIcon("closha/icon/wrench.png");  
                    button.setTitle("Settings");  
                    button.addClickHandler(new ClickHandler() {  
                        public void onClick(ClickEvent event) {  
                        	String id = record.getAttributeAsString("id");
            				xmlModuleModel = xmlModuleModelMap.get(id);
//            				eventBus.fireEvent(new ShowModuleDialogWindowEvent(
//            						iModel.getInstanceName(), xmlModuleModel, getModuleParameters(xmlModuleModel.getKey())));
            				
            				eventBus.fireEvent(new ShowModuleDialogWindowEvent(
            						iModel.getInstanceName(), xmlModuleModel, xmlDispatchModel.getParametersBeanList()));
                        }  
                    });  
                    return button;  
                } else {  
                    return null;  
                }  
            }
		};
		
		pipelineCompositionListGrid.setShowAllRecords(true);
		pipelineCompositionListGrid.setShowRowNumbers(true);
		pipelineCompositionListGrid.setShowRollOver(true);
		pipelineCompositionListGrid.setCanReorderRecords(true);
		pipelineCompositionListGrid.setSelectionAppearance(SelectionAppearance.ROW_STYLE);
		pipelineCompositionListGrid.setEmptyMessage("There are no data.");
		pipelineCompositionListGrid.setShowRecordComponents(true);          
        pipelineCompositionListGrid.setShowRecordComponentsByCell(true);  
		pipelineCompositionListGrid.setWidth100(); 
		pipelineCompositionListGrid.setHeight100();
		pipelineCompositionListGrid.setShowHeader(true);
		pipelineCompositionListGrid.setFields(iconField, nameField, idField, typeField, descField,
				statusField, versionField, scriptField, buttonField);
		
		display.asWidget().addMember(pipelineCompositionListGrid);
		
		return pipelineCompositionListGrid;
	}

	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub

		init();
		
		Presenter parameterInfoPresenter = new ParameterInfoPresenter(eventBus, new ParameterInfoViewer(), iModel.getInstanceName());
		parameterInfoPresenter.go(display.asWidget());
	
		container.addMember(display.asWidget());
	}
}
