package org.kobic.gbox.client.transfer.remote.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.kobic.gbox.client.common.Constants;
import org.kobic.gbox.client.component.ShowAlertDialogs;
import org.kobic.gbox.client.controller.RapidantServiceController;
import org.kobic.gbox.client.fire.event.RenameFireEvent;
import org.kobic.gbox.client.menu.presenter.MenuBarPresenter;
import org.kobic.gbox.client.model.TableFileModel;
import org.kobic.gbox.client.model.TreeFileModel;
import org.kobic.gbox.client.transfer.remote.presenter.RemotePresenter.Display;

import com.pennychecker.eventbus.HandlerManager;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Dialog;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import rapidant.model.client.RapidantClient;

public class RemoteMoveDialog extends Dialog<ButtonType> {

	private TreeView<TreeFileModel> treeView;
	private CheckBoxTreeItem<TreeFileModel> rootTreeItem;
	private BorderPane diaPane;
	private Map<String, String> map;
	private String path;

	private RapidantClient client;
	private Display display;
	private HandlerManager eventBus;
	private String remoteRoot;
	private String selecteItem;
	final static Logger logger = Logger.getLogger(MenuBarPresenter.class);

	public RemoteMoveDialog(String path, RapidantClient client, Display display, HandlerManager eventBus,
			String remoteRoot) {

		this.setTitle(Constants.TITLE);
		this.setHeaderText("Please check the folder path to move.");
		this.setResizable(true);

		map = new HashMap<String, String>();
		this.client = client;
		this.path = path;
		this.display = display;
		this.eventBus = eventBus;
		this.remoteRoot = remoteRoot;

		Pane pane = this.getDialogPane();
		pane.setMinWidth(700);
		pane.setMinHeight(160);

		rootTreeItem = new CheckBoxTreeItem<TreeFileModel>();
		rootTreeItem.setGraphic(new ImageView(new Image("icons/local.png")));
		rootTreeItem.setExpanded(true);
		rootTreeItem.setIndependent(true);
		rootTreeItem.setValue(new TreeFileModel());
		rootTreeItem.selectedProperty().addListener((obs, oldVal, newVal) -> {
			if (rootTreeItem.isSelected()) {
				unChecked(treeView.getRoot().getChildren(), rootTreeItem.getValue().getPath());
				selecteItem = rootTreeItem.getValue().getPath();
			} else {
				unChecked(treeView.getRoot().getChildren(), rootTreeItem.getValue().getPath());
				selecteItem = null;
			}

		});
		rootTreeItem.getValue().setPath(remoteRoot);

		treeView = new TreeView<TreeFileModel>();
		treeView.setShowRoot(true);
		treeView.setRoot(rootTreeItem);
		treeView.setEditable(false);
		treeView.setFocusTraversable(false);
		treeView.setPrefWidth(180);
		treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		treeView.setCellFactory(CheckBoxTreeCell.forTreeView());

		diaPane = new BorderPane();
		diaPane.setCenter(treeView);
		this.getDialogPane().setContent(diaPane);
		this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		setRemoteTreeDataBind(this);
		setMoveTreeViewOnClickEvnet(this);

		Optional<ButtonType> result = this.showAndWait();

		if (result.get() == ButtonType.OK) {

			setPressButtonOKEvent(result);

		} else {

			this.close();
		}

	}

	private void setRemoteTreeDataBind(RemoteMoveDialog dlg) {

		List<TreeFileModel> list = RapidantServiceController.Util.getInstance().getRemoteDirList(client, remoteRoot);

		if (list != null) {
			for (TreeFileModel fileModel : list) {

				CheckBoxTreeItem<TreeFileModel> node;
				if (fileModel.isDiretory()) {
					node = new CheckBoxTreeItem<TreeFileModel>(fileModel, new ImageView(new Image("icons/folder.png")));
					node.setIndependent(true);
					node.selectedProperty().addListener((obs, oldVal, newVal) -> {
						rootTreeItem.setSelected(false);
						if (node.isSelected()) {
							unChecked(treeView.getRoot().getChildren(), node.getValue().getPath());
							selecteItem = node.getValue().getPath();
						} else {
							unChecked(treeView.getRoot().getChildren(), node.getValue().getPath());
							selecteItem = null;
						}

					});
					dlg.getTreeView().getRoot().getChildren().add(node);
				}
			}
		} else {
			new ShowAlertDialogs(AlertType.WARNING, null, "The file does not exist in the path.");
		}
	}

