package org.kobic.gwt.smart.closha.client.file.browser.component;

import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.file.explorer.component.FileExplorerTree;
import org.kobic.gwt.smart.closha.client.file.explorer.controller.HadoopFileSystemController;
import org.kobic.gwt.smart.closha.client.file.explorer.record.FileExplorerRecord;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.FileModel;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
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

public class FileCopyWindow extends Window{
	
	
	private ToolStripButton selectButton;
	
	private Tree fileBrowserTree;
	private TreeGrid fileBrowserTreeGrid;
	private TreeGridField fileNameField; 
	private TreeGridField modifyDateField;
	
	private Map<String, String> config;
	private UserDto userDto;
	private List<FileModel> source;
	
	@Override   
    protected void onInit() {
		setInitTreeDataBind(userDto.getUserId());
	}
	
	private void setInitTreeDataBind(String pathId){
		
		System.out.println("path id: " + pathId);
		
		HadoopFileSystemController.Util.getInstance().getRootDirectoryList(config, pathId, new AsyncCallback<List<FileModel>>() {
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
				
				fileBrowserTree = new FileExplorerTree();
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
					
					HadoopFileSystemController.Util.getInstance().getSubDirectoryList(config, userDto.getUserId(), event.getNode().getAttribute("filePath"), 
							event.getNode().getAttribute("parentId"), new AsyncCallback<List<FileModel>>() {
						@Override
						public void onSuccess(List<FileModel> list) {
							// TODO Auto-generated method stub
							
							if(list.size() == 0){
								SC.say("The folder does not have the data.");
							}else{
								FileExplorerRecord records[] = new FileExplorerRecord[list.size()];
								
								for(int i = 0; i < list.size(); i++){
									FileModel fm = list.get(i);
									
									records[i] = new FileExplorerRecord(fm.getId(), fm.getpId(), 
											fm.getName(), fm.getSize(), fm.getLastModifyDate(), fm.getLastAccessDate(), 
											fm.getType(), fm.getPath(), fm.getUser(), fm.getGroup(), fm.isDic(), fm.getIcon());
								}
								fileBrowserTree.addList(records, event.getNode());
							}
							
							
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
				
				if(record.getAttributeAsBoolean("isDic")){
					
					String target = record.getAttributeAsString("filePath");
					
					HadoopFileSystemController.Util.getInstance().copy(config, userDto, source, target, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							SC.say("File copy the process cannot access the file because it is being used by another process.");
						}

						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							
							SC.confirm("File copy start.", new BooleanCallback() {
								
								@Override
								public void execute(Boolean value) {
									// TODO Auto-generated method stub
									if(value){
										destroy();
									}
								}
							});
						}
					});
					
				}else{
					SC.warn("The select of the directory type is required.");
				}
			}
		});
	}
	
	public FileCopyWindow(Map<String, String> config, UserDto userDto, List<FileModel> source) {
		
		this.config = config;
		this.userDto = userDto;
		this.source = source;
		
		setIsModal(true);  
        setShowModalMask(true); 
        setWidth(600);
		setHeight(700);
		setTitle("Select Copy Directory");
		setHeaderIcon("closha/icon/file.png");
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setCanDragReposition(true);
		setCanDragResize(true);
		setShowShadow(false);
		centerInPage();
		setAutoCenter(true);
		
		fileNameField =  new TreeGridField("fileName", "File Name");
		modifyDateField =  new TreeGridField("lastModifyDate", "Last Modified Date");
		modifyDateField.setWidth(200);
		fileBrowserTreeGrid = new TreeGrid();          
		fileBrowserTreeGrid.setWidth100(); 
		fileBrowserTreeGrid.setHeight100();
		fileBrowserTreeGrid.setShowHeader(true);
		fileBrowserTreeGrid.setSelectionType(SelectionStyle.SINGLE);
		fileBrowserTreeGrid.setLeaveScrollbarGap(false);
		fileBrowserTreeGrid.setCanAcceptDroppedRecords(false);
		fileBrowserTreeGrid.setCanReparentNodes(false);
		fileBrowserTreeGrid.setShowConnectors(true);
		fileBrowserTreeGrid.setCanDragRecordsOut(false);
		fileBrowserTreeGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);  
		fileBrowserTreeGrid.setShowSelectedStyle(true);  
		fileBrowserTreeGrid.setShowPartialSelection(false);  
		fileBrowserTreeGrid.setCascadeSelection(false);  
		fileBrowserTreeGrid.setEmptyMessage("There are no data.");
		
		fileBrowserTreeGrid.setFields(fileNameField, modifyDateField);
		
		fileBrowserTree = new Tree();
		fileBrowserTree.setModelType(TreeModelType.PARENT);
		fileBrowserTree.setNameProperty("fileName");
		
		fileBrowserTree.setIdField("fileId");
		fileBrowserTree.setParentIdField("parentId");
		fileBrowserTree.setAttribute("fileSize", "fileSize", true);
		fileBrowserTree.setAttribute("fileModifyDate", "fileModifyDate", true);
		fileBrowserTree.setAttribute("fileType", "fileType", true);
		
		fileBrowserTree.setShowRoot(false);
		
		selectButton = new ToolStripButton();
		selectButton.setTitle("Confirm");
		selectButton.setIcon("closha/icon/accept.png");
		
		ToolStrip buttomToolStript = new ToolStrip();
		buttomToolStript.setWidth100();
		buttomToolStript.setAlign(Alignment.CENTER);
		buttomToolStript.addFill();
		buttomToolStript.addButton(selectButton);
		
		addItem(fileBrowserTreeGrid);
		addItem(buttomToolStript);
		
		setSubTreeDataBind();
		selectButtonEvent();
	}
}