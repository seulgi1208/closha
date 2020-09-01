package org.kobic.gwt.smart.closha.client.file.browser.component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.file.browser.event.SelectFileWindowEvent;
import org.kobic.gwt.smart.closha.client.file.explorer.component.FileExplorerTree;
import org.kobic.gwt.smart.closha.client.file.explorer.controller.HadoopFileSystemController;
import org.kobic.gwt.smart.closha.client.file.explorer.record.FileExplorerRecord;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.FileModel;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;

public class FileSelectWindow extends Window{
	
	private String formName;
	private String userID;
	private String parameterType;
	private String parameterKey;
	private String key;
	private String type;
	
	private HandlerManager eventBus;

	private ToolStripButton refreshButton;
	private ToolStripButton selectButton;
	
	private Tree fileBrowserTree;
	private TreeGrid fileBrowserTreeGrid;
	private TreeGridField fileNameField; 
	private TreeGridField modifyDateField;
	
	private Map<String, String> config;
	
	private SelectItem selectItem;
	
	private String extensions;
	
	@Override   
    protected void onInit() {
		setInitTreeDataBind(userID);
	}
	
	private List<FileModel> filter(List<FileModel> list){
		
		List<FileModel> file = new ArrayList<FileModel>();
		
		for(int i = 0; i < list.size(); i++){
			FileModel fm = list.get(i);
			
			if(fm.isDic()){
				file.add(fm);
			}else{
				if(CommonUtilsGwt.isExtension(extensions, fm.getName())){
					file.add(fm);
				}
			}
		}
		
		return file;
	}
	
