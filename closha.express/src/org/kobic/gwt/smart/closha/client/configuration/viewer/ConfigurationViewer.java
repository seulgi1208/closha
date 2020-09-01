package org.kobic.gwt.smart.closha.client.configuration.viewer;

import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.client.configuration.presenter.ConfigurationPresenter;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ConfigurationViewer extends VLayoutWidget implements
		ConfigurationPresenter.Display {
	
	private ListGrid pipelineListGrid;
	private ListGrid moduleListGrid;
	
	private ToolStrip pipelineToolStrip;
	private ToolStripButton pipelineDeleteToolStripButton;
	
	private ToolStrip moduleToolStrip;
	private ToolStripButton moduleDeleteToolStripButton;
	private ToolStripButton moduleCheckToolStripButton;
	private ToolStripButton moduleUnCheckToolStripButton;
	private ToolStripButton moduleRefreshToolStripButton;

	public ConfigurationViewer(){
		
		ListGridField pipelineIconField = new ListGridField("pipeline_icon", "Type");
		pipelineIconField.setAlign(Alignment.CENTER);
		pipelineIconField.setType(ListGridFieldType.IMAGE);
		pipelineIconField.setWidth(50);
		
		ListGridField registerIDField = new ListGridField("registerID", "Register ID");
		registerIDField.setAlign(Alignment.CENTER);
		registerIDField.setHidden(true);
		registerIDField.setWidth(250);
		
		ListGridField pipelineNameField = new ListGridField("pipelineName", "Pipeline Name");
		pipelineNameField.setAlign(Alignment.LEFT);
		pipelineNameField.setWidth(200);
		
		ListGridField pipelineDescField = new ListGridField("pipelineDesc", "Pipeline Description");
		pipelineDescField.setAlign(Alignment.CENTER);
		pipelineDescField.setWrap(false);
		pipelineDescField.setWidth("*");
		
		ListGridField pipelineAuthorField = new ListGridField("author", "Author");
		pipelineAuthorField.setAlign(Alignment.CENTER);
		pipelineAuthorField.setWidth(100);
		
		ListGridField pipelineRegisterDateField = new ListGridField("registerDate", "Registration Date");
		pipelineRegisterDateField.setAlign(Alignment.CENTER);
		pipelineRegisterDateField.setWidth(250);
		pipelineRegisterDateField.setHidden(true);
		
		ListGridField pipelineUpdateField = new ListGridField("updateDate", "Update Date");
		pipelineUpdateField.setAlign(Alignment.CENTER);
		pipelineUpdateField.setHidden(true);
		pipelineUpdateField.setWidth(250);
		
		ListGridField pipelineVersionField = new ListGridField("version", "Version");
		pipelineVersionField.setAlign(Alignment.CENTER);
		pipelineVersionField.setWidth(50);
		
		pipelineListGrid = new ListGrid();
		pipelineListGrid.setWidth100();
		pipelineListGrid.setHeight100();
		pipelineListGrid.setShowAllRecords(false);
		pipelineListGrid.setFixedRecordHeights(false);  
		pipelineListGrid.setWrapCells(true);
		pipelineListGrid.setShowRowNumbers(true);
		pipelineListGrid.setShowRollOver(true);
		pipelineListGrid.setSelectionType(SelectionStyle.SIMPLE);
		pipelineListGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		pipelineListGrid.setEmptyMessage("There are no data.");
		pipelineListGrid.setFields(pipelineIconField, registerIDField, pipelineNameField,
				pipelineDescField, pipelineAuthorField, pipelineRegisterDateField,
				pipelineUpdateField, pipelineVersionField);
		
		pipelineDeleteToolStripButton = new ToolStripButton("Delete");
		pipelineDeleteToolStripButton.setIcon("closha/icon/delete.png");

		pipelineToolStrip = new ToolStrip();
		pipelineToolStrip.addFill();
		pipelineToolStrip.addButton(pipelineDeleteToolStripButton);
		
		VLayout pipelineLayout = new VLayout();
		pipelineLayout.setHeight100();
		pipelineLayout.setWidth100();
		pipelineLayout.addMembers(pipelineListGrid, pipelineToolStrip);
		
		ListGridField moduleIconField = new ListGridField("module_icon", "Type");
		moduleIconField.setAlign(Alignment.CENTER);
		moduleIconField.setType(ListGridFieldType.IMAGE);
		moduleIconField.setWidth(100);
		
		ListGridField accessIDField = new ListGridField("moduleID", "Module ID");
		accessIDField.setAlign(Alignment.CENTER);
		accessIDField.setHidden(true);
		accessIDField.setWidth(250);
		
		ListGridField moduleNameField = new ListGridField("moduleName", "Module Name");
		moduleNameField.setAlign(Alignment.LEFT);
		moduleNameField.setWidth("*");
		
		ListGridField moduleAuthorField = new ListGridField("author", "Author");
		moduleAuthorField.setAlign(Alignment.CENTER);
		moduleAuthorField.setWidth(150);
		
		ListGridField moduleRegisterDateField = new ListGridField("registerDate", "Registration Date");
		moduleRegisterDateField.setAlign(Alignment.CENTER);
		moduleRegisterDateField.setWidth(250);
		moduleRegisterDateField.setHidden(true);
		
		ListGridField moduleUpdateField = new ListGridField("updateDate", "Update Date");
		moduleUpdateField.setAlign(Alignment.CENTER);
		moduleUpdateField.setCanEdit(false);
		moduleUpdateField.setWidth(250);
		moduleUpdateField.setHidden(true);
		
		ListGridField descriptionField = new ListGridField("moduleDesc", "Module Description");
		descriptionField.setAlign(Alignment.CENTER);
		descriptionField.setCanEdit(false);
		descriptionField.setWrap(false);
		descriptionField.setWidth("*");
		descriptionField.setHidden(true);
		
		ListGridField moduleVersionField = new ListGridField("version", "Version");
		moduleVersionField.setAlign(Alignment.CENTER);
		moduleVersionField.setCanEdit(false);
		moduleVersionField.setWidth(150);
		
		ListGridField scriptNameField = new ListGridField("scriptPath", "Script Path");
		scriptNameField.setAlign(Alignment.CENTER);
		scriptNameField.setWidth(120);
		scriptNameField.setHidden(true);
		
		ListGridField isOpenField = new ListGridField("isOpen", "Open");
		isOpenField.setAlign(Alignment.CENTER);
		isOpenField.setWidth(100);
		
		ListGridField isMultiField = new ListGridField("isMulti", "Multi");
		isMultiField.setAlign(Alignment.CENTER);
		isMultiField.setWidth(100);
		
		ListGridField isAdminField = new ListGridField("isAdmin", "Admin");
		isAdminField.setAlign(Alignment.CENTER);
		isAdminField.setWidth(100);
		
		ListGridField isCheckField = new ListGridField("isCheck", "Check");
		isCheckField.setAlign(Alignment.CENTER);
		isCheckField.setWidth(100);
		
		moduleListGrid = new ListGrid();
		moduleListGrid.setWidth100();
		moduleListGrid.setHeight100();
		moduleListGrid.setShowAllRecords(true);
		moduleListGrid.setFixedRecordHeights(false);  
		moduleListGrid.setWrapCells(true);
		moduleListGrid.setShowRowNumbers(true);
		moduleListGrid.setShowRollOver(true);
		moduleListGrid.setCanReorderRecords(true);
		moduleListGrid.setSelectionType(SelectionStyle.SIMPLE);
		moduleListGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		moduleListGrid.setEmptyMessage("There are no data.");
		moduleListGrid.setFields(moduleIconField, accessIDField,
				moduleNameField, scriptNameField, descriptionField,
				moduleAuthorField, moduleRegisterDateField, moduleUpdateField,
				moduleVersionField, isOpenField, isMultiField, isAdminField,
				isCheckField);

		moduleCheckToolStripButton = new ToolStripButton("Able");
		moduleCheckToolStripButton.setIcon("closha/icon/add.png");
		
		moduleUnCheckToolStripButton = new ToolStripButton("Disable");
		moduleCheckToolStripButton.setIcon("closha/icon/add_Over.png");
		
		moduleDeleteToolStripButton = new ToolStripButton("Delete");
		moduleDeleteToolStripButton.setIcon("closha/icon/delete.png");
		
		moduleRefreshToolStripButton = new ToolStripButton("Refresh");
		moduleRefreshToolStripButton.setIcon("closha/icon/new_refresh.png");
		
		moduleToolStrip = new ToolStrip();
		moduleToolStrip.addFill();
		moduleToolStrip.addButton(moduleCheckToolStripButton);
		moduleToolStrip.addButton(moduleUnCheckToolStripButton);
		moduleToolStrip.addButton(moduleDeleteToolStripButton);
		moduleToolStrip.addButton(moduleRefreshToolStripButton);
		
		VLayout moduleLayout = new VLayout();
		moduleLayout.setHeight100();
		moduleLayout.setWidth100();
		moduleLayout.addMembers(moduleListGrid, moduleToolStrip);
		
		Tab tTab1 = new Tab("Analysis Pipeline Settings", "closha/icon/chart_organisation_opened.png");
		tTab1.setWidth(200);
		tTab1.setPane(pipelineLayout);

		Tab tTab2 = new Tab("Analysis Program Settings", "closha/icon/application_form.png");
		tTab2.setWidth(200);
		tTab2.setPane(moduleLayout);
		
		final TabSet tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setWidth100();
		tabSet.setHeight100();
		tabSet.addTab(tTab1);
		tabSet.addTab(tTab2);
		
		addMembers(tabSet);
	}
	
	@Override
	public VLayout asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ListGrid getModuleListGrid() {
		// TODO Auto-generated method stub
		return moduleListGrid;
	}
	
	@Override
	public ListGrid getPipelineListGrid() {
		// TODO Auto-generated method stub
		return pipelineListGrid;
	}

	@Override
	public ToolStripButton getModuleDeleteButton() {
		// TODO Auto-generated method stub
		return moduleDeleteToolStripButton;
	}

	@Override
	public ToolStripButton getPipelineDeleteButton() {
		// TODO Auto-generated method stub
		return pipelineDeleteToolStripButton;
	}

	@Override
	public ToolStripButton getModuleCheckButton() {
		// TODO Auto-generated method stub
		return moduleCheckToolStripButton;
	}

	@Override
	public ToolStripButton getModuleRefreshButton() {
		// TODO Auto-generated method stub
		return moduleRefreshToolStripButton;
	}

	@Override
	public ToolStripButton getModuleUnCheckToolStripButton() {
		// TODO Auto-generated method stub
		return moduleUnCheckToolStripButton;
	}
}