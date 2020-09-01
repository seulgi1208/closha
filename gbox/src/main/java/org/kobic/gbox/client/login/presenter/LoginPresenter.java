package org.kobic.gbox.client.login.presenter;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.kobic.gbox.client.common.Constants;
import org.kobic.gbox.client.component.ShowAlertDialogs;
import org.kobic.gbox.client.controller.RapidantServiceController;
import org.kobic.gbox.client.controller.RapidantServiceControllerImpl;
import org.kobic.gbox.client.fire.event.LoginPrecessFireEvent;
import org.kobic.gbox.client.presenter.Presenter;
import org.kobic.sso.client.model.Member;
import org.kobic.sso.client.model.UserAccount;
import org.kobic.sso.member.service.MemberService;
import org.kobic.sso.member.service.MemberServiceImpl;

import com.pennychecker.eventbus.HandlerManager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rapidant.model.client.RapidantClient;

public class LoginPresenter implements Presenter {

	final static Logger logger = Logger.getLogger(LoginPresenter.class);

	@SuppressWarnings("unused")
	private Stage stage;
	private Display display;
	private HandlerManager eventBus;
	private RapidantClient client;
	private MemberService memberService;
	private RapidantServiceController rapidantService;

	public LoginPresenter(Display view, Stage stage, HandlerManager eventBus, RapidantClient client) {
		this.display = view;
		this.eventBus = eventBus;
		this.stage = stage;
		this.client = client;
	}

	public interface Display {
		BorderPane asWidget();

		TextField getUserIdTextField();

		PasswordField getPasswordField();

		Button getLoginButton();

		Button getRegisterButton();

		Text getActiontarget();
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		setLoginButtonEvent();
		setRegisterButtonEvent();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		memberService = new MemberServiceImpl();
		rapidantService = new RapidantServiceControllerImpl();
		bind();
	}

	@Override
	public void go(Object container) {
		// TODO Auto-generated method stub
		BorderPane pane = (BorderPane) container;
		pane.setCenter(display.asWidget());
		init();
	}

	private void setRegisterButtonEvent() {
		display.getRegisterButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
//				eventBus.fireEvent(new FrameChangeFireEvent(Constants.REGISGER_FRAME_ID));
				
				try {
					Desktop.getDesktop().browse(new URI("https://sso.kobic.re.kr/"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}

	private void setLoginButtonEvent() {

//		display.getPasswordField().setOnKeyPressed(event -> {
//			if (event.getCode() == KeyCode.ENTER) {
//				String userId = display.getUserIdTextField().getText();
//				String password = display.getPasswordField().getText();
//
//				doLogin(userId, password);
//			}
//		});
		
		display.getPasswordField().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ENTER) {
					String userId = display.getUserIdTextField().getText();
					String password = display.getPasswordField().getText();

					doLogin(userId, password);
				}				
			}
		});

		display.getLoginButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String userId = display.getUserIdTextField().getText();
				String password = display.getPasswordField().getText();

				doLogin(userId, password);
			}
		});
	}

	private void doLogin(String userId, String password) {

		if (userId.length() == 0 || password.length() == 0) {
			new ShowAlertDialogs(AlertType.WARNING, null, Constants.LOGIN_NOT_VALIDATION_INFO_TEXT);
		} else {
			UserAccount userAccount = new UserAccount();
			userAccount.setMemberId(userId);
			userAccount.setMemberPassword(password);
			Member member = memberService.loginAccount(userAccount);

			boolean login = member != null ? true : false;

			if (login) {

				member = memberService.getMember(member.getMemberNo(), member.getMemberId());

				String message = Constants.LOGIN_VALIDATION_INFO_TEXT;

				Alert loginDialogs = new Alert(AlertType.CONFIRMATION);
				Stage stage = (Stage) loginDialogs.getDialogPane().getScene().getWindow();
				Image dialogIcon = new Image(Constants.APP_ICON);
				stage.getIcons().add(dialogIcon);
				loginDialogs.setTitle(Constants.TITLE);
				loginDialogs.setHeaderText(null);
				loginDialogs.setContentText(
						String.format(message, member.getMemberNm(), member.getMemberPstinst(), member.getMemberOfcps(),
								member.getMemberEmail(), member.getMemberRegDt(), member.getMemberLastLoginDt()));

				Optional<ButtonType> loginResult = loginDialogs.showAndWait();

				if (loginResult.get() == ButtonType.OK) {

					if (RapidantServiceController.Util.getInstance().connection(client)) {

						boolean is_user_dir = rapidantService.existDir(client, Constants.RAPIDANT_PATH,
								member.getMemberId());

						if (!is_user_dir) {
							logger.info("사용자[" + userId + "] 계정의 데이터 폴더 생성 요청을 합니다.");

							boolean is_res = rapidantService.makeDir(client,
									Constants.RAPIDANT_PATH + "/" + member.getMemberId());

							if (!is_res) {
								new ShowAlertDialogs(AlertType.INFORMATION, null, Constants.APP_DISCONNECT_TEXT);
							}
						}

						new ShowAlertDialogs(AlertType.INFORMATION, null, Constants.LOGIN_CONNECT_SERVER_TEXT);
						eventBus.fireEvent(new LoginPrecessFireEvent(Constants.RAPIDANT_FRAME_ID, member));

					} else {
						new ShowAlertDialogs(AlertType.INFORMATION, null, Constants.APP_DISCONNECT_TEXT);
					}
				}

			} else {
				display.getActiontarget().setFill(Color.FIREBRICK);
				display.getActiontarget().setText(Constants.LOGIN_NOT_VALIDATION_USERINFO_TEXT);
			}
		}
	}
}