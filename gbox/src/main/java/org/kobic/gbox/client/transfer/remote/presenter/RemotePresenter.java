package org.kobic.gbox.client.transfer.remote.presenter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.kobic.gbox.client.common.CommonsUtil;
import org.kobic.gbox.client.common.Constants;
import org.kobic.gbox.client.component.ShowAlertDialogs;
import org.kobic.gbox.client.component.SplitPaneComponent;
import org.kobic.gbox.client.controller.RapidantServiceController;
import org.kobic.gbox.client.fire.event.FileTransferInitFireEvent;
import org.kobic.gbox.client.fire.event.GboxShutdownEvent;
import org.kobic.gbox.client.fire.event.GboxShutdownEventHandler;
import org.kobic.gbox.client.fire.event.ProgressInitializationFireEvent;
import org.kobic.gbox.client.fire.event.ProtocolChangeFireEvent;
import org.kobic.gbox.client.fire.event.RenameFireEvent;
import org.kobic.gbox.client.fire.event.RenameFireEventHandler;
import org.kobic.gbox.client.fire.event.RemoteFileListRefreshEvent;
import org.kobic.gbox.client.fire.event.RemoteFileListRefreshEventHandler;
import org.kobic.gbox.client.fire.event.StorageUsageFireEvent;
import org.kobic.gbox.client.fire.event.TransferUploadCancelEvent;
import org.kobic.gbox.client.fire.event.TransferUploadCancelEventHandler;
import org.kobic.gbox.client.fire.event.TransferringWithRAPIDANTEvent;
import org.kobic.gbox.client.menu.presenter.MenuBarPresenter;
import org.kobic.gbox.client.model.TableFileModel;
import org.kobic.gbox.client.model.TableTransferModel;
import org.kobic.gbox.client.model.TransferModel;
import org.kobic.gbox.client.monitor.presenter.TransferMonitorPresenter;
import org.kobic.gbox.client.presenter.Presenter;
import org.kobic.gbox.client.rapidant.handler.TransferFutureHandler;
import org.kobic.gbox.client.transfer.local.presenter.LocalPresenter;
import org.kobic.gbox.client.transfer.remote.component.RemoteMoveDialog;
import org.kobic.gbox.utils.client.GBoxUtilsClient;
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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rapidant.model.client.RapidantClient;
import rapidant.model.client.RapidantClientFactory;
import rapidant.model.client.event.connection.RapidantClientConnectEvent;
import rapidant.model.client.future.RapidantTransferFuture;
import rapidant.model.client.value.RapidantClientTCPConnection;
import rapidant.model.common.value.RapidantAnonymousAuthentication;

public class RemotePresenter implements Presenter {

	final static Logger logger = Logger.getLogger(MenuBarPresenter.class);

	public static String path;
	private String root;
	private String[] name;

	private String uuid;

	private Map<String, RapidantClient> rapidant;

	private boolean deleteProcessResult = false;
	public static boolean target;

	private boolean moveProcessResult = false;

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

	@SuppressWarnings("unused")
	private Stage stage;
	private Member member;
	private Display display;
	private HandlerManager eventBus;
	private List<String> store;
	private RapidantClient client;
	private ImageView imgView;

	public RemotePresenter(Display view, Stage stage, HandlerManager eventBus, Member member, RapidantClient client) {

		this.display = view;
		this.eventBus = eventBus;
		this.stage = stage;
		this.member = member;
		this.store = new ArrayList<String>();
		this.rapidant = new HashMap<String, RapidantClient>();
		this.client = client;
	}

	public interface Display {

		TabPane asWidget();
		
		
		StackPane getSpan();

		TableView<TableFileModel> getTableView();

		ComboBox<String> getComboBox();

		Button getSyncButton();

		Button getRefreshButton();

		Button getParentButton();

		MenuItem getDownloadButton();

		MenuItem getDeleteMenuItem();

		MenuItem getMoveMenuItem();

		MenuItem getNewMenuItem();

		MenuItem getSyncMenuItem();

		TableColumn<TableFileModel, String> getTypeCol();

