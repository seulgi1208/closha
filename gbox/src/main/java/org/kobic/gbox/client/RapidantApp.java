package org.kobic.gbox.client;

import java.util.Optional;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.kobic.gbox.client.common.Constants;
import org.kobic.gbox.client.controller.RapidantServiceController;
import org.kobic.gbox.client.fire.event.GboxShutdownEvent;
import org.kobic.gbox.client.fire.event.StageReSizeFireEvent;
import org.kobic.gbox.client.fire.event.StageReSizeFireEventHandler;
import org.kobic.gbox.utils.client.GBoxUtilsClient;

import com.pennychecker.eventbus.HandlerManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import rapidant.model.client.RapidantClient;
import rapidant.model.client.RapidantClientFactory;

public class RapidantApp extends Application {

	public static String IP;
	public static int PORT;

	private String uuid;

	private RapidantClient client;

	private GBoxUtilsClient gBoxUtilsClient;

	final static Logger logger = Logger.getLogger(RapidantApp.class);
	
	final HandlerManager eventBus = new HandlerManager(null) {
	};

	private void shutdown() {
		
		eventBus.fireEvent(new GboxShutdownEvent());
		
		RapidantServiceController.Util.getInstance().disconnection(client);

		gBoxUtilsClient.disconnect(uuid);
		gBoxUtilsClient.close();

		Platform.exit();
		System.exit(0);
	}
	
	@Override
	public void stop() throws Exception {

		super.stop();

		shutdown();
	}

	@Override
	public void start(final Stage stage) {

		uuid = UUID.randomUUID().toString();

		IP = Constants.SERVER_ADDRESS;
		PORT = Constants.THRIFT_PORT;

		gBoxUtilsClient = new GBoxUtilsClient();
		
		if (!gBoxUtilsClient.isAlive()) {
			
			Alert closeDialogs = new Alert(AlertType.WARNING);
			closeDialogs.setTitle(Constants.TITLE);
			closeDialogs.setHeaderText(null);
			closeDialogs.setContentText(Constants.APP_RECHECK_INTERNET_TEXT);

			try {
				Optional<ButtonType> closeResult = closeDialogs.showAndWait();
				if (closeResult.get() == ButtonType.OK) {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.exit(0);
		} else {

			String version = gBoxUtilsClient.getVersion();

			if (!Constants.VERESION.equals(version)) {
				Alert closeDialogs = new Alert(AlertType.WARNING);
				closeDialogs.setTitle(Constants.TITLE);
				closeDialogs.setHeaderText(null);
				closeDialogs.setContentText(String.format(Constants.APP_UPDATE_TEXT, version));

				try {
					Optional<ButtonType> closeResult = closeDialogs.showAndWait();
					if (closeResult.get() == ButtonType.OK) {
						gBoxUtilsClient.close();
						System.exit(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		logger.info("Synchronization server access address: " + IP + ":" + PORT);

		BorderPane borderPane = new BorderPane();
		borderPane.getStyleClass().add("body");

		

		client = RapidantClientFactory.newRapidantClient();
		gBoxUtilsClient.connenct(uuid);

		System.out.println(gBoxUtilsClient.getConnectCount());

		RapidantAppFactory factory = new RapidantAppFactory(stage, eventBus, client);
		factory.go(borderPane);

		Scene scene = new Scene(borderPane, 445, 700);
//		scene.getStylesheets().add("resources/menu.css");
//		scene.getStylesheets().add("resources/tab.css");
		scene.getStylesheets().add("resources/tree.css");
		scene.getStylesheets().add("resources/table.css");

		stage.setScene(scene);
		stage.setTitle(Constants.FULL_TITLE);
		Image applicationIcon = new Image(Constants.APP_ICON);
		stage.getIcons().add(applicationIcon);
		stage.setResizable(true);
		stage.show();

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub

				Alert closeDialogs = new Alert(AlertType.WARNING);
				Stage stage = (Stage) closeDialogs.getDialogPane().getScene().getWindow();
				stage.getIcons().add(applicationIcon);
				closeDialogs.setTitle(Constants.TITLE);
				closeDialogs.setHeaderText(null);
				closeDialogs.setContentText(Constants.TITLE + Constants.APP_CLOSE_TEXT);

				try {
					Optional<ButtonType> closeResult = closeDialogs.showAndWait();
					if (closeResult.get() == ButtonType.OK) {
						shutdown();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});

		eventBus.addHandler(StageReSizeFireEvent.TYPE, new StageReSizeFireEventHandler() {
			@Override
			public void resize(StageReSizeFireEvent event) {
				// TODO Auto-generated method stub
				stage.setWidth(event.getWidth());
				stage.setHeight(event.getHeight());
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}