package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.event.draw.ParameterSettingEvent;
import org.kobic.gwt.smart.closha.client.file.browser.component.FileSelectWindow;
import org.kobic.gwt.smart.closha.client.file.browser.event.SelectFileWindowEvent;
import org.kobic.gwt.smart.closha.client.file.browser.event.SelectFileWindowEventHandler;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ParameterWindow extends Window{
	
	private HandlerManager eventBus;
	private HandlerRegistration registerHandler[];
	
	private DynamicForm form;
	private FormItem[] items;
	private String[] values;
	
	private ToolStripButton resetButton;
	private ToolStripButton settingButton;
	
	private String projectName;
	private List<XmlParameterModel> parameterList;
	
	private Window fileBrowserWindow;
	
	private int size;
	
	@Override   
    protected void onInit() {
		
		registerHandler = new HandlerRegistration[1];
		
		registerHandler[0] = eventBus.addHandler(SelectFileWindowEvent.TYPE, new SelectFileWindowEventHandler() {
			@Override
			public void selectFileWindowEvent(SelectFileWindowEvent event) {
				// TODO Auto-generated method stub
				String formName = event.getFormName();
				String filePath = event.getFilePath();
				
				form.getField(formName).setValue(filePath);
			}
		});

	}
	
	private void closeWindow(){
		for(int i = 0; i < registerHandler.length; i++){
			registerHandler[i].removeHandler();
		}
		destroy();
	}
	
	private void bind(){
		
		settingButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				for(XmlParameterModel pm : parameterList){
					for(FormItem f : form.getFields()){
						if(f.getAttributeAsString("id").equals(pm.getId())){
							pm.setSetupValue(f.getValue().toString());
						}
					}
					
				}
				eventBus.fireEvent(new ParameterSettingEvent(projectName, parameterList));
				closeWindow();
			}
		});
		
		resetButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				form.reset();
			}
		});
		
		this.addCloseClickHandler(new CloseClickHandler() {
			
			@Override
			public void onCloseClick(CloseClickEvent event) {
				// TODO Auto-generated method stub
				closeWindow();
			}
		});
	}
	
	public ParameterWindow(String projectName, List<XmlParameterModel> parameterList, 
			final HandlerManager eventBus, final String userID, final Map<String, String> config){
		
		this.eventBus = eventBus;
		this.projectName = projectName;
		this.parameterList = parameterList;
		
		size = parameterList.size();
		
		int height = 700;
		
		if(height == 0){
			height = 200;
		}
		
		int width = 800;
		
		items = new FormItem[parameterList.size()];
		
		PickerIcon searchPicker = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {  
            public void onFormItemClick(FormItemIconClickEvent event) {
                fileBrowserWindow = new FileSelectWindow(config, userID, event.getItem().getName(), 
                		event.getItem().getAttributeAsString("parameter_type"),
                		event.getItem().getAttributeAsString("parameter_key"),
                		event.getItem().getAttributeAsString("key"), 
                		event.getItem().getAttributeAsString("type"),
                		event.getItem().getAttributeAsString("extensions"), eventBus);
                fileBrowserWindow.show();
            }  
        }); 

		for(int i = 0; i < parameterList.size(); i++){
			
			XmlParameterModel xm = parameterList.get(i);

			if(!(xm.getType().toLowerCase().equals("file") && xm.getParameterType().toLowerCase().equals("output"))){
				
				if(xm.getType().toLowerCase().equals("combo")){
					
					values = new String[xm.getValue().split(",").length];
					values = xm.getValue().split(",");
					
					items[i] = new SelectItem();
					items[i].setAttribute("id", xm.getId());
					items[i].setAttribute("key", xm.getKey());
					items[i].setAttribute("parameter_type", xm.getParameterType());
					items[i].setTitle(xm.getName() + "\t[" + xm.getModule() + "] ");
					items[i].setValueMap(values);
					items[i].setDefaultValue(values[0]);
					items[i].setTooltip(xm.getDescription());
					items[i].setWrapTitle(false);
					items[i].setWidth(550);
					
				}else{
					items[i] = new TextItem();
					items[i].setAttribute("id", xm.getId());
					items[i].setAttribute("key", xm.getKey());
					items[i].setAttribute("parameter_type", xm.getParameterType());
					items[i].setTitle(xm.getName() + "\t[" + xm.getModule() + "] ");
					items[i].setDefaultValue(xm.getSetupValue());
					items[i].setTooltip(xm.getDescription());
					items[i].setWidth(550);
					items[i].setWrapTitle(false);
					
					if(xm.getType().toUpperCase().equals("FILE")){	
						items[i].setIcons(searchPicker);
						items[i].setAttribute("extensions", xm.getExtensions());
					}
				}
			}
		}
		
		form = new DynamicForm();
		form.setHeight100();
		form.setWidth100();
		form.setAlign(Alignment.LEFT);
		form.setItems(items);
		form.setPadding(10);
		
		resetButton = new ToolStripButton();
		resetButton.setTitle("Reset");
		resetButton.setIcon("closha/icon/new_refresh.png");
		
		settingButton = new ToolStripButton();
		settingButton.setTitle("Register");
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
		
		if(size != 0){
			layout.addMember(form);
		}else{
			Label label = new Label("No parameter value to set in the analysis pipeline exists.");
			layout.addMember(label);
		}
		
		setIsModal(true);  
        setShowModalMask(true); 
		setWidth(width);
		setHeight(height);
		setTitle("Set parameters to execute the analysis program");
		setHeaderIcon("closha/icon/debugt_obj.gif");
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setCanDragReposition(false);
		setCanDragResize(true);
		setShowShadow(false);
		centerInPage();
		setAutoCenter(true);
		addItem(layout);
		addItem(toolStrip);
		
		bind();
	}
}