package org.kobic.gbox.client.monitor.presenter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kobic.gbox.client.common.CommonsUtil;
import org.kobic.gbox.client.common.Constants;
import org.kobic.gbox.client.fire.event.FileTransferInitFireEvent;
import org.kobic.gbox.client.fire.event.FileTransferInitFireEventHandler;
import org.kobic.gbox.client.fire.event.FileTransferStatusFireEvent;
import org.kobic.gbox.client.fire.event.FileTransferStatusFireEventHandler;
import org.kobic.gbox.client.fire.event.TransferUploadCancelEvent;
import org.kobic.gbox.client.fire.event.TransferringWithRAPIDANTEvent;
import org.kobic.gbox.client.fire.event.TransferringWithRAPIDANTEventHandler;
import org.kobic.gbox.client.model.FXTableTransferModel;
import org.kobic.gbox.client.model.TableTransferModel;
import org.kobic.gbox.client.model.TransferModel;
import org.kobic.gbox.client.presenter.Presenter;
import org.kobic.sso.client.model.Member;

import com.pennychecker.eventbus.HandlerManager;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TransferMonitorPresenter implements Presenter {

	private Display display;
	private HandlerManager eventBus;

	protected static final Logger logger = Logger.getLogger(TransferMonitorPresenter.class);

	@SuppressWarnings("unused")
	private Stage stage;

	private Member member;
	
	private DecimalFormat format = new DecimalFormat("#");
	private double prog;
	private String time;
	
	public static int count;

	public TransferMonitorPresenter(Display view, Stage stage, HandlerManager eventBus, Member member) {
		this.display = view;
		this.eventBus = eventBus;
		this.stage = stage;
		this.member = member;
	}
	
	public TransferMonitorPresenter() {
		
	}

	public interface Display {
		VBox asWidget();

		TableView<FXTableTransferModel> getTreeView();

		MenuItem getCancelMenuItem();
	}

	private void setTransferFileTableInitFireEvent() {
		eventBus.addHandler(FileTransferInitFireEvent.TYPE, new FileTransferInitFireEventHandler() {

			@Override
			public void transferTableBindEvent(FileTransferInitFireEvent event) {
				// TODO Auto-generated method stub

				TableTransferModel transfer = event.getTransferData();

				logger.info("Request to transfer [ " +transfer.getName() + " ] in ' " + transfer.getMethod() + "' method");
							
				FXTableTransferModel model = new FXTableTransferModel(transfer.getId(), transfer.getType(),
						transfer.getMethod(), transfer.getName(), transfer.getStatus(), transfer.getSize(),
						transfer.getLocal(), transfer.getProgress(), transfer.getSpeed(),
						transfer.getElapsedTime() + " s",
						CommonsUtil.getInstance().humanReadableByteCount(transfer.getTransferSize(), true));

				display.getTreeView().getItems().add(model);
			}
		});
	}

	private void setFileTransferStatusFireEvent() {
		
		eventBus.addHandler(FileTransferStatusFireEvent.TYPE, new FileTransferStatusFireEventHandler() {
			@Override
			public void transferDataBindEvent(final FileTransferStatusFireEvent event) {
				// TODO Auto-generated method stub

				TableTransferModel tm = event.getTransferData();
				
//				logger.info("Start to transfer [ " +tm.getName() + " ] in '" + tm.getMethod() + "' method");
				
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						ObservableList<FXTableTransferModel> items = display.getTreeView().getItems();

						prog = Double.parseDouble(CommonsUtil.getInstance().calculation(tm.getProgressedRatio()));

						for (int i = 0; i < items.size(); i++) {
							if (items.get(i).getID() == tm.getId()) {

								FXTableTransferModel m = items.get(i);

								time = format.format((tm.getElapsedTime() / 1000.0));

								m.setProgressBar(prog);
								m.setElapsedTime(time + " s");
								m.setTransferSize(
										CommonsUtil.getInstance().humanReadableByteCount(tm.getTransferSize(), true));
								m.setSpeed(tm.getSpeed());
								m.setSize(CommonsUtil.getInstance().humanReadableByteCount(tm.getTotalSize(), true));
								m.setLocal(m.getLocal().replace(Constants.RAPIDANT_PATH, ""));

								if (prog == .0) {
									m.setStatus("Waiting");
								} else if (prog == 1.0) {
									m.setStatus("Complete");
								} else {
									m.setStatus("Transferring");
								}

								if (tm.getTransferStatus() == Constants.END) {
									m.setStatus("Complete");
									m.setProgressBar(1.0);
									m.setTransferSize(CommonsUtil.getInstance()
											.humanReadableByteCount(tm.getTransferSize(), true));
								}

								if (tm.getTransferStatus() == Constants.CANCEL) {
									m.setStatus("Cancel");
								}

								break;
							}
						}
					}
				});
			}
		});
	}

	private void getTransferringWithRAPIDANTCount() {
		
		eventBus.addHandler(TransferringWithRAPIDANTEvent.TYPE, new TransferringWithRAPIDANTEventHandler() {
			
			@Override
			public void transfferingWithRAPIDANT(TransferringWithRAPIDANTEvent event) {
				// TODO Auto-generated method stub
				
				count = 0;
				
				ObservableList<FXTableTransferModel> list = display.getTreeView().getItems();
				
				if (list.size() != 0) {
					
					logger.info(list.size() + " : files transferring");
					
					for(FXTableTransferModel t : list) {
						
						logger.info("[ " + t.getName() +" ]" + t.getMethod() + "\t" + t.getStatus());
						
						if (t.getMethod().equals("R") && (t.getStatus().equals("Waiting") || t.getStatus().equals("Transferring"))) {
							count = count + 1;
						}
					}
				} else {
					count = 0;
				}
				
				logger.info(count + " : files transferring in 'R'method");
			}
		});
	}

	private void setCancelContextMenuEvent() {
		
		display.getCancelMenuItem().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				ObservableList<FXTableTransferModel> list = display.getTreeView().getSelectionModel()
						.getSelectedItems();

				List<TransferModel> cancelList = new ArrayList<TransferModel>();
				
				if (list.size() != 0) {
					
					logger.info(list.size() + " : files select");
					
					for (FXTableTransferModel model : list) {
						
						logger.info("[ " + model.getName() + " ] : Cancel to trasfer");
						
						TransferModel transfer = new TransferModel();
						transfer.setId(model.getID());
						transfer.setMethod(model.getMethod());
						transfer.setType(model.getType());
						
						cancelList.add(transfer);
					}
				}
				
				eventBus.fireEvent(new TransferUploadCancelEvent(cancelList));
			}
		});
	}

	private void setSelectModelEvent() {
		display.getTreeView().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
				// TODO Auto-generated method stub

				// context MenuItem Set visible
				ObservableList<FXTableTransferModel> list = display.getTreeView().getSelectionModel()
						.getSelectedItems();
				if (list.size() != 0) {
					display.getCancelMenuItem().setDisable(false);
				} else {
					display.getCancelMenuItem().setDisable(true);
				}
			}
		});
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		setFileTransferStatusFireEvent();
		setTransferFileTableInitFireEvent();
		setCancelContextMenuEvent();
		setSelectModelEvent();
		getTransferringWithRAPIDANTCount();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

		logger.info("[ " + member.getMemberNm() + " ] is logged in");
		bind();
	}

	@Override
	public void go(Object container) {
		// TODO Auto-generated method stub
		BorderPane pane = (BorderPane) container;
		pane.setCenter(display.asWidget());
		init();
	}
}