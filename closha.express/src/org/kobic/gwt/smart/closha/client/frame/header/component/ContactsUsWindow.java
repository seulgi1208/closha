package org.kobic.gwt.smart.closha.client.frame.header.component;

import org.kobic.gwt.smart.closha.shared.Messages;

import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;

public class ContactsUsWindow extends Window {

	public ContactsUsWindow() {

		Label contactsLabel = new Label();
		contactsLabel.setWidth100();
		contactsLabel.setContents(Messages.CONTACTS_US);
		contactsLabel.setMargin(10);

		setIsModal(true);
		setShowModalMask(true);
		setTitle("Contacts US");
		setHeaderIcon("closha/icon/help.png");
		setKeepInParentRect(true);
		centerInPage();
		setWidth(550);
		setHeight(300);
		setCanDragReposition(false);
		setCanDragResize(false);
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		centerInPage();
		setAutoCenter(true);
		addItem(contactsLabel);
	}
}
