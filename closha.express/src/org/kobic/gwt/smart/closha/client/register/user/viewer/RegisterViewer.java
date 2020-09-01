package org.kobic.gwt.smart.closha.client.register.user.viewer;

import org.kobic.gwt.smart.closha.client.common.component.HLayoutWidget;
import org.kobic.gwt.smart.closha.client.common.component.EmailTextItem;
import org.kobic.gwt.smart.closha.client.register.user.presenter.RegisterPresenter;
import org.kobic.gwt.smart.closha.shared.Constants;

import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;

public class RegisterViewer extends HLayoutWidget implements RegisterPresenter.Display{
	
	private Window registerWindow;
	private DynamicForm registerForm;
	private ButtonItem idButton;
	private IButton registerButton;
	private TextItem nameTextItem;
	private TextItem idTextItem;
	private TextItem emailTextItem;
	private TextItem companyTextItem;
	private TextItem positionTextItem;
	private TextItem phoneNumberTextItem;
	private TextItem mobileTextItem;
	private TextItem faxTextItem;
	private PasswordItem passwordItem;
	private PasswordItem confirmPasswordItem;
	
	public RegisterViewer(){
		
		setID(Constants.REGISTER_WINDOW_ID);
		setBackgroundImage("closha/img/closha3.png");
		setBackgroundPosition("center");
		setBackgroundRepeat(BackgroundRepeat.NO_REPEAT);
		setLayoutAlign(VerticalAlignment.CENTER);
		
		HeaderItem registerTitle = new HeaderItem();  
		registerTitle.setDefaultValue("<br>Please enter your information.<br><br><br>");
		registerTitle.setHeight(40);
		
		idTextItem = new TextItem("id", "<nobr>ID <font color=\"#dc143c\">*</font></nobr>");
		idTextItem.setWrapTitle(false);  
		idTextItem.setWidth(300);
		idTextItem.setRequired(true);
		idTextItem.setRequiredMessage("ID is required and cannot be left blank.");
		
		idButton = new ButtonItem("getid", "Duplication Check");
		idButton.setStartRow(false);
		idButton.setHint("<nobr>It must be 4-12 characters long and </br> start with a English letter.</nobr>");

		nameTextItem = new TextItem("name", "<nobr>Name <font color=\"#dc143c\">*</font></nobr>");
		nameTextItem.setWrapTitle(false);  
		nameTextItem.setWidth(300);
		nameTextItem.setRequired(true);
		nameTextItem.setColSpan(4);
		nameTextItem.setRequiredMessage("Name is required and cannot be left blank.");
		
		companyTextItem = new TextItem("company", "<nobr>Company <font color=\"#dc143c\">*</font></nobr>");
		companyTextItem.setWrapTitle(false);  
		companyTextItem.setWidth(300);
		companyTextItem.setRequired(true);
		companyTextItem.setColSpan(4);
		companyTextItem.setRequiredMessage("Company is required and cannot be left blank.");
		
		positionTextItem = new TextItem("position", "<nobr>Position <font color=\"#dc143c\">*</font></nobr>");
		positionTextItem.setWrapTitle(false);  
		positionTextItem.setWidth(300);
		positionTextItem.setRequired(true);
		positionTextItem.setColSpan(4);
		positionTextItem.setRequiredMessage("Position is required and cannot be left blank.");
		
		phoneNumberTextItem = new TextItem("phone", "<nobr>Phone </nobr>");
		phoneNumberTextItem.setWrapTitle(false);  
		phoneNumberTextItem.setWidth(300);
		phoneNumberTextItem.setColSpan(4);
		
		mobileTextItem = new TextItem("mobile", "<nobr>Mobile </nobr>");
		mobileTextItem.setWrapTitle(false);  
		mobileTextItem.setWidth(300);
		mobileTextItem.setColSpan(4);
		
		faxTextItem = new TextItem("fax", "<nobr>Fax </nobr>");
		faxTextItem.setWrapTitle(false);  
		faxTextItem.setWidth(300);
		faxTextItem.setColSpan(4);
		
		emailTextItem = new EmailTextItem("<nobr>Email <font color=\"#dc143c\">*</font></nobr>");
		emailTextItem.setWidth(300);
		
        passwordItem = new PasswordItem("password","<nobr>Password <font color=\"#dc143c\">*</font></nobr>");
        passwordItem.setColSpan(4);
        passwordItem.setWidth(300);
        passwordItem.setRequired(true);
        passwordItem.setRequiredMessage("Password is required and cannot be left blank.");
        passwordItem.setHint("<nobr>It must be 8-20 characters long and contain at least one upper case letter, </br> lower case letter, number, and special character such as ~!@#$%^&*.</nobr>");
        
        MatchesFieldValidator matchesValidator = new MatchesFieldValidator();  
        matchesValidator.setOtherField("password");  
        matchesValidator.setErrorMessage("Password does not match."); 
        
        confirmPasswordItem = new PasswordItem("confirmPassword","<nobr>Confirm Password <font color=\"#dc143c\">*</font></nobr>");
        confirmPasswordItem.setColSpan(4);
        confirmPasswordItem.setWidth(300);
        confirmPasswordItem.setRequired(true);
        confirmPasswordItem.setRequiredMessage("Confirm Password is required and cannot be left blank.");
        confirmPasswordItem.setValidators(matchesValidator);  
        
        registerForm = new DynamicForm();
        registerForm.setPadding(10);
		registerForm.setWidth100();
		registerForm.setHeight(130);
		registerForm.setNumCols(4);  
		registerForm.setFields(registerTitle, idTextItem, idButton,
				nameTextItem, emailTextItem, passwordItem, confirmPasswordItem,
				companyTextItem, positionTextItem, phoneNumberTextItem,
				mobileTextItem, faxTextItem);

		registerButton = new IButton("Register");
		registerButton.setIcon("closha/icon/accept.png");
		
		HLayout buttonLayout = new HLayout(10);
        buttonLayout.setWidth100();
		buttonLayout.setAlign(Alignment.CENTER);
		buttonLayout.setLayoutMargin(10);
		buttonLayout.setMembers(registerButton);

		registerWindow = new Window();
		registerWindow.setWidth(830);
		registerWindow.setHeight(500);
		registerWindow.setTitle("KOBIC Single Sign On(KSSO)");
		registerWindow.setHeaderIcon("closha/icon/user_add.png");
		registerWindow.setShowMinimizeButton(false);
		registerWindow.setShowCloseButton(true);
		registerWindow.setCanDragReposition(false);
		registerWindow.setCanDragResize(false);
		registerWindow.setShowShadow(true);
		registerWindow.centerInPage();
		registerWindow.setAutoCenter(true);
		registerWindow.addItem(registerForm);
		registerWindow.addItem(buttonLayout);
		
        addChild(registerWindow);
	}
	
	@Override
	public HLayout asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public IButton getRegisterButton() {
		// TODO Auto-generated method stub
		return registerButton;
	}

	@Override
	public DynamicForm getRegisterForm() {
		// TODO Auto-generated method stub
		return registerForm;
	}

	@Override
	public ButtonItem getCheckButton() {
		// TODO Auto-generated method stub
		return idButton;
	}

	@Override
	public Window getRegisterWindow() {
		// TODO Auto-generated method stub
		return registerWindow;
	}
}