	private void setInitTreeDataBind(String pathId){
		
		System.out.println("path id: " + pathId);
		
		HadoopFileSystemController.Util.getInstance().getRootDirectoryList(config, pathId, new AsyncCallback<List<FileModel>>() {
			@Override
			public void onSuccess(List<FileModel> res) {
				// TODO Auto-generated method stub
				
				List<FileModel> list = filter(res);
				
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
					
					HadoopFileSystemController.Util.getInstance().getSubDirectoryList(config, userID, event.getNode().getAttribute("filePath"), 
							event.getNode().getAttribute("parentId"), new AsyncCallback<List<FileModel>>() {
						@Override
						public void onSuccess(List<FileModel> res) {
							// TODO Auto-generated method stub
							
							List<FileModel> list = filter(res);
							
							if(list.size() == 0){
								SC.say("The folder does not have the data of the extension [" + extensions + "].");
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
	
	private void refreshButtonEvent(){
		refreshButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				fileBrowserTree.destroy();
				
				if(selectItem.getValueAsString().equals("public")){
					setInitTreeDataBind(config.get(Constants.PUBLIC_DATA_NAME_KEY));
				}else{
					setInitTreeDataBind(userID);
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
				
				//여기만 변경
				
				
				String selectType = record.getAttributeAsBoolean("isDic") ? "FOLDER" : "FILE";
				
				//선택 값이 reference 일 경우 폴더로 변환하여 비교 확인
				type = type.equals("REFERENCE") ? "FOLDER" : type;
				
				if(selectType.equals(type)){
					String fileName = record.getAttributeAsString("fileName");
					String filePath = record.getAttributeAsString("filePath");
					
					boolean res = false;
					
					if(type.equals("FILE")){
						if(CommonUtilsGwt.isExtension(extensions, filePath)){
							res = true;
						}else{
							SC.say("Invalid input type. Please choose the file with the [" + extensions
									+ "] extension.");
						}
					}else{
						
						TreeNode treeNode = fileBrowserTreeGrid.getSelectedRecord();
						Tree tree = new Tree();
						tree.setRoot(treeNode);
						
						SC.say(tree.getChildren(treeNode).length + " :AAA");
						
						if(tree.getChildren(treeNode).length != 0){
							res = true;
						}else{
							SC.say("19191919");
						}
					}
					
					if(res){
						eventBus.fireEvent(new SelectFileWindowEvent(formName, fileName, filePath, parameterType, parameterKey, key));
						destroy();
					}
				}else{
					if(type.equals("FILE")){
						SC.warn("The setting value of the file type is required.");
					}else{
						SC.warn("The setting value of the directory type is required.");
					}
				}
			}
		});
	}
	
	private void changeSelectEvent(){
		selectItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				// TODO Auto-generated method stub
				
				if(parameterType.equals(Constants.OUTPUT_PRAM)){
					SC.say("The public data path cannot be set as the output path.");
				}else{
					fileBrowserTree.destroy();
					
					if(selectItem.getValueAsString().equals("public")){
						setInitTreeDataBind(config.get(Constants.PUBLIC_DATA_NAME_KEY));
					}else{
						setInitTreeDataBind(userID);
					}
				}
			}
		});
	}

	public FileSelectWindow(Map<String, String> config, String userID,
			String formName, String parameterType, String parameterKey,
			String key, String type, String extensions, HandlerManager eventBus) {
		
		this.formName = formName;
		this.eventBus = eventBus;
		this.userID = userID;
		this.config = config;
		this.parameterType = parameterType;
		this.parameterKey = parameterKey;
		this.key = key;
		this.type = type;
		this.extensions = extensions;
		
		setIsModal(true);  
        setShowModalMask(true); 
        setWidth(600);
		setHeight(700);
		setTitle("Select Input File");
		setHeaderIcon("closha/icon/file.png");
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setCanDragReposition(true);
		setCanDragResize(true);
		setShowShadow(false);
		centerInPage();
		setAutoCenter(true);
		
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("person", "<b>Personal Data</b>");
		valueMap.put("public", "<b>Public Data</b>");
		
		LinkedHashMap<String, String> valueIcons = new LinkedHashMap<String, String>();
		valueIcons.put("person", "closha/icon/user.png");
		valueIcons.put("public", "closha/icon/group.png");
		
		selectItem = new SelectItem();  
	    selectItem.setWidth(150);  
	    selectItem.setShowTitle(false);  
	    selectItem.setDefaultValue("person");
	    selectItem.setValueMap(valueMap);
		selectItem.setValueIcons(valueIcons);
	    
		DynamicForm form = new DynamicForm();
	    form.setNumCols(1);  
	    form.setFields(selectItem);  
	    
	    ToolStrip topToolStript = new ToolStrip();
		topToolStript.setWidth100();
		topToolStript.addFill();
		topToolStript.addChild(form);
		
	    /**
	     * 
	     */
	    
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
		
		/*
		fileBrowserTreeGrid.addCellClickHandler(new CellClickHandler() {
			@Override
			public void onCellClick(CellClickEvent event) {
				// TODO Auto-generated method stub
				
                boolean checked = event.getRecord().getAttributeAsBoolean("checked");
                
                if(checked){
                	event.getRecord().setAttribute("checked", !checked);
                	fileBrowserTreeGrid.deselectRecord(event.getRecord());
                }else{
                	event.getRecord().setAttribute("checked", !checked);
                	fileBrowserTreeGrid.selectRecord(event.getRecord());
                }
                fileBrowserTreeGrid.saveAllEdits();
                fileBrowserTreeGrid.refreshFields();
			}
		});
		*/
		
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

		refreshButton = new ToolStripButton();
		refreshButton.setTitle("Refresh");
		refreshButton.setIcon("closha/icon/new_reset.png");
		
		selectButton = new ToolStripButton();
		selectButton.setTitle("Confirm");
		selectButton.setIcon("closha/icon/accept.png");
		
		ToolStrip buttomToolStript = new ToolStrip();
		buttomToolStript.setWidth100();
		buttomToolStript.setAlign(Alignment.CENTER);
		buttomToolStript.addFill();
		buttomToolStript.addButton(refreshButton);
		buttomToolStript.addButton(selectButton);
		
		addItem(topToolStript);
		addItem(fileBrowserTreeGrid);
		addItem(buttomToolStript);
		
		setSubTreeDataBind();
		refreshButtonEvent();
		selectButtonEvent();
		changeSelectEvent();
	}
}