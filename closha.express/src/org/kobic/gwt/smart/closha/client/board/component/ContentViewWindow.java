package org.kobic.gwt.smart.closha.client.board.component;

import java.util.List;

import org.kobic.gwt.smart.closha.client.board.event.CommentDataRefreshEvent;
import org.kobic.gwt.smart.closha.client.board.event.CommentDataRefreshEventHandler;
import org.kobic.gwt.smart.closha.client.board.event.BoardDataRefreshEvent;
import org.kobic.gwt.smart.closha.client.board.event.BoardContentDataRefreshEvent;
import org.kobic.gwt.smart.closha.client.board.event.BoardContentDataRefreshEventHandler;
import org.kobic.gwt.smart.closha.client.board.record.BoardCommentRecord;
import org.kobic.gwt.smart.closha.client.board.record.BoardRecord;
import org.kobic.gwt.smart.closha.client.controller.BoardController;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.board.ReplyModel;
import org.kobic.gwt.smart.closha.shared.model.board.BoardModel;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class ContentViewWindow extends Window{
	
	private IButton editButton;
	private IButton deleteButton;
	private IButton commentButton;
	private DynamicForm form;
	private TextAreaItem commentItem;
	private ListGrid commentListGrid;
	private Window editWindow;
	private HTMLPane htmlPane;
	
	private UserDto userDto;
	private BoardModel boardModel;
	
	private HandlerManager eventBus;
	
	public ContentViewWindow(HandlerManager eventBus, BoardRecord record, UserDto userDto){

		this.eventBus = eventBus;
		this.userDto = userDto;
		
		boardModel = new BoardModel();
		boardModel.setId(record.getAttribute("id"));
		boardModel.setWriter(record.getAttribute("writer"));
		boardModel.setType(CommonUtilsGwt.imgToTypeConverter(record.getAttribute("type")));
		boardModel.setSummary(record.getAttribute("summary"));
		boardModel.setContent(record.getAttribute("content"));
		boardModel.setHitCount(record.getAttribute("hitCount"));
		boardModel.setRecommandCount(record.getAttribute("recommandCount"));
		boardModel.setEmail(record.getAttribute("email"));
		boardModel.setKeyWord(record.getAttribute("keyWord"));
		boardModel.setDate(record.getAttribute("date"));
		
		setTitle(getWindowTitle(boardModel.getSummary()));
		setWidth(800);
		setHeight(750);
		setIsModal(true);
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setShowMaximizeButton(true);
		setCanDragReposition(true);
		setCanDragResize(true);
		centerInPage();
		setAutoCenter(true);
		setHeaderIcon("closha/icon/page_white_text.png");
		
		addItem(drawBoardView(boardModel));
	}
	
	private void setEvent(final BoardModel boardModel){
		commentButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(form.validate()){
					ReplyModel boardCommentModel = new ReplyModel();
					boardCommentModel.setWriter(userDto.getUserId());
					boardCommentModel.setComment(form.getValueAsString("comment"));
					boardCommentModel.setEmail(userDto.getEmailAdress());
					boardCommentModel.setLinkedNum(boardModel.getId());
					
					BoardController.Util.getInstance().writeCommentBoard(boardCommentModel, boardModel.getId(), new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println(caught.getCause() + ":" + caught.getMessage());
						}
						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							addCommentReLoadData(boardModel.getId());
							commentItem.setValue("");
						}
					});
				}else{
					SC.say("Please enter a comment.");
				}
			}
		});
		
		editButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				BoardController.Util.getInstance().getOneBoardContent(boardModel.getId(), new AsyncCallback<BoardModel>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println(caught.getCause() + ":" + caught.getMessage());
					}
					@Override
					public void onSuccess(BoardModel bean) {
						// TODO Auto-generated method stub
						editWindow = new ContentEditWindow(userDto, bean, eventBus);
						editWindow.show();
					}
				});
			}
		});
		
		deleteButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				BoardController.Util.getInstance().deleteContent(boardModel.getId(), new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println(caught.getCause() + ":" + caught.getMessage());
					}
					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						eventBus.fireEvent(new BoardDataRefreshEvent());
						destroy();
					}
				});
			}
		});
		
		addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new BoardDataRefreshEvent());
				destroy();
			}
		});
		
		eventBus.addHandler(BoardContentDataRefreshEvent.TYPE, new BoardContentDataRefreshEventHandler() {
			@Override
			public void viewWindowDataReload(BoardContentDataRefreshEvent event) {
				// TODO Auto-generated method stub
				BoardController.Util.getInstance().getOneBoardContent(boardModel.getId(), new AsyncCallback<BoardModel>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println(caught.getCause() + ":" + caught.getMessage());
					}
					@Override
					public void onSuccess(BoardModel bean) {
						// TODO Auto-generated method stub
						setTitle(getWindowTitle(bean.getSummary()));
						htmlPane.setContents(getContent(bean));
					}
				});
			}
		});
		
		eventBus.addHandler(CommentDataRefreshEvent.TYPE, new CommentDataRefreshEventHandler() {
			@Override
			public void commentListGridDataReload(CommentDataRefreshEvent event) {
				// TODO Auto-generated method stub
				addCommentReLoadData(event.getLinkedNum());
			}
		});
	}
	
	private String getWindowTitle(String summary){
		if(summary.length() > 100){
			return CommonUtilsGwt.getSummarySubString(summary)+Constants.POINTS;
		}else{
			return summary;
		}
	}
	
	private void addCommentReLoadData(String id){
		BoardController.Util.getInstance().getBoardCommentList(id, new AsyncCallback<List<ReplyModel>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(List<ReplyModel> list) {
				// TODO Auto-generated method stub
				setCommentDataBinding(list);
			}
		});
	}
	
	private void setCommentDataBinding(List<ReplyModel> list){
		BoardCommentRecord[] records = new BoardCommentRecord[list.size()];
		
		for(int i = 0; i < list.size(); i++){
			ReplyModel bm = list.get(i);
			
			records[i] = new BoardCommentRecord(bm.getId(), bm.getWriter(), bm.getComment(), 
					bm.getEmail(), bm.getDate(), bm.getLinkedNum());
		}
		commentListGrid.setData(records);
	}
	
	private String getContent(BoardModel boardModel){
		String content = "<p align=\"justify\" style=\"font-size:12px; line-height: 20px\"><em>" + 
				boardModel.getDate() + Constants.TAB + "Inquiry: " + boardModel.getHitCount() + Constants.TAB + "Reply: " + 
				boardModel.getRecommandCount() + "</em></br></br>" + Constants.SPACE + boardModel.getContent() + "</p>";
		return content;
	}
	
	public VLayout drawBoardView(BoardModel boardModel){
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setMembersMargin(5);
		layout.setMargin(5);
		
		htmlPane = new HTMLPane();		
		htmlPane.setBorder("1px solid #CCCCCC");
		htmlPane.setPadding(5);
		htmlPane.setContents(getContent(boardModel));
		htmlPane.setHeight(350);
		htmlPane.setWidth100();
		
		editButton = new IButton("Modify");
		editButton.setIcon("closha/icon/pencil_go.png");
		
		deleteButton = new IButton("Delete");
		deleteButton.setIcon("closha/icon/delete.png");
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembersMargin(10);
		buttonLayout.setAlign(Alignment.RIGHT);
		buttonLayout.addMember(editButton);
		buttonLayout.addMember(deleteButton);

		SectionStack sectionStack = new SectionStack();  
        sectionStack.setWidth100();
        sectionStack.setHeight100();  
		
		String title = Canvas.imgHTML("closha/icon/page_white_stack.png") + "&nbsp;Reply List";  
        
		SectionStackSection section = new SectionStackSection(title);
        section.setCanCollapse(false);  
        section.setExpanded(true);
		
		commentListGrid = new BoardListGrid(userDto, eventBus);
		
		ListGridField commentField = new ListGridField("comment", "Comment");
		commentField.setAlign(Alignment.CENTER);  
		
		ListGridField writer = new ListGridField("writer", "Writer", 100);
		writer.setAlign(Alignment.CENTER);
		
		ListGridField dateField = new ListGridField("date", "Date", 250);
		dateField.setAlign(Alignment.CENTER);
		
		commentListGrid.setFields(commentField, writer, dateField);
		commentListGrid.setEmptyMessage("There are no data.");
		
		section.setItems(commentListGrid);
		sectionStack.setSections(section);
		
		addCommentReLoadData(boardModel.getId());
		
		form = new DynamicForm();
		form.validate(true);
		
		commentItem = new TextAreaItem();
		commentItem.setName("comment");
		commentItem.setTitle("Comment");
		commentItem.setRequired(true);
		commentItem.setWidth("*");
		commentItem.setHeight(50);
		commentItem.setLength(200);
		
		form.setItems(commentItem);
		
		commentButton = new IButton("Write Comment");
		commentButton.setIcon("closha/icon/pencil_add.png");
		
		HLayout commentLayout = new HLayout();
		commentLayout.setMembersMargin(10);
		commentLayout.setAlign(Alignment.RIGHT);
		commentLayout.addMember(commentButton);
		
		layout.addMember(htmlPane);
		if(CommonUtilsGwt.userCheck(userDto.getEmailAdress(), boardModel.getEmail())){
			layout.addMember(buttonLayout);
		}
		layout.addMember(form);
		layout.addMember(commentLayout);
		layout.addMember(sectionStack);
		
		setEvent(boardModel);
		
		return layout;
	}
}
