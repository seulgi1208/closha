package org.kobic.gbox.client.transfer.remote.view;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import org.kobic.gbox.client.model.TableFileModel;
import org.kobic.gbox.client.transfer.remote.presenter.RemotePresenter;

public class RemoteViewer extends TabPane implements RemotePresenter.Display{

	private ComboBox<String> comboBox;
	private Button treeButton;
	private Button parentButton;
	private Button refreshButton;
	private Button syncButton;
	
	private ContextMenu contextMenu;
	private MenuItem newMenuItem;
	private MenuItem deleteMenuItem;
	private MenuItem moveMenuItem;
	private MenuItem syncMenuItem;
	private MenuItem downMenuItem;
	
	private Tab tab;
	
	private StackPane span;
	private VBox vbx;
	private ProgressIndicator loading;
	
	private TableView<TableFileModel> tableView;
	
	private TableColumn<TableFileModel, String> statusCol;
	private TableColumn<TableFileModel, String> nameCol;
	private TableColumn<TableFileModel, String> sizeCol;
	private TableColumn<TableFileModel, String> typeCol;
	private TableColumn<TableFileModel, String> modifyCol;
	
	@SuppressWarnings("unchecked")
	public RemoteViewer(){
		
		tableView = new TableView<TableFileModel>();
		tableView.setEditable(true);
		tableView.setFocusTraversable(true);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tableView.setTableMenuButtonVisible(true);

		comboBox = new ComboBox<String>();
		comboBox.setEditable(true);
		comboBox.getStyleClass().add("combo-border");
		comboBox.setMinWidth(300.0);
		comboBox.setPrefWidth(500);
		
		Image treeImg = new Image("icons/application_side_tree.png");
		Image parentImg = new Image("icons/folder_up.png");
		Image refreshImg = new Image("icons/page-refresh-icon.png");
		Image syncImg = new Image("icons/arrow_refresh.png");
		
		treeButton = new Button();
		treeButton.setGraphic(new ImageView(treeImg));

		parentButton = new Button();
		parentButton.setGraphic(new ImageView(parentImg));

		refreshButton = new Button();
		refreshButton.setGraphic(new ImageView(refreshImg));
		
		syncButton = new Button();
		syncButton.setGraphic(new ImageView(syncImg));

		ToolBar toolBar = new ToolBar();
		toolBar.getItems().addAll(treeButton, comboBox, parentButton,
				refreshButton, syncButton);
		
		statusCol = new TableColumn<TableFileModel, String>("Status");
		statusCol.setCellValueFactory(new PropertyValueFactory<TableFileModel, String>("status"));
		statusCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
		statusCol.setCellFactory(new Callback<TableColumn<TableFileModel,String>, TableCell<TableFileModel,String>>() {
			@Override
			public TableCell<TableFileModel, String> call(
					TableColumn<TableFileModel, String> param) {
				// TODO Auto-generated method stub
				
				TableCell<TableFileModel, String> tc = new TableCell<TableFileModel, String>(){
					@Override
					public void updateItem(String item, boolean empty) {
						if(!empty){
							ImageView image = new ImageView(new Image(item));
							setGraphic(image);
						}
					}
				};
				tc.setAlignment(Pos.CENTER);
				return tc;
			}
		});

        nameCol = new TableColumn<TableFileModel, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<TableFileModel, String>("vname"));
        nameCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.4));
        nameCol.setCellFactory(new Callback<TableColumn<TableFileModel,String>, TableCell<TableFileModel,String>>() {
			@Override
			public TableCell<TableFileModel, String> call(
					TableColumn<TableFileModel, String> param) {
				// TODO Auto-generated method stub
				
				TableCell<TableFileModel, String> tc = new TableCell<TableFileModel, String>(){
					@Override
					public void updateItem(String item, boolean empty) {
						if(!empty){
							Label label = new Label(item.split("[|]")[0], 
									new ImageView(new Image(item.split("[|]")[1])));
							setGraphic(label);
						}
					}
				};
				return tc;
			}
		});
        
        sizeCol = new TableColumn<TableFileModel, String>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<TableFileModel, String>("calculation"));
        sizeCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        sizeCol.setSortable(false);
        sizeCol.setCellFactory(new Callback<TableColumn<TableFileModel,String>, TableCell<TableFileModel,String>>() {
			@Override
			public TableCell<TableFileModel, String> call(TableColumn<TableFileModel, String> param) {
				// TODO Auto-generated method stub
				TableCell<TableFileModel, String> tc = new TableCell<TableFileModel, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (item != null){
                            setText(item);
                        }
                    }
                };
						
				tc.setAlignment(Pos.CENTER);
				return tc;
			}
		});
        
        typeCol = new TableColumn<TableFileModel, String>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<TableFileModel, String>("type"));
        typeCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        typeCol.setSortType(TableColumn.SortType.ASCENDING);
        typeCol.setCellFactory(new Callback<TableColumn<TableFileModel,String>, TableCell<TableFileModel,String>>() {
			@Override
			public TableCell<TableFileModel, String> call(TableColumn<TableFileModel, String> param) {
				// TODO Auto-generated method stub
				TableCell<TableFileModel, String> tc = new TableCell<TableFileModel, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (item != null){
                            setText(item);
                        }
                    }
                };
						
				tc.setAlignment(Pos.CENTER);
				return tc;
			}
		});
        
        modifyCol = new TableColumn<TableFileModel, String>("Date modified");
        modifyCol.setCellValueFactory(new PropertyValueFactory<TableFileModel, String>("date"));
        modifyCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        modifyCol.setCellFactory(new Callback<TableColumn<TableFileModel,String>, TableCell<TableFileModel,String>>() {
			@Override
			public TableCell<TableFileModel, String> call(TableColumn<TableFileModel, String> param) {
				// TODO Auto-generated method stub
				TableCell<TableFileModel, String> tc = new TableCell<TableFileModel, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (item != null){
                            setText(item);
                        }
                    }
                };
						
				tc.setAlignment(Pos.CENTER);
				return tc;
			}
		});
        
        newMenuItem = new MenuItem("New folder", new ImageView(new Image("icons/folder_add.png")));
        deleteMenuItem = new MenuItem("Delete", new ImageView(new Image("icons/folder_delete.png")));
        moveMenuItem = new MenuItem("Move", new ImageView(new Image("icons/folder_move.png")));
        syncMenuItem = new MenuItem("Sync", new ImageView(syncImg));
        downMenuItem = new MenuItem("Download", new ImageView(new Image("icons/arrow_left.png")));
        
        contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(newMenuItem, deleteMenuItem, moveMenuItem, downMenuItem, syncMenuItem);
        
		tableView.getColumns().addAll(statusCol, nameCol, sizeCol, typeCol, modifyCol);
		tableView.setContextMenu(contextMenu);
		
		loading = new ProgressIndicator();
		vbx = new VBox(loading);
		vbx.setAlignment(Pos.CENTER);
		vbx.setVisible(false);
		
		span = new StackPane();
		span.getChildren().add(tableView);
		span.getChildren().add(vbx);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(toolBar);
		borderPane.setCenter(span);
		
		tab = new Tab("Remote");
		tab.setClosable(false);
		tab.setGraphic(new ImageView(new Image("icons/computer.png")));
		tab.setContent(borderPane);
		
		getTabs().addAll(tab);
	}
	

	@Override
	public StackPane getSpan() {
		return span;
	}

	@Override
	public TabPane asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public TableView<TableFileModel> getTableView() {
		// TODO Auto-generated method stub
		return tableView;
	}

	@Override
	public ComboBox<String> getComboBox() {
		// TODO Auto-generated method stub
		return comboBox;
	}

	@Override
	public Button getRefreshButton() {
		// TODO Auto-generated method stub
		return refreshButton;
	}

	@Override
	public MenuItem getDeleteMenuItem() {
		// TODO Auto-generated method stub
		return deleteMenuItem;
	}

	@Override
	public MenuItem getMoveMenuItem() {
		// TODO Auto-generated method stub
		return moveMenuItem;
	}
	
	@Override
	public MenuItem getSyncMenuItem() {
		return syncMenuItem;
	}

	@Override
	public TableColumn<TableFileModel, String> getTypeCol() {
		// TODO Auto-generated method stub
		return typeCol;
	}

	@Override
	public Tab getTab() {
		// TODO Auto-generated method stub
		return tab;
	}

	@Override
	public MenuItem getNewMenuItem() {
		// TODO Auto-generated method stub
		return newMenuItem;
	}

	@Override
	public Button getParentButton() {
		// TODO Auto-generated method stub
		return parentButton;
	}

	@Override
	public MenuItem getDownloadButton() {
		// TODO Auto-generated method stub
		return downMenuItem;
	}

	@Override
	public Button getSyncButton() {
		// TODO Auto-generated method stub
		return syncButton;
	}

}
