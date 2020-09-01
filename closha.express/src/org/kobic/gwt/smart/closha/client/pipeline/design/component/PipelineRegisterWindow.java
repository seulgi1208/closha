package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.kobic.gwt.smart.closha.client.controller.InstancePipelineController;
import org.kobic.gwt.smart.closha.client.controller.OntologyController;
import org.kobic.gwt.smart.closha.client.controller.RegisterPipelineController;
import org.kobic.gwt.smart.closha.client.frame.right.component.AddOntologyWindow;
import org.kobic.gwt.smart.closha.client.frame.right.event.PipelineOntologyDataEvent;
import org.kobic.gwt.smart.closha.client.frame.right.event.PipelineOntologyDataEventHandler;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.ontology.OntologyModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.PipelineModel;
import org.kobic.gwt.smart.closha.shared.utils.common.UUID;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PipelineRegisterWindow extends Window{
	
	private HandlerManager eventBus;
	private HandlerRegistration registerHandler[];
	private List<OntologyModel> ontologyList;
	private LinkedHashMap<String, String> ontologyValueMap;

	private DynamicForm form;
	private TextItem pipelineNameTextItem;
	private TextItem pipelineIdTextItem;
	private TextItem registerIdTextItem;
	private TextItem authorTextItem;
	private TextItem createDateTextItem;
	private TextAreaItem descTextAreaItem;
	private SelectItem ontologySelectItem;
	private SelectItem openSelectItem;
	private SelectItem versionSelectItem;
	private IButton resetButton;
	private IButton registerButton;
	private Window ontologyWindow;

	private XmlDispatchModel xmlDispatchModel;
	private String instanceID;
	private boolean isRegister;
	
	@Override   
    protected void onInit() {
		registerHandler = new HandlerRegistration[1];
		ontologyList = new ArrayList<OntologyModel>();
		ontologyValueMap = new LinkedHashMap<String, String>();
	}
	
	private void closeWindow(boolean fireEvent){
		if(registerHandler.length != 0){
			for(int i = 0; i < registerHandler.length; i++){
				registerHandler[i].removeHandler();
			}
		}
		
		if(true){
			eventBus.fireEvent(new PipelineOntologyDataEvent());
		}
		
		destroy();
	}
	
	private void init(){
		setOntologyChangeFireEvent();
		setOntologySelectItemDataBinding();
		setOnotologyPickerEvent();
		bind();
	}
	
	private void setOntologyChangeFireEvent(){
		registerHandler[0] = eventBus.addHandler(PipelineOntologyDataEvent.TYPE, 
				new PipelineOntologyDataEventHandler() {
			
			@Override
			public void pipelineOntologyDataReload(PipelineOntologyDataEvent event) {
				// TODO Auto-generated method stub
				setOntologySelectItemDataBinding();
			}
		});
	}
	
	private void setOntologySelectItemDataBinding(){
		OntologyController.Util.getInstance().getOntologyList(Constants.PIPELINE_TYPE, 
				new AsyncCallback<List<OntologyModel>>() {
					@Override
					public void onSuccess(List<OntologyModel> list) {
						// TODO Auto-generated method stub
						
						ontologyList = list;
						
						if(ontologyValueMap.size() != 0){
							ontologyValueMap.clear();
						}
						
						String ontologyName;
						
						for(OntologyModel ontologyModel : list){
							ontologyName = ontologyModel.getOntologyName();
							ontologyValueMap.put(ontologyModel.getOntologyID(), ontologyName);
						}
						
						ontologySelectItem.setValueMap(ontologyValueMap);
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						SC.logDebug(caught.getMessage());
					}
				});
	}
	
	private void setOnotologyPickerEvent(){
		PickerIcon addPicker = new PickerIcon(PickerIcon.SEARCH,
				new FormItemClickHandler() {
					public void onFormItemClick(FormItemIconClickEvent event) {
						ontologyWindow = new AddOntologyWindow(ontologyList, Constants.PIPELINE_TYPE, eventBus);
						ontologyWindow.show();
					}
				});
		ontologySelectItem.setIcons(addPicker);
	}
	
	private void bind(){
		
		registerButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(form.validate()){
					
					String name = form.getValueAsString("name");
					String version = form.getValueAsString("version");
					String desc = form.getValueAsString("desc");
					
					System.out.println("registration info: [" + name + ":"
							+ version + ":" + desc + "]");
					
					PipelineModel registerPipelineModel = new PipelineModel();
					registerPipelineModel.setPipelineID(form.getValueAsString("pipelineId"));
					registerPipelineModel.setRegisterID(form.getValueAsString("registerId"));
					registerPipelineModel.setPipelineName(name);
					registerPipelineModel.setPipelineDesc(desc);
					registerPipelineModel.setPipelineAuthor(form.getValueAsString("author"));
					registerPipelineModel.setRegisterDate(form.getValueAsString("date"));
					registerPipelineModel.setUpdateDate(form.getValueAsString("date"));
					registerPipelineModel.setPipelineXML(null);
					registerPipelineModel.setLinkedKey(null);
					registerPipelineModel.setOntologyID(form.getValueAsString("ontology"));
					registerPipelineModel.setVersion(version);
					registerPipelineModel.setPipelineType(Integer.parseInt(form.getValueAsString("pipeline_type")));
					
					xmlDispatchModel.setName(name);
					xmlDispatchModel.setVersion(version);
					xmlDispatchModel.setDescription(desc);
					
					RegisterPipelineController.Util.getInstance().insertRegisterPipelineModule(
							registerPipelineModel, xmlDispatchModel, new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println(caught.getCause() + ":" + caught.getMessage());
						}
						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							if(!isRegister){
								InstancePipelineController.Util.getInstance().updateInstancePipelineRegister(
										instanceID, true, new AsyncCallback<Integer>() {
									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub
										System.out.println(caught.getCause() + ":" + caught.getMessage());
									}
									@Override
									public void onSuccess(Integer result) {
										// TODO Auto-generated method stub
									}
								});
							}
							closeWindow(true);
						}
					});
					
				}
			}
		});
		
		resetButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				SC.confirm("Are you sure you want to initialize all of the information?", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						// TODO Auto-generated method stub
						if(value){
							form.resetValues();
						}
					}
				});
			}
		});
	}
	
	public PipelineRegisterWindow(final HandlerManager eventBus,
			String projectName, XmlDispatchModel xmlDispatchModel, String instanceID, boolean isRegister) {
		
		this.eventBus = eventBus;
		this.xmlDispatchModel = xmlDispatchModel;
		this.instanceID = instanceID;
		this.isRegister = isRegister;
		
		setIsModal(true);  
        setShowModalMask(true); 
		setWidth(600);
		setHeight(500);
		setTitle(projectName + " Pipeline Register Window");
		setHeaderIcon("closha/icon/registration.gif");
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setCanDragReposition(false);
		setCanDragResize(false);
		setShowShadow(false);
		centerInPage();
		setAutoCenter(true);
		
		pipelineNameTextItem = new TextItem();
		pipelineNameTextItem.setName("name");
		pipelineNameTextItem.setTitle("Pipeline Name");
		pipelineNameTextItem.setWidth("*");
		pipelineNameTextItem.setValue(xmlDispatchModel.getName());
		pipelineNameTextItem.setRequired(true);
		
		pipelineIdTextItem = new TextItem();
		pipelineIdTextItem.setName("pipelineId");
		pipelineIdTextItem.setTitle("Pipeline ID");
		pipelineIdTextItem.setWidth("*");
		pipelineIdTextItem.setRequired(true);
		pipelineIdTextItem.disable();
		pipelineIdTextItem.setValue(UUID.uuid());
		
		registerIdTextItem = new TextItem();
		registerIdTextItem.setName("registerId");
		registerIdTextItem.setTitle("Register ID");
		registerIdTextItem.setWidth("*");
		registerIdTextItem.setRequired(true);
		registerIdTextItem.disable();
		registerIdTextItem.setValue(UUID.uuid());
		
		createDateTextItem = new TextItem();
		createDateTextItem.setName("date");
		createDateTextItem.setTitle("Date");
		createDateTextItem.setWidth("*");
		createDateTextItem.disable();
		createDateTextItem.setValue(CommonUtilsGwt.getDate());
		
		authorTextItem = new TextItem();
		authorTextItem.setName("author");
		authorTextItem.setTitle("Writer");
		authorTextItem.setWidth("*");
		authorTextItem.disable();
		authorTextItem.setValue(xmlDispatchModel.getAuthor());

		LinkedHashMap<String, String> versionValueMap = new LinkedHashMap<String, String>();
		versionValueMap.put("0.1", "0.1");
		versionValueMap.put("0.2", "0.2");
		versionValueMap.put("0.3", "0.3");
		versionValueMap.put("0.4", "0.4");
		versionValueMap.put("0.5", "0.5");
		versionValueMap.put("0.6", "0.6");
		versionValueMap.put("0.7", "0.7");
		versionValueMap.put("0.8", "0.8");
		versionValueMap.put("0.9", "0.9");
		versionValueMap.put("1.0", "1.0");
		versionValueMap.put("1.5", "1.5");
		versionValueMap.put("2.0", "2.0");
		versionValueMap.put("2.5", "2.5");
		versionValueMap.put("3.0", "3.0");
		versionValueMap.put("3.5", "3.5");
		versionValueMap.put("4.0", "4.0");
		versionValueMap.put("4.5", "4.5");
		versionValueMap.put("5.0", "5.0");
		
		versionSelectItem = new SelectItem();
		versionSelectItem.setWidth("*");
		versionSelectItem.setTitle("Version");
		versionSelectItem.setName("version");
		versionSelectItem.setValueMap(versionValueMap);
		versionSelectItem.setRequired(true);
		
		ontologySelectItem = new SelectItem();
		ontologySelectItem.setWidth("*");
		ontologySelectItem.setTitle("Ontology");
		ontologySelectItem.setName("ontology");
		ontologySelectItem.setRequired(true);
		
		LinkedHashMap<Integer, String> openValueMap = new LinkedHashMap<Integer, String>();
		openValueMap.put(0, "PIPELINE");
		openValueMap.put(1, "PIPING");
		openValueMap.put(2, "API");
		
		openSelectItem = new SelectItem();
		openSelectItem.setWidth("*");
		openSelectItem.setTitle("Pipeline Type");
		openSelectItem.setName("pipeline_type");
		openSelectItem.setRequired(true);
		openSelectItem.setValueMap(openValueMap);
		openSelectItem.setDefaultValue(true);
				
		descTextAreaItem = new TextAreaItem();
		descTextAreaItem.setName("desc");
		descTextAreaItem.setTitle("Description");
		descTextAreaItem.setLength(5000);
		descTextAreaItem.setColSpan(2);
		descTextAreaItem.setWidth("*");
		descTextAreaItem.setHeight(150);
		descTextAreaItem.setRequired(true);
		descTextAreaItem.setValue(xmlDispatchModel.getDescription());
		
		form = new DynamicForm();
		form.setHeight100();
		form.setWidth100();
		form.setAlign(Alignment.LEFT);
		form.setFields(pipelineNameTextItem, pipelineIdTextItem,
				registerIdTextItem, authorTextItem, createDateTextItem,
				ontologySelectItem, openSelectItem, versionSelectItem, descTextAreaItem);
		
		resetButton = new IButton();
		resetButton.setTitle("Reset");
		resetButton.setIcon("closha/icon/field_reset.png");
		
		registerButton = new IButton();
		registerButton.setTitle("Register");
		registerButton.setIcon("closha/icon/add.png");
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setHeight(30);
		buttonLayout.setWidth100();
		buttonLayout.setAlign(Alignment.CENTER);
		buttonLayout.setMembersMargin(10);
		buttonLayout.addMember(resetButton);
		buttonLayout.addMember(registerButton);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setMembersMargin(10);
		layout.setPadding(5);
		layout.addMember(form);
		layout.addMember(buttonLayout);
		
		addItem(layout);
		
		init();
	}
}