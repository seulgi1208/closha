package org.kobic.gbox.client.composition.presenter;

import org.kobic.gbox.client.component.SplitPaneComponent;
import org.kobic.gbox.client.monitor.presenter.TransferMonitorPresenter;
import org.kobic.gbox.client.monitor.view.TransferMonitorViewer;
import org.kobic.gbox.client.presenter.Presenter;
import org.kobic.gbox.client.transfer.local.presenter.LocalPresenter;
import org.kobic.gbox.client.transfer.local.view.LocalViewer;
import org.kobic.gbox.client.transfer.remote.presenter.RemotePresenter;
import org.kobic.gbox.client.transfer.remote.view.RemoteViewer;
import org.kobic.sso.client.model.Member;

import com.pennychecker.eventbus.HandlerManager;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import rapidant.model.client.RapidantClient;

public class MainframePresenter implements Presenter {

	private Display display;
	private HandlerManager eventBus;
	private Stage stage;
	private Member member;
	private RapidantClient client;

	public MainframePresenter(Display view, Stage stage, HandlerManager eventBus, Member member,
			RapidantClient client) {

		this.display = view;
		this.eventBus = eventBus;
		this.stage = stage;
		this.member = member;
		this.client = client;
	}

	public interface Display {
		SplitPaneComponent asWidget();

		SplitPaneComponent getHSplitPane();

		BorderPane getBottomLayout();
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

		Presenter localPresenter = new LocalPresenter(new LocalViewer(), stage, eventBus, member);
		localPresenter.go(display.getHSplitPane());

		Presenter remotePresenter = new RemotePresenter(new RemoteViewer(), stage, eventBus, member, client);
		remotePresenter.go(display.getHSplitPane());

		Presenter transferPresenter = new TransferMonitorPresenter(new TransferMonitorViewer(), stage, eventBus,
				member);
		transferPresenter.go(display.getBottomLayout());

		display.asWidget().getItems().addAll(display.getHSplitPane(), display.getBottomLayout());
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
