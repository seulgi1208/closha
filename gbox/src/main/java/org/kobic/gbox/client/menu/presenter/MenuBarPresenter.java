package org.kobic.gbox.client.menu.presenter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.kobic.gbox.client.common.CommonsUtil;
import org.kobic.gbox.client.common.Constants;
import org.kobic.gbox.client.component.ShowAlertDialogs;
import org.kobic.gbox.client.controller.RapidantServiceController;
import org.kobic.gbox.client.fire.event.ProtocolChangeFireEvent;
import org.kobic.gbox.client.fire.event.ProtocolChangeFireEventHandler;
import org.kobic.gbox.client.fire.event.StageReSizeFireEvent;
import org.kobic.gbox.client.fire.event.StorageUsageFireEvent;
import org.kobic.gbox.client.fire.event.StorageUsageFireEventHandler;
import org.kobic.gbox.client.presenter.Presenter;
import org.kobic.gbox.utils.client.GBoxUtilsClient;
import org.kobic.sso.client.model.Member;
import org.kobic.sso.member.service.MemberService;
import org.kobic.sso.member.service.MemberServiceImpl;

import com.pennychecker.eventbus.HandlerManager;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import rapidant.model.client.RapidantClient;

public class MenuBarPresenter implements Presenter {

	final static Logger logger = Logger.getLogger(MenuBarPresenter.class);

	public static int fairness;

	public static String protocol;

	@SuppressWarnings("unused")
	private Stage stage;
	private Display display;
	private Member member;

	private RapidantClient client;

	private HandlerManager eventBus;

	private String message = "";

	public static boolean is_connection;
	private MemberService memberService;

	public MenuBarPresenter(Display view, Stage stage, HandlerManager eventBus, Member member, RapidantClient client) {
		this.display = view;
		this.eventBus = eventBus;
		this.stage = stage;
		this.member = member;
		this.client = client;
	}

	public interface Display {

		VBox asWidget();

		MenuItem getExitMenuItem();

		Button getSizeButton();

		Button getHelpButton();

		Button getRefreshButton();

		MenuItem getAboutMenuItem();

		MenuItem getHelpContactMenuItem();

		ComboBox<String> getSpeedComboBox();

		ComboBox<String> getMethodComboBox();

		ComboBox<String> getProtocoComboBox();

		ProgressBar getProgressBar();

		Label getUsageLabel();

		Label getTotalUserLabel();

		Button getClearButton();
	}

