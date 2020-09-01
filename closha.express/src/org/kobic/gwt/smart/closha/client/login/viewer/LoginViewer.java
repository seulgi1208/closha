package org.kobic.gwt.smart.closha.client.login.viewer;

import org.kobic.gwt.smart.closha.client.common.component.HLayoutWidget;
import org.kobic.gwt.smart.closha.client.login.presenter.LoginPresenter;
import org.kobic.gwt.smart.closha.shared.Constants;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LoginViewer extends HLayoutWidget implements LoginPresenter.Display{
	
	private DynamicForm form;
	private ImgButton loginButton;
	private ImgButton forgetButton;
	private ImgButton registerButton;
	
	public LoginViewer(){ 
		
		setID(Constants.LOGIN_WINDOW_ID);
		setLayoutAlign(VerticalAlignment.CENTER);
		
		TextItem idTextItem = new TextItem("id");
		idTextItem.setWidth(300);
		idTextItem.setHeight(30);
		idTextItem.setRequired(true);
		idTextItem.setTitle("User ID");
		idTextItem.setRequiredMessage("Enter your ID.");
		
		PasswordItem passwordItem = new PasswordItem("password");
		passwordItem.setRequired(true);
		passwordItem.setRequiredMessage("Enter your password.");
		passwordItem.setWidth(300);
		passwordItem.setHeight(30);
		passwordItem.setTitle("Password");
		passwordItem.setStartRow(true);
		
		form = new DynamicForm();
		form.setWidth(300);
		form.setHeight100();
		form.setTitleOrientation(TitleOrientation.TOP);  
		form.setFields(idTextItem ,passwordItem);
		
		HLayout loginWrapperLayout = new HLayout();
		loginWrapperLayout.setWidth100();
		loginWrapperLayout.setHeight(80);
		loginWrapperLayout.addMember(form);
		loginWrapperLayout.setAlign(Alignment.CENTER);
		
		loginButton = new ImgButton();
		loginButton.setWidth(300);
		loginButton.setHeight(40);
		loginButton.setShowRollOver(false);
		loginButton.setShowDown(false);
		loginButton.setSrc("closha/img/login_btn.png");
		loginButton.setOpacity(100);
		
		forgetButton = new ImgButton();
		forgetButton.setWidth(147);
		forgetButton.setHeight(20);
		forgetButton.setShowRollOver(false);
		forgetButton.setShowDown(false);
		forgetButton.setSrc("closha/img/idpw_btn.png");
		
		registerButton = new ImgButton();
		registerButton.setWidth(147);
		registerButton.setHeight(20);
		registerButton.setShowRollOver(false);
		registerButton.setShowDown(false);
		registerButton.setSrc("closha/img/register_btn.png");
	
		HLayout firstButtonLayout = new HLayout(10);
		firstButtonLayout.setHeight(20);
		firstButtonLayout.setAlign(Alignment.CENTER);
		firstButtonLayout.setMembers(loginButton);
		
		HLayout secondButtonLayout = new HLayout(6);
		secondButtonLayout.setHeight(20);
		secondButtonLayout.setAlign(Alignment.CENTER);
		secondButtonLayout.setMembers(registerButton, forgetButton);
		
		VLayout buttonLayout = new VLayout(20);
		buttonLayout.addMembers(firstButtonLayout, secondButtonLayout);

		HTMLPane htmlPane = new HTMLPane();
		htmlPane.setBackgroundColor("#3c3f45");
		htmlPane.setOpacity(50);
		htmlPane.setHeight(70);
		
		String contents = "<p style=\" padding-top:15px;  \" padding-top: align=\"center\">"
				+ "125 Gwahangno, Yuseong-gu, Daejeon, 305-806, Korea. Tel: 82-42-879-8539, Fax: 82-42-879-8519<br>"
				+ "COPYRIGHT(C) Korean Bioinformation Center (KOBIC), All Rights Reserved. <b>E-mail: <a href='mailto:webmaster@kobic.kr'>webmaster@kobic.kr</a></b></p>";
		htmlPane.setContents(contents);
		
		Img titleImg = new Img("closha/img/closha.png", 300, 40);
		
		HLayout titleLayout = new HLayout();
		titleLayout.setHeight(40);
		titleLayout.setWidth100();
		titleLayout.addMember(titleImg);
		titleLayout.setAlign(Alignment.CENTER);
		
		VLayout loginWrapperView = new VLayout(20);
		loginWrapperView.setPadding(20);
		loginWrapperView.setHeight(300);
		loginWrapperView.setWidth(400);
		loginWrapperView.addMembers(titleLayout, loginWrapperLayout, buttonLayout);
		loginWrapperView.setBackgroundImage("closha/img/bg.png");
		loginWrapperView.setLayoutAlign(VerticalAlignment.CENTER);
		
		Img IMG_1 = new Img("closha/img/main_icon_w_01.png", 170, 90);
		Img IMG_2 = new Img("closha/img/main_icon_w_02.png", 170, 90);
		Img IMG_3 = new Img("closha/img/main_icon_w_03.png", 170, 90);
		Img IMG_4 = new Img("closha/img/main_icon_w_04.png", 170, 90);
		Img IMG_5 = new Img("closha/img/main_icon_w_05.png", 170, 90);
		
		HLayout iconLayout = new HLayout();
		iconLayout.setHeight(100);
		iconLayout.setWidth100();
		iconLayout.setAlign(Alignment.CENTER);
		iconLayout.addMembers(IMG_1,IMG_2 ,IMG_3 ,IMG_4,IMG_5);
		
		String desc = "<p style=\" padding-top:15px; font-size: 130%; text-align: center; color:white; \" >"
				+ "Easy-to-use, reliable and optimal analysis environment will be provided with the use of the latest large-scale genomic data analysis applications available in a networkable environment.</br></br>"
				+ "It provides and a hybrid cluster infrastructure system based on big data platform designed to facilitate high-speed analysis with an efficient and reliable storage system for large-capacity genome data.</br></br>"
				+ "It also provides its own transmission software that can securely transmit large amounts of genome-related data at high speeds regardless of data type and capacity.</p>";
		
		HTMLPane descPane = new HTMLPane();
		descPane.setHeight(150);
		descPane.setWidth100();
		descPane.setContents(desc);
		
		VLayout mainWrapperLayout = new VLayout(20);
		mainWrapperLayout.setWidth100();
		mainWrapperLayout.setHeight100();
		mainWrapperLayout.setAlign(VerticalAlignment.CENTER);
		mainWrapperLayout.addMembers(loginWrapperView, iconLayout, descPane);
		
		HLayout topLayout = new HLayout();
		topLayout.setHeight100();
		topLayout.setWidth100();
		
		topLayout.setBackgroundImage("closha/img/main_bg_4.png");
		topLayout.setBackgroundPosition("left");
		topLayout.setBackgroundRepeat(BackgroundRepeat.NO_REPEAT);
		topLayout.setLayoutAlign(VerticalAlignment.CENTER);
//		topLayout.setBackgroundColor("#dee6ec");
		topLayout.setBackgroundColor("#d8e0e5");
		topLayout.addMembers(mainWrapperLayout);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.addMembers(topLayout, htmlPane);

		addMembers(layout);
	}

	@Override
	public HLayout asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public DynamicForm getLoginForm() {
		// TODO Auto-generated method stub
		return form;
	}

	@Override
	public ImgButton getForgetButton() {
		// TODO Auto-generated method stub
		return forgetButton;
	}

	@Override
	public ImgButton getRegisterButton() {
		// TODO Auto-generated method stub
		return registerButton;
	}

	@Override
	public ImgButton getLoginButton() {
		// TODO Auto-generated method stub
		return loginButton;
	}
}
