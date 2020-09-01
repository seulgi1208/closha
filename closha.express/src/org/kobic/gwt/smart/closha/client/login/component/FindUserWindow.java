package org.kobic.gwt.smart.closha.client.login.component;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.common.component.EmailTextItem;
import org.kobic.gwt.smart.closha.client.component.Component;
import org.kobic.gwt.smart.closha.client.user.controller.UserController;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class FindUserWindow extends Window implements Component{
	
	private Map<String, String> config;
	
	private DynamicForm passwordForm;
	private DynamicForm findIdForm;
	private IButton passwordButton;
	private IButton findIdButton;
	private TextItem passwordEmailTextItem;
	private TextItem findIdEmailTextItem;
	
	public FindUserWindow(final Map<String, String> config){
		this.config = config;
		
        HeaderItem findPasswordHeader = new HeaderItem();  
		findPasswordHeader.setDefaultValue("Please enter your name and email to register a new password.<br>");
		findPasswordHeader.setHeight(30);
		
		TextItem idTextItem = new TextItem("id", "<nobr>ID</nobr>");
		idTextItem.setWidth("*");
		idTextItem.setRequired(true);
		idTextItem.setRequiredMessage("Enter your email.");
		
		passwordEmailTextItem = new EmailTextItem("<nobr>Email</nobr>");
		passwordEmailTextItem.setWidth("*");
		
		passwordForm = new DynamicForm();
        passwordForm.setPadding(10);
        passwordForm.setNumCols(2);  
        passwordForm.setColWidths(60, 300); 
        passwordForm.setValidateOnExit(true);
		passwordForm.setFields(findPasswordHeader, idTextItem, passwordEmailTextItem);
		
		passwordButton = new IButton("Confirm");
		passwordButton.setIcon("silk/accept.png");
		
		
		HLayout passwordButtonLayout = new HLayout(10);
		passwordButtonLayout.setHeight(20);
		passwordButtonLayout.setWidth100();
		passwordButtonLayout.setAlign(Alignment.CENTER);
		passwordButtonLayout.setMembers(passwordButton);
		
		VLayout passwordLayout = new VLayout();
		passwordLayout.addMembers(passwordForm, passwordButtonLayout);
		
		Tab passwordTab = new Tab("Change Password", "silk/user_edit.gif");
		passwordTab.setPane(passwordLayout);
		
		HeaderItem findIdHeader = new HeaderItem();  
		findIdHeader.setDefaultValue("Please enter your name and email to find your ID.<br>");
		findIdHeader.setHeight(30);
		
		TextItem nameTextItem = new TextItem("name", "<nobr>Name</nobr>");
		nameTextItem.setWidth("*");
		nameTextItem.setRequired(true);
		nameTextItem.setRequiredMessage("Enter your name.");
		
		findIdEmailTextItem = new EmailTextItem("<nobr>Email</nobr>");
		findIdEmailTextItem.setWidth("*");
		
		findIdForm = new DynamicForm();
		findIdForm.setPadding(10);
		findIdForm.setNumCols(2);  
		findIdForm.setColWidths(60, 300); 
		findIdForm.setValidateOnExit(true);
		findIdForm.setFields(findIdHeader, nameTextItem, findIdEmailTextItem);
		
		findIdButton = new IButton("Find ID");
		findIdButton.setIcon("closha/icon/accept.png");
		
		
		HLayout findIdButtonLayout = new HLayout(10);
		findIdButtonLayout.setHeight(20);
		findIdButtonLayout.setWidth100();
		findIdButtonLayout.setAlign(Alignment.CENTER);
		findIdButtonLayout.setMembers(findIdButton);
		
		VLayout findIdLayout = new VLayout();
		findIdLayout.addMembers(findIdForm, findIdButtonLayout);

		Tab findIdTab = new Tab("Find ID", "silk/zoom.png");
		findIdTab.setPane(findIdLayout);
		
		final TabSet tabset = new TabSet();  
        tabset.setTabBarPosition(Side.TOP);  
        tabset.setWidth100();
        tabset.setHeight100();
        tabset.addTab(passwordTab);  
        tabset.addTab(findIdTab);  
        
        VLayout layout = new VLayout();
        layout.setWidth100();
        layout.setHeight100();
        layout.setPadding(10);
        layout.addMember(tabset);
  
        setIsModal(true);  
		setShowModalMask(true);  
		setTitle("Find ID/Password");
		setHeaderIcon("silk/textfield_key.png");
		setKeepInParentRect(true);
		centerInPage();
		setWidth(500);
		setHeight(350);
		setAutoCenter(true);
        setCanDragReposition(false);
        setCanDragResize(false);
        setCanDrag(false);
        setShowMinimizeButton(false);  
        setShowCloseButton(true);
		addItem(layout);
		event();
	}

	@Override
	public void event() {
		// TODO Auto-generated method stub
		passwordButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(passwordEmailTextItem.validate()){
					UserController.Util.getInstance().getTempPassword(
							config, 
							passwordForm.getValueAsString("id"), 
							passwordForm.getValueAsString("email"),
							new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							passwordForm.resetValues();
							System.out.println(caught.getCause() + ":" + caught.getMessage());
						}
						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							SC.say("A temporary password has been sent to your email.");
						}
					});
				}
			}
		});
		
		findIdButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(findIdEmailTextItem.validate()){
					
					UserController.Util.getInstance().findUserId(
							findIdForm.getValueAsString("name"), 
							findIdForm.getValueAsString("email"), 
							new AsyncCallback<String>() {
						
						@Override
						public void onSuccess(String userId) {
							// TODO Auto-generated method stub
							if(userId == null){
								SC.say("Your ID cannot be found.");
							}else{
								System.out.println(userId);
							}
							findIdForm.resetValues();
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
	}
}