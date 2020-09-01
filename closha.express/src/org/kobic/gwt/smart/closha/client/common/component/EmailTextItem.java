package org.kobic.gwt.smart.closha.client.common.component;

import org.kobic.gwt.smart.closha.client.component.Component;

import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

public class EmailTextItem extends TextItem implements Component{
	
	public EmailTextItem(String title){
		
		RegExpValidator emailValidator = new RegExpValidator();
		emailValidator.setErrorMessage("Invalid email address.");
		emailValidator.setExpression("^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$");
		
		setName("email");
		setTitle(title);
		setRequired(true);
		setRequiredMessage("Please, enter your email address.");
		setValidators(emailValidator);
	}

	@Override
	public void event() {
		// TODO Auto-generated method stub
		
	}
}
