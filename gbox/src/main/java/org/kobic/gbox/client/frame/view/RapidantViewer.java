package org.kobic.gbox.client.frame.view;

import org.kobic.gbox.client.frame.presenter.RapidantPresenter;

import javafx.scene.layout.BorderPane;

public class RapidantViewer extends BorderPane implements RapidantPresenter.Display{

	public RapidantViewer(){
		
	}
	
	@Override
	public BorderPane asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

}
