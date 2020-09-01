package org.kobic.gwt.smart.closha.client.preference.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.controller.ProjectController;
import org.kobic.gwt.smart.closha.client.event.content.ClearTransferListGridEvent;
import org.kobic.gwt.smart.closha.client.preference.record.ShareUserListGridRecord;
import org.kobic.gwt.smart.closha.client.user.controller.UserController;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.preference.ShareInstancePipelineModel;
import org.kobic.gwt.smart.closha.shared.model.preference.ShareUserModel;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class SharePipelineUserWindow extends Window{
	
	private HandlerManager eventBus;
	private UserDto userDto;
	
	private ListGrid shareListGrid;
	private DynamicForm selectComboForm;
	private SelectItem selectItemWithIcons;

	private ToolStrip toolStrip;
	private ToolStripButton submitButton;
	
	private Map<String, String> mailMap;
	
	private List<ShareInstancePipelineModel> sharedProjectList;
	private List<ShareUserModel> shareUserList;
	
	private Map<String, String> config;
	
	@Override   
    protected void onInit() {
		mailMap = new HashMap<String, String>();
		
		sharedProjectList = new ArrayList<ShareInstancePipelineModel>();
		shareUserList = new ArrayList<ShareUserModel>();
	}
	
	private void setEvent(){
		
		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				if(sharedProjectList.size() != 0 && selectItemWithIcons.getValueAsString() != null){
					
					if(selectItemWithIcons.getSelectedRecords().length != 0){
						
						//Progress Start..
						//final Window progressBarWindow = new ProgressWindow(Messages.PROJECT_SHARE_TITLE, Messages.PROJECT_SHARE_LABEL_CONTENT, 50);
						//progressBarWindow.show();
						//progressBarWindow.destroy();
						
						String userID = ""; 

						List<String> pipeline = new ArrayList<String>();
						List<String> user = new ArrayList<String>();
						
						for(ShareInstancePipelineModel in : sharedProjectList){
							pipeline.add(in.getInstanceName());
						}
						
						for(int i = 0; i < selectItemWithIcons.getSelectedRecords().length; i++){
							
							userID = selectItemWithIcons.getSelectedRecords()[i].getAttributeAsString("share");
							
							ShareUserModel shareUserModel = new ShareUserModel();
							shareUserModel.setUserID(userID);
							shareUserModel.setEmail(mailMap.get(userID));
							
							shareUserList.add(shareUserModel);
							user.add(userID);
						}
						
						String mgs = CommonUtilsGwt.join(pipeline, ",", "No information") + 
								"The analysis pipeline is shared with the selected user [" + 
								CommonUtilsGwt.join(user, ",", "No information") + "], including the execution parameters, and input/output data.\n\n" + 
								"Upon sharing the analysis pipeline, the details on sharing will be sent via email.";
						
						SC.say(mgs, new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								// TODO Auto-generated method stub
								destroy();
								eventBus.fireEvent(new ClearTransferListGridEvent());
							}
						});
						
						ProjectController.Util.getInstance().sharingProjects(config, userDto.getUserId(), userDto.getEmailAdress(), sharedProjectList, shareUserList, 
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
					}else{
						SC.say("Select one or more users");
					}
				}else{
					SC.say("Select one or more users to share with and an analysis pipeline project.");
				}
			}
		});
	}
	
	private void shareListGridDataBinding(List<ShareInstancePipelineModel> list){
		ShareUserListGridRecord[] records = new ShareUserListGridRecord[list.size()];
		
		for(int i = 0; i < records.length; i++){
			ShareInstancePipelineModel model = list.get(i);
			records[i] = new ShareUserListGridRecord(model.getInstanceID(), model.getInstanceName());
		}
		shareListGrid.setData(records);
	}
	
	public SharePipelineUserWindow(HandlerManager eventBus, Map<String, String> config, UserDto userDto, final List<ShareInstancePipelineModel> list) {
		
		this.eventBus = eventBus;
		this.userDto = userDto;
		this.config = config;
		
		setTitle("Share Project Window");
		setWidth(500);
		setHeight(400);
		setIsModal(true);
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		centerInPage();
		setAutoCenter(true);
		setHeaderIcon("closha/icon/cluster16.gif");
		
		UserController.Util.getInstance().getUsers(new AsyncCallback<List<UserDto>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}

			@Override
			public void onSuccess(List<UserDto> result) {
				// TODO Auto-generated method stub
				
				Collections.sort(result, new Comparator<UserDto>(){
				      public int compare(UserDto obj1, UserDto obj2){
				            // TODO Auto-generated method stub
				            return obj1.getUserId().compareToIgnoreCase(obj2.getUserId());
				      }
				});

				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				LinkedHashMap<String, String> valueIcons = new LinkedHashMap<String, String>();

				for (UserDto userDto : result) {
					mailMap.put(userDto.getUserId(), userDto.getEmailAdress());
					valueMap.put(userDto.getUserId(), userDto.getUserId());
					valueIcons.put(userDto.getUserId(), "closha/icon/user_add.png");
				}

				selectItemWithIcons = new SelectItem();
				selectItemWithIcons.setWidth("*");
				selectItemWithIcons.setMultiple(true);
				selectItemWithIcons.setTitle("KSSO Users");
				selectItemWithIcons.setWrapTitle(false);
				selectItemWithIcons.setName("share");
				selectItemWithIcons.setValueMap(valueMap);
				selectItemWithIcons.setValueIcons(valueIcons);

				selectComboForm = new DynamicForm();
				selectComboForm.setWidth100();
				selectComboForm.setItems(selectItemWithIcons);

				ListGridField instanceNameField = new ListGridField(
						"instanceName", "Project Name");
				instanceNameField.setAlign(Alignment.CENTER);

				ListGridField instanceIDField = new ListGridField(
						"instanceID", "Project ID");
				instanceIDField.setAlign(Alignment.CENTER);

				shareListGrid = new ListGrid();
				shareListGrid.setHeight100();
				shareListGrid.setWidth100();
				shareListGrid.setShowAllRecords(true);
				shareListGrid.setShowRowNumbers(true);
				shareListGrid.setCellHeight(32);
				shareListGrid.setEmptyMessage("There are no data.");
				shareListGrid.setFields(instanceNameField,
						instanceIDField);

				shareListGridDataBinding(list);
				sharedProjectList.addAll(list);

				submitButton = new ToolStripButton("Share");
				submitButton.setIcon("closha/icon/accept.png");

				toolStrip = new ToolStrip();
				toolStrip.addFill();
				toolStrip.addButton(submitButton);
				
				VLayout layout = new VLayout();
				layout.setHeight100();
				layout.setWidth100();
				layout.setMargin(5);
				layout.addMember(selectComboForm);
				layout.addMember(shareListGrid);
				layout.addMember(toolStrip);

				addItem(layout);
				setEvent();
			}
		});
	}
}