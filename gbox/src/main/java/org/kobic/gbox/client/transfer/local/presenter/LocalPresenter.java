package org.kobic.gbox.client.transfer.local.presenter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.kobic.gbox.client.common.CommonsUtil;
import org.kobic.gbox.client.common.Constants;
import org.kobic.gbox.client.component.ShowAlertDialogs;
import org.kobic.gbox.client.component.SplitPaneComponent;
import org.kobic.gbox.client.controller.FTPClientServiceController;
import org.kobic.gbox.client.controller.FileSystemController;
import org.kobic.gbox.client.controller.RapidantServiceController;
import org.kobic.gbox.client.fire.event.FileTransferInitFireEvent;
import org.kobic.gbox.client.fire.event.GboxShutdownEvent;
import org.kobic.gbox.client.fire.event.GboxShutdownEventHandler;
import org.kobic.gbox.client.fire.event.LocalFileListRefreshEvent;
import org.kobic.gbox.client.fire.event.LocalFileListRefreshEventHandler;
import org.kobic.gbox.client.fire.event.ProgressInitializationFireEvent;
import org.kobic.gbox.client.fire.event.TransferUploadCancelEvent;
import org.kobic.gbox.client.fire.event.TransferUploadCancelEventHandler;
import org.kobic.gbox.client.fire.event.TransferringWithRAPIDANTEvent;
import org.kobic.gbox.client.ftp.handler.FTPTransferHandler;
import org.kobic.gbox.client.menu.presenter.MenuBarPresenter;
import org.kobic.gbox.client.model.TableFileModel;
import org.kobic.gbox.client.model.TableTransferModel;
import org.kobic.gbox.client.model.TransferModel;
import org.kobic.gbox.client.model.TreeFileModel;
import org.kobic.gbox.client.monitor.presenter.TransferMonitorPresenter;
import org.kobic.gbox.client.presenter.Presenter;
import org.kobic.gbox.client.rapidant.handler.TransferFutureHandler;
import org.kobic.gbox.client.transfer.remote.presenter.RemotePresenter;
import org.kobic.sso.client.model.Member;

import com.pennychecker.eventbus.HandlerManager;
import com.sun.javafx.scene.control.skin.TableViewSkinBase;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import rapidant.model.client.RapidantClient;
import rapidant.model.client.RapidantClientFactory;
import rapidant.model.client.event.connection.RapidantClientConnectEvent;
import rapidant.model.client.future.RapidantTransferFuture;
import rapidant.model.client.value.RapidantClientTCPConnection;
import rapidant.model.common.value.RapidantAnonymousAuthentication;

public class LocalPresenter implements Presenter {

	final static Logger logger = Logger.getLogger(LocalPresenter.class);

	private boolean deleteProcessResult = false;

	@SuppressWarnings("unused")
	private Stage stage;
	private Display display;
	private HandlerManager eventBus;
	private List<String> store;
	private Map<String, String> map;
	private Map<String, RapidantClient> rapidant;
	private Map<String, FTPClient> ftp;
	private ImageView imgView;
	private String[] name;

	private Member member;

	public static boolean target;

	public static String path;

	private String uuid;

	public String getPath() {
		return path;
	}

