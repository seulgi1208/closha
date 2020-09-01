package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.HasAllTouchHandlers;
import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.save.IsDiagramSerializable;
import com.allen_sauer.gwt.dnd.client.util.Location;
import com.allen_sauer.gwt.dnd.client.util.WidgetLocation;

final class WindowPanel extends FocusPanel implements HasAllTouchHandlers,IsDiagramSerializable{
	
	String identifier;
	String content;

	/**
	 * WindowPanel direction constant, used in
	 * {@link ResizeDragController#makeDraggable(Widget, DirectionConstant)}.
	 */
	public static class DirectionConstant {

		public final int directionBits;

		public final String directionLetters;

		private DirectionConstant(int directionBits, String directionLetters) {
			this.directionBits = directionBits;
			this.directionLetters = directionLetters;
		}
	}
	/**
	 * Specifies that resizing occur at the east edge.
	 */
	public static final int DIRECTION_EAST = 0x0001;

	/**
	 * Specifies that resizing occur at the both edge.
	 */
	public static final int DIRECTION_NORTH = 0x0002;

	/**
	 * Specifies that resizing occur at the south edge.
	 */
	public static final int DIRECTION_SOUTH = 0x0004;

	/**
	 * Specifies that resizing occur at the west edge.
	 */
	public static final int DIRECTION_WEST = 0x0008;

	/**
	 * Specifies that resizing occur at the east edge.
	 */
	public static final DirectionConstant EAST = new DirectionConstant(
			DIRECTION_EAST, "e");

	/**
	 * Specifies that resizing occur at the both edge.
	 */
	public static final DirectionConstant NORTH = new DirectionConstant(
			DIRECTION_NORTH, "n");

	/**
	 * Specifies that resizing occur at the north-east edge.
	 */
	public static final DirectionConstant NORTH_EAST = new DirectionConstant(
			DIRECTION_NORTH | DIRECTION_EAST, "ne");

	/**
	 * Specifies that resizing occur at the north-west edge.
	 */
	public static final DirectionConstant NORTH_WEST = new DirectionConstant(
			DIRECTION_NORTH | DIRECTION_WEST, "nw");

	/**
	 * Specifies that resizing occur at the south edge.
	 */
	public static final DirectionConstant SOUTH = new DirectionConstant(
			DIRECTION_SOUTH, "s");

	/**
	 * Specifies that resizing occur at the south-east edge.
	 */
	public static final DirectionConstant SOUTH_EAST = new DirectionConstant(
			DIRECTION_SOUTH | DIRECTION_EAST, "se");

	/**
	 * Specifies that resizing occur at the south-west edge.
	 */
	public static final DirectionConstant SOUTH_WEST = new DirectionConstant(
			DIRECTION_SOUTH | DIRECTION_WEST, "sw");

	/**
	 * Specifies that resizing occur at the west edge.
	 */
	public static final DirectionConstant WEST = new DirectionConstant(
			DIRECTION_WEST, "w");

	private static final int BORDER_THICKNESS = 5;

	private static final String CSS_DEMO_RESIZE_EDGE = "demo-resize-edge";

	private static final String CSS_DEMO_RESIZE_PANEL = "demo-WindowPanel";

	private static final String CSS_DEMO_RESIZE_PANEL_HEADER = "demo-WindowPanel-header";

	private int contentHeight;

	private Widget contentOrScrollPanelWidget;

	private int contentWidth;

	private Widget eastWidget;

	private Grid grid = new Grid(3, 3);

	private final FocusPanel headerContainer;

	private final Widget headerWidget;

	private boolean initialLoad = false;

	private Widget northWidget;

	private Widget southWidget;

	private Widget westWidget;

	@SuppressWarnings("unused")
	private final WindowController windowController;
	
