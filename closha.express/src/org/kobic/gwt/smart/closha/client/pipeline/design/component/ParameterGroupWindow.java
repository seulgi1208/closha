package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.kobic.gwt.smart.closha.client.event.draw.ParameterSettingEvent;
import org.kobic.gwt.smart.closha.client.file.browser.component.FileSelectWindow;
import org.kobic.gwt.smart.closha.client.file.browser.event.SelectFileWindowEvent;
import org.kobic.gwt.smart.closha.client.file.browser.event.SelectFileWindowEventHandler;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.design.XmlConnectLinkedModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.PickerIcon.Picker;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ParameterGroupWindow extends Window{
	
	private HandlerManager eventBus;
	private HandlerRegistration registerHandler[];
	
	private String[] values;
	
	private Map<String, String> config;
	
	private ToolStripButton resetButton;
	private ToolStripButton settingButton;
	
	private String projectName;
	private String userDir;
	private String projectDir;
	private String moduleDir;
	private String tmp;
	private String extensions;
	private List<XmlParameterModel> parameterList;
	private List<XmlConnectLinkedModel> connectLinkedList;
	private List<XmlModuleModel> moduleList;
	private Window fileBrowserWindow;
	private Window innerParameterWindow;
	private Map<Integer, DynamicForm> formMap = new LinkedHashMap<Integer, DynamicForm>();
	private Map<String, List<FormItem>> itemMap = new LinkedHashMap<String, List<FormItem>>();
	private Map<String, List<String>> sourceLinkedMap = new LinkedHashMap<String, List<String>>();
	private Map<String, List<String>> targetLinkedMap = new LinkedHashMap<String, List<String>>();
	private Map<String, String> moduleMap = new LinkedHashMap<String, String>();
	
	public List<XmlParameterModel> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<XmlParameterModel> parameterList) {
		this.parameterList = parameterList;
	}
	
	@Override   
    protected void onInit() {
		
		registerHandler = new HandlerRegistration[1];
		
		registerHandler[0] = eventBus.addHandler(SelectFileWindowEvent.TYPE, new SelectFileWindowEventHandler() {
			
			@Override
			public void selectFileWindowEvent(final SelectFileWindowEvent event) {
				// TODO Auto-generated method stub
				
				String filePath = event.getFilePath();
				String key = event.getKey();
				String parameterKey = event.getParameterKey();
				
				if(formMap.get(CommonUtilsGwt.changeType(key)).getField("pName_" + parameterKey) != null){
					
					extensions = formMap.get(CommonUtilsGwt.changeType(key)).getField("pName_" + parameterKey).getAttribute("extensions");
					
//					if(CommonUtilsGwt.isExtension(extensions, filePath)){
//						
//						formMap.get(CommonUtilsGwt.changeType(key)).getField("pName_" + parameterKey).setValue(filePath);
//						
//						if(!event.getParameterType().equals("OUTPUT")){
//							setOutput(key, formMap.get(CommonUtilsGwt.changeType(key)).getField("pName_" + parameterKey).getAttribute("module_name"));
//						}else if(event.getParameterType().equals("OUTPUT")){
//							setOutputFilePath(filePath, event.getKey());
//						}
//						
//						setParameterValue();
//						
//						formMap.get(CommonUtilsGwt.changeType(key)).setBackgroundImage("closha/img/selected_bg_green.png");
//					}else{
//						SC.say("Invalid input type. Please choose the file with the [" + extensions
//								+ "] extension.");
//					}
					
					formMap.get(CommonUtilsGwt.changeType(key)).getField("pName_" + parameterKey).setValue(filePath);
					
					if(!event.getParameterType().equals("OUTPUT")){
						setOutput(key, formMap.get(CommonUtilsGwt.changeType(key)).getField("pName_" + parameterKey).getAttribute("module_name"));
					}else if(event.getParameterType().equals("OUTPUT")){
						setOutputFilePath(filePath, event.getKey());
					}
					
					setParameterValue();
					
					formMap.get(CommonUtilsGwt.changeType(key)).setBackgroundImage("closha/img/selected_bg_green.png");
				}
			}
		});
	}
	
	private void setOutput(final String key, String moduleName){
		
		for(final FormItem item : formMap.get(CommonUtilsGwt.changeType(key)).getFields()){
			
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
					formMap.get(CommonUtilsGwt.changeType(key)).setBackgroundImage("closha/img/selected_bg_green.png");
				}
			}
				
		}
	}
	
	private void setOutputFilePath(String path, String key){
		
		for(XmlParameterModel pm : parameterList){
			
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
	
	private void closeWindow(){
		for(int i = 0; i < registerHandler.length; i++){
			registerHandler[i].removeHandler();
		}
		this.clear();
		this.destroy();
	}
	
	private void setParameterValue(){
		for(XmlParameterModel pm : parameterList){
			for (Map.Entry<Integer, DynamicForm> elem : formMap.entrySet()) {
				for(FormItem f : elem.getValue().getFields()){
					if(f.getAttributeAsString("id").equals(pm.getId())){
						pm.setSetupValue(f.getValue().toString());
					}
				}
			}
		}
	}
	
	private void bind(){
		
		settingButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub				
				setParameterValue();
				eventBus.fireEvent(new ParameterSettingEvent(projectName, parameterList));
				closeWindow();
			}
		});
		
		resetButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				SC.say(projectName, "Are you sure you want to initialize the parameter values?", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						// TODO Auto-generated method stub
						for (Map.Entry<Integer, DynamicForm> elem : formMap.entrySet()) {
							elem.getValue().reset();
						}	
					}
				});
			}
		});
		
		this.addCloseClickHandler(new CloseClickHandler() {
			
			@Override
			public void onCloseClick(CloseClickEvent event) {
				// TODO Auto-generated method stub
				
				for (Map.Entry<Integer, DynamicForm> elem : formMap.entrySet()) {
					elem.getValue().reset();
				}
				
				closeWindow();
			}
		});
	}
	
	public ParameterGroupWindow(final String projectName,
			List<XmlModuleModel> moduleList,
			List<XmlParameterModel> parameterList,
			List<XmlConnectLinkedModel> connectLinkedList,
			final HandlerManager eventBus, final String userId,
			final Map<String, String> config) {
		
		this.eventBus = eventBus;
		this.config = config;
		this.projectName = projectName;
		this.parameterList = parameterList;
		this.connectLinkedList = connectLinkedList;
		this.moduleList = moduleList;
		
		setParameterList(parameterList);
		
		int height = 700;
		int width = 800;
		
		userDir = config.get(Constants.HDFS_DIR_KEY) + "/" + userId;
		projectDir = userDir
				+ "/"
				+ config.get(Constants.SERVICE_NAME_KEY)
				+ "/" + Constants.PROJECT + "/"
				+ projectName;
		
		Picker dir = new Picker("closha/icon/folder_explore_bt.png");
		Picker pipe = new Picker("closha/icon/pipeline_bt.png");
		
		PickerIcon searchPicker = new PickerIcon(dir, new FormItemClickHandler() {  
            public void onFormItemClick(FormItemIconClickEvent event) {
            	
                fileBrowserWindow = new FileSelectWindow(config, userId, event.getItem().getName(), 
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
				innerParameterWindow = new ParameterInProjectWindow(config, userId, event.getItem().getName(), 
						event.getItem().getAttributeAsString("parameter_type"),
						event.getItem().getAttributeAsString("parameter_key"), getParameterList(),						
						event.getItem().getAttributeAsString("key"), projectName, 
						event.getItem().getAttributeAsString("type"), 
						event.getItem().getAttributeAsString("extensions"), eventBus);
				
				innerParameterWindow.show();
			}
		});
		
		for(XmlModuleModel x : this.moduleList){
			moduleMap.put(x.getKey(), x.getModuleName());
		}
		
		for(XmlConnectLinkedModel c : this.connectLinkedList){
			
			System.out.println(c.getKey() + "==>" + c.getSource() + ":" + c.getTarget());
			
			if(sourceLinkedMap.get(c.getTarget()) == null){
				List<String> source = new ArrayList<String>();
				source.add(moduleMap.get(c.getSource()));
				sourceLinkedMap.put(c.getTarget(), source);
			}else{
				sourceLinkedMap.get(c.getTarget()).add(moduleMap.get(c.getSource()));
			}
			
			if(targetLinkedMap.get(c.getSource()) == null){
				List<String> target = new ArrayList<String>();
				target.add(moduleMap.get(c.getTarget()));
				targetLinkedMap.put(c.getSource(), target);
			}else{
				targetLinkedMap.get(c.getSource()).add(moduleMap.get(c.getTarget()));
			}
		}
		
		for (int i = 0; i < this.parameterList.size(); i++) {

			XmlParameterModel xm = this.parameterList.get(i);
			
			if (formMap.get(CommonUtilsGwt.changeType(xm.getKey())) == null) {
				DynamicForm form = new DynamicForm();
				form.setHeight100();
				form.setWidth100();
				form.setAlign(Alignment.LEFT);
				form.setPadding(10);
				form.setGroupTitle("<p align=\"justify\" style=\"font-size:12px;\">" + xm.getModule()
						+ ": ["
						+ CommonUtilsGwt.getExplorerFont(CommonUtilsGwt.join(sourceLinkedMap.get(xm.getKey()), ",", "No Data"), "#a3c700")
						+ " >> "
						+ CommonUtilsGwt.getExplorerFont(xm.getModule(),
								"#45a6ff") + " >> "
						+ CommonUtilsGwt.getExplorerFont(CommonUtilsGwt.join(targetLinkedMap.get(xm.getKey()), ",", "No Data"), "#ff7438") + "]</p>");
				form.setIsGroup(true);				
//				form.setID(xm.getModule() + "_" + i);
				
				List<FormItem> list = new ArrayList<FormItem>();

				System.out.println("=======>" + xm.getKey() + " : " + xm.getModule() + ":" + xm.getName());
				
				formMap.put(CommonUtilsGwt.changeType(xm.getKey()), form);
				itemMap.put(xm.getKey(), list);
			}
		}
		
		for (int i = 0; i < this.parameterList.size(); i++) {

			final XmlParameterModel xm = this.parameterList.get(i);
			
			System.out.println( i + ":" + xm.getKey() + ":" + xm.getModule() + ":" + xm.getName());
			
			if(!(xm.getType().toLowerCase().equals("file") && xm.getParameterType().toLowerCase().equals("output"))){
							
				if (xm.getType().toLowerCase().equals("combo")) {
					
					values = new String[xm.getValue().split(",").length];
					values = xm.getValue().split(",");

					SelectItem selectItem = new SelectItem();
					selectItem.setAttribute("id", xm.getId());
					selectItem.setAttribute("key", xm.getKey());
					selectItem.setAttribute("parameter_type", xm.getParameterType());
					selectItem.setAttribute("parameter_key", String.valueOf(i));
					selectItem.setAttribute("module_name", xm.getModule());
					selectItem.setAttribute("type", xm.getType());
					selectItem.setAttribute("reg_value", xm.getValue());
					selectItem.setTitle("[" + xm.getName() + "] ");
					selectItem.setName("pName_" + String.valueOf(i));
					selectItem.setTitleColSpan(2);
					selectItem.setValueMap(values);
					selectItem.setDefaultValue(xm.getSetupValue().split(",").length != 1 ? values[0] : xm.getSetupValue());
					selectItem.setTooltip(xm.getDescription());
					selectItem.setWrapTitle(false);
					selectItem.setWidth(550);
					
					itemMap.get(xm.getKey()).add(selectItem);

				} else {
					
					final TextItem textItem = new TextItem();
					textItem.setAttribute("id", xm.getId());
					textItem.setAttribute("key", xm.getKey());
					textItem.setAttribute("parameter_type", xm.getParameterType());
					textItem.setAttribute("parameter_key", String.valueOf(i));
					textItem.setAttribute("module_name", xm.getModule());
					textItem.setAttribute("type", xm.getType());
					textItem.setAttribute("reg_value", xm.getValue());
					textItem.setTitle("[" + xm.getName() + "] ");
					textItem.setName("pName_" + String.valueOf(i));
					textItem.setTitleColSpan(2);
					
					if(xm.getParameterType().toLowerCase().equals("output") 
							&& xm.getType().toLowerCase().equals("folder")){
						
						if(!xm.getSetupValue().startsWith(userDir)){
							textItem.setDefaultValue(projectDir + "/" + xm.getModule() + "/" + xm.getValue());
						}else{
							textItem.setDefaultValue(xm.getSetupValue());
						}
					}else{
						textItem.setDefaultValue(xm.getSetupValue());
					}
					
					textItem.setTooltip(xm.getDescription());
					textItem.setWidth(550);
					textItem.setWrapTitle(false);

					if (xm.getType().toLowerCase().equals("file")
							|| xm.getType().toLowerCase().equals("folder")
							|| xm.getType().toLowerCase().equals("reference")) {	
						
						textItem.setAttribute("extensions", xm.getExtensions());
						
						textItem.setIcons(parameterPicker, searchPicker);
						
						textItem.addChangedHandler(new ChangedHandler() {
							
							@Override
							public void onChanged(ChangedEvent event) {
								// TODO Auto-generated method stub
								
								String path = (String) event.getItem().getValue();
								
								if(!path.startsWith(userDir)){
									event.getItem().setTooltip("Please select the input/output data using the file selection window provided.");
									event.getItem().setValue(xm.getSetupValue());
								}else{
									event.getItem().setTooltip(xm.getDescription());
								}
							}
						});
						
						textItem.addKeyPressHandler(new KeyPressHandler() {
							
							@Override
							public void onKeyPress(KeyPressEvent event) {
								// TODO Auto-generated method stub
								SC.say("Please select the input/output data using the file selection window provided.");
							}
						});
						
					}else{
						textItem.addChangedHandler(new ChangedHandler() {
							@Override
							public void onChanged(ChangedEvent event) {
								// TODO Auto-generated method stub
								setParameterValue();
							}
						});
					}

					itemMap.get(xm.getKey()).add(textItem);
				}
			}			
		}

		resetButton = new ToolStripButton();
		resetButton.setTitle("Reset");
		resetButton.setIcon("closha/icon/field_reset.png");

		settingButton = new ToolStripButton();
		settingButton.setTitle("Confirm");
		settingButton.setIcon("closha/icon/accept.png");

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.addFill();
		toolStrip.addButton(resetButton);
		toolStrip.addButton(settingButton);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setMembersMargin(10);
		layout.setPadding(5);

		if (formMap.size() != 0) {
			
			TreeMap<Integer, DynamicForm> treeMap = new TreeMap<Integer, DynamicForm>(formMap);
		        
			for (Map.Entry<Integer, DynamicForm> elem : treeMap.entrySet()) {
				
				List<FormItem> list = itemMap.get(CommonUtilsGwt.changeType(elem.getKey()));
				
				FormItem[] items = new FormItem[list.size()];
				
				for (int i = 0; i < list.size(); i++) {
					
					System.out.println("!!==>" + list.get(i).getAttribute("module_name") + ":" + list.get(i).getTitle() + ":" + list.get(i).getAttribute("parameter_key"));
					
					items[i] = list.get(i);
				}
				
				Arrays.sort(items, new Comparator<FormItem>() {
					@Override
					public int compare(FormItem o1, FormItem o2) {
						// TODO Auto-generated method stub
						if (Integer.parseInt(o1.getAttribute("parameter_key")) < Integer.parseInt(o2.getAttribute("parameter_key"))){
				            return -1;
				        }else{
				            return 1;
				        }
					}
				});
				
				elem.getValue().setItems(items);

				layout.addMember(elem.getValue());
			}
		} else {
			Label label = new Label("There are no execution parameters in this analysis program.");
			layout.addMember(label);
		}

		setIsModal(true);
		setShowModalMask(true);
		setWidth(width);
		setHeight(height);
		setTitle("Analysis Program Parameter Settings");
		setHeaderIcon("closha/icon/setting.png");
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setShowMaximizeButton(true);
		setCanDragReposition(true);
		setCanDragResize(true);
		setShowShadow(false);
		centerInPage();
		setAutoCenter(true);
		addItem(layout);
		addItem(toolStrip);

		bind();
	}
}