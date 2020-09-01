package org.kobic.gwt.smart.closha.client.register.module.presenter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.controller.OntologyController;
import org.kobic.gwt.smart.closha.client.controller.RegisterPipelineController;
import org.kobic.gwt.smart.closha.client.event.registration.RegistrationRemoveRegisterEvent;
import org.kobic.gwt.smart.closha.client.event.registration.RegistrationRemoveRegisterEventHandler;
import org.kobic.gwt.smart.closha.client.event.registration.SelectScriptFileEvent;
import org.kobic.gwt.smart.closha.client.event.registration.SelectScriptFileEventHandler;
import org.kobic.gwt.smart.closha.client.frame.right.component.AddOntologyWindow;
import org.kobic.gwt.smart.closha.client.frame.right.event.ModuleOntologyDataEvent;
import org.kobic.gwt.smart.closha.client.frame.right.event.ModuleOntologyDataEventHandler;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.register.module.component.CodeFileExplorerWindow;
import org.kobic.gwt.smart.closha.client.register.module.record.ModuleParameterListGridRecord;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.Messages;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.module.register.RegisterModuleModel;
import org.kobic.gwt.smart.closha.shared.model.ontology.OntologyModel;
import org.kobic.gwt.smart.closha.shared.utils.common.UUID;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.ImgButton;
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
import com.smartgwt.client.widgets.form.fields.events.KeyUpEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyUpHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ModuleRegisterPresenter implements Presenter{
	
	private HandlerManager eventBus;
	private Display display; 
	private UserDto userDto;
	private Window sciprtExplorerWindow;
	private Window ontologyWindow;
	private String name;
	private String dataType;
	private String parameterType;
	private LinkedHashMap<String, String> ontologyValueMap;
	private LinkedHashMap<String, String> nextLinkedValueMap;
	private List<OntologyModel> ontologyList;
	private List<RegisterModuleModel> moduleList;
	private HandlerRegistration registerHandler[];
	private Map<String, String> config;
	private Window window;
	
	public interface Display{
		Window asWidget();
		TextItem getAuthorTextItem();
		TextItem getCreateDateTextItem();
		TextItem getScriptPathTextItem();
		TextItem getModuleNameTextItem();
		TextItem getExternalCmdTextItem();
		SelectItem getOntologySelectItem();
		SelectItem getTypeSelectItem();
		SelectItem getParameterTypeSelectItem();
		SelectItem getDataTypeSelectItem();
		SelectItem getNextLinkedSelectItem();
		SelectItem getAppFormatSelectItem();
		SelectItem getLanguageSelectItem();
		SelectItem getOpenSelectItem();
		SelectItem getMultiSelectItem();
		SelectItem getParallelSelectItem();
		SelectItem getAlignmentSelectItem();
		SelectItem getIconSelectItem();
		ImgButton getAddButton();
		ImgButton getRemoveButton();
		ListGrid getParameterListGrid();
		DynamicForm getForm();
		ToolStripButton getSubmitButton();
		ToolStripButton getResetButton();
	}
	
	public ModuleRegisterPresenter(HandlerManager eventBus, 
			Map<String, String> config, UserDto userDto, Display view){
		this.eventBus = eventBus;
		this.display = view;
		this.userDto = userDto;
		this.config = config;
	}
	
	public void init(){
		ontologyList = new ArrayList<OntologyModel>();
		moduleList = new ArrayList<RegisterModuleModel>();
		ontologyValueMap = new LinkedHashMap<String, String>();
		nextLinkedValueMap = new LinkedHashMap<String, String>();
		registerHandler = new HandlerRegistration[3];
		formDataInitialization();
		bind();
	}
	
	public void bind(){
		removeRegisterFireEvent();
		setOntologyChangeFireEvent();
//		setOnotologyPickerEvent();
		setScriptPickerEvent();
		setAddButtonEvent();
		setRemoveButtonEvent();
		setOntologySelectItemDataBinding();
		setNextLinkedSelectItemDataBinding();
		setEnterNameCheckEvent();
		setFormResetButtonEvent();
		setSubmitButtonEvent();
		setAppFormatSelectItemChangeEvent();
		setSelectScriptFilePathFireEvent();
		setCloseEvnet();
	}
	
	private void formDataInitialization(){
		display.getAuthorTextItem().setValue(userDto.getUserId());
		display.getAuthorTextItem().disable();
		
		display.getCreateDateTextItem().setValue(CommonUtilsGwt.getDate());
		display.getCreateDateTextItem().disable();
		
		display.getExternalCmdTextItem().disable();
		
		if(!userDto.isAdmin()){
			display.getTypeSelectItem().disable();
			display.getAppFormatSelectItem().disable();
			display.getOpenSelectItem().disable();
			display.getIconSelectItem().setVisible(false);
			display.getIconSelectItem().setRequired(false);
		}else{
			
			PickerIcon addPicker = new PickerIcon(PickerIcon.SEARCH,
					new FormItemClickHandler() {
						public void onFormItemClick(FormItemIconClickEvent event) {
							ontologyWindow = new AddOntologyWindow(ontologyList, Constants.MODULE_TYPE, eventBus);
							ontologyWindow.show();
						}
					});

			display.getOntologySelectItem().setIcons(addPicker);
			
		}
	}
	
	private void setAppFormatSelectItemChangeEvent(){
		display.getAppFormatSelectItem().addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				String appFormat = display.getAppFormatSelectItem().getValueAsString();
				
				if(appFormat.equals(Constants.SCRIPT_TYPE)){
					display.getExternalCmdTextItem().disable();
					display.getExternalCmdTextItem().setRequired(false);
					display.getExternalCmdTextItem().setValue(Constants.NULL);
					
					display.getLanguageSelectItem().enable();
					display.getLanguageSelectItem().setRequired(true);
					display.getScriptPathTextItem().enable();
					display.getScriptPathTextItem().setRequired(true);
				}else if(appFormat.equals(Constants.EXTENAL_TYPE)){
					display.getLanguageSelectItem().disable();
					display.getLanguageSelectItem().setRequired(false);
					display.getLanguageSelectItem().setValue(Constants.NULL);
					display.getScriptPathTextItem().disable();
					display.getScriptPathTextItem().setRequired(false);
					display.getScriptPathTextItem().setValue(Constants.NULL);
					
					display.getExternalCmdTextItem().enable();
					display.getExternalCmdTextItem().setRequired(true);
				}
			}
		});
	}
	
	private void setFormResetButtonEvent(){
		display.getResetButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				SC.confirm("Are you sure you want to proceed with the initialization?", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						// TODO Auto-generated method stub
						if(value){
							display.getForm().resetValues();
							display.getParameterListGrid().selectAllRecords();
							display.getParameterListGrid().removeSelectedData();
						}
					}
				});
			}
		});
	}
	
	private void setSubmitButtonEvent(){
		display.getSubmitButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(display.getForm().validate()){
					
					ListGridRecord[] records = display.getParameterListGrid().getRecords();
					
					for(int i = 0; i <records.length; i++){
						if(!display.getParameterListGrid().validateRow(i)){
							SC.say("Enter the information on the parameter you want to register.");
							return;
						}
					}
					
					String parameters = "";
					
					if(records.length == 0){
						parameters = "|";
					}else{
						for(int i = 0; i < records.length; i++){
							ListGridRecord record = records[i];
							
							if(i != 0){
								parameters += "&";
							}
							
							parameters += record
									.getAttributeAsString("parameterName")
									+ ":"
									+ record.getAttributeAsString("description")
									+ ":"
									+ record.getAttributeAsString("dataType")
									+ ":"
									+ record.getAttributeAsString("parameterType")
									+ ":"
									+ record.getAttributeAsString("extensions")
									+ "|"
									+ record.getAttributeAsString("defaultValue");
						}
					}
					
					String languageType = display.getForm().getValueAsString("languageType");
					String iconPath = null;
					
					RegisterModuleModel rm = new RegisterModuleModel();
					rm.setType(display.getForm().getValueAsString("type"));
					rm.setModuleID(UUID.uuid());
					rm.setOntologyID(display.getForm().getValueAsString("ontology"));
					rm.setAppFormat(display.getForm().getValueAsString("appFormat"));
					rm.setLanguage(languageType);
					rm.setScriptPath(display.getForm().getValueAsString("scriptPath"));
					rm.setCmd(display.getForm().getValueAsString("extenal"));
					rm.setModuleName(display.getForm().getValueAsString("name"));
					rm.setModuleDesc(display.getForm().getValueAsString("desc"));
					rm.setModuleAuthor(display.getForm().getValueAsString("author"));
					rm.setRegisterDate(display.getForm().getValueAsString("date"));
					rm.setUpdateDate(display.getForm().getValueAsString("date"));
					rm.setParameter(parameters);
					rm.setLinkedKey(display.getForm().getValueAsString("nextLinked"));
					rm.setVersion(display.getForm().getValueAsString("version"));
					rm.setOpen(Boolean.parseBoolean(display.getForm().getValueAsString("open")));
					rm.setMulti(Boolean.parseBoolean(display.getForm().getValueAsString("multi")));
					rm.setUrl(display.getForm().getValueAsString("urlLink"));
					rm.setParallel(Boolean.parseBoolean(display.getForm().getValueAsString("parallel")));
					rm.setAlignment(Boolean.parseBoolean(display.getForm().getValueAsString("alignment")));
					
					if(userDto.isAdmin()){
						iconPath = "images/closha/module/" + display.getForm().getValueAsString("iconType") + ".png";
					}else{
						if(languageType.equals("java")){
							iconPath = "images/closha/module/java.png";
						}else if(languageType.equals("perl")){
							iconPath = "images/closha/module/perl.png";
						}else if(languageType.equals("python")){
							iconPath = "images/closha/module/python.png";
						}else if(languageType.equals("shell")){
							iconPath = "images/closha/module/shell.png";
						}else if(languageType.equals("r")){
							iconPath = "images/closha/module/r.png";
						}else{
							//여기 고쳐라 !!!!!!!!!!!!!
							iconPath = "images/closha/module/predict_48.png";
						}
					}
					
					rm.setIcon(iconPath);
					rm.setAdmin(userDto.isAdmin());
					rm.setCheck(userDto.isAdmin());
					
					RegisterPipelineController.Util.getInstance().insertRegisterPipelineModule(config, userDto, rm, new AsyncCallback<String>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println(caught.getCause() + ":" + caught.getMessage());
						}
						@Override
						public void onSuccess(String name) {
							// TODO Auto-generated method stub
							display.getForm().resetValues();
							display.getParameterListGrid().selectAllRecords();
							display.getParameterListGrid().removeSelectedData();
							eventBus.fireEvent(new ModuleOntologyDataEvent());
							
							SC.confirm("Registering the " + name + " analysis program has been completed.", new BooleanCallback() {
								
								@Override
								public void execute(Boolean value) {
									// TODO Auto-generated method stub
									if(value){
										window.destroy();
										window = null;
										removeRegisterEvent();
									}
								}
							});
						}
					});
				}
			}
		});
	}
	
	private void setSelectScriptFilePathFireEvent(){
		registerHandler[2] = eventBus.addHandler(SelectScriptFileEvent.TYPE, 
				new SelectScriptFileEventHandler() {
			@Override
			public void selectScriptFileEvent(SelectScriptFileEvent event) {
				// TODO Auto-generated method stub
				display.getForm().getItem(event.getFormName()).setValue(event.getFilePath());
			}
		});
	}
	
	private void setOntologyChangeFireEvent(){
		registerHandler[1] = eventBus.addHandler(ModuleOntologyDataEvent.TYPE, 
				new ModuleOntologyDataEventHandler() {
			@Override
			public void moduleOntologyDataReload(ModuleOntologyDataEvent event) {
				// TODO Auto-generated method stub
				setOntologySelectItemDataBinding();
			}
		});
	}
	
	private void removeRegisterFireEvent(){
		registerHandler[0] = eventBus.addHandler(RegistrationRemoveRegisterEvent.TYPE, 
				new RegistrationRemoveRegisterEventHandler() {
			@Override
			public void registrationRemoveEvent(RegistrationRemoveRegisterEvent event) {
				// TODO Auto-generated method stub
				if(registerHandler.length != 0){
					for(int i = 0; i < registerHandler.length; i++){
						registerHandler[i].removeHandler();
					}
				}
			}
		});
	}
	
	private void removeRegisterEvent(){
		if(registerHandler.length != 0){
			for(int i = 0; i < registerHandler.length; i++){
				registerHandler[i].removeHandler();
			}
		}
	}
	
