package org.kobic.gwt.smart.closha.client.board.presenter;

import java.util.List;

import org.kobic.gwt.smart.closha.client.board.component.ContentViewWindow;
import org.kobic.gwt.smart.closha.client.board.component.ContentWriteWindow;
import org.kobic.gwt.smart.closha.client.board.event.BoardDataRefreshEvent;
import org.kobic.gwt.smart.closha.client.board.event.BoardDataRefreshEventHandler;
import org.kobic.gwt.smart.closha.client.board.record.BoardRecord;
import org.kobic.gwt.smart.closha.client.controller.BoardController;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.board.BoardModel;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class BoardPresenter implements Presenter{

	private HandlerManager eventBus;
	private Display display; 
	private UserDto userDto;
	
	private Window writeWindow;
	private Window viewWindow;
	
	private int startNum = 0;
	private int pageUnit = 10;
	private int totalNum = 0;
	private int nextNum = 0;
	private int prevNum = 0;
	
	public interface Display{
		VLayout asWidget();
		IButton getWriteButton();
		IButton getMyButton();
		IButton getListButton();
		IButton getFullButton();
		IButton getSearchButton();
		IButton getNextButton();
		IButton getprevButton();
		IButton getSkipGoButton();
		IButton getSkipBackButton();
		Label getTotalCountLabel();
		Label getCountLabel();
		ListGrid getListGrid();
		SelectItem getNumItem();
		SelectItem getIssueItem();
		SelectItem getSearchItem();
		TextItem getSearchTextItem();
	}
	
	public BoardPresenter(HandlerManager eventBus, UserDto userDto, Display view){
		this.eventBus = eventBus;
		this.display = view;
		this.userDto = userDto;
	}
	
	@Override 
	public void bind(){
		onFocusWriteButtonEvent();
		setBoardListGridCellEvent();
		reloadBoardDataFireEvent();
		onChangeEventNumItem();
		onMyBoardButtonEvent();
		onBoardListButtonEvent();
		onBoardFullButtonEvent();
		onBoardSearchButtonEvent();
		onChangeEventIssueItem();
		onPreviousButtonEvent();
		onNextButtonEvent();
		onSkipBackButtonEvent();
		onSkipGoButtonEvent();
	}
	
	private void boardValuesInitialization(){
		startNum = 0;
		pageUnit = 10;
		nextNum = 0;
		prevNum = 0;
		display.getNumItem().setValue(String.valueOf(pageUnit));
		display.getTotalCountLabel().setContents(String.valueOf(pageUnit));
		display.getCountLabel().setContents("0");
		display.getSearchTextItem().setValue("");
	}
	
	private void boardListInitialization(){
		getDataFromBoard(0, pageUnit);
	}
	
	@Override
	public void init(){
		boardTotalConentCount();
		getDataFromBoard(startNum, pageUnit);
		startNum = pageUnit;
		bind();
	}
	
	private void onPreviousButtonEvent(){
		display.getprevButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				prevNum = startNum - pageUnit;
				
				if(prevNum > 0){
					getDataFromBoard(prevNum, startNum);
					startNum = startNum - pageUnit;
				}else{
					boardListInitialization();
					startNum = pageUnit;
				}
			}
		});
	}
	
	private void onNextButtonEvent(){
		display.getNextButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				nextNum = startNum + pageUnit;
				
				if(nextNum < totalNum){
					getDataFromBoard(startNum, nextNum);
					startNum = startNum + pageUnit;
				}else if(nextNum >= totalNum && (nextNum - totalNum) < pageUnit){
					getDataFromBoard(startNum, totalNum);
				}
			}
		});
	}
	
	private void onSkipGoButtonEvent(){
		display.getSkipGoButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				startNum = totalNum - (totalNum % pageUnit);
				getDataFromBoard(totalNum - (totalNum % pageUnit), totalNum);
			}
		});
	}
	
	private void onSkipBackButtonEvent(){
		display.getSkipBackButton().addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				boardListInitialization();
				startNum = pageUnit;
			}
		});
	}
	
	private void onBoardSearchButtonEvent(){
		display.getSearchButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				String searchType = display.getSearchItem().getValueAsString();
				String searchTerm = display.getSearchTextItem().getValueAsString();
				
				BoardController.Util.getInstance().searchCotentList(searchType, searchTerm, new AsyncCallback<List<BoardModel>>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println(caught.getCause() + ":" + caught.getMessage());
					}
					@Override
					public void onSuccess(List<BoardModel> list) {
						// TODO Auto-generated method stub
						boardValuesInitialization();
						setListGridDataBiding(list);
					}
				});
			}
		});
	}
	
	private void onBoardFullButtonEvent(){
		display.getFullButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				boardValuesInitialization();
				boardListInitialization();
			}
		});
	}
	
	private void onBoardListButtonEvent(){
		display.getListButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				boardValuesInitialization();
				boardListInitialization();
			}
		});
	}
	
	private void onMyBoardButtonEvent(){
		display.getMyButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				BoardController.Util.getInstance().getMyBoardList(userDto.getUserId(), 
						new AsyncCallback<List<BoardModel>>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println(caught.getCause() + ":" + caught.getMessage());
					}
					@Override
					public void onSuccess(List<BoardModel> list) {
						// TODO Auto-generated method stub
						boardValuesInitialization();
						setListGridDataBiding(list);
					}
				});
			}
		});
	}
	
	private void onChangeEventIssueItem(){
		display.getIssueItem().addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				String searchType = display.getIssueItem().getValueAsString();
				
				BoardController.Util.getInstance().getTypeBoardList(searchType, new AsyncCallback<List<BoardModel>>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println(caught.getCause() + ":" + caught.getMessage());
					}
					@Override
					public void onSuccess(List<BoardModel> list) {
						// TODO Auto-generated method stub
						boardValuesInitialization();
						setListGridDataBiding(list);
					}
				});
			}
		});
	}
	
	private void onChangeEventNumItem(){
		display.getNumItem().addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				pageUnit = Integer.parseInt(event.getValue().toString());
				boardListInitialization();
				startNum = pageUnit;
			}
		});
	}
	
	private void reloadBoardDataFireEvent(){
		eventBus.addHandler(BoardDataRefreshEvent.TYPE, new BoardDataRefreshEventHandler() {
			@Override
			public void reloadBoardData(BoardDataRefreshEvent event) {
				// TODO Auto-generated method stub
				boardValuesInitialization();
				boardListInitialization();
				boardTotalConentCount();
			}
		});
	}
	
	private void setBoardListGridCellEvent(){
		display.getListGrid().addCellDoubleClickHandler(new CellDoubleClickHandler() {
			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event) {
				// TODO Auto-generated method stub
				final BoardRecord record = (BoardRecord) event.getRecord();
				
				BoardController.Util.getInstance().updateHitCount(record.getAttribute("id"), new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println(caught.getCause() + ":" + caught.getMessage());
					}
					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						viewWindow = new ContentViewWindow(eventBus, record, userDto);
						viewWindow.show();
					}
				});
			}
		});
	}
	
	private void getDataFromBoard(int startNum, int endNum){
		
		setPasingNumber(startNum, endNum);
		
		BoardController.Util.getInstance().getBoardList(startNum, pageUnit, new AsyncCallback<List<BoardModel>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(List<BoardModel> list) {
				// TODO Auto-generated method stub
				setListGridDataBiding(list);
			}
		});
	}
	
	private void setListGridDataBiding(List<BoardModel> list){
		BoardRecord[] records = new BoardRecord[list.size()];
		
		for(int i = 0; i < list.size(); i++){
			BoardModel bm = list.get(i);
			
			records[i] = new BoardRecord(bm.getId(), bm.getWriter(), CommonUtilsGwt.typeToImgConverter(bm.getType()), 
					bm.getSummary(), bm.getContent(), bm.getHitCount(), bm.getRecommandCount(), 
					bm.getEmail(), bm.getKeyWord(), bm.getDate());
		}
		display.getListGrid().setData(records);
	}
	
	private void onFocusWriteButtonEvent(){
		display.getWriteButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				writeWindow = new ContentWriteWindow("Board Write", userDto, eventBus);
				writeWindow.show();
			}
		});
	}
	
	private void boardTotalConentCount(){
		BoardController.Util.getInstance().getTotalBoardContentCount(new AsyncCallback<Integer>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(Integer result) {
				// TODO Auto-generated method stub
				totalNum = result;
			}
		});
	}
	
	private void setPasingNumber(int startCount, int totalCount){
		display.getCountLabel().setContents(String.valueOf(startCount));
		display.getTotalCountLabel().setContents(String.valueOf(totalCount));
	}
	
	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.asWidget());
		init();
	}
}