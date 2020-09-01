package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.event.draw.ExpertFunctionStraightEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ParameterSettingEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ShowSourceCodeTabEvent;
import org.kobic.gwt.smart.closha.client.file.browser.component.FileSelectWindow;
import org.kobic.gwt.smart.closha.client.file.browser.event.SelectFileWindowEvent;
import org.kobic.gwt.smart.closha.client.file.browser.event.SelectFileWindowEventHandler;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.PickerIcon.Picker;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ModuleDialogWindow extends Window{
	
	private HandlerManager eventBus;
	private HandlerRegistration registerHandler[];
	private DynamicForm form;
	private DynamicForm infoForm;
	private TextItem moduleNameTextItem;
	private TextItem moduleIDTextItem;
	private TextItem moduleAuthorTextItem;
	private TextAreaItem moduleDescTextItem;
	private TextItem moduleTypeTextItem;
	private TextItem moduleScriptTextItem;
	private ToolStrip toolStrip;
	private ToolStripButton editButton;
	private ToolStripButton submitButton;
	private ToolStripButton resetButton;
	private ToolStripButton straightButton;
	private Window innerParameterWindow;
	private Window fileBrowserWindow;
	private FormItem[] items;
	private Tab tTab1;
	private Tab tTab2;
	
	private String[] values;
	private String projectName;
	private String projectDir;
	private String moduleDir;
	private String userDir;
	private UserDto userDto;
	private String key;
	private String tmp;
	private String extensions;
	private List<XmlParameterModel> parameter;
	private List<XmlParameterModel> setParameter;
	private Map<String, String> config;
	private XmlModuleModel xmlModuleModel;
	
	@Override   
    protected void onInit() {
		registerHandler = new HandlerRegistration[1];
	}
		
	private void setOutput(final String moduleName, final String key){
		
		System.out.println("==> module name: " + moduleName);
		
		for(final FormItem item : form.getFields()){
			
			if(item.getAttribute("module_name").equals(moduleName) &&
					item.getAttribute("parameter_type").equals("OUTPUT") 
					&& item.getAttribute("type").equals("FOLDER")){
				
				moduleDir = projectDir
						+ "/"
						+ config.get(Constants.SERVICE_NAME_KEY)
						+ "/" + Constants.PROJECT + "/"
						+ projectName + "/" + moduleName;
				
				tmp = (String) item.getValue();
				
				moduleDir =  tmp.startsWith(userDir) ? tmp : moduleDir + "/" + CommonUtilsGwt.getUUID(); 
				
				if(!item.getValue().equals(moduleDir)){
					item.setValue(moduleDir);	
					setOutputFilePath(moduleDir, key);
//					formMap.get(CommonUtilsGwt.changeType(key)).setBackgroundImage("closha/img/selected_bg_green.png");
				}
			}
		}
	}
	
	private void setOutputFilePath(String path, String key){
		
		for(XmlParameterModel pm : setParameter){
			
			if(pm.getKey().equals(key) && pm.getParameterType().equals("OUTPUT") && pm.getType().equals("FILE")){
				
				List<String> extension = new ArrayList<String>();
				
				String tmp[] = pm.getSetupValue().split("[.]");
				
				for(int i = 1; i < tmp.length; i++){
					extension.add(tmp[i]);
				}
				
				pm.setSetupValue(path + "/output." + CommonUtilsGwt.join(extension, ".", "out"));
			}
		}
	}
	
	private List<XmlParameterModel> getModuleParameters(List<XmlParameterModel> parameter, String key){
		
		List<XmlParameterModel> xmlParameterModelList = null;
		
		xmlParameterModelList = new ArrayList<XmlParameterModel>();
		
		for(XmlParameterModel xm : parameter){
			
			if(xm.getKey().equals(key)){
				
				if(!(xm.getType().toLowerCase().equals("file") && xm.getParameterType().toLowerCase().equals("output"))){
					xmlParameterModelList.add(xm);
				}
				
			}
		}
		return  xmlParameterModelList;
	}
	
	private void setParameterValue(){
		for(XmlParameterModel pm : setParameter){
			for(FormItem f : form.getFields()){
				if(f.getAttributeAsString("id").equals(pm.getId())){
					pm.setSetupValue(f.getValue().toString());
				}
			}
		}
	}
	
	private void closed(){
		for(int i = 0; i < registerHandler.length; i++){
			registerHandler[i].removeHandler();
		}
		destroy();
	}
	
	private void bind(){
		
		this.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				// TODO Auto-generated method stub
				centerInPage();
			}
		});
		
		this.addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				// TODO Auto-generated method stub
				closed();
			}
		});
		
		tTab2.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				// TODO Auto-generated method stub
				if(parameter.size() == 0){
					SC.warn("There are no execution parameters in this analysis program.");
				}
			}
		});
		
		editButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				if(xmlModuleModel.getAppFormat().equals(Constants.SCRIPT_TYPE)){
					eventBus.fireEvent(new ShowSourceCodeTabEvent(xmlModuleModel, projectName));
					closed();
				}else{
					SC.say("This analysis program does not provide a script file.");
				}
				
			}
		});
		
		straightButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new ExpertFunctionStraightEvent(projectName, key));
			}
		});
		
		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub			
				setParameterValue();
				eventBus.fireEvent(new ParameterSettingEvent(projectName, setParameter));
				closed();
			}
		});
		
		resetButton.addClickHandler(new ClickHandler() {		
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				infoForm.reset();
				form.reset();
			}
		});
		
		registerHandler[0] = eventBus.addHandler(SelectFileWindowEvent.TYPE, new SelectFileWindowEventHandler() {
			@Override
			public void selectFileWindowEvent(SelectFileWindowEvent event) {
				// TODO Auto-generated method stub
				
				String formName = event.getFormName();
				String filePath = event.getFilePath();
				
//				extensions = form.getField(formName).getAttribute("extensions");
				
//				if(CommonUtilsGwt.isExtension(extensions, filePath)){
//					form.getField(formName).setValue(filePath);
//					
//					if(!event.getParameterType().equals("OUTPUT")){	
//						setOutput(form.getField(formName).getAttribute("module_name"), event.getKey());
//					}else if(event.getParameterType().equals("OUTPUT")){
//						setOutputFilePath(filePath, event.getKey());
//					}
//					
//					setParameterValue();
//				}else{
//					SC.say("Invalid input type. Please choose the file with the [" + extensions
//							+ "] extension.");
//				}
				form.getField(formName).setValue(filePath);
				
				if(!event.getParameterType().equals("OUTPUT")){	
					setOutput(form.getField(formName).getAttribute("module_name"), event.getKey());
				}else if(event.getParameterType().equals("OUTPUT")){
					setOutputFilePath(filePath, event.getKey());
				}
				
				setParameterValue();
			}
		});	
	}
	
	public ModuleDialogWindow(final UserDto userDto, final String projectName,
			String key, XmlModuleModel xmlModuleModel,
			final List<XmlParameterModel> parameter,
			final HandlerManager eventBus, final Map<String, String> config) {
		
		this.userDto = userDto;
		this.projectName = projectName;
		this.key = key;
		this.parameter = getModuleParameters(parameter, key);
		this.setParameter = parameter;
		this.eventBus = eventBus;
		this.xmlModuleModel = xmlModuleModel;
		this.config = config;
		
		setIsModal(true);  
        setShowModalMask(true); 
		setTitle(xmlModuleModel.getModuleName() + " setting window");
		setHeaderIcon("closha/icon/debugt_obj.gif");
		setID(Constants.MODULE_DIALOG_WINDOW_ID);
		setWidth(700);
		setHeight(500);
		setShowMinimizeButton(false);
		setCanDragReposition(false);
		setCanDragResize(true);
		setShowCloseButton(true);
		setShowMaximizeButton(true);
		setCanDragReposition(true);
		setAutoCenter(true);
		setShowShadow(false);
		centerInPage();
		
		userDir = config.get(Constants.HDFS_DIR_KEY) + "/"
				+ userDto.getUserId();
		projectDir = userDir + "/" + config.get(Constants.SERVICE_NAME_KEY)
				+ "/" + Constants.PROJECT + "/" + projectName;
		
		draw_module();
	}
	
	private void draw_module(){
		
		moduleNameTextItem = new TextItem();
		moduleNameTextItem.setName("moduleName");
		moduleNameTextItem.setTitle("Program Name");
		moduleNameTextItem.setWidth("*");
		moduleNameTextItem.setRequired(true);
		moduleNameTextItem.setCanEdit(false);
		moduleNameTextItem.setValue(xmlModuleModel.getModuleName());
		
		moduleIDTextItem = new TextItem();
		moduleIDTextItem.setName("moduleID");
		moduleIDTextItem.setTitle("Program ID");
		moduleIDTextItem.setWidth("*");
		moduleIDTextItem.setRequired(true);
		moduleIDTextItem.disable();
		moduleIDTextItem.setValue(xmlModuleModel.getModuleID());
		
		moduleAuthorTextItem = new TextItem();
		moduleAuthorTextItem.setName("moduleAuthor");
		moduleAuthorTextItem.setTitle("Writer");
		moduleAuthorTextItem.setWidth("*");
		moduleAuthorTextItem.setRequired(true);
		moduleAuthorTextItem.disable();
		moduleAuthorTextItem.setValue(xmlModuleModel.getModuleAuthor());
		
		moduleDescTextItem = new TextAreaItem();
		moduleDescTextItem.setName("desc");
		moduleDescTextItem.setTitle("Description");
		moduleDescTextItem.setLength(5000);
		moduleDescTextItem.setColSpan(2);
		moduleDescTextItem.setWidth("*");
		moduleDescTextItem.setHeight(150);
		moduleDescTextItem.setRequired(true);
		moduleDescTextItem.setCanEdit(false);
		moduleDescTextItem.setValue(xmlModuleModel.getModuleDesc());
		
		moduleTypeTextItem = new TextItem();
		moduleTypeTextItem.setName("moduleType");
		moduleTypeTextItem.setTitle("Type");
		moduleTypeTextItem.setWidth("*");
		moduleTypeTextItem.setRequired(true);
		moduleTypeTextItem.disable();
		moduleTypeTextItem.setValue(xmlModuleModel.getType());
		
		moduleScriptTextItem = new TextItem();
		moduleScriptTextItem.setName("scriptPath");
		moduleScriptTextItem.setTitle("Script Path");
		moduleScriptTextItem.setWidth("*");
		moduleScriptTextItem.setRequired(true);
		moduleScriptTextItem.setCanEdit(false);
		moduleScriptTextItem.setVisible(userDto.isAdmin());
		moduleScriptTextItem.setValue(xmlModuleModel.getScriptPath());
		
		infoForm = new DynamicForm();
		infoForm.setHeight100();
		infoForm.setWidth100();
		infoForm.setIsGroup(false);
		infoForm.setNumCols(2);
		infoForm.setColWidths(150, 550);
		infoForm.setPadding(5);
		infoForm.setCanDragResize(false);
		infoForm.setValidateOnChange(true);
		infoForm.setFields(moduleNameTextItem, moduleIDTextItem,
				moduleAuthorTextItem, moduleDescTextItem, moduleTypeTextItem,
				moduleScriptTextItem);
		
		VLayout moduleInfoLayout = new VLayout();
		moduleInfoLayout.setHeight100();
		moduleInfoLayout.setWidth100();
		moduleInfoLayout.addMember(infoForm);
		
		Picker dir = new Picker("closha/icon/folder_explore_bt.png");
		Picker pipe = new Picker("closha/icon/pipeline_bt.png");
		
		PickerIcon searchPicker = new PickerIcon(dir, new FormItemClickHandler() {  
            public void onFormItemClick(FormItemIconClickEvent event) {
                fileBrowserWindow = new FileSelectWindow(config, userDto.getUserId(), event.getItem().getName(), 
                		event.getItem().getAttributeAsString("parameter_type"),
                		event.getItem().getAttributeAsString("parameter_key"),
                		event.getItem().getAttributeAsString("key"),
                		event.getItem().getAttributeAsString("type"),
                		event.getItem().getAttributeAsString("extensions"), eventBus);
                fileBrowserWindow.show();
            }  
        }); 
		
		PickerIcon parameterPicker = new PickerIcon(pipe, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				// TODO Auto-generated method stub
				innerParameterWindow = new ParameterInProjectWindow(config, userDto.getUserId(), event.getItem().getName(), 
						event.getItem().getAttributeAsString("parameter_type"),
						event.getItem().getAttributeAsString("parameter_key"), parameter,						
						event.getItem().getAttributeAsString("key"), projectName,
						event.getItem().getAttributeAsString("type"), 
						event.getItem().getAttributeAsString("extensions"), eventBus);
				
				innerParameterWindow.show();
			}
		});
		
		items = new FormItem[this.parameter.size()];
		
		for(int i = 0; i < this.parameter.size(); i++){
			
			final XmlParameterModel xm = this.parameter.get(i);
			
			System.out.println("parameter_name : " + xm.getName() + ", key: " + key + ":" + xm.getKey());
			
			if(xm.getKey().equals(key)){
				
				if(xm.getType().toLowerCase().equals("combo")){
					
					values = new String[xm.getValue().split(",").length];
					values = xm.getValue().split(",");
					
					items[i] = new SelectItem();
					items[i].setAttribute("id", xm.getId());
					items[i].setAttribute("key", xm.getKey());
					items[i].setAttribute("parameter_type", xm.getParameterType());
					items[i].setAttribute("module_name", xm.getModule());
					items[i].setAttribute("type", xm.getType());
					items[i].setAttribute("reg_value", xm.getValue());
					items[i].setTitle("[" + xm.getName() + "] ");
					items[i].setValueMap(values);
					items[i].setDefaultValue(xm.getSetupValue().split(",").length != 1 ? values[0] : xm.getSetupValue());
					items[i].setTooltip(xm.getDescription());
					items[i].setWidth("*");
					
				}else{
					items[i] = new TextItem();
					items[i].setAttribute("id", xm.getId());
					items[i].setAttribute("key", xm.getKey());
					items[i].setAttribute("parameter_type", xm.getParameterType());
					items[i].setAttribute("module_name", xm.getModule());
					items[i].setAttribute("type", xm.getType());
					items[i].setAttribute("reg_value", xm.getValue());
					items[i].setTitle("[" + xm.getName() + "] ");
					
					if(xm.getParameterType().toLowerCase().equals("output") 
							&& xm.getType().toLowerCase().equals("folder")){
						
						if(!xm.getSetupValue().startsWith(userDir)){
							items[i].setDefaultValue(projectDir + "/" + xm.getModule() + "/" + xm.getValue());
						}else{
							items[i].setDefaultValue(xm.getSetupValue());
						}
					}else{
						items[i].setDefaultValue(xm.getSetupValue());
					}
					
					items[i].setTooltip(xm.getDescription());
					items[i].setWidth("*");
					
					if (xm.getType().toLowerCase().equals("file")
							|| xm.getType().toLowerCase().equals("folder")
							|| xm.getType().toLowerCase().equals("reference")) {
						
						items[i].setAttribute("extensions", xm.getExtensions());
						
						items[i].setIcons(parameterPicker, searchPicker);
						
						items[i].addKeyPressHandler(new KeyPressHandler() {
							@Override
							public void onKeyPress(KeyPressEvent event) {
								// TODO Auto-generated method stub
								SC.say("This is an input value that cannot be set. Please select the input/output data using the file selection window provided.");
							}
						});
						
						items[i].addChangedHandler(new ChangedHandler() {
							@Override
							public void onChanged(ChangedEvent event) {
								// TODO Auto-generated method stub
								
								String path = (String) event.getItem().getValue();
								
								if(!path.startsWith(projectDir)){
									event.getItem().setTooltip("This is an input value that cannot be set. Please select the input/output data using the file selection window provided.");
									event.getItem().setValue(xm.getSetupValue());
								}else{
									event.getItem().setTooltip(xm.getDescription());
								}
							}
						});
					}else{
						items[i].addChangedHandler(new ChangedHandler() {
							@Override
							public void onChanged(ChangedEvent event) {
								// TODO Auto-generated method stub
								setParameterValue();
							}
						});
					}
				}
			}			
		}
		
		form = new DynamicForm();
		form.setHeight100();
		form.setWidth100();
		form.setIsGroup(false);
		form.setNumCols(2);
		form.setColWidths(200, 500);
		form.setPadding(5);
		form.setCanDragResize(false);
		form.setItems(items);
		
		HTMLPane htmlPane = new HTMLPane();
		htmlPane.setHeight100();
		htmlPane.setWidth100();
		htmlPane.setShowEdges(true);
		htmlPane.setContentsURL(xmlModuleModel.getUrl());
		htmlPane.setContentsType(ContentsType.PAGE);
		
        SectionStackSection section1 = new SectionStackSection(xmlModuleModel.getModuleName() + " Parameters Setting");  
        section1.setExpanded(true);  
        section1.setCanCollapse(true); 
        section1.addItem(form);
        
        SectionStackSection section2 = new SectionStackSection(xmlModuleModel.getModuleName()  + " Relevant website");  
        section2.setExpanded(true);  
        section2.setCanCollapse(true);  
        section2.addItem(htmlPane);  
        
        final SectionStack sectionStack = new SectionStack();  
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);  
        sectionStack.setWidth100();
        sectionStack.setHeight100();  
        sectionStack.addSection(section1);  
        sectionStack.addSection(section2);  
		
		tTab1 = new Tab("Basic Information", "closha/icon/document.png");
		tTab1.setWidth(200);
		tTab1.setPane(moduleInfoLayout);

		tTab2 = new Tab("Parameter", "closha/icon/setting.png");
		tTab2.setWidth(200);
		tTab2.setPane(sectionStack);

		final TabSet tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setWidth100();
		tabSet.setHeight100();
		tabSet.addTab(tTab1);
		tabSet.addTab(tTab2);
		
		editButton = new ToolStripButton("Edit Code");
		editButton.setIcon("closha/icon/application_edit.png");
		editButton.setActionType(SelectionType.BUTTON);
		editButton.setVisible(false);
		
		if (userDto.isAdmin()
				|| xmlModuleModel.getModuleAuthor().equals(userDto.getUserId())) {
			editButton.setVisible(true);
		}
		
		straightButton = new ToolStripButton("Reset Link");
		straightButton.setIcon("closha/icon/arrow_refresh_small.png");
		straightButton.setActionType(SelectionType.BUTTON);

		submitButton = new ToolStripButton("Confirm");
		submitButton.setIcon("closha/icon/accept.png");
		submitButton.setActionType(SelectionType.BUTTON);
		
		resetButton = new ToolStripButton("Reset");
		resetButton.setIcon("closha/icon/field_reset.png");
		resetButton.setActionType(SelectionType.BUTTON);
		
		toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setAlign(Alignment.CENTER);
		toolStrip.addFill();
		toolStrip.addButton(editButton);
		toolStrip.addButton(straightButton);
		toolStrip.addButton(resetButton);
		toolStrip.addButton(submitButton);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.addMembers(tabSet, toolStrip);
		
		addItem(layout);
		
		bind();
	}
}