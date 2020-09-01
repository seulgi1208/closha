package org.kobic.gbox.client.component;

import org.kobic.gbox.client.common.Constants;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ShowAlertDialogs extends Alert {

	public ShowAlertDialogs(AlertType alertType, String header, String message) {
		super(alertType);
		// TODO Auto-generated constructor stub
		Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
		Image dialogIcon = new Image(Constants.APP_ICON);
		stage.getIcons().add(dialogIcon);
		setTitle(Constants.TITLE);
		setHeaderText(header);
		setContentText(message);
		showAndWait();
	}

}
