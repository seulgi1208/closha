package org.kobic.gwt.smart.closha.client.file.browser.component;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.file.browser.event.FileDataBindEvent;
import org.kobic.gwt.smart.closha.client.file.explorer.controller.HadoopFileSystemController;
import org.kobic.gwt.smart.closha.client.file.explorer.event.FileBrowserDataLoadEvent;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class EditWindow extends Window {
	
	private HandlerManager eventBus;
	
	private void dataReLoad(Map<String, String> config, String path){
		HadoopFileSystemController.Util.getInstance().getParentDirectoryPath(config, path, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(String parentPath) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new FileBrowserDataLoadEvent(parentPath));
				eventBus.fireEvent(new FileDataBindEvent());
				destroy();
			}
		});
	}

	public EditWindow(final String path, final String fileName, final HandlerManager eventBus, final Map<String, String> config) {
		
		this.eventBus = eventBus;
		
		setTitle(fileName);
		setWidth(400);
		setHeight(80);
		setIsModal(true);
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setCanDragReposition(true);
		setCanDragResize(true);
		centerInPage();
		setAutoCenter(true);
		setHeaderIcon("closha/icon/pencil_go.png");

		final TextItem nameText = new TextItem("FileName");
		nameText.setWidth(200);
		nameText.setDefaultValue(fileName);

		final ButtonItem submitButton = new ButtonItem();
		submitButton.setTitle("Modify");
		submitButton.setIcon("closha/icon/pencil_go.png");
		submitButton.disable();
		submitButton.setStartRow(false);
		submitButton.setWidth(80);
		
		nameText.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				if(fileName.equals(nameText.getValueAsString())){
					submitButton.disable();
				}else{
					submitButton.enable();
				}
			}
		});
		
		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(nameText.getValueAsString() != null){
					String name = nameText.getValueAsString().replaceAll("\\p{Space}", "_");
					name = name.trim();
					
					HadoopFileSystemController.Util.getInstance().editFile(config, path, name, new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println(caught.getCause() + ":" + caught.getMessage());
						}
						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							dataReLoad(config, path);
						}
					});
				}else{
					SC.say("Please enter a new name.");
				}
			}
		});
		
		DynamicForm form = new DynamicForm();
		form.setLayoutAlign(Alignment.CENTER);
		form.setMargin(5);
		form.setNumCols(3);
		form.setColWidths(100, "*", "*");
		form.setFields(nameText, submitButton);
		
		addItem(form);
	}
}