package org.kobic.gbox.client.monitor.view;

import org.kobic.gbox.client.model.FXTableTransferModel;
import org.kobic.gbox.client.monitor.presenter.TransferMonitorPresenter;

import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class TransferMonitorViewer implements TransferMonitorPresenter.Display {

	private VBox layout;

	private TableView<FXTableTransferModel> tableView;

	private TableColumn<FXTableTransferModel, Integer> idCol;
	private TableColumn<FXTableTransferModel, String> uuidCol;
	private TableColumn<FXTableTransferModel, Integer> typeCol;
	private TableColumn<FXTableTransferModel, String> methodCol;
	private TableColumn<FXTableTransferModel, String> nameCol;
	private TableColumn<FXTableTransferModel, String> statusCol;
	private TableColumn<FXTableTransferModel, String> sizeCol;
	private TableColumn<FXTableTransferModel, String> localCol;
	private TableColumn<FXTableTransferModel, Double> progressBarCol;
	private TableColumn<FXTableTransferModel, String> speedCol;
	private TableColumn<FXTableTransferModel, String> elapsedTimeCol;
	private TableColumn<FXTableTransferModel, String> transferSizeCol;

	private ContextMenu contextMenu;

	private MenuItem cancelMenuItem;

	@SuppressWarnings("unchecked")
	public TransferMonitorViewer() {

		tableView = new TableView<FXTableTransferModel>();
		tableView.setEditable(true);
		tableView.setFocusTraversable(false);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tableView.setTableMenuButtonVisible(true);
		tableView.maxHeight(Integer.MAX_VALUE);
		tableView.setPrefHeight(Integer.MAX_VALUE);

		idCol = new TableColumn<FXTableTransferModel, Integer>("ID");
		idCol.setVisible(false);
		idCol.setCellValueFactory(new PropertyValueFactory<FXTableTransferModel, Integer>("id"));
		idCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
		idCol.setSortable(false);

		uuidCol = new TableColumn<FXTableTransferModel, String>("UUID");
		uuidCol.setVisible(false);
		uuidCol.setCellValueFactory(new PropertyValueFactory<FXTableTransferModel, String>("uuid"));
		uuidCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
		uuidCol.setSortable(false);

		typeCol = new TableColumn<FXTableTransferModel, Integer>("Type");
		typeCol.setCellValueFactory(new PropertyValueFactory<FXTableTransferModel, Integer>("type"));
		typeCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
		typeCol.setSortable(false);
		typeCol.setCellFactory(
				new Callback<TableColumn<FXTableTransferModel, Integer>, TableCell<FXTableTransferModel, Integer>>() {
					@Override
					public TableCell<FXTableTransferModel, Integer> call(
							TableColumn<FXTableTransferModel, Integer> param) {
						// TODO Auto-generated method stub
						TableCell<FXTableTransferModel, Integer> tc = new TableCell<FXTableTransferModel, Integer>() {
							@Override
							public void updateItem(Integer item, boolean empty) {
								if (!empty) {
									String img = item == 0 ? "icons/up.gif" : "icons/down.gif";
									setGraphic(new ImageView(new Image(img)));
								}
							}
						};

						tc.setAlignment(Pos.CENTER);

						return tc;
					}
				});
		
		methodCol = new TableColumn<FXTableTransferModel, String>("Method");
		methodCol.setCellValueFactory(new PropertyValueFactory<FXTableTransferModel, String>("method"));
		methodCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.03));
		methodCol.setSortable(false);
		
		nameCol = new TableColumn<FXTableTransferModel, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<FXTableTransferModel, String>("name"));
		nameCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
		nameCol.setSortable(false);
		
		statusCol = new TableColumn<FXTableTransferModel, String>("Status");
		statusCol.setCellValueFactory(new PropertyValueFactory<FXTableTransferModel, String>("status"));
		statusCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
		statusCol.setSortable(false);
		statusCol.setCellFactory(
				new Callback<TableColumn<FXTableTransferModel, String>, TableCell<FXTableTransferModel, String>>() {
					@Override
					public TableCell<FXTableTransferModel, String> call(
							TableColumn<FXTableTransferModel, String> param) {
						// TODO Auto-generated method stub
						TableCell<FXTableTransferModel, String> tc = new TableCell<FXTableTransferModel, String>() {
							@Override
							public void updateItem(String item, boolean empty) {
								if (item != null) {
									setText(item);
								}
							}
						};

						tc.setAlignment(Pos.CENTER);
						return tc;
					}
				});

		sizeCol = new TableColumn<FXTableTransferModel, String>("Size");
		sizeCol.setCellValueFactory(new PropertyValueFactory<FXTableTransferModel, String>("size"));
		sizeCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
		sizeCol.setSortable(false);
		sizeCol.setCellFactory(
				new Callback<TableColumn<FXTableTransferModel, String>, TableCell<FXTableTransferModel, String>>() {
					@Override
					public TableCell<FXTableTransferModel, String> call(
							TableColumn<FXTableTransferModel, String> param) {
						// TODO Auto-generated method stub
						TableCell<FXTableTransferModel, String> tc = new TableCell<FXTableTransferModel, String>() {
							@Override
							public void updateItem(String item, boolean empty) {
								if (item != null) {
									setText(item);
								}
							}
						};

						tc.setAlignment(Pos.CENTER);
						return tc;
					}
				});

		localCol = new TableColumn<FXTableTransferModel, String>("Source");
		localCol.setCellValueFactory(new PropertyValueFactory<FXTableTransferModel, String>("local"));
		localCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
		localCol.setSortable(false);
		localCol.setCellFactory(
				new Callback<TableColumn<FXTableTransferModel, String>, TableCell<FXTableTransferModel, String>>() {
					@Override
					public TableCell<FXTableTransferModel, String> call(
							TableColumn<FXTableTransferModel, String> param) {
						// TODO Auto-generated method stub
						TableCell<FXTableTransferModel, String> tc = new TableCell<FXTableTransferModel, String>() {
							@Override
							public void updateItem(String item, boolean empty) {
								if (item != null) {
									setText(item);
								}
							}
						};

						tc.setAlignment(Pos.CENTER);
						return tc;
					}
				});

		progressBarCol = new TableColumn<FXTableTransferModel, Double>("Progress Bar");
		progressBarCol.setCellValueFactory(new PropertyValueFactory<FXTableTransferModel, Double>("progressBar"));
		progressBarCol.setCellFactory(ProgressBarTableCell.forTableColumn());
		progressBarCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
		progressBarCol.setSortable(false);

		speedCol = new TableColumn<FXTableTransferModel, String>("Speed");
		speedCol.setCellValueFactory(new PropertyValueFactory<FXTableTransferModel, String>("speed"));
		speedCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
		speedCol.setSortable(false);
		speedCol.setCellFactory(
				new Callback<TableColumn<FXTableTransferModel, String>, TableCell<FXTableTransferModel, String>>() {
					@Override
					public TableCell<FXTableTransferModel, String> call(
							TableColumn<FXTableTransferModel, String> param) {
						// TODO Auto-generated method stub
						TableCell<FXTableTransferModel, String> tc = new TableCell<FXTableTransferModel, String>() {
							@Override
							public void updateItem(String item, boolean empty) {
								if (item != null) {
									setText(item);
								}
							}
						};

						tc.setAlignment(Pos.CENTER);
						return tc;
					}
				});

		elapsedTimeCol = new TableColumn<FXTableTransferModel, String>("Elapsed Time");
		elapsedTimeCol.setCellValueFactory(new PropertyValueFactory<FXTableTransferModel, String>("elapsedTime"));
		elapsedTimeCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
		elapsedTimeCol.setSortable(false);
		elapsedTimeCol.setCellFactory(
				new Callback<TableColumn<FXTableTransferModel, String>, TableCell<FXTableTransferModel, String>>() {
					@Override
					public TableCell<FXTableTransferModel, String> call(
							TableColumn<FXTableTransferModel, String> param) {
						// TODO Auto-generated method stub
						TableCell<FXTableTransferModel, String> tc = new TableCell<FXTableTransferModel, String>() {
							@Override
							public void updateItem(String item, boolean empty) {
								if (item != null) {
									setText(item);
								}
							}
						};

						tc.setAlignment(Pos.CENTER);
						return tc;
					}
				});

		transferSizeCol = new TableColumn<FXTableTransferModel, String>("Transferred Size");
		transferSizeCol.setCellValueFactory(new PropertyValueFactory<FXTableTransferModel, String>("transferSize"));
		transferSizeCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
		transferSizeCol.setSortable(false);
		transferSizeCol.setCellFactory(
				new Callback<TableColumn<FXTableTransferModel, String>, TableCell<FXTableTransferModel, String>>() {
					@Override
					public TableCell<FXTableTransferModel, String> call(
							TableColumn<FXTableTransferModel, String> param) {
						// TODO Auto-generated method stub
						TableCell<FXTableTransferModel, String> tc = new TableCell<FXTableTransferModel, String>() {
							@Override
							public void updateItem(String item, boolean empty) {
								if (item != null) {
									setText(item);
								}
							}
						};

						tc.setAlignment(Pos.CENTER);
						return tc;
					}
				});

		cancelMenuItem = new MenuItem("Cancel", new ImageView(new Image("icons/cross.png")));
		cancelMenuItem.setDisable(true);

		contextMenu = new ContextMenu();
		contextMenu.getItems().addAll(cancelMenuItem);

		tableView.getColumns().addAll(typeCol, methodCol, nameCol, statusCol, sizeCol, transferSizeCol, localCol,
				progressBarCol, speedCol, elapsedTimeCol);
		tableView.setPlaceholder(new Label(""));
		tableView.setContextMenu(contextMenu);

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(tableView);

		layout = new VBox();
		layout.getChildren().addAll(borderPane);
	}

	@Override
	public VBox asWidget() {
		// TODO Auto-generated method stub
		return layout;
	}

	@Override
	public TableView<FXTableTransferModel> getTreeView() {
		// TODO Auto-generated method stub
		return tableView;
	}

	@Override
	public MenuItem getCancelMenuItem() {
		return cancelMenuItem;
	}

}