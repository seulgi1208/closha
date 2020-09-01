package org.kobic.gwt.smart.closha.client.board.component;

import java.util.LinkedHashMap;

import org.kobic.gwt.smart.closha.client.board.event.BoardDataRefreshEvent;
import org.kobic.gwt.smart.closha.client.controller.BoardController;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.board.BoardModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ContentWriteWindow extends Window{
	
	private SelectItem issueItem;
	private DynamicForm form;

	public ContentWriteWindow(String title, final UserDto userDto, final HandlerManager eventBus){
		
		setTitle(title);
		setWidth(600);
		setHeight(400);
		setIsModal(true);
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setCanDragReposition(true);
		setCanDragResize(true);
		setShowShadow(true);
		centerInPage();
		setAutoCenter(true);
		setHeaderIcon("closha/icon/pencil.png");
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setMembersMargin(10);
		layout.setMargin(10);
		
		form =  new DynamicForm();
		form.validate(true);
		
		issueItem = new SelectItem();
		issueItem.setRequired(true);
		issueItem.setDefaultValues("comment");
		issueItem.setTitle("Type");
		issueItem.setName("type");

		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put(Constants.DEBUG, "<b>Debug</b>");
		valueMap.put(Constants.COMMENT, "<b>Notice</b>");
		valueMap.put(Constants.ISSUE, "<b>Issue</b>");
		
		issueItem.setValueMap(valueMap);
		
		LinkedHashMap<String, String> valueIcons = new LinkedHashMap<String, String>();
		valueIcons.put(Constants.DEBUG, Constants.DEBUG_IMG);
		valueIcons.put(Constants.COMMENT, Constants.COMMENT_IMG);
		valueIcons.put(Constants.ISSUE, Constants.ISSUE_IMG);
		
		issueItem.setValueIcons(valueIcons);
		
		TextAreaItem summaryItem = new TextAreaItem();
		summaryItem.setName("summary");
		summaryItem.setTitle("Title");
		summaryItem.setRequired(true);
		summaryItem.setWidth("*");
		summaryItem.setHeight(50);
		summaryItem.setLeft(300);
		
		TextAreaItem contentItem = new TextAreaItem();
		contentItem.setName("content");
		contentItem.setTitle("Content");
		contentItem.setHeight(200);  
		contentItem.setRequired(true);
		contentItem.setWidth("*");
		contentItem.setLength(5000);  
		
		TextItem keyItem = new TextItem();
		keyItem.setName("key");
		keyItem.setRequired(true);
		keyItem.setTitle("Keyword");
		keyItem.setWidth(300);
		keyItem.setHint("(Keyword<b>,</b>&nbsp;Keyword)");
		
		form.setItems(issueItem, summaryItem, contentItem, keyItem);
		
		IButton writeButton = new IButton("Write");
		writeButton.setIcon("closha/icon/pencil.png");
		writeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(form.validate()){
					String type = form.getValueAsString("type");
					String summary = form.getValueAsString("summary");
					String content = form.getValueAsString("content");
					String key = form.getValueAsString("key");
					
					BoardModel boardModel = new BoardModel();
					boardModel.setWriter(userDto.getUserId());
					boardModel.setType(type);
					boardModel.setSummary(summary);
					boardModel.setContent(content);
					boardModel.setEmail(userDto.getEmailAdress());
					boardModel.setKeyWord(key);
					
					BoardController.Util.getInstance().writeContentBoard(boardModel, new AsyncCallback<Void>() {
						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							eventBus.fireEvent(new BoardDataRefreshEvent());
							destroy();
						}
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println(caught.getCause() + ":" + caught.getMessage());
						}
					});
				}
			}
		});
		
		IButton resetButton = new IButton("Reset");
		resetButton.setIcon("closha/icon/new_refresh.png");
		resetButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				form.reset();
			}
		});
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setHeight100();
		buttonLayout.setWidth100();
		buttonLayout.setMembersMargin(10);
		buttonLayout.setAlign(Alignment.CENTER); 
		buttonLayout.addMember(writeButton);
		buttonLayout.addMember(resetButton);
		
		layout.addMember(form);
		layout.addMember(buttonLayout);
		
		addItem(layout);
	}
}