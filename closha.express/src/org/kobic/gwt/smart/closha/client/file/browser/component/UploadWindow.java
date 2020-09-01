package org.kobic.gwt.smart.closha.client.file.browser.component;

import gwtupload.client.IUploader;
import gwtupload.client.IUploadStatus.Status;

import org.kobic.gwt.smart.closha.client.common.component.ProgressWindow;
import org.kobic.gwt.smart.closha.client.common.component.UploadRowSetLayout;
import org.kobic.gwt.smart.closha.shared.Messages;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class UploadWindow extends Window{
	
	private UploadRowSetLayout file;
	private IButton submitButton;
	private Window progressBarWindow;
	private HTMLFlow descriptionHTML;
	private HTMLFlow statusHTML;
	
	private boolean check = true;
	
	private void rowClear(){
		file.getFileUploader().clear();
	}
	
	private void rowReset(){
		file.getFileUploader().reset();
	}
	
	private void setEvent(){
		file.getFileUploader().addOnStatusChangedHandler(new IUploader.OnStatusChangedHandler() {
			@Override
			public void onStatusChanged(IUploader uploader) {
				// TODO Auto-generated method stub
				statusHTML.setContents(uploader.getInputName() + ": " + uploader.getStatus() + " (" + CommonUtilsGwt.getDate() + ")");
			}
		});
		
		file.getFileUploader().addOnCancelUploadHandler(new IUploader.OnCancelUploaderHandler() {
			@Override
			public void onCancel(IUploader uploader) {
				// TODO Auto-generated method stub
				check = false;
			}
		});
		
		file.getFileUploader().addOnStartUploadHandler(new IUploader.OnStartUploaderHandler() {
			@Override
			public void onStart(IUploader uploader) {
				// TODO Auto-generated method stub
				check = false;
			}
		});
		
		file.getFileUploader().addOnFinishUploadHandler(new IUploader.OnFinishUploaderHandler() {
			public void onFinish(IUploader uploader) {
				if (uploader.getStatus() == Status.SUCCESS) {
					check = true;
				}
			}
		});
		
		addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				// TODO Auto-generated method stub
				if(check){
					destroy();
				}else{
					SC.say("Uploading file is in progress.</BR>" +
							"Please retry after the file upload has been completed.");
				}
			}
		});
		
		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(check){
					progressBarWindow = new ProgressWindow(Messages.UPLOAD_PROGRESS_TITLE);
					progressBarWindow.show();
					
					Timer timer = new Timer() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							progressBarWindow.destroy();
							SC.confirm("Upload Completed", "File upload has been completed.</BR>" +
									"Do you want to upload another file?", new BooleanCallback() {
								@Override
								public void execute(Boolean result) {
									// TODO Auto-generated method stub
									if(String.valueOf(result).equals("true")){
										rowClear();
										rowReset();
									}else{
										rowReset();
										destroy();
									}
								}
							});
						}
					};
					timer.schedule(800);
				}else{
					SC.say("Uploading file is in progress.</BR>" +
							"Please retry after the file upload has been completed.");
				}
			}
		});
	}

	public UploadWindow(String path, HandlerManager eventBus){
		
		setTitle("Upload File");
		setWidth(550);
		setHeight(370);
		setIsModal(true);
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setCanDragReposition(false);
		setCanDragResize(false);
		setShowShadow(true);
		centerInPage();
		setAutoCenter(true);
		setHeaderIcon("closha/icon/upload.png");
		
		VLayout uploadLayout = new VLayout();
		uploadLayout.setHeight100();
		uploadLayout.setWidth100();
		uploadLayout.setMargin(10);
		
		descriptionHTML = new HTMLFlow();  
        descriptionHTML.setWidth100();
        descriptionHTML.setStyleName("exampleTextBlock");  
        descriptionHTML.setContents(Messages.UPLOAD_DESCRIPTION);  
		
		file = new UploadRowSetLayout("<nobr>Upload Files(maximum of 10) : </nobr>");
		file.setID("file_1");
		file.fileUploader.setServletPath(file.fileUploader.getServletPath() + "?path="+path);
		
		statusHTML = new HTMLFlow();  
		statusHTML.setWidth100();
		statusHTML.setContents("Initialization in progress...");  
		
		uploadLayout.addMember(descriptionHTML);
		uploadLayout.addMember(file);
		uploadLayout.addMember(statusHTML);
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setWidth100();
		buttonLayout.setHeight100();
		buttonLayout.setMembersMargin(10);
		buttonLayout.setAlign(Alignment.CENTER);
		
		submitButton = new IButton("Upload");
		submitButton.setIcon("closha/icon/upload.png");
		
		buttonLayout.addMember(submitButton);
		
		addItem(uploadLayout);
		addItem(buttonLayout);
		
		setEvent();
	}
}
