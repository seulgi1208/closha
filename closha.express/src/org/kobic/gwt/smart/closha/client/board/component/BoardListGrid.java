package org.kobic.gwt.smart.closha.client.board.component;

import org.kobic.gwt.smart.closha.client.board.event.CommentDataRefreshEvent;
import org.kobic.gwt.smart.closha.client.controller.BoardController;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.board.ReplyModel;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class BoardListGrid extends ListGrid{
	
	private IButton editButton;
	private IButton deleteButton;
	private Window commentEditWindow;
	
	private UserDto userDto;
	
	private HandlerManager eventBus;
	
	public BoardListGrid(UserDto userDto, HandlerManager eventBus){
		setWidth100();
		setHeight100();
		setCanExpandRecords(true);
		setShowAllRecords(true);  
		setShowRowNumbers(true);  
		setDetailField("comment");  
		setEmptyMessage("There are no data.");
        
		this.eventBus =  eventBus;
		this.userDto = userDto;
	}
	
	private void setEvent(String email, final ReplyModel boardCommentModel){
		
		boolean check = userDto.getEmailAdress().equals(email);
		
		if(check){
			editButton.enable();
			deleteButton.enable();
		}
		
		editButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				commentEditWindow = new ReplyEditWindow(boardCommentModel, eventBus);
				commentEditWindow.show();
			}
		});

		deleteButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				BoardController.Util.getInstance().deleteComment(boardCommentModel.getId(), new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println(caught.getCause() + ":" + caught.getMessage());
					}
					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						eventBus.fireEvent(new CommentDataRefreshEvent(boardCommentModel.getLinkedNum()));
					}
				});
			}
		});
	} 
	
	@Override
	protected Canvas getExpansionComponent(final ListGridRecord record) {
		VLayout layout = new VLayout(5);
		layout.setPadding(5);

		String id = record.getAttribute("id");
		String writer = record.getAttribute("writer");
		String comment = record.getAttribute("comment");
		String date = record.getAttribute("date");
		String email = record.getAttribute("email");
		String linkedNum = record.getAttribute("linkedNum");
		
		final ReplyModel boardCommentModel = new ReplyModel();
		boardCommentModel.setId(id);
		boardCommentModel.setWriter(writer);
		boardCommentModel.setComment(comment);
		boardCommentModel.setDate(date);
		boardCommentModel.setEmail(email);
		boardCommentModel.setLinkedNum(linkedNum);
		
		String htmlContent = "<p align=\"justify\" style=\"font-size:12px; line-height: 20px\"><em>" + 
				date + "</em>" + Constants.BR + comment + "</p>";
		
		HTMLPane htmlPane = new HTMLPane();				
		htmlPane.setContents(htmlContent);
		htmlPane.setHeight(100);
		htmlPane.setWidth100();
		editButton = new IButton("Modify");
		editButton.disable();
		editButton.setIcon("closha/icon/pencil_go.png");
		
		deleteButton = new IButton("Delete");
		deleteButton.disable();
		deleteButton.setIcon("closha/icon/delete.png");
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembersMargin(10);
		buttonLayout.setAlign(Alignment.CENTER);
		buttonLayout.addMember(editButton);
		buttonLayout.addMember(deleteButton);

		layout.addMember(htmlPane);
		
		if(CommonUtilsGwt.userCheck(userDto.getEmailAdress(), record.getAttribute("email"))){
			layout.addMember(buttonLayout);
		}

		setEvent(email, boardCommentModel);
		
		return layout;
	}
}