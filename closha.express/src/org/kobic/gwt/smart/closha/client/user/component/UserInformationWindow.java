package org.kobic.gwt.smart.closha.client.user.component;

import org.kobic.gwt.smart.closha.client.user.controller.UserController;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.layout.HLayout;

public class UserInformationWindow extends Window{

	private DynamicForm editForm;
	private IButton editButton;
	private TextItem emailadressItem;
	private PasswordItem tempPasswordItem;
	private PasswordItem newPasswordItem;
	private PasswordItem confirmPasswordItem;
	
	public UserInformationWindow(final UserDto userDto){
		setIsModal(true);  
        setShowModalMask(true); 
		setWidth(580);
		setHeight(230);
		setTitle("Change user information");
		setHeaderIcon("silk/folder_user.png");
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setCanDragReposition(false);
		setCanDragResize(false);
		setShowShadow(false);
		centerInPage();
		setAutoCenter(true);

        editForm = new DynamicForm();
        editForm.setPadding(10);
		editForm.setWidth100();
		editForm.setHeight(130);
		editForm.setNumCols(4);  
		editForm.setValidateOnExit(true);
		
		HeaderItem registerTitle = new HeaderItem();  
		registerTitle.setDefaultValue("<br>Enter your personal information.<br><br><br>");
		registerTitle.setHeight(40);
		
		RegExpValidator regExpValidator = new RegExpValidator();   
        regExpValidator.setExpression("^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$");
		
		emailadressItem = new TextItem("email", "<nobr>Email <font color=\"#dc143c\">*</font></nobr>");
		emailadressItem.setWrapTitle(false);  
		emailadressItem.setWidth(200);
		emailadressItem.setRequired(true);
		emailadressItem.setDefaultValue(userDto.getUserId());
		emailadressItem.disable();
		emailadressItem.setRequiredMessage("The ID is required and cannot be left blank.");
        emailadressItem.setValidators(regExpValidator);
		
        tempPasswordItem = new PasswordItem("tPassword","<nobr>Password <font color=\"#dc143c\">*</font></nobr>");
        tempPasswordItem.setColSpan(4);
        tempPasswordItem.setWidth(200);
        tempPasswordItem.setRequired(true);
        tempPasswordItem.setRequiredMessage("The password is required and cannot be left blank.");
        
        newPasswordItem = new PasswordItem("nPassword","<nobr>New Password <font color=\"#dc143c\">*</font></nobr>");
        newPasswordItem.setColSpan(4);
        newPasswordItem.setWidth(200);
        newPasswordItem.setRequired(true);
        newPasswordItem.setRequiredMessage("The password is required and cannot be left blank.");
        newPasswordItem.setHint("<nobr>Enter a new password.</nobr>");
        
        confirmPasswordItem = new PasswordItem("reEnterPassword","<nobr>Confirm Password <font color=\"#dc143c\">*</font></nobr>");
        confirmPasswordItem.setColSpan(4);
        confirmPasswordItem.setWidth(200);
        confirmPasswordItem.setRequired(true);
        confirmPasswordItem.setRequiredMessage("The password confirmation is required and cannot be left blank.");
        confirmPasswordItem.setHint("<nobr>Please enter the password again.</nobr>");
        
        MatchesFieldValidator matchesValidator = new MatchesFieldValidator();  
        matchesValidator.setOtherField("nPassword");  
        matchesValidator.setErrorMessage("Password does not match.");          
        confirmPasswordItem.setValidators(matchesValidator);  
        
        editForm.setFields(registerTitle, emailadressItem, tempPasswordItem, newPasswordItem, confirmPasswordItem);
      
        HLayout buttonLayout = new HLayout(10);
        buttonLayout.setWidth100();
		buttonLayout.setAlign(Alignment.CENTER);
		buttonLayout.setLayoutMargin(10);
		
		editButton = new IButton("Edit");
		editButton.setIcon("silk/vcard_edit.png");
		editButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(editForm.validate()){
					if(tempPasswordItem.getValueAsString().equals(userDto.getPassword())){
						
						String newPassword = newPasswordItem.getValueAsString();
						
						if(newPassword.length() >= 7 && newPassword.length() <= 13){
							
							UserController.Util.getInstance().updatePassword(userDto.getUserId(), userDto.getPassword(), newPassword, new AsyncCallback<Integer>() {
								@Override
								public void onSuccess(Integer result) {
									// TODO Auto-generated method stub
									destroy();
								}
								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									System.out.println(caught.getCause() + ":" + caught.getMessage());
								}
							});
						}else{
							SC.say("It must be 8-20 characters long and contain at least one upper case letter, lower case letter, number, and special character such as ~!@#$%^&*.");
						}
					}else{
						SC.say("The password you have entered does not match. Please check again.");
						tempPasswordItem.setValue("");
						tempPasswordItem.focusInItem();
					}
				}
			}
		});
		
		buttonLayout.setMembers(editButton);
		
		addItem(editForm);
		addItem(buttonLayout);
	}
}
