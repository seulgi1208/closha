package org.kobic.gwt.smart.closha.client.frame.header.viewer;

import org.kobic.gwt.smart.closha.client.frame.header.presenter.HeaderPresenter;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.HLayout;

public class HeaderViewer implements HeaderPresenter.Display{

	private HLayout header;
	private Img logo;
	
	public HeaderViewer(){
		
		logo = new Img("closha/logo/login_logo2.png", 210, 50);
		logo.setImageType(ImageStyle.NORMAL);  
		
		header = new HLayout();
		header.setHeight(50);
		header.setWidth100();
		header.setBackgroundColor("#3c3f45");
		header.setAlign(Alignment.LEFT);
		header.addMember(logo);
		
	}

	@Override
	public HLayout getHeader() {
		// TODO Auto-generated method stub
		return header;
	}

	@Override
	public Img getImg() {
		// TODO Auto-generated method stub
		return logo;
	}
}