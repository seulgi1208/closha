package org.kobic.gwt.smart.closha.client.frame.right.component;

import java.util.ArrayList;
import java.util.List;

import org.kobic.gwt.smart.closha.client.controller.OntologyController;
import org.kobic.gwt.smart.closha.client.frame.right.event.ModuleOntologyDataEvent;
import org.kobic.gwt.smart.closha.client.frame.right.event.PipelineOntologyDataEvent;
import org.kobic.gwt.smart.closha.client.frame.right.record.OntologyListGridRecord;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.ontology.OntologyModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class AddOntologyWindow extends Window{
	
	private HandlerManager eventBus;
	private ButtonItem addButton;
	private DynamicForm form;
	private TextItem newOntologyTextItem;
	private ListGrid ontologyListGrid;
	private ToolStrip toolStrip;
	private ToolStripButton deleteButton;
	
	private int TYPE;
	private String title;
	private List<OntologyModel> ontologyList;
	
	private void init(){
		bind();
	}
	
	private void bind(){
		setOntologyListGridInit();
		setNewOntologyWindowCloseEvent();
		setDeleteButtonEvent();
		setAddButtonEvent();
	}
	
	private void setNewOntologyWindowCloseEvent(){
		this.addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				// TODO Auto-generated method stub
				
				if(TYPE == Constants.MODULE_TYPE){
					eventBus.fireEvent(new ModuleOntologyDataEvent());
				}else{
					eventBus.fireEvent(new PipelineOntologyDataEvent());
				}
				
				destroy();
			}
		});
	}
	
	private void setOntologyListGridInit(){
		setOntologyListGridDataBinding(ontologyList);
	}
	
	private void setOntologyListGridDataReload(){
		OntologyController.Util.getInstance().getOntologyList(TYPE,
				new AsyncCallback<List<OntologyModel>>() {
					@Override
					public void onSuccess(List<OntologyModel> ontologyList) {
						// TODO Auto-generated method stub
						setOntologyListGridDataBinding(ontologyList);
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						SC.logDebug(caught.getMessage());
					}
				});
	}
	
	private void setAddButtonEvent(){
		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				OntologyController.Util.getInstance().addOntology(
						newOntologyTextItem.getValueAsString(), TYPE,
						new AsyncCallback<Void>() {
							@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						SC.logDebug(caught.getMessage());
					}
					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						setOntologyListGridDataReload();
					}
				});
			}
		});
	}
	
	private void setDeleteButtonEvent(){
		deleteButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				// TODO Auto-generated method stub
				
				final List<String> idList = new ArrayList<String>();
				final List<String> nameList = new ArrayList<String>();
				
				for(ListGridRecord record : ontologyListGrid.getSelectedRecords()){
					idList.add(record.getAttributeAsString("id"));
					nameList.add(record.getAttributeAsString("name"));
				}
				
				SC.confirm(nameList.toString(), new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						// TODO Auto-generated method stub
						if(value){
							OntologyController.Util.getInstance().deleteOntology(idList, new AsyncCallback<Void>() {
								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									SC.logDebug(caught.getMessage());
								}
								@Override
								public void onSuccess(Void result) {
									// TODO Auto-generated method stub
									setOntologyListGridDataReload();
								}
							});
						}
					}
				});	
			}
		});
	}
	
	private void setOntologyListGridDataBinding(List<OntologyModel> ontologyList){
		
		int size = ontologyList.size();
		
		OntologyListGridRecord[] records = new OntologyListGridRecord[size];
		
		for(int i = 0; i < ontologyList.size(); i++){
			OntologyModel om = ontologyList.get(i);
			
			records[i] = new OntologyListGridRecord(om.getOntologyID(),
					om.getOntologyName(), om.getType());
		}
		
		ontologyListGrid.setData(records);
	}
	
	public AddOntologyWindow(List<OntologyModel> ontologyList, int TYPE,
			HandlerManager eventBus) {
		
		this.ontologyList = ontologyList;
		this.TYPE = TYPE;
		this.eventBus = eventBus;
		
		ListGridField ontologyIdField = new ListGridField("id", "Ontology ID");
		ontologyIdField.setCanEdit(false);
		ontologyIdField.setAlign(Alignment.CENTER);
		
		ListGridField ontologyNameField = new ListGridField("name", "Ontology");
		ontologyNameField.setCanEdit(true);
		ontologyNameField.setAlign(Alignment.LEFT);
		
		ontologyListGrid = new ListGrid();
		ontologyListGrid.setHeight100();
		ontologyListGrid.setWidth100();
		ontologyListGrid.setShowRowNumbers(true);
		ontologyListGrid.setSelectionType(SelectionStyle.SIMPLE);  
		ontologyListGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);  
		ontologyListGrid.setEmptyMessage("There are no data.");
		ontologyListGrid.setFields(ontologyIdField, ontologyNameField);
		
		newOntologyTextItem = new TextItem();
		newOntologyTextItem.setName("newOntology");
		newOntologyTextItem.setTitle("Add Ontology");
		newOntologyTextItem.setWidth("*");

		addButton = new ButtonItem("add", "Add");
		addButton.setStartRow(false);
		addButton.setWidth(80);
		addButton.setIcon("closha/icon/folder_add.png");

		form = new DynamicForm();
		form.setIsGroup(false);
		form.setWidth100();
		form.setHeight(30);
		form.setNumCols(3);
		form.setColWidths(100, "*", "*");
		form.setCanDragResize(false);
		form.setAutoFocus(true);
		form.setFields(newOntologyTextItem, addButton);
		
		deleteButton = new ToolStripButton("Delete");
		deleteButton.setIcon("[SKIN]actions/remove.png");
		
		toolStrip = new ToolStrip();
		toolStrip.addFill();
		toolStrip.addButton(deleteButton);
		
		VLayout wrapperLayout = new VLayout();
		wrapperLayout.setHeight100();
		wrapperLayout.setWidth100();
		wrapperLayout.addMembers(form, ontologyListGrid, toolStrip);
		
		if(TYPE == Constants.MODULE_TYPE){
			title = "Register Analysis Program Ontology Window";
		}else{
			title = "Register Analysis Pipeline Ontology Window";
		}
		
		setIsModal(true);  
        setShowModalMask(true); 
        setWidth(600);
		setHeight(400);
		setTitle(title);
		setHeaderIcon("closha/icon/chart_organisation_opened.png");
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setCanDragReposition(false);
		setCanDragResize(false);
		setShowShadow(false);
		centerInPage();
		setAutoCenter(true);
		addItem(wrapperLayout);
		
		init();
	}
}