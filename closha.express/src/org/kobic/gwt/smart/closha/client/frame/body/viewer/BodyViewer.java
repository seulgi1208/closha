package org.kobic.gwt.smart.closha.client.frame.body.viewer;

import org.kobic.gwt.smart.closha.client.frame.body.presenter.BodyPresenter;

import com.smartgwt.client.widgets.layout.VLayout;

public class BodyViewer implements BodyPresenter.Display{
	
	private VLayout panel;
	
	public BodyViewer(){
		panel = new VLayout();
		panel.setHeight100();
	    panel.setWidth100();
		panel.setStyleName("tabSetContainer");
	    panel.setLayoutMargin(0); 
	    panel.setBorder("solid 0px #000000");
	}
	
	
	@Override
	public VLayout asWidget() {
		// TODO Auto-generated method stub
		return panel;
	}
}
