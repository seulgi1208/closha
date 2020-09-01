package org.kobic.gbox.client.transfer.local.view;

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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import org.kobic.gbox.client.component.SplitPaneComponent;
import org.kobic.gbox.client.model.TableFileModel;
import org.kobic.gbox.client.model.TreeFileModel;
import org.kobic.gbox.client.transfer.local.presenter.LocalPresenter;

public class LocalViewer extends TabPane implements LocalPresenter.Display{
	
	private ComboBox<String> comboBox;
	private Button treeButton;
	private Button parentButton;
	private Button refreshButton;
	
	private TreeItem<TreeFileModel> treeItem;
	private TreeView<TreeFileModel> treeView;

	private ContextMenu contextMenu;
	private MenuItem newMenuItem;
	private MenuItem deleteMenuItem;
	private MenuItem sendMenuItem;
	
	private Tab tab;
	private StackPane span;
	private VBox vbx;
	private ProgressIndicator loading;
	
	private TableView<TableFileModel> tableView;
	
	private TableColumn<TableFileModel, String> nameCol;
	private TableColumn<TableFileModel, String> sizeCol;
	private TableColumn<TableFileModel, String> typeCol;
	private TableColumn<TableFileModel, String> modifyCol;
	
	@SuppressWarnings("unchecked")
	public LocalViewer(){
		
		comboBox = new ComboBox<String>();
		comboBox.setEditable(true);
		comboBox.getStyleClass().add("combo-border");
		comboBox.setMinWidth(300.0);
		comboBox.setPrefWidth(500);
		
		Image treeImg = new Image("icons/application_side_tree.png");
		Image parentImg = new Image("icons/folder_up.png");
		Image refreshImg = new Image("icons/page-refresh-icon.png");

		treeButton = new Button();
		treeButton.setGraphic(new ImageView(treeImg));

		parentButton = new Button();
		parentButton.setGraphic(new ImageView(parentImg));
		
		refreshButton = new Button();
		refreshButton.setGraphic(new ImageView(refreshImg));

		ToolBar toolBar = new ToolBar();
		toolBar.getItems().addAll(treeButton, comboBox, parentButton,
				refreshButton);

		treeItem = new TreeItem<TreeFileModel>();
		treeItem.setGraphic(new ImageView(new Image("icons/local.png")));
		treeItem.setExpanded(true);
		treeItem.setValue(new TreeFileModel());
		
		treeView = new TreeView<TreeFileModel>();
		treeView.setShowRoot(true);
		treeView.setRoot(treeItem);
		treeView.setEditable(false);
		treeView.setFocusTraversable(false);
		treeView.setPrefWidth(180);
		treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);  
		
		tableView = new TableView<TableFileModel>();
		tableView.setEditable(true);
		tableView.setFocusTraversable(true);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tableView.setTableMenuButtonVisible(true);
		
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
        sendMenuItem = new MenuItem("Upload", new ImageView(new Image("icons/arrow_right.png")));
        
        contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(newMenuItem, deleteMenuItem, sendMenuItem);
        
		tableView.getColumns().addAll(nameCol, sizeCol, typeCol, modifyCol);
		tableView.setContextMenu(contextMenu);
		
		span = new StackPane();
		loading = new ProgressIndicator();
		vbx = new VBox(loading);
		vbx.setAlignment(Pos.CENTER);
		vbx.setVisible(false);
		
		span.getChildren().add(tableView);
		span.getChildren().add(vbx);
		
		SplitPaneComponent split = new SplitPaneComponent(Orientation.HORIZONTAL, .3);
		split.getItems().addAll(treeView, span);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(toolBar);
		borderPane.setCenter(split);
		
		tab = new Tab();
		tab.setClosable(false);
		tab.setGraphic(new ImageView(new Image("icons/local.png")));
		tab.setContent(borderPane);
		
		getTabs().addAll(tab);
	}
	
	@Override
	public TabPane asWidget() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public StackPane getSpan() {
		// TODO Auto-generated method stub
		return span;
	}

	@Override
	public TreeView<TreeFileModel> getTreeView() {
		// TODO Auto-generated method stub
		return treeView;
	}

	@Override
	public TreeItem<TreeFileModel> getTreeItem() {
		// TODO Auto-generated method stub
		return treeItem;
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
	public MenuItem getSendMenuItem() {
		// TODO Auto-generated method stub
		return sendMenuItem;
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
	public Button getRefreshButton() {
		// TODO Auto-generated method stub
		return refreshButton;
	}

	@Override
	public MenuItem getDeleteMenuItem() {
		// TODO Auto-generated method stub
		return deleteMenuItem;
	}

}