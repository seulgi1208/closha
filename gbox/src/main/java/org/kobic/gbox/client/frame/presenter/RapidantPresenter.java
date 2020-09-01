package org.kobic.gbox.client.frame.presenter;

import org.kobic.gbox.client.composition.presenter.MainframePresenter;
import org.kobic.gbox.client.composition.view.MainframeViewer;
import org.kobic.gbox.client.menu.presenter.MenuBarPresenter;
import org.kobic.gbox.client.menu.view.MenuBarViewer;
import org.kobic.gbox.client.presenter.Presenter;
import org.kobic.sso.client.model.Member;

import com.pennychecker.eventbus.HandlerManager;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import rapidant.model.client.RapidantClient;

public class RapidantPresenter implements Presenter {

	private Display display;
	private HandlerManager eventBus;
	private Stage stage;
	private Member member;
	private RapidantClient client;
	
	public RapidantPresenter(Display view, Stage stage, HandlerManager eventBus, Member member, RapidantClient client) {

		this.display = view;
		this.eventBus = eventBus;
		this.stage = stage;
		this.member = member;
		this.client = client;
	}

	public interface Display {
		BorderPane asWidget();
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub

		Presenter menubarPresenter = new MenuBarPresenter(new MenuBarViewer(), stage, eventBus, member, client);
		menubarPresenter.go(display.asWidget());

		Presenter mainframePresenter = new MainframePresenter(new MainframeViewer(), stage, eventBus, member, client);
		mainframePresenter.go(display.asWidget());
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		bind();
	}

	@Override
	public void go(Object container) {
		// TODO Auto-generated method stub
		BorderPane pane = (BorderPane) container;
		pane.setCenter(display.asWidget());
		init();
	}
}
