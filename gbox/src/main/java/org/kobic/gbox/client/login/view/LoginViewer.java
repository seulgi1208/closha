package org.kobic.gbox.client.login.view;

import org.kobic.gbox.client.common.Constants;
import org.kobic.gbox.client.login.presenter.LoginPresenter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LoginViewer extends BorderPane implements LoginPresenter.Display {
	
	private TextField userIdTextField;
	private PasswordField passwordField;
	private Button loginButton;
	private Button registerButton;
	private Text actiontarget;
	
	public LoginViewer() {
		
		Image image = new Image(Constants.LOGIN_MAIN_BG);
		BackgroundImage backgroundImage = new BackgroundImage(image, null, null, null, null);
		setBackground(new Background(backgroundImage));
		
		Image logoImg = new Image(Constants.LOGIN_MAIN_TITLE_IMAGE);
		ImageView logoImgViewer = new ImageView(logoImg);

		Text loginTitle = new Text("     "+Constants.VERESION);
		loginTitle.setId(Constants.LOGIN_TITLE_ID);
		loginTitle.setFill(Color.WHITE);
		loginTitle.setFont(Font.font(Constants.FONT_VERDANA, FontWeight.SEMI_BOLD, 13));
		
		VBox versionLayout = new VBox();
		versionLayout.setAlignment(Pos.BOTTOM_CENTER);
		versionLayout.getChildren().add(loginTitle);

		HBox logoLayout = new HBox();
		logoLayout.getChildren().addAll(logoImgViewer, versionLayout);
		logoLayout.setAlignment(Pos.CENTER_RIGHT);
	    
		userIdTextField = new TextField();
		userIdTextField.setPromptText(Constants.LOGIN_PROMPT_IDTEXTFIELD_TEXT);
		userIdTextField.setPrefSize(300, 30);
		userIdTextField.setTooltip(new Tooltip(Constants.LOGIN_TOOLTIP_IDTEXTFIELD_TEXT));
		userIdTextField.setStyle(Constants.STYLE_ARIAL);
		
		passwordField = new PasswordField();
		passwordField.setPrefSize(300, 30);
		passwordField.setTooltip(new Tooltip(Constants.LOGIN_TOOLTIP_PASSWORD_TEXT));
		passwordField.setPromptText(Constants.LOGIN_PROMPT_PASSWORD_TEXT);
		passwordField.setStyle(Constants.STYLE_ARIAL);
		
		loginButton = new Button(Constants.LOGIN_BUTTON_LOGIN_TEXT);
		loginButton.setId(Constants.LOGIN_BUTTON_LOGIN_ID);
		loginButton.setMinSize(150, 30);
		
		registerButton = new Button(Constants.LOGIN_BUTTON_REGISTER_TEXT);
		registerButton.setId(Constants.LOGIN_BUTTON_REGISTER_ID);
		registerButton.setMinSize(150, 30);
		
		HBox buttonLayout = new HBox(10);
		buttonLayout.setAlignment(Pos.BOTTOM_CENTER);
		buttonLayout.getChildren().add(registerButton);
		buttonLayout.getChildren().add(loginButton);

		actiontarget = new Text();
		actiontarget.setFill(Color.FIREBRICK);
		actiontarget.setId(Constants.LOGIN_TEXT_ACTIONTARGET_ID);
		
		GridPane g = new GridPane();
		g.setAlignment(Pos.BOTTOM_CENTER);
		g.setHgap(10);
		g.setVgap(10);
		g.setPadding(new Insets(0.0, 0.0, 40.0, 0.0));
		g.setId(Constants.LOGIN_GRIDPANE_G_ID);
		g.addRow(0, logoLayout);
		g.addRow(2, userIdTextField);
		g.addRow(3, passwordField);
		g.addRow(4, buttonLayout);
		g.addRow(5, actiontarget);
		
		setCenter(g);
		setMargin(g, new Insets(0.0, 0.0, 170.0, 0.0));
	}

	@Override
	public BorderPane asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public TextField getUserIdTextField() {
		// TODO Auto-generated method stub
		return userIdTextField;
	}

	@Override
	public PasswordField getPasswordField() {
		// TODO Auto-generated method stub
		return passwordField;
	}

	@Override
	public Button getLoginButton() {
		// TODO Auto-generated method stub
		return loginButton;
	}

	@Override
	public Button getRegisterButton() {
		// TODO Auto-generated method stub
		return registerButton;
	}

	@Override
	public Text getActiontarget() {
		// TODO Auto-generated method stub
		return actiontarget;
	}
}