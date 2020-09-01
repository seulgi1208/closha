package org.kobic.gwt.smart.closha.client.board.component;

import java.util.LinkedHashMap;

import org.kobic.gwt.smart.closha.client.board.event.BoardContentDataRefreshEvent;
import org.kobic.gwt.smart.closha.client.controller.BoardController;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.board.BoardModel;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

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

public class ContentEditWindow extends Window{
	
	private SelectItem issueItem;
	private DynamicForm form;
	private IButton editButton;
	private IButton resetButton;
	
	private String summary = "";
	private UserDto userDto;
	private BoardModel boardModel;
	
	private HandlerManager eventBus;
	
	private void setEvent(){
		
		editButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(form.validate()){
					String type = form.getValueAsString("type");
					String summary = form.getValueAsString("summary");
					String content = form.getValueAsString("content");
					String key = form.getValueAsString("key");
					
					BoardModel bean = new BoardModel();
					bean.setId(boardModel.getId());
					bean.setWriter(userDto.getUserId());
					bean.setType(type);
					bean.setSummary(summary);
					bean.setContent(content);
					bean.setEmail(userDto.getEmailAdress());
					bean.setKeyWord(key);
					
					BoardController.Util.getInstance().editCotent(bean, new AsyncCallback<Void>() {
						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							eventBus.fireEvent(new BoardContentDataRefreshEvent(boardModel.getId()));
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
		
		resetButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				form.reset();
			}
		});
	}

	public ContentEditWindow(UserDto userDto, BoardModel boardModel, HandlerManager eventBus){
		
		this.userDto = userDto;
		this.boardModel = boardModel;
		this.eventBus = eventBus;
		
		summary = boardModel.getSummary();
		
		if(summary.length() > 100){
			setTitle(CommonUtilsGwt.getSummarySubString(summary)+Constants.POINTS);
		}else{
			setTitle(summary);
		}
		setWidth(600);
		setHeight(400);
		setIsModal(true);
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setCanDragReposition(true);
		setCanDragResize(true);
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
		issueItem.setDefaultValues(boardModel.getType());
		issueItem.setTitle("Type");
		issueItem.setName("type");

		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("debug", "<b>Debug</b>");
		valueMap.put("comment", "<b>Notice</b>");
		valueMap.put("issue", "<b>Issue</b>");
		
		issueItem.setValueMap(valueMap);
		
		LinkedHashMap<String, String> valueIcons = new LinkedHashMap<String, String>();
		valueIcons.put("debug", "closha/icon/bug.png");
		valueIcons.put("comment", "closha/icon/award_star_gold_1.png");
		valueIcons.put("issue", "closha/icon/award_star_silver_1.pn");
		
		issueItem.setValueIcons(valueIcons);
		
		TextAreaItem summaryItem = new TextAreaItem();
		summaryItem.setName("summary");
		summaryItem.setTitle("Title");
		summaryItem.setValue(boardModel.getSummary());
		summaryItem.setRequired(true);
		summaryItem.setWidth("*");
		summaryItem.setHeight(50);
		summaryItem.setLeft(300);
		
		TextAreaItem contentItem = new TextAreaItem();
		contentItem.setName("content");
		contentItem.setTitle("Content");
		contentItem.setHeight(200);  
		contentItem.setValue(boardModel.getContent());
		contentItem.setRequired(true);
		contentItem.setWidth("*");
		contentItem.setLength(5000);  
		
		TextItem keyItem = new TextItem();
		keyItem.setName("key");
		keyItem.setRequired(true);
		keyItem.setTitle("Keyword");
		keyItem.setWidth(300);
		keyItem.setValue(boardModel.getKeyWord());
		keyItem.setHint("(Keyword<b>,</b>&nbsp;Keyword)");
		
		form.setItems(issueItem, summaryItem, contentItem, keyItem);
		
		editButton = new IButton("Modify");
		editButton.setIcon("closha/icon/pencil_go.png");
		
		resetButton = new IButton("Reset");
		resetButton.setIcon("closha/icon/new_refresh.png");
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setHeight100();
		buttonLayout.setWidth100();
		buttonLayout.setMembersMargin(10);
		buttonLayout.setAlign(Alignment.CENTER); 
		buttonLayout.addMember(editButton);
		buttonLayout.addMember(resetButton);
		
		layout.addMember(form);
		layout.addMember(buttonLayout);
		
		addItem(layout);
		
		setEvent();
	}
}