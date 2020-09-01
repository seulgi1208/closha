package org.kobic.gwt.smart.closha.client.file.browser.presenter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.common.component.ProgressWindow;
import org.kobic.gwt.smart.closha.client.file.browser.component.EditWindow;
import org.kobic.gwt.smart.closha.client.file.browser.component.FileCopyWindow;
import org.kobic.gwt.smart.closha.client.file.browser.component.UploadWindow;
import org.kobic.gwt.smart.closha.client.file.browser.component.MakeDirectoryWindow;
import org.kobic.gwt.smart.closha.client.file.browser.component.DataViewerWindow;
import org.kobic.gwt.smart.closha.client.file.browser.event.FileDataBindEvent;
import org.kobic.gwt.smart.closha.client.file.browser.record.FileBrowserRecord;
import org.kobic.gwt.smart.closha.client.file.explorer.controller.HadoopFileSystemController;
import org.kobic.gwt.smart.closha.client.file.explorer.event.FileBrowserDataLoadEvent;
import org.kobic.gwt.smart.closha.client.file.explorer.event.FileBrowserDataLoadEventHandler;
import org.kobic.gwt.smart.closha.client.frame.center.event.FileBrowserDataRefreshEvent;
import org.kobic.gwt.smart.closha.client.frame.center.event.FileBrowserDataRefreshEventHandler;
import org.kobic.gwt.smart.closha.client.frame.center.event.RemoveFileBrowserRegisterEvents;
import org.kobic.gwt.smart.closha.client.frame.center.event.RemoveFileBrowserRegisterEventsHandler;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.Messages;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.FileModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class FileBrowserPresenter implements Presenter{

	private HandlerManager eventBus;
	private HandlerRegistration registerHandler[];
	
	private Display display; 
	private UserDto userDto;
	
	private String rootPath = "";
	private String parentPath = "";
	private String path = "";
	
	private LinkedHashMap<String, String> map;
	private LinkedHashMap<String, String> iconMap;
	private Map<String, String> config;
	
	private boolean isUse;
	
	public interface Display{
		VLayout asWidget();
		ListGrid getFileBrowserListGrid();
		ToolStripButton getMyHomeButton();
		ToolStripButton getDownloadButton();
		ToolStripButton getMakeButton();
		ToolStripButton getDeleteButton();
		ToolStripButton getEditButton();
		ToolStripButton getRefreshButton();
		ToolStripButton getCopyButton();
		SelectItem getItem();
		SelectItem getSelectItem();
	}
	
	public FileBrowserPresenter(HandlerManager eventBus, Map<String, String> config, UserDto userDto, Display view){
		this.eventBus = eventBus;
		this.display = view;
		this.userDto = userDto;
		this.config = config;
	}
	
	@Override
	public void init(){
		
		HadoopFileSystemController.Util.getInstance().getRootPath(config, userDto.getUserId(), new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				SC.warn(caught.getMessage());
			}
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				
				parentPath = rootPath = path = result;
				
				registerHandler = new HandlerRegistration[3];
				
				map = new LinkedHashMap<String, String>();
				
				iconMap = new LinkedHashMap<String, String>();
				iconMap.put(rootPath, "closha/icon/folder.png");
				
				isUse = true;
				
				bind();
			}
		});
	}
	
	@Override
	public void bind(){
		fileBrowserInitDataBinding();
		setFileBrowserCellDoubleClickEvent();
		setMyHomeButtonEvent();
		itemEvent();
		setMkdirButtonEvent();
		fileBrowserReLoadFireEvent();
		removeRegisterEvnetFileEvent();
		reciveSetFileInputPathFireEvent();
		deleteFileButtonEvent();
		editFileButtonEvent();
		downloadButtonEvent();
		fileBrowserListGridCellEvent();
		RefreshButtonEvent();
		copyButtonEvent();
		changeSelectEvent();
	}
	
	private void fileBrowserListGridCellEvent(){
		display.getFileBrowserListGrid().addCellDoubleClickHandler(new CellDoubleClickHandler() {
			
			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event) {
				// TODO Auto-generated method stub
				if(!event.getRecord().getAttributeAsBoolean("isDic")){
					
					final String path = event.getRecord().getAttributeAsString("filePath");
					
					HadoopFileSystemController.Util.getInstance().isSize(config, path, new AsyncCallback<Boolean>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							SC.say("A serious error has occurred while checking the status of your selected file.");
						}

						@Override
						public void onSuccess(Boolean result) {
							// TODO Auto-generated method stub
							if(result){
								Window dataViewerWindow = new DataViewerWindow(path, config);
								dataViewerWindow.show();
							}else{
								SC.warn("The data size you have selected is not supported by the system. Please download it before use.");
							}
						}
					});
					
					
				}
			}
		});
	}
	
	private void RefreshButtonEvent(){
		display.getRefreshButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				getFileBrowserFileList(path, parentPath);
				setItemValues(path, viewPath(path));
			}
		});
	}
	
	private void changeSelectEvent(){
		display.getSelectItem().addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				
				String uid = null;
				
				if(display.getSelectItem().getValueAsString().equals("public")){
					uid = config.get(Constants.PUBLIC_DATA_NAME_KEY);
					isUse = false;
				}else{
					uid = userDto.getUserId();
					isUse = true;
				}
				
				HadoopFileSystemController.Util.getInstance().getRootPath(config, uid, new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						SC.warn(caught.getMessage());
					}
					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						parentPath = rootPath = path = result;
						fileBrowserInitDataBinding();
					}
				});
			}
		});
	}
	
	
	private void copyButtonEvent(){
		display.getCopyButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				List<FileModel> list = getFileList();
				
				if(list.size() == 0){
					SC.say("Please select a file or folder to copy.");
				}else{
					FileCopyWindow copyWindow = new FileCopyWindow(config, userDto, list);
					copyWindow.show();
				}
			}
		});
	}
	
	private void downloadButtonEvent(){
		display.getDownloadButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(getFileList().size() == 0){
					SC.say("Please select a file or folder to download.");
				}else if(getFileList().size() > 1){
					SC.say("Two or more files or folders cannot be downloaded simultaneously.");
				}else{
					
					final FileModel fm = getFileList().get(0);
					
					if(fm.isDic() || fm.getSize().length() > 8){
						SC.confirm("A file or folder with the size over 100M will be downloaded using KoDS. Do you want to proceed?", new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								// TODO Auto-generated method stub
								
								SC.say("An email will be sent upon completion of processing the data. <br/>Please, check the email and then download it using KoDS.");
								
								HadoopFileSystemController.Util.getInstance().convertHDFSPathToRapidantPath(config, fm.getPath(), 
										userDto.getUserName(), userDto.getEmailAdress(), 
										new AsyncCallback<Void>() {
									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub
									}
									@Override
									public void onSuccess(Void result) {
										// TODO Auto-generated method stub
									}
								});
							}
						});
					}else{
						final Window progressBarWindow = new ProgressWindow(Messages.DOWNLOAD_PROGRESS_TITLE, true);
						progressBarWindow.show();
						
						String path = fm.getPath();
						
						@SuppressWarnings("unused")
						String name = fm.getName();
						
						/*
						HadoopFileSystemController.Util.getInstance().fileCompress(config, path, name, new AsyncCallback<CompressModel>() {
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								SC.say(caught.getMessage());
							}
							@Override
							public void onSuccess(CompressModel output) {
								// TODO Auto-generated method stub
								eventBus.fireEvent(new FileBrowserDataLoadEvent(output.getParentPath()));
								eventBus.fireEvent(new FileDataBindEvent());
								progressBarWindow.destroy();
								
								com.google.gwt.user.client.Window.open(GWT.getModuleBaseURL() + "download?output="+output.getPath(), "", "");
							}
						});
						*/
						
						HadoopFileSystemController.Util.getInstance().getTempContentFile(config, path, new AsyncCallback<String>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								SC.warn("This is an unsupported file type.");
							}

							@Override
							public void onSuccess(String localFilePath) {
								// TODO Auto-generated method stub
								progressBarWindow.destroy();
								com.google.gwt.user.client.Window.open(GWT.getModuleBaseURL() + "download?output="+localFilePath, "", "");
							}
						});
					}
				}
			}
		});
	}
	
	@SuppressWarnings("unused")
	private void showUploadWindow(){
		Window uploadWindow = new UploadWindow(path, eventBus);
		uploadWindow.show();
	}
	
	private List<String> getDeleteList(){
		ListGridRecord[] records = new FileBrowserRecord[]{}; 
		records = display.getFileBrowserListGrid().getSelectedRecords();
		
		List<String> pathList = new ArrayList<String>();
		
		for(int i = 0; i < records.length; i++){
			if(!records[i].getAttributeAsString("fileName").equals("..")){
				pathList.add(records[i].getAttribute("filePath"));
			}
		}
		return pathList;
	}
	
	private void editFileButtonEvent(){
		display.getEditButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				if(!isUse){
					SC.warn("It is a function that can not be used.");
				}else{
					if(getFileList().size() == 0){
						SC.say("Please select a file to edit.");
					}else if(getFileList().size() > 1){
						SC.say("Two or more files or folders cannot be downloaded simultaneously.");
					}else{
						Window editWindow = new EditWindow(getFileList().get(0).getPath(), 
								getFileList().get(0).getName(), eventBus, config);
						editWindow.show();
					}		
				}
			}
		});
	}
	
	private void deleteFileButtonEvent(){
		display.getDeleteButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(getDeleteList().size() == 0){
					SC.say("Please select a file to delete.");
				}else if(!isUse){
					SC.warn("It is a function that can not be used.");
				}else{
					
					SC.confirm("Are you sure you want to permanently delete this " + getDeleteList().size() + " item(s)?", new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							// TODO Auto-generated method stub
							if(value){
								HadoopFileSystemController.Util.getInstance().deleteDirectory(config, getDeleteList(), new AsyncCallback<Void>() {
									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub
										System.out.println(caught.getCause() + ":" + caught.getMessage());
									}
									@Override
									public void onSuccess(Void result) {
										// TODO Auto-generated method stub
										getFileBrowserFileList(path, parentPath);
										eventBus.fireEvent(new FileDataBindEvent());
										
										for(String path : getDeleteList()){
											if(map.containsKey(path) && iconMap.containsKey(path)){
												map.remove(path);
												iconMap.remove(path);
											}
										}
										setItemValues();
									}
								});
							}
						}
					});
				}
			}
		});
	}
	
	private void setMkdirButtonEvent() {
		// TODO Auto-generated method stub
		display.getMakeButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Window makeWindow =  new MakeDirectoryWindow(path, viewPath(path), eventBus, config);
				makeWindow.show();
			}
		});
	}
	
	private List<FileModel> getFileList(){
		ListGridRecord[] records = new FileBrowserRecord[]{}; 
		records = display.getFileBrowserListGrid().getSelectedRecords();
		
		List<FileModel> fileModelList = new ArrayList<FileModel>();
		
		for(int i = 0; i < records.length; i++){
			if(!records[i].getAttributeAsString("fileName").equals("..")){
				FileModel fm = new FileModel();
				fm.setPath(records[i].getAttribute("filePath"));
				fm.setName(records[i].getAttribute("fileName"));
				fm.setDic(records[i].getAttributeAsBoolean("isDic"));
				fm.setSize(records[i].getAttribute("fileSize"));
				fileModelList.add(fm);
			}
		}
		return fileModelList;
	}
	
	private void removeRegisterEvnetFileEvent(){
		registerHandler[2] = eventBus.addHandler(RemoveFileBrowserRegisterEvents.TYPE, new RemoveFileBrowserRegisterEventsHandler() {
			@Override
			public void fileBrowserRemoveRegisterEventHandler(
					RemoveFileBrowserRegisterEvents event) {
				// TODO Auto-generated method stub
				for(int i = 0 ; i < registerHandler.length; i++){
					registerHandler[i].removeHandler();
				}
			}
		});
	}
	
	private void fileBrowserReLoadFireEvent(){
		registerHandler[1] = eventBus.addHandler(FileBrowserDataRefreshEvent.TYPE, new FileBrowserDataRefreshEventHandler() {
			@Override
			public void dataRefresh(FileBrowserDataRefreshEvent event) {
				// TODO Auto-generated method stub
				if(path.contains(event.getProjectName())){
					fileBrowserInitDataBinding();
					path = parentPath = rootPath;
					
					String projectPath = rootPath + Constants.SEPERATOR
							+ config.get(Constants.SERVICE_NAME_KEY)
							+ Constants.SEPERATOR + Constants.PROJECT
							+ Constants.SEPERATOR
							+ event.getProjectName();
					
					if(map.containsKey(projectPath) && iconMap.containsKey(projectPath)){
						map.remove(projectPath);
						iconMap.remove(projectPath);
					}
					setItemValues();
				}
			}
		});
	}
	
	private void reciveSetFileInputPathFireEvent(){
		
		registerHandler[0] = eventBus.addHandler(FileBrowserDataLoadEvent.TYPE, new FileBrowserDataLoadEventHandler() {
			@Override
			public void selectFileInputPathSetting(final FileBrowserDataLoadEvent event) {
				// TODO Auto-generated method stub
				
				path = event.getFilePath();
				
				System.out.println("Begin loading a file browser call data. " + path);
				
				HadoopFileSystemController.Util.getInstance().getParentDirectoryPath(config, path, new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println(caught.getCause() + ":" + caught.getMessage());
					}
					@Override
					public void onSuccess(String parentPath) {
						// TODO Auto-generated method stub
						
						setItemValues(event.getFilePath(), viewPath(event.getFilePath()));
						getFileBrowserFileList(event.getFilePath(), parentPath);
					}
				});
			}
		});
	}
	
	private void setMyHomeButtonEvent(){
		display.getMyHomeButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				fileBrowserInitDataBinding();
				path = parentPath = rootPath;
				setItemValues();
			}
		});
	}

	private void itemEvent(){
		display.getItem().addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				path = event.getItem().getValue().toString();
				getFileBrowserFileList(event.getItem().getValue().toString(), parentPath);
			}
		});
	}
	
	private String viewPath(String path){
		return path;
	}
	
	private void setItemValues(){
		display.getItem().setValueMap(map);
		display.getItem().setValueIcons(iconMap);
		
		display.getItem().clearValue();
		display.getItem().setDefaultValue(path);
	}
	
	private void setItemValues(String key, String value){
		
		display.getItem().clearValue();
		display.getItem().setDefaultValue(value);
		
		map.put(key, value);
		iconMap.put(key, "closha/icon/folder.png");
		
		display.getItem().setValueMap(map);
		display.getItem().setValueIcons(iconMap);
	}
	
	private void fileBrowserInitDataBinding(){
		HadoopFileSystemController.Util.getInstance().getParentDirectoryPath(config, rootPath, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				SC.warn(caught.getMessage());
			}

			@Override
			public void onSuccess(String parentPath) {
				// TODO Auto-generated method stub
				getFileBrowserFileList(rootPath, parentPath);
			}
		});
	}
	
	private void fileListGridSubDataBind(final List<FileModel> list, final String parentPath){
		
		System.out.println("HDFS path to inject file browser data : " + path + "\tParent path: " + parentPath);
		
		HadoopFileSystemController.Util.getInstance().getParentDirectoryPath(config, parentPath, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				SC.warn(caught.getMessage());
			}

			@Override
			public void onSuccess(String topPath) {
				// TODO Auto-generated method stub
				int size = list.size() + 1;
				
				FileBrowserRecord records[] = new FileBrowserRecord[size];
				records[0] = new FileBrowserRecord(parentPath, topPath);
				
				for(int i = 0; i < list.size(); i++){
					FileModel fm = list.get(i);
					records[i+1] = new FileBrowserRecord(fm.getId(), fm.getpId(), 
							fm.getName(), fm.getSize(), fm.getLastModifyDate(), fm.getLastAccessDate(), 
							fm.getType(), fm.getPath(), fm.getpPath(), fm.getUser(), fm.getGroup(), fm.isDic(), fm.getIcon());
				}
				display.getFileBrowserListGrid().setData(records);
			}
		});
	}
	
	private void setFileBrowserCellDoubleClickEvent(){
		
		display.getFileBrowserListGrid().addCellDoubleClickHandler(new CellDoubleClickHandler() {
			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event) {
				// TODO Auto-generated method stub			
				
				if(event.getRecord().getAttributeAsBoolean("isDic")){
					
					path = event.getRecord().getAttributeAsString("filePath");
					parentPath = event.getRecord().getAttributeAsString("parentPath");
					
					System.out.println("Path for File Browser Click Event : " + path + "\tParent path: " + parentPath);
					
					if(path.endsWith(config.get(Constants.HDFS_DIR_KEY))){
						SC.say("This is the top level path.");
					}else{
						getFileBrowserFileList(path, parentPath);
						setItemValues(path, viewPath(path));
					}
				}
			}
		});
	}
	
	private void getFileBrowserFileList(final String path, final String parentPath){
	
		System.out.println("Path to request for HDFS file lists : " + path + "\tParent path: " + parentPath);
		
		HadoopFileSystemController.Util.getInstance().getFileBrowserSubDirectory(config, userDto.getUserId(), path, 
				new AsyncCallback<List<FileModel>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(List<FileModel> list) {
				// TODO Auto-generated method stub
				
				fileListGridSubDataBind(list, parentPath);
			}
		});
	}
	
	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.asWidget());
		init();
	}
}
