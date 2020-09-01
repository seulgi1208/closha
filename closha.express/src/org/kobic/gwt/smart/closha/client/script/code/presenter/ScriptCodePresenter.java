package org.kobic.gwt.smart.closha.client.script.code.presenter;

import java.util.HashMap;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.common.component.ProgressWindow;
import org.kobic.gwt.smart.closha.client.event.draw.ShowSubSourceCodeTabEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ShowSubSourceCodeTabEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.SourceCodeViewerRemoveRegisterEvent;
import org.kobic.gwt.smart.closha.client.event.draw.SourceCodeViewerRemoveRegisterEventHandler;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.unix.file.controller.UnixFileSystemController;
import org.kobic.gwt.smart.closha.server.controller.unix.file.UnixFileSystemControllerImpl;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.Messages;
import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.CodeFileModel;

import com.google.codemirror2_gwt.client.CodeMirrorConfig;
import com.google.codemirror2_gwt.client.CodeMirrorWrapper;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ScriptCodePresenter implements Presenter{
	
	public static final native CodeMirrorWrapper createEditorFromTextArea(
			TextAreaElement hostElement, CodeMirrorConfig config) 
	/*-{
		return $wnd.CodeMirror.fromTextArea(hostElement, config);
	}-*/;
	
	public static native int getScreenWidth() /*-{ 
		return $wnd.screen.width;
	}-*/;

	public static native int getScreenHeight() /*-{ 
	 	return $wnd.screen.height;
	}-*/;
	
	private HandlerManager eventBus;
	private HandlerRegistration registerHandler[];
	private Display display; 
	private Window progressBarWindow;
	private Map<String, TextAreaItem> wrapperMap;
	private String value;
	private String scriptPath;
	private String fileName;
	private TextAreaItem codeWrapper;
	private Map<String, String> config;
	private String projectName;
	private XmlModuleModel xmlModuleModel;
	
	public interface Display{
		VLayout asWidget();
		TabSet getTabSet();
		ToolStripButton getRedoButton();
		ToolStripButton getUndoButton();
		ToolStripButton getSaveButton();
		ToolStripButton getCloseButton();
	}
	
	public ScriptCodePresenter(HandlerManager eventBus, Map<String, String> config, String projectName,
			XmlModuleModel xmlModuleModel, Display view) {
		this.eventBus = eventBus;
		this.display = view;
		this.config = config;
		
		registerHandler = new HandlerRegistration[2];
		wrapperMap = new HashMap<String, TextAreaItem>();
		
		this.projectName = projectName;
		this.xmlModuleModel = xmlModuleModel;
		
		registerHandler = new HandlerRegistration[2];
		
		addTabSet(xmlModuleModel, projectName);
	}
	
	@Override
	public void init(){
		bind();
	}

	@Override
	public void bind(){
		
		display.getSaveButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				codeWrapper = getCodeWrapper();
				
				if(codeWrapper != null){
					scriptPath = display.getTabSet().getSelectedTab().getAttribute("scriptPath");
					fileName = display.getTabSet().getSelectedTab().getAttribute("fileName");
					value = codeWrapper.getValueAsString();
					
					UnixFileSystemControllerImpl.Util.getInstance().writeFile(scriptPath, value, false, new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							SC.say(caught.getMessage());
						}
						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							SC.say("[" + fileName + "] save complete.");
						}
					});
				}
			}
		});
		
		display.getUndoButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
			}
		});
		
		display.getRedoButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
			}
		});
		
		display.getCloseButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(display.getTabSet().getTabs().length >= 1){
					display.getTabSet().removeTab(display.getTabSet().getSelectedTab().getID());
				}
			}
		});
		
		registerHandler[0] = eventBus.addHandler(SourceCodeViewerRemoveRegisterEvent.TYPE, new SourceCodeViewerRemoveRegisterEventHandler() {
			@Override
			public void sourceCodeViewerRemoveRegister(
					SourceCodeViewerRemoveRegisterEvent event) {
				// TODO Auto-generated method stub
				if(registerHandler.length != 0){
					for(int i = 0; i < registerHandler.length; i++){
						registerHandler[i].removeHandler();
					}
				}
			}
		});
		
		registerHandler[1] = eventBus.addHandler(ShowSubSourceCodeTabEvent.TYPE, new ShowSubSourceCodeTabEventHandler() {
			@Override
			public void showSubSourceCodeTab(ShowSubSourceCodeTabEvent event) {
				// TODO Auto-generated method stub
				addTabSet(event.getXmlModuleModel(), event.getProjectName());
			}
		});
	}
	
	private TextAreaItem getCodeWrapper(){
		
		if(display.getTabSet().getTabs().length == 0){
			return null;
		}else{
			
			codeWrapper = wrapperMap.get(display.getTabSet().getSelectedTab().getAttribute("moduleID"));
			
			if(codeWrapper == null){
				return null;
			}else{
				return codeWrapper;
			}
		}
	}
	
	private void addTabSet(final XmlModuleModel xmlModuleModel, final String projectName){
		UnixFileSystemController.Util.getInstance().readSourceCodeFile(xmlModuleModel.getScriptPath(), new AsyncCallback<CodeFileModel>() {
			@Override
			public void onSuccess(CodeFileModel model) {
				// TODO Auto-generated method stub
				if(model.getCode().equals(Constants.NULL_MESSAGE)){
					SC.warn("Unable to open the file.");
				}else{
					makeSourceCodeTab(model.getCode(), projectName, model.getFileName(), xmlModuleModel.getScriptPath(), xmlModuleModel.getModuleID());
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				SC.say(caught.getMessage());
			}
		});
	}
	
	private void makeSourceCodeTab(String code, String projectName, String fileName, String scriptPath, final String moduleID){
		
		final TextAreaItem textArea = new TextAreaItem();
		textArea.setValue(code);	
		textArea.setShowTitle(false);  
        textArea.setHeight("*");  
        textArea.setWidth((display.getTabSet().getWidth() - 20));
//		textArea.setTextBoxStyle("hintTextStyle");
		
		DynamicForm form = new DynamicForm();
		form.setHeight("*");
		form.setWidth("*");
		form.setFields(textArea);
		
		Tab tab = new Tab();
		tab.setAttribute("scriptPath", scriptPath);
		tab.setAttribute("moduleID", moduleID);
		tab.setAttribute("fileName", fileName);
		tab.setTitle(projectName + "/" + fileName); 
		tab.setWidth(300);
		tab.setIcon("silk/page_code.png");
		tab.setPane(form);
		
		display.getTabSet().addTab(tab);
		display.getTabSet().selectTab(display.getTabSet().getTabs().length-1);
		
		progressBarWindow = new ProgressWindow(Messages.SOURCE_CODE_LOAD_PROGRESS_TITLE);
		progressBarWindow.show();
		
		Timer timer = new Timer() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				wrapperMap.put(moduleID, textArea);
				progressBarWindow.destroy();
			}
		};timer.schedule(2000);
	}

	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.asWidget());
		init();
	}
}