		Tab getTab();
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		setTableViewOnClickEvent();
		setComboBoxSelectEvent();
		setRefreshButtonEvent();
		setRefereshFireEvent();
		setDeleteMunuItemEvent();
		setDeleteKeyEvent();
		setTableViewDnDEvent();
		setNewMenuItemEvent();
		setMoveMenuItemEvent();
		setParentButtonEvent();
		setDownMenuItemEvent();
		getSyncMenuItemEvent();
		setSyncButtonEvent();
		setRenameFireEvent();
		setDownloadCancelFireEvent();
		setGboxShutdownFireEvent();
	}

	private void setTableViewDnDEvent() {

		display.getTableView().setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (event.isPrimaryButtonDown()) {

					if (display.getTableView().getSelectionModel().getSelectedIndex() == -1 && event.isDragDetect()) {

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
			@Override
			public void handle(final DragEvent event) {
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
			public void handle(DragEvent event) {
				// TODO Auto-generated method stub
				imgView.setVisible(false);
			}
		});

		display.getTableView().setOnDragEntered(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				// TODO Auto-generated method stub

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
			}
		});

		display.getTableView().setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				// TODO Auto-generated method stub
				imgView.setVisible(false);
				event.setDropCompleted(true);
				LocalPresenter.target = true;
			}
		});

		display.getTableView().setOnDragDone(new EventHandler<DragEvent>() {
			public void handle(DragEvent e) {

				imgView.setVisible(false);

				if (target) {

					if (MenuBarPresenter.protocol.equals("FTP")) {

						Alert dialog = new Alert(AlertType.CONFIRMATION);
						dialog.setTitle(Constants.TITLE);
						dialog.setHeaderText(null);
						dialog.setContentText(
								"The FTP method does not support large data download. Do you want to start downloading using RAPIDANT?");

						Optional<ButtonType> result = dialog.showAndWait();

						if (result.get() == ButtonType.OK) {
							eventBus.fireEvent(new ProtocolChangeFireEvent());
						}

					} else {
						downloadWithRAPIDANT();
					}

				} else {
					new ShowAlertDialogs(AlertType.WARNING, null, "The file could not be transferred.");
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

				logger.info("Top path to cloud current path: [" + result.isPresent()
						+ "], Current path to cloud users: [" + result.get() + "]");

				String name = null;

				if (result.isPresent()) {
					name = result.get();
				}

				logger.info("Create a new folder in this path [ /" + name + " ]");

				if (name != null) {

					String makePath = getPath() + "/" + name.trim().replaceAll("\\p{Z}", "_");

					RapidantServiceController.Util.getInstance().makeDir(client, makePath);

					logger.info("Created folder's Cloud user path request: [" + makePath + "]");
					logger.info("Created folder's Hue path request: ["
							+ makePath.replace(Constants.RAPIDANT_PATH, Constants.HDFS_ROOT_PATH) + "]");

					// 여기에 HDFS 폴더 존재하는지 확인하고 생성하기
					GBoxUtilsClient gBoxUtilsClient = new GBoxUtilsClient();

					if (!gBoxUtilsClient.isHDFSExist(makePath)) {
						logger.info("gboxUtilsClient is Alive => " + gBoxUtilsClient.isAlive());
						logger.info("Making folder is successful: " + gBoxUtilsClient
								.makeFolder(makePath.replace(Constants.RAPIDANT_PATH, Constants.HDFS_ROOT_PATH)));
					}

					gBoxUtilsClient.close();

					setTableViewDataBind(getPath());
				}
			}
		});
	}

	private void setMoveMenuItemEvent() {
		display.getMoveMenuItem().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				// Create the custom dialog
				String remoteRoot = root + "/" + member.getMemberId();
				new RemoteMoveDialog(path, client, display, eventBus, remoteRoot);
			}
		});
	}

	private void getSyncMenuItemEvent() {
		display.getSyncMenuItem().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				Alert closeDialogs = new Alert(AlertType.WARNING);
				closeDialogs.setTitle(Constants.TITLE);
				closeDialogs.setHeaderText(null);
				closeDialogs.setContentText(Constants.TITLE
						+ " All data in the current remote path is synchronized.\nPlease use data after synchronization.");
				//loading indicator show
				display.getSpan().getChildren().get(1).setVisible(true);
				
				try {
					Optional<ButtonType> closeResult = closeDialogs.showAndWait();
					if (closeResult.get() == ButtonType.OK) {
						GBoxUtilsClient gBoxUtilsClient = new GBoxUtilsClient();
						gBoxUtilsClient.synchronize(getPath());
						gBoxUtilsClient.close();
						
						//loading indicator show
						display.getSpan().getChildren().get(1).setVisible(false);
						
					}
				} catch (Exception e) {
					// TODO: handle exception
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

					rapidantFileDeleteTask(path);

				} else {
					new ShowAlertDialogs(AlertType.WARNING, null, "Please select one or more files.");
				}
			}
		});
	}

	private void setDeleteKeyEvent() {
		display.getTableView().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				System.out.println(event.getText());
				// TODO Auto-generated method stub
				ObservableList<TableFileModel> list = display.getTableView().getSelectionModel().getSelectedItems();

				if (event.getCode().equals(KeyCode.DELETE)) {

					if (list.size() != 0) {

						List<String> path = new ArrayList<String>();

						for (TableFileModel fm : list) {
							path.add(fm.getPath());
						}

						rapidantFileDeleteTask(path);

					} else {
						new ShowAlertDialogs(AlertType.WARNING, null, "Please select one or more files.");
					}
				}
			}
		});
	}

	private void setDownMenuItemEvent() {

		display.getDownloadButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				downloadWithRAPIDANT();
			}
		});
	}

	private void downloadWithRAPIDANT() {

		/**
		 * 여기서 확인하고 넘기기 현재 진행 중인 카운트..
		 */
		
		eventBus.fireEvent(new TransferringWithRAPIDANTEvent());
		logger.info("Checking Rapidant transmit File count");
		int count = TransferMonitorPresenter.count;

		if (count > 3) {
			Alert dialog = new Alert(AlertType.CONFIRMATION);
			dialog.setTitle(Constants.TITLE);
			dialog.setHeaderText(null);
			dialog.setContentText("Rapidant cannot transmit more than 3 simultaneously.");

			Optional<ButtonType> result = dialog.showAndWait();

			if (result.get() == ButtonType.OK) {
				/**
				 * 뭔가를 해야하나..
				 */
				logger.info("transferring  3 files simultaneously in Rapidant");
			}
		} else {
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
					dialog.setContentText(Arrays.toString(names) + " Are you sure you want to Download?");

					Optional<ButtonType> result = dialog.showAndWait();

					if (result.get() == ButtonType.OK) {

						uuid = CommonsUtil.getInstance().getUID();

						TableTransferModel tm = new TableTransferModel(uuid, Constants.DOWNLOAD, "R",
								StringUtils.join(names, ","), "Waiting",
								CommonsUtil.getInstance().humanReadableByteCount(size, true), getPath(), -1.0, " 0Mbps",
								0L, 0L, 0L);

						eventBus.fireEvent(new FileTransferInitFireEvent(tm));

						downloadWithRAPIDANTTask(getPath(), names, tm);
					}

				} else {
					new ShowAlertDialogs(AlertType.WARNING, null, "Please select one or more files.");
				}
			} else {
				new ShowAlertDialogs(AlertType.WARNING, null,
						"The connection is broken. You may proceed with transmission after connection.");
			}
		}
	}

	private void downloadWithRAPIDANTTask(final String path, final String[] name, final TableTransferModel tm) {

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

				logger.info(name + "=>" + connect.isSuccessful());

				RapidantTransferFuture future = RapidantServiceController.Util.getInstance().dataTransferReceive(client,
						path, name, MenuBarPresenter.fairness);

				TransferFutureHandler handler = new TransferFutureHandler(eventBus, name, Constants.DOWNLOAD, tm,
						member);
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

	private void rapidantFileDeleteTask(final List<String> path) {

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
					
					//loading indicator show
					display.getSpan().getChildren().get(1).setVisible(true);

					Task<Void> sleeper = new Task<Void>() {
						@Override
						protected Void call() throws Exception {
							
							deleteProcessResult = RapidantServiceController.Util.getInstance().removes(client, eventBus,path);

							if (deleteProcessResult) {

								/**
								 * 삭제 부분 코드 리팩토링 요망..
								 */

								List<String> t = new ArrayList<String>();
								for (String p : path) {
									t.add(p.replace(Constants.RAPIDANT_PATH, Constants.HDFS_ROOT_PATH));
								}
								GBoxUtilsClient gBoxUtilsClient = new GBoxUtilsClient();
								gBoxUtilsClient.remove(t);
								gBoxUtilsClient.close();
							}
							
							return null;
						}
					};

					sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
						@Override
						public void handle(WorkerStateEvent event) {
							
							//loading indicator remove
							display.getSpan().getChildren().get(1).setVisible(false);

							String sentence = deleteProcessResult ? "item(s) have been deleted."
									: " item(s) could not be deleted.";

							setTableViewDataBind(getPath());
							eventBus.fireEvent(new StorageUsageFireEvent());
							new ShowAlertDialogs(AlertType.INFORMATION, null, path.size() + sentence);
						}
					});

					new ShowAlertDialogs(AlertType.WARNING, null, "Deleting " + path.size() + " item(s) will start.");

					new Thread(sleeper).start();
				}
			});
		}
	}

	private void setSyncButtonEvent() {
		display.getSyncButton().setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub

				Alert closeDialogs = new Alert(AlertType.WARNING);
				closeDialogs.setTitle(Constants.TITLE);
				closeDialogs.setHeaderText(null);
				closeDialogs.setContentText(Constants.TITLE
						+ " All data in the current remote path is synchronized.\nPlease use data after synchronization.");
				
				//loading indicator show
				display.getSpan().getChildren().get(1).setVisible(true);

				try {
					Optional<ButtonType> closeResult = closeDialogs.showAndWait();
	
					if (closeResult.get() == ButtonType.OK) {
						
						GBoxUtilsClient gBoxUtilsClient = new GBoxUtilsClient();
						gBoxUtilsClient.synchronize(getPath());
						gBoxUtilsClient.close();
						//loading indicator remove
						display.getSpan().getChildren().get(1).setVisible(false);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	private void setRefreshButtonEvent() {
		display.getRefreshButton().setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
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
				setTableViewDataBind(new File(getPath()).getParent().replaceAll("\\\\", "/"));
			}
		});
	}

	private void setRefereshFireEvent() {
		eventBus.addHandler(RemoteFileListRefreshEvent.TYPE, new RemoteFileListRefreshEventHandler() {
			@Override
			public void remoteFileListRefresh(RemoteFileListRefreshEvent event) {
				// TODO Auto-generated method stub
				setTableViewDataBind(getPath());
			}
		});
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
				setTableViewDataBind(t1);
			}
		});
	}

	private void setTableViewDataBind(String path) {

		logger.info("Cloud user path request: [" + path + "], Top acceptable path: [" + root + "]");

		if (path.equals(root)) {
			new ShowAlertDialogs(AlertType.WARNING, null, "You are not authorized to use it.");
		} else {

			display.getComboBox().setValue(path);

			setPath(path);

			setComboBoxDataBind(path);

			List<TableFileModel> list = RapidantServiceController.Util.getInstance().getRemoteFileList(client, path);

			boolean res = false;

			try {

				GBoxUtilsClient gBoxUtilsClient = new GBoxUtilsClient();

				for (TableFileModel t : list) {
					

					/**
					 * 파일 각각이 체크를 하지 말고, 리스트로 넘겨 받아오기 로딩 표시하기.
					 */

					res = gBoxUtilsClient
							.isHDFSExist(t.getPath().replace(Constants.RAPIDANT_PATH, Constants.HDFS_ROOT_PATH));

					//System.out.println(t.isDiretory() ? "[D]" : "[F] " + t.getName() + ":" + res);

					t.setStatus(res ? "icons/bullet_green.png" : "icons/bullet_red.png");
				}

				gBoxUtilsClient.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

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
		display.getSpan().getChildren().get(1).setVisible(false);
	}

	private void setTableViewOnClickEvent() {

		display.getTableView().setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

					TableFileModel fm = display.getTableView().getSelectionModel().getSelectedItem();

					if (fm != null && fm.isDiretory()) {
						setTableViewDataBind(display.getTableView().getSelectionModel().getSelectedItem().getPath());
					}
				}
			}
		});
	}

	private void rapidantFileRenameTask(String target, final Map<String, String> path) {

		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle(Constants.TITLE);
		dialog.setHeaderText(null);
		dialog.setContentText(target + "?");

		Optional<ButtonType> dialogResult = dialog.showAndWait();

		if (dialogResult.get() == ButtonType.OK) {

			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					//loading indicator remove
					display.getSpan().getChildren().get(1).setVisible(true);

					Task<Void> sleeper = new Task<Void>() {
						@Override
						protected Void call() throws Exception {

							moveProcessResult = RapidantServiceController.Util.getInstance().rename(client, eventBus,
									path);

							logger.info(path + " move result: " + moveProcessResult);

							/**
							 * RapidantServiceController rename 이 비동기이므로 결과 HDFS 삭제 역시 병렬로 진행하도록 변경..
							 */

							GBoxUtilsClient gBoxUtilsClient = new GBoxUtilsClient();

							for (Map.Entry<String, String> elem : path.entrySet()) {
								/**
								 * key = source value = target
								 */
								logger.info("Moving file's Cloud user path current: [" + elem.getKey() + "]");
								logger.info("Moving file's Cloud user path request: [" + elem.getValue() + "]");

								logger.info("Moving file's Hue path current: ["
										+ elem.getKey().replace(Constants.RAPIDANT_PATH, Constants.HDFS_ROOT_PATH)
										+ "]");
								logger.info("Moving file's Hue path request: ["
										+ elem.getValue().replace(Constants.RAPIDANT_PATH, Constants.HDFS_ROOT_PATH)
										+ "]");

								gBoxUtilsClient.reName(
										elem.getKey().replace(Constants.RAPIDANT_PATH, Constants.HDFS_ROOT_PATH),
										elem.getValue().replace(Constants.RAPIDANT_PATH, Constants.HDFS_ROOT_PATH));
							}

							gBoxUtilsClient.close();

							return null;
						}
					};

					sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
						@Override
						public void handle(WorkerStateEvent event) {

							//loading indicator remove
							display.getSpan().getChildren().get(1).setVisible(false);
							
							String sentence = moveProcessResult ? "item(s) have been moved."
									: " item(s) could not be moved.";

							setTableViewDataBind(getPath());
							eventBus.fireEvent(new StorageUsageFireEvent());
							new ShowAlertDialogs(AlertType.INFORMATION, null, path.size() + sentence);
						}
					});

					new ShowAlertDialogs(AlertType.WARNING, null, "Move " + path.size() + " item(s) will start.");

					new Thread(sleeper).start();
				}
			});
		}
	}

	private void setRenameFireEvent() {
		eventBus.addHandler(RenameFireEvent.TYPE, new RenameFireEventHandler() {
			@Override
			public void rename(RenameFireEvent event) {
				// TODO Auto-generated method stub
				rapidantFileRenameTask(event.getTarget(), event.getPath());
			}
		});
	}

	private void setDownloadCancelFireEvent() {

		eventBus.addHandler(TransferUploadCancelEvent.TYPE, new TransferUploadCancelEventHandler() {

			@Override
			public void transferCancel(TransferUploadCancelEvent event) {
				// TODO Auto-generated method stub
				for (TransferModel t : event.getTransferModel()) {

					if (t.getType() == Constants.DOWNLOAD) {
						if (t.getMethod().equals("R")) {

							RapidantClient client = rapidant.get(t.getId());

							if (client != null) {
								client.disconnect();
								logger.info(client.getId() + " : Cancel to Download");
							}
						}
					}
				}
			}
		});
	}

	private void setGboxShutdownFireEvent() {
		eventBus.addHandler(GboxShutdownEvent.TYPE, new GboxShutdownEventHandler() {

			@Override
			public void terminateFunctionEnable(GboxShutdownEvent event) {
				// TODO Auto-generated method stub

				for (Map.Entry<String, RapidantClient> elem : rapidant.entrySet()) {
					if (elem.getValue() != null) {
						elem.getValue().disconnect();
						logger.info(elem.getValue() + " : disconnect");
					}
				}
			}
		});
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

		root = Constants.RAPIDANT_PATH;
		path = root + "/" + member.getMemberId();
		display.getComboBox().setValue(path);

		setTableViewDataBind(path);

		bind();
	}

	@Override
	public void go(Object container) {
		// TODO Auto-generated method stub

		SplitPaneComponent pane = (SplitPaneComponent) container;
		pane.getItems().addAll(display.asWidget());
		init();
	}
}