package org.kobic.gwt.smart.closha.client.board.component;

import org.kobic.gwt.smart.closha.client.board.presenter.BoardPresenter;
import org.kobic.gwt.smart.closha.client.board.viewer.BoardViewer;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class BoardTabPanel extends Tab{
	
	private Presenter boardBrowserPresenter;
	
	public BoardTabPanel(HandlerManager evnetBus, UserDto userDto){
		
		setID(Constants.BOARD_WINDOW_ID);
		setTitle(Constants.BOARD_WINDOW_TITLE);
		setIcon("closha/icon/paste_plain.png");
		setWidth(200);
		
		HLayout boardLayout = new HLayout();
		boardLayout.setBorder("solid 1px #DCDCDC");
		boardLayout.setHeight100();
		boardLayout.setWidth100();
		boardLayout.setAlign(Alignment.CENTER);
		
		boardBrowserPresenter = new BoardPresenter(evnetBus, userDto, new BoardViewer());
		boardBrowserPresenter.go(boardLayout);
		
		setPane(boardLayout);
	}
}
