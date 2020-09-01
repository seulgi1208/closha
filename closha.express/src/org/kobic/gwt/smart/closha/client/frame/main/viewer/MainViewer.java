package org.kobic.gwt.smart.closha.client.frame.main.viewer;

import org.kobic.gwt.smart.closha.client.common.component.HLayoutWidget;
import org.kobic.gwt.smart.closha.client.frame.main.presenter.MainPresenter;
import org.kobic.gwt.smart.closha.shared.Constants;

import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class MainViewer extends HLayoutWidget implements MainPresenter.Display{

	private VLayout centerPanel;
	private VLayout leftPanel;
	private VLayout rightPanel;
	
	public MainViewer(){
		setID(Constants.CLOSHA_WINDOW_ID);
		setHeight100();
	    setWidth100();
		setMembersMargin(5);
		
		leftPanel = new VLayout();
		leftPanel.setWidth("25%");
		leftPanel.setMinWidth(250);
		leftPanel.setMaxWidth(400);
		leftPanel.setHeight100();
		leftPanel.setShowResizeBar(true);
		
		centerPanel = new VLayout();
		centerPanel.setHeight100();
		centerPanel.setWidth100();
		centerPanel.setShowResizeBar(true);
		
		rightPanel = new VLayout();
		rightPanel.setHeight100();
		rightPanel.setWidth("25%");

		addMember(leftPanel);
		addMember(centerPanel);
		addMember(rightPanel);
	}
	
	@Override
	public HLayout asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public VLayout getLeftPanel() {
		// TODO Auto-generated method stub
		return leftPanel;
	}

	@Override
	public VLayout getRightPanel() {
		// TODO Auto-generated method stub
		return rightPanel;
	}

	@Override
	public VLayout getCenterPanel() {
		// TODO Auto-generated method stub
		return centerPanel;
	}

}
