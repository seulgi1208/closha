package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import com.google.gwt.user.client.ui.AbsolutePanel;

import com.allen_sauer.gwt.dnd.client.PickupDragController;

final class WindowController {

	private final AbsolutePanel boundaryPanel;

	private PickupDragController pickupDragController;

	WindowController(AbsolutePanel boundaryPanel) {
		this.boundaryPanel = boundaryPanel;

		pickupDragController = new PickupDragController(boundaryPanel, true);
		pickupDragController.setBehaviorConstrainedToBoundaryPanel(true);
		pickupDragController.setBehaviorMultipleSelection(false);
	}

	public AbsolutePanel getBoundaryPanel() {
		return boundaryPanel;
	}

	public PickupDragController getPickupDragController() {
		return pickupDragController;
	}
}