	private void setProtocolComboBoxSelectEvent() {
		display.getProtocoComboBox().getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						// TODO Auto-generated method stub

						if (newValue.equals("FTP")) {
							message = "It can be transmitted using FTP protocol.";
						} else if (newValue.equals("RAPIDANT")) {
							message = "It can be transmitted using RAPIDANT protocol.";
						}

						Alert dialogs = new Alert(AlertType.CONFIRMATION);
						dialogs.setTitle(Constants.TITLE);
						dialogs.setHeaderText(null);
						dialogs.setContentText(message);

						Optional<ButtonType> result = dialogs.showAndWait();

						if (result.get() == ButtonType.OK) {
							if (newValue.equals("FTP")) {
								protocol = "FTP";
							} else {
								protocol = "RAPIDANT";
							}
						}
					}
				});
	}

	private void setSpeedComboBoxSelectEvent() {
		display.getSpeedComboBox().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub

				if (newValue.endsWith("Optimize")) {
					message = "Optimal transmission will be made in consideration of other network traffic.";
				} else if (newValue.endsWith("Top Priority")) {
					message = "Transmission will be made with higher priority than any other network traffic.";
				} else {
					message = "Transmission will be made at a fixed rate of " + newValue + " whenever possible.";
				}

				Alert dialogs = new Alert(AlertType.CONFIRMATION);
				dialogs.setTitle(Constants.TITLE);
				dialogs.setHeaderText(null);
				dialogs.setContentText(message);

				Optional<ButtonType> result = dialogs.showAndWait();

				if (result.get() == ButtonType.OK) {
					if (newValue.endsWith("Optimize")) {
						fairness = Constants.FAIRNESS_DEFAULT;
					} else if (newValue.endsWith("Top Priority")) {
						fairness = Constants.FAIRNESS_WEIGHTED;
					} else {
						fairness = Integer.parseInt(newValue.replace("Mbps", ""));
					}
				}
			}
		});
	}

	private void setMethodComboBoxSelectEvent() {
		display.getMethodComboBox().getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						// TODO Auto-generated method stub

						if (newValue.equals("TCP/IP")) {
							message = "Transmission will be made using the TCP/IP protocol.";
						} else if (newValue.equals("UDP")) {
							message = "Transmission will be made using the UDP protocol.";
						}

						Alert dialogs = new Alert(AlertType.CONFIRMATION);
						dialogs.setTitle(Constants.TITLE);
						dialogs.setHeaderText(null);
						dialogs.setContentText(message);

						Optional<ButtonType> result = dialogs.showAndWait();

						if (result.get() == ButtonType.OK) {
							RapidantServiceController.Util.getInstance().connection(client, newValue);
						}
					}
				});
	}

	private void setProtocolChangeFireEvent() {

		eventBus.addHandler(ProtocolChangeFireEvent.TYPE, new ProtocolChangeFireEventHandler() {

			@Override
			public void protocolChange(ProtocolChangeFireEvent event) {
				// TODO Auto-generated method stub

				display.getProtocoComboBox().getSelectionModel().select(0);
			}
		});

	}

	private void showContactDialog() {
		new ShowAlertDialogs(AlertType.INFORMATION, Constants.TITLE + " High-speed Transmission System",
				Constants.MENUBAR_SHOW_CONTACT_MSG);
	}

	private void showAboutDialog() {

		new ShowAlertDialogs(AlertType.INFORMATION, "GBox High-speed Transmission System",
				"It is a data transmission application provided by KOBIC to transmit a large amount of genomic data.\n"
						+ "Version" + Constants.VERESION + "\n" + "Release Date: " + Constants.RELEASE_DATE);
	}

	private void setHelpContactMenuEvent() {
		display.getHelpContactMenuItem().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				showContactDialog();
			}
		});
	}

	private void setAboutMenuEvent() {
		display.getAboutMenuItem().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				showAboutDialog();
			}
		});
	}

	private void setExitEvent() {
		display.getExitMenuItem().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				RapidantServiceController.Util.getInstance().disconnection(client);
				System.exit(2);
			}
		});
	}

	private void setSizeButtonEvent() {
		display.getSizeButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

				logger.info("User screen size: [" + visualBounds.getWidth() + " x " + visualBounds.getHeight() + "]");

				eventBus.fireEvent(new StageReSizeFireEvent(visualBounds.getWidth(), visualBounds.getHeight()));
			}
		});

	}

	private void setClearButtonEvent() {
		display.getClearButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				GBoxUtilsClient gBoxUtilsClient = new GBoxUtilsClient();
				gBoxUtilsClient.clearConnect();
			}
		});

	}

	private void setHelpButtonEvent() {
		display.getHelpButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				showContactDialog();
			}
		});
	}

	private void totalUserCount() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				GBoxUtilsClient gBoxUtilsClient = new GBoxUtilsClient();
				int total = gBoxUtilsClient.getConnectCount();
				gBoxUtilsClient.close();
				
				display.getTotalUserLabel().setText("Total Connect: " + total);
				
				if (total >= 50) {
					display.getTotalUserLabel().setGraphic(new ImageView("icons/bullet_red.png"));
				} else if (total >= 10 && total < 50) {
					display.getTotalUserLabel().setGraphic(new ImageView("icons/left_close.png"));
				} else if (total < 10) {
					display.getTotalUserLabel().setGraphic(new ImageView("icons/bullet_green.png"));
				}
			}
		});
	}

	private void setshowTerminateButton() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				memberService = new MemberServiceImpl();

				if (memberService.isAdmin(member.getMemberNo())) {

					display.getClearButton().setVisible(true);

				} else {
					display.getClearButton().setVisible(false);
				}
			}
		});
	}

	private void usageStoraCalculation() {

		Thread thread = new Thread() {
			@Override
			public void run() {

				String root = Constants.RAPIDANT_PATH;
				String path = root + "/" + member.getMemberId();

				logger.info("Cloud user path request: [" + path + "]");

				long size = 0;

//				if(client.isAlive()){
//					size = Long.parseLong(client.getSize(path));					
//					size = size * 1024;
//				}

				Long total = 1099511627776L;
				String usage = CommonsUtil.getInstance().humanReadableByteCount(size, true);

				double prog = ((1.0 * (100 - ((total - size) / (1024 * 1024 * 1024)))) / 100);

				DecimalFormat format = new DecimalFormat("#.#");
				format.setRoundingMode(RoundingMode.FLOOR);

				logger.info("User usage request source data size: [" + size + "] ==> " + usage + "==>"
						+ Double.parseDouble(format.format(prog)));

				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						display.getUsageLabel().setText("Usage: [" + usage + "]");

						double progress_ratio = Double.parseDouble(format.format(prog)) < 0 ? 0.0
								: Double.parseDouble(format.format(prog));

						display.getProgressBar().setProgress(progress_ratio);
						display.getProgressBar().setTooltip(new Tooltip("User cloud storage usage: [" + usage + "]"));
					}
				});
			}
		};

		thread.setDaemon(true);
		thread.start();
	}

	private void setUsageRefreshButtonEvent() {
		display.getRefreshButton().setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				usageStoraCalculation();
			}
		});
	}

	private void setStorageUsageFireEvent() {
		eventBus.addHandler(StorageUsageFireEvent.TYPE, new StorageUsageFireEventHandler() {

			@Override
			public void storageUsageRefresh(StorageUsageFireEvent event) {
				// TODO Auto-generated method stub
				usageStoraCalculation();
			}
		});
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		setExitEvent();
		setAboutMenuEvent();
		setHelpContactMenuEvent();
		setSizeButtonEvent();
		setHelpButtonEvent();
		setSpeedComboBoxSelectEvent();
		setMethodComboBoxSelectEvent();
		setProtocolComboBoxSelectEvent();
		setUsageRefreshButtonEvent();
		setStorageUsageFireEvent();
		setProtocolChangeFireEvent();
		setClearButtonEvent();
		setshowTerminateButton();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

		usageStoraCalculation();
		totalUserCount();

		fairness = Constants.FAIRNESS_WEIGHTED;
		protocol = "RAPIDANT";
		is_connection = true;

		bind();
	}

	@Override
	public void go(Object container) {
		// TODO Auto-generated method stub
		BorderPane pane = (BorderPane) container;
		pane.setTop(display.asWidget());
		init();
	}
}
