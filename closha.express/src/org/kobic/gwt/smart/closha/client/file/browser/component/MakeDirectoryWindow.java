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
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

public class MakeDirectoryWindow extends Window {

	public MakeDirectoryWindow(final String path, final String title, final HandlerManager eventBus, final Map<String, String> config) {

		setTitle(title);
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
		setHeaderIcon("closha/icon/folder_add.png");

		final TextItem nameText = new TextItem("Name");
		nameText.setWidth(200);

		ButtonItem submitButton = new ButtonItem();
		submitButton.setTitle("Add");
		submitButton.setIcon("closha/icon/folder_add.png");
		submitButton.setStartRow(false);
		submitButton.setWidth(80);
		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(nameText.getValueAsString() != null){
					String name = nameText.getValueAsString().replaceAll("\\p{Space}", "_");
					name = name.trim();
					
					HadoopFileSystemController.Util.getInstance().makeDirectory(config, name, path, new AsyncCallback<Void>() {
						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							destroy();
							eventBus.fireEvent(new FileBrowserDataLoadEvent(path));
							eventBus.fireEvent(new FileDataBindEvent());
							
						}
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println(caught.getCause() + ":" + caught.getMessage());
						}
					});
				}else{
					SC.say("Please enter a new folder name.");
				}
			}
		});
		
		DynamicForm form = new DynamicForm();
		form.setLayoutAlign(Alignment.CENTER);
		form.setFields(nameText, submitButton);
		form.setMargin(5);
		form.setNumCols(3);
		form.setColWidths(100, "*", "*");
		
		addItem(form);
	}
}
