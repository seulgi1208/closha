package org.kobic.gbox.client.component;

import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;

public class SplitPaneComponent extends SplitPane{
	
	public SplitPaneComponent(Orientation o, double splitLocation){
		setOrientation(o);
		setDividerPosition(0, splitLocation);
	}

	public Parent getNode() {
		return this;
	}
}
