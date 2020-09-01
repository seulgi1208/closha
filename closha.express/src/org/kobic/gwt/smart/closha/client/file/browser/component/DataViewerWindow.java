package org.kobic.gwt.smart.closha.client.file.browser.component;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.file.explorer.controller.HadoopFileSystemController;
import org.kobic.gwt.smart.closha.client.unix.file.controller.UnixFileSystemController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;

public class DataViewerWindow extends Window{
	
	private HTMLPane textPane;
	private Image resultImg;
	private String filePath;
	private String localFilePath;
	private Map<String, String> config;
	
	public String getLocalFilePath() {
		return localFilePath;
	}

	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}

	@Override   
    protected void onInit() {
		HadoopFileSystemController.Util.getInstance().getExtension(config, filePath, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(final String extension) {
				// TODO Auto-generated method stub
				
				if(extension.equals("html")){
					
					HadoopFileSystemController.Util.getInstance().getTempContentFile(config, filePath, new AsyncCallback<String>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							SC.warn("This is an unsupported file type.");
						}

						@Override
						public void onSuccess(String localFilePath) {
							// TODO Auto-generated method stub
							resultHtmlDataView(localFilePath);
							setLocalFilePath(localFilePath);
							event();
						}
					});
					
				}else if(extension.equals("png") || extension.equals("jpg") ||
						extension.equals("gif") || extension.equals("bmp")){
					
					HadoopFileSystemController.Util.getInstance().getTempContentFile(config, filePath, new AsyncCallback<String>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							SC.warn("This is an unsupported file type.");
						}

						@Override
						public void onSuccess(String localFilePath) {
							// TODO Auto-generated method stub
							resultImgDataView(extension, localFilePath);
							setLocalFilePath(localFilePath);
							event();
						}
					});
					
				}else{
					sampleDataView();
				}
			}
		});
	}
	
	public void event(){
		this.addCloseClickHandler(new CloseClickHandler() {
			
			@Override
			public void onCloseClick(CloseClickEvent event) {
				// TODO Auto-generated method stub
				UnixFileSystemController.Util.getInstance().deleteDirectory(getLocalFilePath(), new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						SC.warn(caught.getMessage());
					}
					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						destroy();
					}
				});
			}
		});
	}
	
	public DataViewerWindow(String filePath, Map<String, String> config){
		
		this.filePath = filePath;
		this.config = config;
		
		setTitle("Preview");
		setWidth(500);
		setHeight(400);
		setIsModal(false);
		setShowMinimizeButton(true);
		setShowCloseButton(true);
		setShowMaximizeButton(true);		
		setCanDragReposition(true);
		setCanDragResize(true);
		centerInPage();
		setAutoCenter(true);
		setHeaderIcon("silk/page_white.png");
	}
	
	private void sampleDataView(){
		HadoopFileSystemController.Util.getInstance().getPreviewData(config, filePath, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				textPane = new HTMLPane();    
		        textPane.setHeight100();
		        textPane.setWidth100();
		        textPane.setContents(result);
				addItem(textPane);
			}
		});
	}
	
	private void resultHtmlDataView(String path){
		textPane = new HTMLPane();    
		textPane.setContentsType(ContentsType.PAGE); 
        textPane.setHeight100();
        textPane.setWidth100();
        textPane.setContentsURL(GWT.getModuleBaseURL() + "result?path=" + path);
		addItem(textPane);
	}
	
	private void resultImgDataView(String type, String path){
		resultImg = new Image();
        resultImg.setUrl(GWT.getModuleBaseURL() + "image?path=" + path + "&type=" + type);
        resultImg.setHeight(getHeight()-30 + "px");
        resultImg.setWidth(getWidth()-30 + "px");
        addItem(resultImg);
	}
}
