package org.kobic.gbox.client.composition.view;

import org.kobic.gbox.client.component.SplitPaneComponent;
import org.kobic.gbox.client.composition.presenter.MainframePresenter;

import javafx.geometry.Orientation;
import javafx.scene.layout.BorderPane;

public class MainframeViewer implements MainframePresenter.Display{

	private SplitPaneComponent hSplitPane;
	private SplitPaneComponent vSplitPane;
	private BorderPane borderPane;
	
	public MainframeViewer(){
		vSplitPane = new SplitPaneComponent(Orientation.VERTICAL, .6);
		hSplitPane = new SplitPaneComponent(Orientation.HORIZONTAL, .5);
		borderPane = new BorderPane();
	}
	
	@Override
	public SplitPaneComponent asWidget() {
		// TODO Auto-generated method stub
		return vSplitPane;
	}

	@Override
	public SplitPaneComponent getHSplitPane() {
		// TODO Auto-generated method stub
		return hSplitPane;
	}

	@Override
	public BorderPane getBottomLayout() {
		// TODO Auto-generated method stub
		return borderPane;
	}
}
