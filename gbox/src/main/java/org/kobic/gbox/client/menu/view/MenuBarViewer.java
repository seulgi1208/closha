package org.kobic.gbox.client.menu.view;

import java.util.ArrayList;
import java.util.List;

import org.kobic.gbox.client.common.Constants;
import org.kobic.gbox.client.menu.presenter.MenuBarPresenter;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MenuBarViewer extends VBox implements MenuBarPresenter.Display {

	private MenuBar menuBar;
	private ToolBar toolbar;

	private MenuItem newMenuItem;
	private MenuItem openMenuItem;

	private MenuItem printMenuItem;
	private MenuItem exitMenuItem;

	private MenuItem copyMenuItem;
	private MenuItem pasteMenuItem;
	private MenuItem findMenuItem;

	private MenuItem refreshMenuItem;

	private MenuItem makeMenuItem;
	private MenuItem renameMenuItem;
	private MenuItem deleteMenuItem;

	private MenuItem syncMangerMenuItem;
	private MenuItem optionMenuItem;

	private MenuItem helpContactMenuItem;
	private MenuItem aboutMenuItem;

	private Button sizeButton;
	private Button helpButton;
	private Button refreshButton;

	private ComboBox<String> speedComboBox;
	private ComboBox<String> methodComboBox;
	private ComboBox<String> protocolComboBox;

	private ProgressBar progressBar;
	private Label usageTextLabel;

	private Label totalUserLabel;
	private Button clearButton;

	public MenuBarViewer() {

		openMenuItem = new MenuItem("Open", new ImageView(new Image("icons/folder.png")));
		openMenuItem.setDisable(true);

		newMenuItem = new MenuItem("New File", new ImageView(new Image("icons/application_add.png")));
		newMenuItem.setDisable(true);

		printMenuItem = new MenuItem("Log print", new ImageView(new Image("icons/printer.png")));
		printMenuItem.setDisable(true);

		exitMenuItem = new MenuItem("Exit", new ImageView(new Image("icons/door_out.png")));

		Menu fileMenu = new Menu("File");
		fileMenu.getItems().addAll(openMenuItem, newMenuItem, new SeparatorMenuItem(), printMenuItem,
				new SeparatorMenuItem(), exitMenuItem);

		copyMenuItem = new MenuItem("Copy", new ImageView(new Image("icons/page_copy.png")));
		copyMenuItem.setDisable(true);

		pasteMenuItem = new MenuItem("Paste", new ImageView(new Image("icons/page_paste.png")));
		pasteMenuItem.setDisable(true);

		findMenuItem = new MenuItem("Search", new ImageView(new Image("icons/magnifier.png")));
		findMenuItem.setDisable(true);

		Menu editMenu = new Menu("Edit");
		editMenu.getItems().addAll(copyMenuItem, pasteMenuItem, findMenuItem);

		refreshMenuItem = new MenuItem("Refresh", new ImageView(new Image("icons/arrow_rotate_clockwise.png")));
		refreshMenuItem.setDisable(true);

		Menu viewMenu = new Menu("View");
		viewMenu.getItems().addAll(refreshMenuItem);

		makeMenuItem = new MenuItem("New Folder", new ImageView(new Image("icons/folder_add.png")));
		makeMenuItem.setDisable(true);

		renameMenuItem = new MenuItem("Rename", new ImageView(new Image("icons/folder_edit.png")));
		renameMenuItem.setDisable(true);

		deleteMenuItem = new MenuItem("Delete", new ImageView(new Image("icons/folder_delete.png")));
		deleteMenuItem.setDisable(true);

		Menu commandMenu = new Menu("Command");
		commandMenu.getItems().addAll(makeMenuItem, renameMenuItem, deleteMenuItem);

		syncMangerMenuItem = new MenuItem("Sync", new ImageView(new Image("icons/arrow_refresh.png")));
		syncMangerMenuItem.setDisable(true);

		optionMenuItem = new MenuItem("Settings", new ImageView(new Image("icons/cog.png")));
		optionMenuItem.setDisable(true);

		Menu toolsMenu = new Menu("Tools");
		toolsMenu.getItems().addAll(syncMangerMenuItem, optionMenuItem);

		helpContactMenuItem = new MenuItem("Help", new ImageView(new Image("icons/help.png")));
		aboutMenuItem = new MenuItem("About" + Constants.TITLE, new ImageView(new Image("icons/comment.png")));

		Menu helpMenu = new Menu("Help");
		helpMenu.getItems().addAll(helpContactMenuItem, aboutMenuItem);

		menuBar = new MenuBar();
		menuBar.getStyleClass().add("menuBar");
		menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu, commandMenu, toolsMenu, helpMenu);

		Image sizeImg = new Image("icons/arrow_out.png");
		Image helpImg = new Image("icons/help.png");
		Image refreshImg = new Image("icons/page-refresh-icon.png");


		sizeButton = new Button();
		sizeButton.setGraphic(new ImageView(sizeImg));

		helpButton = new Button();
		helpButton.setGraphic(new ImageView(helpImg));

		Label speedLabel = new Label();
		speedLabel.setText("Transmission Speed: ");

		List<String> trensferSpeedList = new ArrayList<String>();
		trensferSpeedList.add("Top Priority");
		trensferSpeedList.add("Optimize");

		speedComboBox = new ComboBox<String>();
		speedComboBox.setPrefWidth(80);
		speedComboBox.setMinSize(60, 20);
		speedComboBox.promptTextProperty().setValue("Top Priority");
		speedComboBox.getItems().addAll(trensferSpeedList);

		Label methodLabel = new Label();
		methodLabel.setText("Protocol: ");

		List<String> methodList = new ArrayList<String>();
		methodList.add("TCP/IP");
		methodList.add("UDP");

		methodComboBox = new ComboBox<String>();
		methodComboBox.setPrefWidth(90);
		methodComboBox.setMinSize(80, 20);
		methodComboBox.promptTextProperty().setValue("TCP/IP");
		methodComboBox.getItems().addAll(methodList);

		Label protocolLabel = new Label();
		protocolLabel.setText("Method: ");

		List<String> protocolList = new ArrayList<String>();
		protocolList.add("RAPIDANT");
		protocolList.add("FTP");

		protocolComboBox = new ComboBox<String>();
		protocolComboBox.setPrefWidth(110);
		protocolComboBox.setMinSize(100, 20);
		protocolComboBox.promptTextProperty().setValue("RAPIDANT");
		protocolComboBox.getItems().addAll(protocolList);

		Label usageLabel = new Label();
		usageLabel.setText("Usage: ");

		progressBar = new ProgressBar(0);
		progressBar.setPrefSize(60, 20);

		usageTextLabel = new Label();

		totalUserLabel = new Label();

		refreshButton = new Button();
		refreshButton.setGraphic(new ImageView(refreshImg));
		
		clearButton = new Button();
		clearButton.setGraphic(new ImageView("icons/trash.png"));

		toolbar = new ToolBar();
		toolbar.getItems().addAll(sizeButton, helpButton, speedLabel, speedComboBox,
				methodLabel, methodComboBox, protocolLabel, protocolComboBox, usageLabel, progressBar, usageTextLabel,
				refreshButton, totalUserLabel,clearButton);

		getChildren().add(menuBar);
		getChildren().add(toolbar);
	}


	@Override
	public VBox asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public MenuItem getExitMenuItem() {
		// TODO Auto-generated method stub
		return exitMenuItem;
	}

	@Override
	public MenuItem getAboutMenuItem() {
		// TODO Auto-generated method stub
		return aboutMenuItem;
	}

	@Override
	public MenuItem getHelpContactMenuItem() {
		// TODO Auto-generated method stub
		return helpContactMenuItem;
	}

	@Override
	public Button getSizeButton() {
		// TODO Auto-generated method stub
		return sizeButton;
	}

	@Override
	public Button getHelpButton() {
		// TODO Auto-generated method stub
		return helpButton;
	}

	@Override
	public ComboBox<String> getSpeedComboBox() {
		// TODO Auto-generated method stub
		return speedComboBox;
	}

	@Override
	public ComboBox<String> getMethodComboBox() {
		// TODO Auto-generated method stub
		return methodComboBox;
	}

	@Override
	public ProgressBar getProgressBar() {
		// TODO Auto-generated method stub
		return progressBar;
	}

	@Override
	public Label getUsageLabel() {
		// TODO Auto-generated method stub
		return usageTextLabel;
	}

	@Override
	public Button getRefreshButton() {
		// TODO Auto-generated method stub
		return refreshButton;
	}

	@Override
	public ComboBox<String> getProtocoComboBox() {
		// TODO Auto-generated method stub
		return protocolComboBox;
	}

	@Override
	public Label getTotalUserLabel() {
		// TODO Auto-generated method stub
		return totalUserLabel;
	}
	
	@Override
	public Button getClearButton() {
		return clearButton;
	}

}