	@SuppressWarnings("static-access")
	public void setPath(String path) {
		this.path = path;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public LocalPresenter(Display view, Stage stage, HandlerManager eventBus, Member member) {
		this.display = view;
		this.eventBus = eventBus;
		this.stage = stage;
		this.member = member;
		this.store = new ArrayList<String>();
		this.map = new HashMap<String, String>();
		this.rapidant = new HashMap<String, RapidantClient>();
		this.ftp = new HashMap<String, FTPClient>();
	}

	public interface Display {

		TabPane asWidget();
		
		StackPane getSpan();

		TreeItem<TreeFileModel> getTreeItem();

		TreeView<TreeFileModel> getTreeView();

		TableView<TableFileModel> getTableView();

		ComboBox<String> getComboBox();

		MenuItem getSendMenuItem();

		MenuItem getNewMenuItem();

		MenuItem getDeleteMenuItem();

		TableColumn<TableFileModel, String> getTypeCol();

		Tab getTab();

		Button getParentButton();

		Button getRefreshButton();
	}

	private void setInitTreeViewDataBind() {

		File[] drives = FileSystemController.Util.getInstance().getRootDrive();

		for (File file : drives) {
			TreeFileModel fm = new TreeFileModel();
			fm.setId(CommonsUtil.getInstance().getUID());
			fm.setPid(Constants.ROOT_ID);
			fm.setName(file.getPath());
			fm.setPath(file.getPath());
			fm.setSize(file.getTotalSpace());
			fm.setModified(file.lastModified());
			fm.setDiretory(file.isDirectory());

			TreeItem<TreeFileModel> node;

			if (fm.isDiretory()) {
				node = new TreeItem<TreeFileModel>(fm, new ImageView(new Image("icons/folder.png")));
			} else {
				node = new TreeItem<TreeFileModel>(fm, new ImageView(new Image("icons/page_white.png")));
			}

			display.getTreeItem().getChildren().add(node);
		}
	}

	private void setComboBoxDataBind(String path) {

		if (!store.contains(path) && !path.equals(Constants.ROOT_PATH)) {
			store.add(path);
			display.getComboBox().getItems().add(path);
		}
	}

	@SuppressWarnings("rawtypes")
	private void setComboBoxSelectEvent() {

		display.getComboBox().valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				System.out.println("combochangeEvent");
				setTableViewDataBind(t1);
			}
		});
	}

	private void setRefeshButtonEvent() {

		display.getRefreshButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				setTableViewDataBind(getPath());
			}
		});
	}

	private void setRefereshFireEvent() {

		eventBus.addHandler(LocalFileListRefreshEvent.TYPE, new LocalFileListRefreshEventHandler() {
			@Override
			public void localFileListRefresh(LocalFileListRefreshEvent event) {
				// TODO Auto-generated method stub
				setTableViewDataBind(getPath());
			}
		});
	}

	private void setParentButtonEvent() {

		display.getParentButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				String pPath = new File(getPath()).getParent();

				if (pPath != null) {
//					setTableViewDataBind(pPath);
					display.getComboBox().setValue(pPath);
				} else {
					new ShowAlertDialogs(AlertType.WARNING, null,
							"The current path is the top-level folder of [" + getPath() + "].");
				}
			}
		});
	}

	private void setTableViewDataBind(String path) {

		setComboBoxDataBind(path);
		setPath(path);
		display.getComboBox().setValue(path);

		logger.info("User's current path: [" + path + "]");

		List<TableFileModel> list = FileSystemController.Util.getInstance().getSubDirectory(path);

		int size = display.getTableView().getItems().size();

		if (list != null) {
			if (list.size() != 0) {

				if (size != 0) {
					display.getTableView().getProperties().put(TableViewSkinBase.RECREATE, Boolean.TRUE);
				}

				display.getTableView().getItems().clear();
				display.getTableView().setItems(FXCollections.observableArrayList(list));
				display.getTableView().getSortOrder().add(display.getTypeCol());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void setTreeViewOnClickEvnet() {

		display.getTreeView().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub

				TreeItem<TreeFileModel> selectedItem = (TreeItem<TreeFileModel>) newValue;

				TreeFileModel fm = selectedItem.getValue();

				if (!fm.getId().equals(Constants.ROOT_ID) && fm.isDiretory()) {

//					setTableViewDataBind(fm.getPath());
					display.getComboBox().setValue(fm.getPath());

					if (map.get(fm.getPath()) == null) {

						map.put(fm.getPath(), fm.getPath());

						List<TreeFileModel> list = FileSystemController.Util.getInstance().getSubDirectory(fm.getPath(),
								fm.getId());

						if (list != null) {
							for (TreeFileModel fileModel : list) {

								TreeItem<TreeFileModel> node;

								if (fileModel.isDiretory()) {
									node = new TreeItem<TreeFileModel>(fileModel,
											new ImageView(new Image("icons/folder.png")));
								} else {
									node = new TreeItem<TreeFileModel>(fileModel,
											new ImageView(new Image("icons/page_white.png")));
								}

								display.getTreeView().getSelectionModel().getSelectedItem().getChildren().add(node);
							}
						} else {
							new ShowAlertDialogs(AlertType.WARNING, null, "The file does not exist in the path.");
						}
					}
				}
			}
		});
	}

	private void setTableViewOnClickEvent() {

		display.getTableView().setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

					TableFileModel fm = display.getTableView().getSelectionModel().getSelectedItem();

					if (fm != null && fm.isDiretory()) {
//						setTableViewDataBind(display.getTableView().getSelectionModel().getSelectedItem().getPath());
						display.getComboBox().setValue(fm.getPath());
					}
				}
			}
		});
	}

	private void setTableViewContextMenuEvent() {

		display.getTableView().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.SECONDARY) {
					display.getTableView().getContextMenu().show(display.getTableView(), e.getScreenX(),
							e.getScreenY());
				}
			}
		});
	}

	private void setNewMenuItemEvent() {

		display.getNewMenuItem().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle(Constants.TITLE);
				dialog.setHeaderText("A new folder will be created in the path " + getPath() + ".");
				dialog.setContentText("New folder name: ");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();

				String name = null;

				logger.info("Top path of user's current path: [" + result.isPresent() + "], User's current path: ["
						+ result.get() + "]");

				if (result.isPresent()) {
					name = result.get();
				}

				if (name != null) {
					FileSystemController.Util.getInstance().makeDirectory(getPath(),
							name.trim().replaceAll("\\p{Z}", "_"));
					setTableViewDataBind(getPath());
				}
			}
		});
	}

	private void setDeleteMunuItemEvent() {

		display.getDeleteMenuItem().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				ObservableList<TableFileModel> list = display.getTableView().getSelectionModel().getSelectedItems();

				if (list.size() != 0) {

					List<String> path = new ArrayList<String>();

					for (TableFileModel fm : list) {
						path.add(fm.getPath());
					}

					localDeleteTask(path);

				} else {
					new ShowAlertDialogs(AlertType.WARNING, null, "Please select one or more files.");
				}
			}
		});
	}

	private void localDeleteTask(final List<String> path) {

		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle(Constants.TITLE);
		dialog.setHeaderText(null);
		dialog.setContentText(path.toString() + "\nAre you sure you want to delete " + path.size() + " item(s)?");

		Optional<ButtonType> dialogResult = dialog.showAndWait();

		if (dialogResult.get() == ButtonType.OK) {

			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					//loading indicator add
					display.getSpan().getChildren().get(1).setVisible(true);
					
					Task<Void> sleeper = new Task<Void>() {
						@Override
						protected Void call() throws Exception {

							deleteProcessResult = FileSystemController.Util.getInstance().delete(path);
							return null;
						}
					};

					sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
						@Override
						public void handle(WorkerStateEvent event) {

							display.getSpan().getChildren().get(1).setVisible(false);
							
							String sentence = deleteProcessResult ? " item(s) have been deleted."
									: " item(s) could not be deleted.";

//							setTableViewDataBind(getPath());
							display.getComboBox().setValue(getPath());

							new ShowAlertDialogs(AlertType.INFORMATION, null, path.size() + sentence);
						}
					});

					new ShowAlertDialogs(AlertType.WARNING, null, "Deleting " + path.size() + " item(s) will start.");
					new Thread(sleeper).start();
				}
			});
		}
	}

	private void setDeleteKeyEvent() {

		display.getTableView().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(final KeyEvent event) {

				// TODO Auto-generated method stub

				ObservableList<TableFileModel> list = display.getTableView().getSelectionModel().getSelectedItems();

				if (event.getCode().equals(KeyCode.DELETE)) {

					if (list.size() != 0) {

						List<String> path = new ArrayList<String>();

						for (TableFileModel fm : list) {
							path.add(fm.getPath());
						}

						localDeleteTask(path);

					} else {
						new ShowAlertDialogs(AlertType.WARNING, null, "Please select one or more files.");
					}
				}
			}
		});
	}

	private void setSendMenuItemEvent() {

		display.getSendMenuItem().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				dataUploadWithRAPIDANT();
			}
		});
	}

	private List<String> getSmallSizeFiles(ObservableList<TableFileModel> tmList) {

		List<String> list = new ArrayList<String>();

		for (TableFileModel tm : tmList) {
			if (tm.getSize() <= 104857600) {
				list.add(tm.isDiretory() ? tm.getPath() + "/" : tm.getPath());
			}
		}

		logger.info(list.size() + " : files are below 100MB");
		
		return list;
	}

	private boolean isHanCheck(ObservableList<TableFileModel> tmList) {

		List<TableFileModel> noEnglishFileList = new ArrayList<TableFileModel>();

		for (TableFileModel tm : tmList) {

			if (tm.getName().matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
				noEnglishFileList.add(tm);
			}
		}

		if (noEnglishFileList.size() != 0) {
			return true;

		} else {
			return false;
		}
	}

	private void setUploadTask() {

		imgView.setVisible(false);

		ObservableList<TableFileModel> list = display.getTableView().getSelectionModel().getSelectedItems();

		if (list.size() != 0) {

			List<String> smallFiles = getSmallSizeFiles(list);

			if (!isHanCheck(list)) {

				if (smallFiles != null && !MenuBarPresenter.protocol.equals("FTP")) {

					Alert dialog = new Alert(AlertType.CONFIRMATION);
					dialog.setTitle(Constants.TITLE);
					dialog.setHeaderText(null);
					dialog.setContentText(StringUtils.join(smallFiles, ",")
							+ "Files below 100MB of RAPIDANT Method may not be uploaded normally. Do you want to upload it anyway?");

					Optional<ButtonType> result = dialog.showAndWait();

					if (result.get() == ButtonType.OK) {
						uploadWithMethodTask();
					} else {
						dialog.close();
					}
				} else {
					uploadWithMethodTask();
				}
			} else {
				new ShowAlertDialogs(AlertType.WARNING, null, "Filenames are not in English.");
			}
		} else {
			new ShowAlertDialogs(AlertType.WARNING, null, "Please select one or more files.");
		}
	}

	private void uploadWithRAPIDNATTask(final String path, final String[] name, TableTransferModel tm) {

		setName(name);

		Task<Integer> task = new Task<Integer>() {
			@Override
			protected Integer call() throws Exception {

				RapidantClient client = RapidantClientFactory.newRapidantClient();

				RapidantClientTCPConnection connection = new RapidantClientTCPConnection();
				connection.setHost(Constants.SERVER_ADDRESS);
				connection.setPort(Constants.SERVER_TCP_PORT);
				connection.setFairnessPort(Constants.SERVER_TCP_FAIRNESS_PORT);
				connection.setConnectionTimeout(10000);
				connection.setAuthentication(new RapidantAnonymousAuthentication());
				connection.setUseSecurity(false);
				connection.setSocketBufferSize(1024 * 1024 * 8);
				connection.setDiskBufferSize(1024 * 1024 * 1);
				connection.setNumberOfStream(10);

				RapidantClientConnectEvent connect = client.connect(connection).await();

				rapidant.put(tm.getId(), client);

				logger.info(name + "=>" + connect.isSuccessful());

				RapidantTransferFuture future = RapidantServiceController.Util.getInstance().dataTransferSend(client,
						path, name, MenuBarPresenter.fairness);

				TransferFutureHandler handler = new TransferFutureHandler(eventBus, name, Constants.UPLOAD, tm, member);
				future.setHandler(handler);
				future.await();

				RapidantServiceController.Util.getInstance().disconnection(client);

				return 0;
			}
		};

		eventBus.fireEvent(new ProgressInitializationFireEvent());

		ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.submit(task);
	}

	private void uploadCancelFireEvent() {

		eventBus.addHandler(TransferUploadCancelEvent.TYPE, new TransferUploadCancelEventHandler() {

			@Override
			public void transferCancel(TransferUploadCancelEvent event) {
				// TODO Auto-generated method stub
				for (TransferModel t : event.getTransferModel()) {

					if (t.getType() == Constants.UPLOAD) {

						if (t.getMethod().equals("R")) {

							RapidantClient client = rapidant.get(t.getId());

							if (client != null) {
								client.disconnect();
								logger.info("user [" + client.getId() + "] disconnet");
							}
						} else if (t.getMethod().equals("F")) {

							logger.info(t.getId() + " : get FTP Client");

							FTPClient client = ftp.get(t.getId());

							if (client != null) {
								try {
									
									boolean res = client.abort();
									logger.info("abort to transfer FTP : " + res );
									
								} catch (IOException e) {
									// TODO Auto-generated catch block
									logger.fatal("ftp client is null");
								}
							} else {
								logger.fatal("ftp client is null");
							}

						}
					}
				}
			}
		});
	}

	/**
	 * Drag & Drop 이벤트
	 */
	private void setTableViewDnDEvent() {

		display.getTableView().setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (event.isPrimaryButtonDown()) {

					if (display.getTableView().getSelectionModel().getSelectedIndex() == -1 && event.isDragDetect()) {
						logger.info("Drag Not Event");
					} else {

						Image image = new Image("icons/drag_up.png");
						imgView = new ImageView(image);
						imgView.setFitHeight(100);
						imgView.setFitWidth(100);

						BorderPane pane = (BorderPane) display.getTableView().getScene().getRoot();

						if (!pane.getChildren().contains(imgView)) {
							pane.getChildren().add(imgView);
						}

						imgView.setOpacity(0.5);
						imgView.toFront();
						imgView.setMouseTransparent(true);
						imgView.setVisible(true);
						imgView.relocate((int) (event.getSceneX() - imgView.getBoundsInLocal().getWidth() / 2),
								(int) (event.getSceneY() - imgView.getBoundsInLocal().getHeight() / 2));

						target = false;

						List<TableFileModel> list = display.getTableView().getSelectionModel().getSelectedItems();

						String[] name = new String[list.size()];
						for (int i = 0; i < list.size(); i++) {
							name[i] = list.get(i).isDiretory() ? list.get(i).getName() + "/" : list.get(i).getName();
						}

						Dragboard db = display.getTableView().startDragAndDrop(TransferMode.ANY);
						ClipboardContent content = new ClipboardContent();
						content.putString(Arrays.toString(name));
						db.setContent(content);
						event.consume();

						imgView.setVisible(false);
					}
				} else {
					new ShowAlertDialogs(AlertType.WARNING, null, "Drag & drop using the mouse.");
				}
			}
		});

		display.getTableView().setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {

				Point2D localPoint = display.getTableView().getScene().getRoot()
						.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
				imgView.relocate((int) (localPoint.getX() - imgView.getBoundsInLocal().getWidth() / 2),
						(int) (localPoint.getY() - imgView.getBoundsInLocal().getHeight() / 2));
				event.acceptTransferModes(TransferMode.ANY);
				event.consume();
			}
		});

		display.getTableView().setOnDragExited(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent evnet) {
				// TODO Auto-generated method stub
				imgView.setVisible(false);
			}
		});

		display.getTableView().setOnDragEntered(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				// TODO Auto-generated method stub

				Image image = new Image("icons/drag_down.png");
				imgView = new ImageView(image);
				imgView.setFitHeight(100);
				imgView.setFitWidth(100);

				BorderPane pane = (BorderPane) display.getTableView().getScene().getRoot();

				if (!pane.getChildren().contains(imgView)) {
					pane.getChildren().add(imgView);
				}

				imgView.setOpacity(0.5);
				imgView.toFront();
				imgView.setMouseTransparent(true);
				imgView.setVisible(true);
				imgView.relocate((int) (event.getSceneX() - imgView.getBoundsInLocal().getWidth() / 2),
						(int) (event.getSceneY() - imgView.getBoundsInLocal().getHeight() / 2));
			}
		});

		display.getTableView().setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				// TODO Auto-generated method stub

				imgView.setVisible(false);
				event.setDropCompleted(true);
				RemotePresenter.target = true;
			}
		});

		display.getTableView().setOnDragDone(new EventHandler<DragEvent>() {
			public void handle(DragEvent e) {
				setUploadTask();
			}
		});
	}

	/**
	 * FTP or RAPIDANT Send task
	 */
	private void uploadWithMethodTask() {
		if (target) {
			if (MenuBarPresenter.protocol.equals("FTP")) {
				dataUploadWithFTP();
			} else {

				/**
				 * 여기서 확인하고 넘기기 현재 진행 중인 카운트..
				 */
				eventBus.fireEvent(new TransferringWithRAPIDANTEvent());

				int count = TransferMonitorPresenter.count;

				logger.info(count + " : files transffering in 'R' method ");

				if (count >= 3) {
					Alert dialog = new Alert(AlertType.CONFIRMATION);
					dialog.setTitle(Constants.TITLE);
					dialog.setHeaderText(null);
					dialog.setContentText("Rapidant cannot transmit more than 3 simultaneously.");

					Optional<ButtonType> result = dialog.showAndWait();

					if (result.get() == ButtonType.OK) {
						/**
						 * 뭔가를 해야하나..
						 */
					}
				} else {
					dataUploadWithRAPIDANT();
				}
			}
		} else {
			new ShowAlertDialogs(AlertType.WARNING, null, "Cannot transmit file.");
		}
	}

	private void dataUploadWithFTP() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				ObservableList<TableFileModel> list = display.getTableView().getSelectionModel().getSelectedItems();

				if (list.size() != 0) {

					List<File> files = new ArrayList<File>();
					List<String> names = new ArrayList<String>();

					for (int i = 0; i < list.size(); i++) {
						File file = new File(list.get(i).getPath());
						files.add(file);
						names.add(file.getName());
					}

					Alert dialog = new Alert(AlertType.CONFIRMATION);
					dialog.setTitle(Constants.TITLE);
					dialog.setHeaderText(null);
					dialog.setContentText(Arrays.toString(names.toArray()) + " Are you sure you want to upload?");

					Optional<ButtonType> result = dialog.showAndWait();

					if (result.get() == ButtonType.OK) {

						for (File file : files) {

							Task<Integer> task = new Task<Integer>() {
								@Override
								protected Integer call() throws Exception {

									try {

										uuid = CommonsUtil.getInstance().getUID();

										FTPClient ftpClient = FTPClientServiceController.Util.getInstance()
												.connection();
										FTPTransferHandler handler = new FTPTransferHandler(eventBus, file, ftpClient,
												member, Constants.UPLOAD, uuid);
										handler.start();

										ftp.put(uuid, ftpClient);

										System.out.println(uuid + " :: upload with 'FTP'");

									} catch (Exception e) {
										e.printStackTrace();
									}
									return 0;
								}
							};

							ExecutorService executorService = Executors.newFixedThreadPool(1);
							executorService.submit(task);
						}
					}

				} else {
					new ShowAlertDialogs(AlertType.WARNING, null, "Please select one or more files.");
				}
			}
		});
	}

	private void dataUploadWithRAPIDANT() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				ObservableList<TableFileModel> list = display.getTableView().getSelectionModel().getSelectedItems();

				logger.info("Current rapidant connection progress: [" + MenuBarPresenter.is_connection + "]");

				if (MenuBarPresenter.is_connection) {

					long size = 0L;

					if (list.size() != 0) {

						String[] names = new String[list.size()];

						for (int i = 0; i < list.size(); i++) {
							names[i] = list.get(i).isDiretory() ? list.get(i).getName() + "/" : list.get(i).getName();
						}

						Alert dialog = new Alert(AlertType.CONFIRMATION);
						dialog.setTitle(Constants.TITLE);
						dialog.setHeaderText(null);
						dialog.setContentText(Arrays.toString(names) + " Are you sure you want to upload?");

						Optional<ButtonType> result = dialog.showAndWait();

						if (result.get() == ButtonType.OK) {

							uuid = CommonsUtil.getInstance().getUID();

							// 테이블 정보 추가
							TableTransferModel tm = new TableTransferModel(uuid, Constants.UPLOAD, "R",
									StringUtils.join(names, ","), "Waiting",
									CommonsUtil.getInstance().humanReadableByteCount(size, true), getPath(), -1.0,
									"0 MB", 0L, 0L, 0L);

							eventBus.fireEvent(new FileTransferInitFireEvent(tm));

							/**
							 * 여기서 전송 시작
							 */
							uploadWithRAPIDNATTask(getPath(), names, tm);
						} else {
							dialog.close();
						}

					} else {
						new ShowAlertDialogs(AlertType.WARNING, null, "Please select one or more files.");
					}
				} else {
					new ShowAlertDialogs(AlertType.WARNING, null,
							"The connection is broken. You may proceed with transmission after connection.");
				}
			}
		});
	}
	
	private void setGboxShutdownFireEvent() {
		eventBus.addHandler(GboxShutdownEvent.TYPE, new GboxShutdownEventHandler() {
			
			@Override
			public void terminateFunctionEnable(GboxShutdownEvent event) {
				// TODO Auto-generated method stub

				
				for( Map.Entry<String, RapidantClient> elem : rapidant.entrySet() ){ 
					if(elem.getValue() != null) {
						elem.getValue().disconnect();
					}
				}

				for( Map.Entry<String, FTPClient> elem : ftp.entrySet() ){ 
					if(elem.getValue() != null) {
						try {
							elem.getValue().abort();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		setTreeViewOnClickEvnet();
		setTableViewOnClickEvent();
		setComboBoxSelectEvent();
		setTableViewContextMenuEvent();
		setSendMenuItemEvent();
		setTableViewDnDEvent();
		setNewMenuItemEvent();
		setDeleteMunuItemEvent();
		setDeleteKeyEvent();
		setRefeshButtonEvent();
		setParentButtonEvent();
		setRefereshFireEvent();
		uploadCancelFireEvent();
		setGboxShutdownFireEvent();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		display.getTab().setText(member.getMemberId());
		setInitTreeViewDataBind();
		display.getComboBox().setValue(new File(Constants.ROOT_PATH).getAbsolutePath());
		setTableViewDataBind(new File(Constants.ROOT_PATH).getAbsolutePath());
		bind();
	}

	@Override
	public void go(Object container) {
		// TODO Auto-generated method stub
		SplitPaneComponent pane = (SplitPaneComponent) container;
		pane.getItems().add(display.asWidget());
		init();
	}
}