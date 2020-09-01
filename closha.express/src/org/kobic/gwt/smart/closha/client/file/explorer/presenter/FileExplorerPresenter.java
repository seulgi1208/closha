package org.kobic.gwt.smart.closha.client.file.explorer.presenter;

import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.file.browser.event.FileDataBindEvent;
import org.kobic.gwt.smart.closha.client.file.browser.event.FileDataBindEventHandler;
import org.kobic.gwt.smart.closha.client.file.explorer.component.FileExplorerTree;
import org.kobic.gwt.smart.closha.client.file.explorer.controller.HadoopFileSystemController;
import org.kobic.gwt.smart.closha.client.file.explorer.event.FileExplorerDataReLoadEvent;
import org.kobic.gwt.smart.closha.client.file.explorer.event.FileExplorerDataReLoadEventHandler;
import org.kobic.gwt.smart.closha.client.file.explorer.event.ShowFileBrowserEvent;
import org.kobic.gwt.smart.closha.client.file.explorer.record.FileExplorerRecord;
import org.kobic.gwt.smart.closha.client.frame.center.event.RemoveFileBrowserRegisterEvents;
import org.kobic.gwt.smart.closha.client.frame.center.event.RemoveFileBrowserRegisterEventsHandler;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.FileModel;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;

public class FileExplorerPresenter implements Presenter{

	private final HandlerManager eventBus;
	private HandlerRegistration registerHandler[];
	
	private final Display display; 
	private final UserDto userDto;
	private final Map<String, String> config;
	
	@SuppressWarnings("unused")
	private Window preViewWindow;
	
	private Tree fileExplorerTree;
		
	public interface Display{
		VLayout asWidget();
		TreeGrid getFileBrowserTreeGrid();
	}
	
	public FileExplorerPresenter(HandlerManager eventBus, Map<String, String> config, UserDto userDto, Display view){
		this.eventBus = eventBus;
		this.display = view;
		this.userDto = userDto;
		this.config = config;
	}
	
	@Override
	public void init(){
		registerHandler = new HandlerRegistration[2];
		setInitTreeDataBind();
		removeRegisterFireEvent();
		bind();
	}
	
	@Override
	public void bind(){
		transmitSelectDirectoryData();
		setSubTreeDataBind();
		reciveFileBrowserDataLoadFireEvent();
		fileExplorerDataReLoadFireEvent();
	}
	
	private void fileExplorerDataReLoadFireEvent(){
		eventBus.addHandler(FileExplorerDataReLoadEvent.TYPE, new FileExplorerDataReLoadEventHandler() {
			
			@Override
			public void fileExplorerDataReLoadEvent(FileExplorerDataReLoadEvent event) {
				// TODO Auto-generated method stub
				setInitTreeDataBind();
			}
		});
	}
	
	private void removeRegisterFireEvent(){
		
		registerHandler[1] = eventBus.addHandler(RemoveFileBrowserRegisterEvents.TYPE, new RemoveFileBrowserRegisterEventsHandler() {
			@Override
			public void fileBrowserRemoveRegisterEventHandler(
					RemoveFileBrowserRegisterEvents event) {
				// TODO Auto-generated method stub
				if(registerHandler.length != 0){
					for(int i = 0; i < registerHandler.length; i++){
						registerHandler[i].removeHandler();
					}
				}
			}
		});
		
	}
	
	private void reciveFileBrowserDataLoadFireEvent(){
		
		registerHandler[0] = eventBus.addHandler(FileDataBindEvent.TYPE, new FileDataBindEventHandler() {
			@Override
			public void fileBrowserDataReLoad(FileDataBindEvent event) {
				// TODO Auto-generated method stub
				setInitTreeDataBind();
			}
		});
		
	}
	
	private void transmitSelectDirectoryData(){
		display.getFileBrowserTreeGrid().addCellClickHandler(new CellClickHandler() {
			@Override
			public void onCellClick(CellClickEvent event) {
				// TODO Auto-generated method stub
				if(event.getRecord().getAttributeAsBoolean("isDic")){
					
					String path = event.getRecord().getAttribute("filePath");
					
					eventBus.fireEvent(new ShowFileBrowserEvent(true, path));
				  //eventBus.fireEvent(new FileBrowserDataLoadEvent(event.getRecord().getAttribute("filePath")));
					
					System.out.println("File Explorer Clicked Item Path : " + event.getRecord().getAttribute("filePath"));
				}
			}
		});
	}
	
	private void setSubTreeDataBind(){
		
		display.getFileBrowserTreeGrid().addNodeClickHandler(new NodeClickHandler() {
			@Override
			public void onNodeClick(final NodeClickEvent event) {
				// TODO Auto-generated method stub
				
				if(event.getNode().getAttributeAsBoolean("isDic") 
						&& !event.getNode().getAttributeAsBoolean("event")){
					
					event.getNode().setAttribute("event", true);
					
					HadoopFileSystemController.Util.getInstance().getSubDirectoryList(config, userDto.getUserId(), event.getNode().getAttribute("filePath"), 
							event.getNode().getAttribute("parentId"), new AsyncCallback<List<FileModel>>() {
						
						@Override
						public void onSuccess(List<FileModel> list) {
							// TODO Auto-generated method stub
							
							FileExplorerRecord records[] = new FileExplorerRecord[list.size()];
							
							for(int i = 0; i < list.size(); i++){
								
								FileModel fm = list.get(i);
								
								records[i] = new FileExplorerRecord(fm.getId(), fm.getpId(), 
										fm.getName() + Constants.SPACE + 
										CommonUtilsGwt.getExplorerFont(Constants.SPACE + fm.getLastModifyDate() + Constants.SPACE + userDto.getUserId()), 
										fm.getSize(), fm.getLastModifyDate(), fm.getLastAccessDate(), 
										fm.getType(), fm.getPath(), fm.getUser(), fm.getGroup(), fm.isDic(), fm.getIcon());
							}
							
							fileExplorerTree.addList(records, event.getNode());
						}
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println(caught.getCause() + ":" + caught.getMessage());
						}
					});
				}else if(!event.getNode().getAttributeAsBoolean("isDic")){
					/*
					preViewWindow = new DataViewerWindow(event.getNode().getAttribute("filePath"), config);
					preViewWindow.show();
					*/
				}
			}
		});
	}
	
	private void setInitTreeDataBind(){
		
		HadoopFileSystemController.Util.getInstance().getRootDirectoryList(config, userDto.getUserId(), new AsyncCallback<List<FileModel>>() {
			@Override
			public void onSuccess(List<FileModel> list) {
				// TODO Auto-generated method stub
				
				FileExplorerRecord records[] = new FileExplorerRecord[list.size()];
				
				for(int i = 0; i < list.size(); i++){
					
					FileModel fm = list.get(i);
					
							records[i] = new FileExplorerRecord(fm.getId(), 
									fm.getpId(), fm.getName(), fm.getSize(),
									CommonUtilsGwt.getExplorerFont(fm.getLastModifyDate()), 
									fm.getLastAccessDate(), fm.getType(),
									fm.getPath(), fm.getUser(), fm.getGroup(),
									fm.isDic(), fm.getIcon());
				}
				
				fileExplorerTree = new FileExplorerTree();
				fileExplorerTree.setData(records);
				
				display.getFileBrowserTreeGrid().addDrawHandler(new DrawHandler() {
					@Override
					public void onDraw(DrawEvent event) {
						// TODO Auto-generated method stub
						display.getFileBrowserTreeGrid().getData().closeAll();
					}
				});
				
				display.getFileBrowserTreeGrid().setData(fileExplorerTree);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
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