	private void setMoveTreeViewOnClickEvnet(RemoteMoveDialog dlg) {

		dlg.getTreeView().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub

				CheckBoxTreeItem<TreeFileModel> selectedItem = (CheckBoxTreeItem<TreeFileModel>) newValue;

				TreeFileModel fm = selectedItem.getValue();

				if (!fm.getPath().equals(remoteRoot) && fm.isDiretory()) {
					if (map.get(fm.getPath()) == null) {
						map.put(fm.getPath(), fm.getPath());

						List<TreeFileModel> list = RapidantServiceController.Util.getInstance().getRemoteDirList(client,
								selectedItem.getValue().getPath());

						if (list != null) {
							for (TreeFileModel fileModel : list) {

								CheckBoxTreeItem<TreeFileModel> node;

								if (fileModel.isDiretory()) {
									node = new CheckBoxTreeItem<TreeFileModel>(fileModel,
											new ImageView(new Image("icons/folder.png")));
									node.setIndependent(true);
									node.selectedProperty().addListener((obs, oldVal, newVal) -> {
										rootTreeItem.setSelected(false);
										if (node.isSelected()) {
											unChecked(treeView.getRoot().getChildren(), node.getValue().getPath());
											selecteItem = node.getValue().getPath();
										} else {
											unChecked(treeView.getRoot().getChildren(), node.getValue().getPath());
											selecteItem = null;
										}

									});
									dlg.getTreeView().getSelectionModel().getSelectedItem().getChildren().add(node);
								}
							}
						} else {
							new ShowAlertDialogs(AlertType.WARNING, null, "The file does not exist in the path.");
						}
					}
				}
			}
		});
	}

	private void setPressButtonOKEvent(Optional<ButtonType> result) {

		if (selecteItem != null) {
			logger.info("Top path to cloud current path: [" + result.isPresent() + "], Current path to cloud users: ["
					+ selecteItem + "]");

			String target = null;

			if (result.isPresent()) {
				target = selecteItem;
			}

			logger.info("Create a new folder in this path [ /" + target + " ]");

			if (target != null) {

				List<TableFileModel> list = display.getTableView().getSelectionModel().getSelectedItems();
				String name = null;

				Map<String, String> path = new HashMap<String, String>();

				for (int i = 0; i < list.size(); i++) {
					name = list.get(i).isDiretory() ? list.get(i).getName() + "/" : list.get(i).getName();
					name = name.trim().replaceAll("\\p{Z}", "_");

					path.put(getPath() + "/" + name, target + "/" + name);
				}

				eventBus.fireEvent(new RenameFireEvent(target, path));
			}
		} else {
			new ShowAlertDialogs(AlertType.WARNING, null, "Please check the directory.");
		}
	}

	private void unChecked(ObservableList<TreeItem<TreeFileModel>> t, String path) {

		for (TreeItem<TreeFileModel> item : t) {

			if (item instanceof CheckBoxTreeItem) {

				if (!item.getValue().getPath().equals(path)) {

					if (item.getChildren().size() != 0) {
						unChecked(item.getChildren(), path);
					}

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							((CheckBoxTreeItem<TreeFileModel>) item).setSelected(false);
						}
					});

				} else if (item.getValue().getPath().equals(path)) {
					if (item.getChildren().size() != 0) {
						unChecked(item.getChildren(), path);
					}
				}
			}
		}
	}

	public TreeView<TreeFileModel> getTreeView() {
		return treeView;
	}

	public void setTreeView(TreeView<TreeFileModel> treeView) {
		this.treeView = treeView;
	}

	public CheckBoxTreeItem<TreeFileModel> getRootTreeItem() {
		return rootTreeItem;
	}

	public void setRootTreeItem(CheckBoxTreeItem<TreeFileModel> rootTreeItem) {
		this.rootTreeItem = rootTreeItem;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
