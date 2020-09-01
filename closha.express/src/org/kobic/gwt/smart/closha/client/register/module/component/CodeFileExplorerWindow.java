package org.kobic.gwt.smart.closha.client.register.module.component;

import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.event.registration.SelectScriptFileEvent;
import org.kobic.gwt.smart.closha.client.file.browser.component.UploadWindow;
import org.kobic.gwt.smart.closha.client.file.explorer.record.FileExplorerRecord;
import org.kobic.gwt.smart.closha.client.unix.file.controller.UnixFileSystemController;
import org.kobic.gwt.smart.closha.shared.Messages;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.FileModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;

public class CodeFileExplorerWindow extends Window{
	
	private String formName;
	private String moduleName;
	private String userID;
	
	private HandlerManager eventBus;

	private ToolStrip toolStrip;
	private ToolStripButton selectButton;
	private ToolStripButton uploadButton;
	
	private Tree fileBrowserTree;
	private TreeGrid fileBrowserTreeGrid;
	private TreeGridField fileNameField; 
	
	private Map<String, String> config;
	
	@Override   
    protected void onInit() {
		setInitTreeDataBind();
	}
	
	private void init(){
		bind();
	}
	
	private void bind(){
		setSubTreeDataBind();
		selectButtonEvent();
		setUploadButtonEvent();
	}
	
	private void setInitTreeDataBind(){
		UnixFileSystemController.Util.getInstance().getRootDirectoryList(config, userID, new AsyncCallback<List<FileModel>>() {
			@Override
			public void onSuccess(List<FileModel> list) {
				// TODO Auto-generated method stub
				FileExplorerRecord records[] = new FileExplorerRecord[list.size()];
				
				for(int i = 0; i < list.size(); i++){
					FileModel fm = list.get(i);
					records[i] = new FileExplorerRecord(fm.getId(), fm.getpId(), 
							fm.getName(), fm.getSize(), fm.getLastModifyDate(), fm.getLastAccessDate(), 
							fm.getType(), fm.getPath(), fm.getUser(), fm.getGroup(), fm.isDic(), fm.getIcon());
				}
				fileBrowserTree.setData(records);
				fileBrowserTreeGrid.addDrawHandler(new DrawHandler() {
					@Override
					public void onDraw(DrawEvent event) {
						// TODO Auto-generated method stub
						fileBrowserTree.closeAll();
					}
				});
				fileBrowserTreeGrid.setData(fileBrowserTree);
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
		});
	}
	
	private void setSubTreeDataBind(){
		fileBrowserTreeGrid.addNodeClickHandler(new NodeClickHandler() {
			@Override
			public void onNodeClick(final NodeClickEvent event) {
				// TODO Auto-generated method stub
				if(event.getNode().getAttributeAsBoolean("isDic") && !event.getNode().getAttributeAsBoolean("closhaEvent")){
					
					event.getNode().setAttribute("closhaEvent", true);
					
					UnixFileSystemController.Util.getInstance().getSubDirectoryList(config, userID, event.getNode().getAttribute("filePath"), 
							event.getNode().getAttribute("parentId"), new AsyncCallback<List<FileModel>>() {
						@Override
						public void onSuccess(List<FileModel> list) {
							// TODO Auto-generated method stub
							FileExplorerRecord records[] = new FileExplorerRecord[list.size()];
							
							for(int i = 0; i < list.size(); i++){
								FileModel fm = list.get(i);
								
								records[i] = new FileExplorerRecord(fm.getId(), fm.getpId(), 
										fm.getName(), fm.getSize(), fm.getLastModifyDate(), fm.getLastAccessDate(), 
										fm.getType(), fm.getPath(), fm.getUser(), fm.getGroup(), fm.isDic(), fm.getIcon());
							}
							fileBrowserTree.addList(records, event.getNode());
						}
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println(caught.getCause() + ":" + caught.getMessage());
						}
					});
				}
			}
		});
	}
	
