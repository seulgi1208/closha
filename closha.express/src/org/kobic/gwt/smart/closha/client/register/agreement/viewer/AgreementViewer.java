package org.kobic.gwt.smart.closha.client.register.agreement.viewer;

import org.kobic.gwt.smart.closha.client.common.component.HLayoutWidget;
import org.kobic.gwt.smart.closha.client.register.agreement.presenter.AgreementPresenter;
import org.kobic.gwt.smart.closha.shared.Constants;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.HLayout;

public class AgreementViewer extends HLayoutWidget implements AgreementPresenter.Display{

	private Window agreementWindow;
	
	private TextAreaItem serviceTerms;
	private TextAreaItem policy;
	private CheckboxItem acceptCheckBox;
	private ButtonItem confirmButton;
	
	public AgreementViewer(){
		
		setID(Constants.AGREEMENT_WINDOW_ID);
		setBackgroundImage("closha/img/closha3.png");
		setBackgroundPosition("center");
		setBackgroundRepeat(BackgroundRepeat.NO_REPEAT);
		setLayoutAlign(VerticalAlignment.CENTER);
		
        HeaderItem serviceTermsTitle = new HeaderItem();  
        serviceTermsTitle.setDefaultValue("KOBIC Single Sign On(KSSO) terms and conditions");
			
		serviceTerms = new TextAreaItem("terms");		
        serviceTerms.setShowTitle(false);  
        serviceTerms.setColSpan(4);  
        serviceTerms.setHeight(300);  
        serviceTerms.setWidth(1000);
        serviceTerms.setAttribute("readOnly", true);
        
        HeaderItem policyTitle = new HeaderItem();  
        policyTitle.setDefaultValue("KOBIC Single Sign On(KSSO) personal information protection policy");
		
		policy = new TextAreaItem("policy");
		policy.setColSpan(4);  
		policy.setShowTitle(false);  
		policy.setHeight(300);  
		policy.setWidth(1000);
		policy.setAttribute("readOnly", true);
        
		acceptCheckBox = new CheckboxItem("accept", "I agree to the Terms of Service and Privacy Policy.");
		acceptCheckBox.setColSpan(4);  
		acceptCheckBox.setAlign(Alignment.CENTER);
		
		confirmButton = new ButtonItem();  
		confirmButton.setTitle("OK");  
		confirmButton.setDisabled(true);
		confirmButton.setColSpan(4);  
		confirmButton.setWidth(130);
		confirmButton.setAlign(Alignment.CENTER);
		
		DynamicForm policyForm = new DynamicForm();
        policyForm.setWidth100();
        policyForm.setPadding(5);
        policyForm.setNumCols(4);  
        policyForm.setAlign(Alignment.CENTER);
        policyForm.setCellPadding(5);
        policyForm.setColWidths(10,100,100,100);
		policyForm.setFields(serviceTermsTitle, serviceTerms, policyTitle, policy, acceptCheckBox, confirmButton);
			
		agreementWindow = new Window();
		agreementWindow.setWidth(1024);
		agreementWindow.setHeight(800);
		agreementWindow.setTitle("KOBIC Single Sign On(KSSO) terms and conditions");
		agreementWindow.setHeaderIcon("closha/icon/user_add.png");
		agreementWindow.setShowMinimizeButton(false);
		agreementWindow.setShowCloseButton(true);
		agreementWindow.setCanDragReposition(false);
		agreementWindow.setCanDragResize(false);
		agreementWindow.setShowShadow(true);
		agreementWindow.centerInPage();
		agreementWindow.setAutoCenter(true);
		agreementWindow.addItem(policyForm);
		
        addChild(agreementWindow);
	}
	
	@Override
	public HLayout asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Window getAgreementWindow() {
		// TODO Auto-generated method stub
		return agreementWindow;
	}

	@Override
	public TextAreaItem getServiceTerms() {
		// TODO Auto-generated method stub
		return serviceTerms;
	}

	@Override
	public TextAreaItem getPolicy() {
		// TODO Auto-generated method stub
		return policy;
	}

	@Override
	public CheckboxItem getAcceptCheckBox() {
		// TODO Auto-generated method stub
		return acceptCheckBox;
	}

	@Override
	public ButtonItem getConfirmButton() {
		// TODO Auto-generated method stub
		return confirmButton;
	}
}