	public WindowPanel(final WindowController windowController,
			Widget headerWidget, Widget contentWidget,
			boolean wrapContentInScrollPanel, String content, String key) {
		
		this.content = content;
		this.identifier = key;
		
		/*
		 * 각 모듈을 감싸고 있는 Border 영역
		 */
//		getElement().getStyle().setBorderWidth(1, Unit.PX);
//		getElement().getStyle().setBorderColor("bule");
//		getElement().getStyle().setBorderStyle(BorderStyle.DOTTED);
//		getElement().getStyle().setBackgroundColor("#CCCCCC");
//		getElement().getStyle().setMargin(0, Unit.PX);
//		getElement().getStyle().setPadding(0, Unit.PX);
//		getElement().getStyle().setBackgroundImage("closha/icon_bg2.jpg");
//		getElement().getStyle().setHeight(78, Unit.PX);
//		getElement().getStyle().setWidth(158, Unit.PX);
//		getElement().getStyle().setOverflow(Overflow.AUTO);
//		setStyleName("myDecoratedPanelStyle");
		
		this.windowController = windowController;
		this.headerWidget = headerWidget;
		addStyleName(CSS_DEMO_RESIZE_PANEL);

		contentOrScrollPanelWidget = wrapContentInScrollPanel ? new ScrollPanel(contentWidget) : contentWidget;
		
		headerContainer = new FocusPanel();
		headerContainer.addStyleName(CSS_DEMO_RESIZE_PANEL_HEADER);

		windowController.getPickupDragController().makeDraggable(this, headerContainer);
		headerContainer.add(headerWidget);

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(contentOrScrollPanelWidget);
		verticalPanel.add(headerContainer);

		grid.setCellSpacing(0);
		grid.setCellPadding(0);
		grid.getElement().getStyle().setBackgroundImage("url('images/icon_bg_1.png')");
		add(grid);

		setupCell(0, 0, NORTH_WEST);
		northWidget = setupCell(0, 1, NORTH);
		setupCell(0, 2, NORTH_EAST);

		westWidget = setupCell(1, 0, WEST);
		grid.setWidget(1, 1, verticalPanel);
		eastWidget = setupCell(1, 2, EAST);

		setupCell(2, 0, SOUTH_WEST);
		southWidget = setupCell(2, 1, SOUTH);
		setupCell(2, 2, SOUTH_EAST);
	}

	public int getContentHeight() {
		return contentHeight;
	}

	public int getContentWidth() {
		return contentWidth;
	}

	public void moveBy(int right, int down) {
		AbsolutePanel parent = (AbsolutePanel) getParent();
		Location location = new WidgetLocation(this, parent);
		int left = location.getLeft() + right;
		int top = location.getTop() + down;
		parent.setWidgetPosition(this, left, top);
	}

	public void setContentSize(int width, int height) {
		if (width != contentWidth) {
			contentWidth = width;
			headerContainer.setPixelSize(contentWidth,
					headerWidget.getOffsetHeight());
			northWidget.setPixelSize(contentWidth, BORDER_THICKNESS);
			southWidget.setPixelSize(contentWidth, BORDER_THICKNESS);
		}
		if (height != contentHeight) {
			contentHeight = height;
			int headerHeight = headerContainer.getOffsetHeight();
			westWidget.setPixelSize(BORDER_THICKNESS, contentHeight
					+ headerHeight);
			eastWidget.setPixelSize(BORDER_THICKNESS, contentHeight
					+ headerHeight);
		}
		contentOrScrollPanelWidget.setPixelSize(contentWidth, contentHeight);
	}

	private Widget setupCell(int row, int col, DirectionConstant direction) {
		final FocusPanel widget = new FocusPanel();
		widget.setPixelSize(BORDER_THICKNESS, BORDER_THICKNESS);
		grid.setWidget(row, col, widget);

		grid.getCellFormatter().addStyleName(
				row,
				col,
				CSS_DEMO_RESIZE_EDGE + " demo-resize-"
						+ direction.directionLetters);
		return widget;
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		if (!initialLoad && contentOrScrollPanelWidget.getOffsetHeight() != 0) {
			initialLoad = true;
			headerWidget.setPixelSize(headerWidget.getOffsetWidth(),
					headerWidget.getOffsetHeight());
			setContentSize(contentOrScrollPanelWidget.getOffsetWidth(),
					contentOrScrollPanelWidget.getOffsetHeight());
		}
	}
	
	@Override
	public HandlerRegistration addDragStartHandler(DragStartHandler handler){
		return addDomHandler(handler, DragStartEvent.getType());
	}
	
	@Override
	public HandlerRegistration addDragEndHandler(DragEndHandler handler){
		return addDomHandler(handler, DragEndEvent.getType());
	}
	
	@Override
	public HandlerRegistration addTouchStartHandler(TouchStartHandler handler){
		return addDomHandler(handler, TouchStartEvent.getType());
	}
	
	@Override
	public HandlerRegistration addTouchEndHandler(TouchEndHandler handler){
		return addDomHandler(handler, TouchEndEvent.getType());
	}
	
	@Override
	public HandlerRegistration addTouchMoveHandler(TouchMoveHandler handler){
		return addDomHandler(handler, TouchMoveEvent.getType());
	}
	
	@Override
	public HandlerRegistration addTouchCancelHandler(TouchCancelHandler handler){
		return addDomHandler(handler, TouchCancelEvent.getType());
	}

	@Override
	public String getType() {
		return this.identifier;
	}

	@Override
	public String getContentRepresentation() {
		return this.content;
	}

	@Override
	public void setContentRepresentation(String contentRepresentation) {
		this.content = contentRepresentation;
	}
}