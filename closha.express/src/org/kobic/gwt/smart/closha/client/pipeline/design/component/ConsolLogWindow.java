package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import java.util.LinkedHashMap;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.event.draw.CloseFunctionWindowEvent;
import org.kobic.gwt.smart.closha.client.unix.file.controller.UnixFileSystemController;
import org.kobic.gwt.smart.closha.shared.Constants;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionType;
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
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ConsolLogWindow extends Window{

	private String projectName;
	private String userID;
	private HandlerManager eventBus;
	private Map<String, String> config;
	private HTMLPane textPane;
	private ToolStrip toolStrip;
	private ToolStripButton logContentReloadButton;
	private Timer timer;
	
	private DynamicForm form;
	private SelectItem logsSelectItem;
	private ButtonItem logsListRefreshButton;
	
	@Override   
    protected void onInit() {
		setSelectItemDataBind();
	}
	
	private void closed(){
		
		if(logContentReloadButton.getSelected() != null){
			if(logContentReloadButton.getSelected() && timer != null){
				timer.cancel();
			}			
		}
		
		eventBus.fireEvent(new CloseFunctionWindowEvent(projectName, Constants.CONSOL_LOG_WINDOW_ID));
		
		destroy();
	}
	
	private void warning(){
		SC.warn("The execution log file of analysis pipeline does not exist.");
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
		
		setSelectLogContentReloadButton();
		setSelectItemChangedEvent();
		setLogsListRefreshButtonEvent();
	}
	
	private void setSelectLogContentReloadButton(){
		
		logContentReloadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				String logPath = logsSelectItem.getValueAsString();
				getProjectLogData(logPath);
			}
		});
	}
	
	private void setLogsListRefreshButtonEvent(){
		logsListRefreshButton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			
			@Override
			public void onClick(
					com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				// TODO Auto-generated method stub
				setSelectItemDataBind();
			}
		});
	}
	
	private void setSelectItemDataBind(){
		UnixFileSystemController.Util.getInstance().getLogFile(config, userID, projectName, new AsyncCallback<LinkedHashMap<String, String>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				warning();
			}
			
			@Override
			public void onSuccess(LinkedHashMap<String, String> result) {
				// TODO Auto-generated method stub
				
				LinkedHashMap<String, String> logsIconMap = new LinkedHashMap<String, String>();
				
				for (Map.Entry<String, String> elem : result.entrySet()) {
					logsIconMap.put(elem.getKey(), "silk/page_white_tux.png");
				}
				
				logsSelectItem.setValueMap(result);
				logsSelectItem.setValueIcons(logsIconMap);
			}
		});
	}
	
	private void getProjectLogData(String logPath){
		
		UnixFileSystemController.Util.getInstance().readFile(logPath, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				warning();
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				textPane.setContents(result);
			}
		});
		
	}
	
	private void setSelectItemChangedEvent(){
		logsSelectItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				String logPath = logsSelectItem.getValueAsString();
				getProjectLogData(logPath);
			}
		});
	}
	
	public ConsolLogWindow(HandlerManager eventBus, Map<String, String> config, String projectName, String userID) {
		
		this.projectName = projectName;
		this.userID = userID;
		this.eventBus = eventBus;
		this.config = config;
		
		logsSelectItem = new SelectItem();
		logsSelectItem.setWidth(300);
		logsSelectItem.setName("logs_file");
		logsSelectItem.setRequired(true);
		logsSelectItem.setShowTitle(false);
		
		logsListRefreshButton = new ButtonItem("logs_reload", "Refresh");
		logsListRefreshButton.setStartRow(false);
		logsListRefreshButton.setWidth(100);
		logsListRefreshButton.setIcon("silk/database_refresh.png");
 
		form = new DynamicForm();
		form.setHeight(25);
		form.setWidth100();
		form.setAlign(Alignment.RIGHT);
		form.setFields(logsSelectItem, logsListRefreshButton);
		
		textPane = new HTMLPane();    
        textPane.setHeight100();
        textPane.setWidth100();
        textPane.setBorder("1px solid gray");
        
		logContentReloadButton = new ToolStripButton("Logs Data Reload");
		logContentReloadButton.setIcon("silk/page_refresh.png");
		logContentReloadButton.setActionType(SelectionType.CHECKBOX);

		toolStrip = new ToolStrip();
		toolStrip.addFill();
		toolStrip.addButton(logContentReloadButton);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setMembers(form, textPane, toolStrip);
		
		setWidth(600);
		setHeight(500);
		setTitle(projectName + " Console");
		setHeaderIcon("silk/application_xp_terminal.png");
		setCanDragReposition(true);  
        setCanDragResize(true);
        setShowCloseButton(true);
        setShowMaximizeButton(true);
        setShowMinimizeButton(true);
        
		addItem(layout);
		
		bind();
	}
}
