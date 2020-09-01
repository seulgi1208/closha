package org.kobic.gwt.smart.closha.client.board.component;

import org.kobic.gwt.smart.closha.client.board.event.CommentDataRefreshEvent;
import org.kobic.gwt.smart.closha.client.controller.BoardController;
import org.kobic.gwt.smart.closha.shared.model.board.ReplyModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReplyEditWindow extends Window {

	private DynamicForm form;
	private IButton editButton;
	private IButton resetButton;

	private String id = "";
	private String linkedNum = "";
	
	private HandlerManager eventBus;

	private void setEvent() {

		editButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (form.validate()) {
					ReplyModel boardCommentModel = new ReplyModel();
					boardCommentModel.setComment(form.getValueAsString("comment"));
					boardCommentModel.setId(id);
					BoardController.Util.getInstance().editComment(boardCommentModel, new AsyncCallback<Void>() {
						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							eventBus.fireEvent(new CommentDataRefreshEvent(linkedNum));
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

	public ReplyEditWindow(ReplyModel boardCommentModel, HandlerManager eventBus) {


		this.eventBus = eventBus;
		this.id = boardCommentModel.getId();
		this.linkedNum = boardCommentModel.getLinkedNum();

		setTitle("Modify Commnet");
		setWidth(600);
		setHeight(200);
		setIsModal(true);
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setCanDragReposition(true);
		setCanDragResize(true);
		centerInPage();
		setAutoCenter(true);
		setHeaderIcon("closha/icon/comment_edit.png");

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setMembersMargin(10);
		layout.setMargin(10);

		form = new DynamicForm();
		form.validate(true);

		TextAreaItem commentItem = new TextAreaItem();
		commentItem.setName("comment");
		commentItem.setTitle("Content");
		commentItem.setHeight(100);
		commentItem.setValue(boardCommentModel.getComment());
		commentItem.setRequired(true);
		commentItem.setWidth("*");
		commentItem.setLength(300);

		form.setItems(commentItem);

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
