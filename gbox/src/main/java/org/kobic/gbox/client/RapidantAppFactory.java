package org.kobic.gbox.client;

import org.apache.log4j.Logger;
import org.kobic.gbox.client.common.Constants;
import org.kobic.gbox.client.fire.event.LoginPrecessFireEvent;
import org.kobic.gbox.client.fire.event.LoginPrecessFireEventHandler;
import org.kobic.gbox.client.fire.event.StageReSizeFireEvent;
import org.kobic.gbox.client.fire.event.frame.FrameChangeFireEvent;
import org.kobic.gbox.client.fire.event.frame.FrameChangeFireEventHandler;
import org.kobic.gbox.client.frame.presenter.RapidantPresenter;
import org.kobic.gbox.client.frame.view.RapidantViewer;
import org.kobic.gbox.client.login.presenter.LoginPresenter;
import org.kobic.gbox.client.login.view.LoginViewer;
import org.kobic.gbox.client.presenter.Presenter;
import org.kobic.sso.client.model.Member;

import com.pennychecker.eventbus.HandlerManager;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import rapidant.model.client.RapidantClient;

public class RapidantAppFactory implements Presenter {

	final static Logger logger = Logger.getLogger(RapidantAppFactory.class);

	private HandlerManager eventBus;
	private RapidantClient client;
	private Stage stage;

	private BorderPane container;
	private Member member;

	public RapidantAppFactory(Stage stage, HandlerManager eventBus, RapidantClient client) {
		this.eventBus = eventBus;
		this.stage = stage;
		this.client = client;
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		registLoginFireEvent();
		FrameChangeFireEvent();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		member = null;
		bind();
	}

	public void setContent(String type) {
		Presenter presenter = null;

		if (type != null && type.equals(Constants.LOGIN_FRAME_ID)) {
			presenter = new LoginPresenter(new LoginViewer(), stage, eventBus, client);
			eventBus.fireEvent(new StageReSizeFireEvent(460, 735));
		} else if (type != null && type.equals(Constants.RAPIDANT_FRAME_ID)) {
			if (member != null) {
				presenter = new RapidantPresenter(new RapidantViewer(), stage, eventBus, member, client);
				eventBus.fireEvent(new StageReSizeFireEvent(1000, 850));

			} else {
				Alert errDialogs = new Alert(AlertType.ERROR);
				errDialogs.setTitle(Constants.TITLE);
				errDialogs.setHeaderText(null);
				errDialogs.setContentText(Constants.TITLE + Constants.APP_OCCUR_NOT_VALIDATION_TEXT);
			}
		} else {
			presenter = new LoginPresenter(new LoginViewer(), stage, eventBus, client);
		}

		if (presenter != null)
			presenter.go(container);
	}

	public void registLoginFireEvent() {
		eventBus.addHandler(LoginPrecessFireEvent.TYPE, new LoginPrecessFireEventHandler() {
			@Override
			public void loginFireEvent(LoginPrecessFireEvent event) {
				// TODO Auto-generated method stub
				member = event.getMember();
				setContent(event.getFrameId());
			}
		});
	}

	public void FrameChangeFireEvent() {
		eventBus.addHandler(FrameChangeFireEvent.TYPE, new FrameChangeFireEventHandler() {
			@Override
			public void registerFireEvent(FrameChangeFireEvent event) {
				// TODO Auto-generated method stub
				setContent(event.getFrameId());
			}
		});
	}

	@Override
	public void go(Object container) {
		// TODO Auto-generated method stub
		this.container = (BorderPane) container;
		setContent(null);
		init();
	}
}