	private void selectButtonEvent(){
		selectButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub 
				ListGridRecord record = fileBrowserTreeGrid.getSelectedRecord();
				
				if(record != null && !record.getAttributeAsBoolean("isDic")){
					String filePath = record.getAttributeAsString("filePath");
					eventBus.fireEvent(new SelectScriptFileEvent(formName, filePath));
					destroy();
				}else{
					SC.warn("Select code file!!");
				}
					
			}
		});
	}

	private void setUploadButtonEvent(){
		uploadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				SC.confirm(Messages.UPLOAD_SCRIPT_FILE_MESSAGE, new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						// TODO Auto-generated method stub
						if(value){
							UnixFileSystemController.Util.getInstance().makeUploadScriptFolder(config, 
									userID, moduleName, new AsyncCallback<String>() {
								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									SC.logDebug(caught.getMessage());
								}
								@Override
								public void onSuccess(String path) {
									// TODO Auto-generated method stub
									destroy();

									Window uploadWindow = new UploadWindow(path, eventBus);
									uploadWindow.show();
								}
							});
						}
					}
				});
			}
		});
	}
	
	public CodeFileExplorerWindow(String userID, String moduleName,
			String formName, HandlerManager eventBus, Map<String, String> config) {
		
		this.formName = formName;
		this.eventBus = eventBus;
		this.userID = userID;
		this.moduleName = moduleName;
		this.config = config;
		
		setIsModal(true);  
        setShowModalMask(true); 
        setWidth(300);
		setHeight(400);
		setTitle("Script Explorer");
		setHeaderIcon("closha/icon/file.png");
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setCanDragReposition(false);
		setCanDragResize(false);
		setShowShadow(false);
		centerInPage();
		setAutoCenter(true);
		setCanDragResize(true);  

		fileNameField =  new TreeGridField("fileName", "File Name");
		
		fileBrowserTreeGrid = new TreeGrid();          
		fileBrowserTreeGrid.setWidth100(); 
		fileBrowserTreeGrid.setHeight100();
		fileBrowserTreeGrid.setShowHeader(false);
		fileBrowserTreeGrid.setSelectionType(SelectionStyle.SINGLE);
		fileBrowserTreeGrid.setLeaveScrollbarGap(false);
		fileBrowserTreeGrid.setAnimateFolders(true);
		fileBrowserTreeGrid.setAnimateFolderSpeed(100);
		fileBrowserTreeGrid.setCanAcceptDroppedRecords(true);
		fileBrowserTreeGrid.setCanReparentNodes(false);
		fileBrowserTreeGrid.setShowConnectors(true);
		fileBrowserTreeGrid.setCanDragRecordsOut(true);
		fileBrowserTreeGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);  
		fileBrowserTreeGrid.setShowSelectedStyle(true);  
		fileBrowserTreeGrid.setShowPartialSelection(false);  
		fileBrowserTreeGrid.setCascadeSelection(false);  
		fileBrowserTreeGrid.setFields(fileNameField);
		fileBrowserTreeGrid.setEmptyMessage("There are no data.");
		
		fileBrowserTree = new Tree();
		fileBrowserTree.setModelType(TreeModelType.PARENT);
		fileBrowserTree.setNameProperty("fileName");
		fileBrowserTree.setIdField("fileId");
		fileBrowserTree.setParentIdField("parentId");
		fileBrowserTree.setAttribute("fileSize", "fileSize", true);
		fileBrowserTree.setAttribute("fileModifyDate", "fileModifyDate", true);
		fileBrowserTree.setAttribute("fileType", "fileType", true);
		fileBrowserTree.setShowRoot(false);
		
		selectButton = new ToolStripButton("Select");
		selectButton.setIcon("closha/icon/accept.png");

		uploadButton = new ToolStripButton("Upload");
		uploadButton.setIcon("closha/icon/upload.png");
		
		toolStrip = new ToolStrip();
		toolStrip.addFill();
		toolStrip.addButton(uploadButton);
		toolStrip.addButton(selectButton);
		
		addItem(fileBrowserTreeGrid);
		addItem(toolStrip);
		
		init();
	}
}