//	private void setOnotologyPickerEvent(){
//		PickerIcon addPicker = new PickerIcon(PickerIcon.SEARCH,
//				new FormItemClickHandler() {
//					public void onFormItemClick(FormItemIconClickEvent event) {
//						ontologyWindow = new AddOntologyWindow(ontologyList, Constants.MODULE_TYPE, eventBus);
//						ontologyWindow.show();
//					}
//				});
//
//		display.getOntologySelectItem().setIcons(addPicker);
//	}
	
	private void setScriptPickerEvent(){
		PickerIcon uploadPicker = new PickerIcon(PickerIcon.SEARCH,
				new FormItemClickHandler() {
					public void onFormItemClick(FormItemIconClickEvent event) {
						
						String moduleName = display.getModuleNameTextItem().getValueAsString();
						
						if(moduleName == null){
							SC.warn("Please enter a program name.");
						}else{
							sciprtExplorerWindow = new CodeFileExplorerWindow(
									userDto.getUserId(), moduleName, 
									display.getScriptPathTextItem().getName(), eventBus, config);
							sciprtExplorerWindow.show();
						}
					}
				});

		display.getScriptPathTextItem().setIcons(uploadPicker);
	}

	private void setAddButtonEvent(){
		display.getAddButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				dataType = display.getDataTypeSelectItem().getValueAsString();
				parameterType = display.getParameterTypeSelectItem().getValueAsString();

				ModuleParameterListGridRecord record = new ModuleParameterListGridRecord(
						parameterType, dataType, Constants.NULL, Constants.NULL);
				
				display.getParameterListGrid().addData(record);
			}
		});
	}
	
	private void setRemoveButtonEvent(){
		display.getRemoveButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				display.getParameterListGrid().removeSelectedData();  
			}
		});
	}
	
	private void setEnterNameCheckEvent(){
		display.getModuleNameTextItem().addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub
				
				name = display.getModuleNameTextItem().getValueAsString();
				
				for(RegisterModuleModel rm : moduleList){
					if(rm.getModuleName().toLowerCase().equals(name.toLowerCase())){
						SC.confirm(Messages.REGISTRATION_ALERT, new BooleanCallback() {
							@SuppressWarnings("static-access")
							@Override
							public void execute(Boolean value) {
								// TODO Auto-generated method stub
								if(value != null && value) {
									display.getModuleNameTextItem().setValue(name + Constants.UNDER_LINE + new UUID().uuid());
								}else{
									display.getModuleNameTextItem().setValue(name + Constants.UNDER_LINE);
									display.getModuleNameTextItem().focusInItem();
								}
							}
						});
					}
				}
			}
		});
	}
	
	private void setNextLinkedSelectItemDataBinding(){
		RegisterPipelineController.Util.getInstance().getRegisterPipelineModuleList(true, 
				new AsyncCallback<List<RegisterModuleModel>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				SC.logDebug(caught.getMessage());
			}

			@Override
			public void onSuccess(List<RegisterModuleModel> result) {
				// TODO Auto-generated method stub
				moduleList = result;
				for(RegisterModuleModel rm : moduleList){
					nextLinkedValueMap.put(rm.getModuleID(), rm.getModuleName());
				}
				
				display.getNextLinkedSelectItem().setValueMap(nextLinkedValueMap);
			}
		});
	}
	
	private void setOntologySelectItemDataBinding(){
		OntologyController.Util.getInstance().getOntologyList(Constants.MODULE_TYPE, 
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
						
						display.getOntologySelectItem().setValueMap(ontologyValueMap);
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						SC.logDebug(caught.getMessage());
					}
				});
	}
	
	private void setCloseEvnet(){
		window.addCloseClickHandler(new CloseClickHandler() {
			
			@Override
			public void onCloseClick(CloseClickEvent event) {
				// TODO Auto-generated method stub
				removeRegisterEvent();
				window.close();
				window = null;
			}
		});
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
			SC.warn("The analysis program registration window is already running.");
		}		
	}
}