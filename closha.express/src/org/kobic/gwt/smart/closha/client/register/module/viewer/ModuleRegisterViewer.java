package org.kobic.gwt.smart.closha.client.register.module.viewer;

import java.util.LinkedHashMap;

import org.kobic.gwt.smart.closha.client.register.module.presenter.ModuleRegisterPresenter;
import org.kobic.gwt.smart.closha.shared.Constants;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ModuleRegisterViewer extends Window implements
		ModuleRegisterPresenter.Display {

	private DynamicForm form;
	private DynamicForm controllerForm;
	private TextItem moduleNameTextItem;
	private TextItem authorTextItem;
	private TextItem createDateTextItem;
	private TextItem externalCmdTextItem;
	private TextItem scriptPathTextItem;
	private TextItem urlLinkTextItem;
	private TextAreaItem descTextItem;
	private SelectItem ontologySelectItem;
	private SelectItem moduleSelectItem;
	private SelectItem appFormatSelectItem;
	private SelectItem languageSelectItem;
	private SelectItem parameterTypeSelectItem;
	private SelectItem dataTypeSelectItem;
	private SelectItem iconSelectItem;
	private SelectItem nextLinkedSelectItem;
	private TextItem versionSelectItem;
	private SelectItem openSelectItem;
	private SelectItem multiSelectItem;
	private SelectItem parallelSelectItem;
	private SelectItem alignmentSelectItem;
	private ImgButton addButton;
	private ImgButton removeButton;
	private ListGrid parameterListGrid;
	private ToolStrip toolstrip;
	private ToolStripButton submitButton;
	private ToolStripButton resetButton;
	
	public ModuleRegisterViewer(){
		
		setWidth(700);
		setHeight(850);
		setIsModal(true);
		setShowModalMask(true);
		setShowShadow(true);
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setCanDragReposition(false);
		setCanDragResize(true);
		centerInPage();
		setAutoCenter(true);
		setHeaderIcon("closha/icon/application_form_add.gif");
		setTitle("Register Analysis Program");
	
		moduleNameTextItem = new TextItem();
		moduleNameTextItem.setName("name");
		moduleNameTextItem.setTitle("Program Name");
		moduleNameTextItem.setWidth("*");
		moduleNameTextItem.setRequired(true);

		authorTextItem = new TextItem();
		authorTextItem.setName("author");
		authorTextItem.setTitle("Writer");
		authorTextItem.setWidth("*");

		createDateTextItem = new TextItem();
		createDateTextItem.setName("date");
		createDateTextItem.setTitle("Date");
		createDateTextItem.setWidth("*");
		
		versionSelectItem = new TextItem();
		versionSelectItem.setWidth("*");
		versionSelectItem.setTitle("Version");
		versionSelectItem.setName("version");
		versionSelectItem.setRequired(true);
		
		LinkedHashMap<Boolean, String> openValueMap = new LinkedHashMap<Boolean, String>();
		openValueMap.put(true, "Public");
		openValueMap.put(false, "Private");
		
		openSelectItem = new SelectItem();
		openSelectItem.setWidth("*");
		openSelectItem.setTitle("Public");
		openSelectItem.setName("open");
		openSelectItem.setValueMap(openValueMap);
		openSelectItem.setDefaultValue(true);
		openSelectItem.setRequired(true);
		
		LinkedHashMap<Boolean, String> multiValueMap = new LinkedHashMap<Boolean, String>();
		multiValueMap.put(true, "Use");
		multiValueMap.put(false, "Not used");
		
		multiSelectItem = new SelectItem();
		multiSelectItem.setWidth("*");
		multiSelectItem.setTitle("Multi Core");
		multiSelectItem.setName("multi");
		multiSelectItem.setValueMap(multiValueMap);
		multiSelectItem.setDefaultValue(false);
		multiSelectItem.setRequired(true);
		
		LinkedHashMap<Boolean, String> parallelValueMap = new LinkedHashMap<Boolean, String>();
		parallelValueMap.put(true, "Use");
		parallelValueMap.put(false, "Not used");
		
		parallelSelectItem = new SelectItem();
		parallelSelectItem.setWidth("*");
		parallelSelectItem.setTitle("Parallel");
		parallelSelectItem.setName("parallel");
		parallelSelectItem.setValueMap(parallelValueMap);
		parallelSelectItem.setDefaultValue(false);
		parallelSelectItem.setRequired(true);
		
		LinkedHashMap<Boolean, String> alignmnetValueMap = new LinkedHashMap<Boolean, String>();
		alignmnetValueMap.put(true, "Yes");
		alignmnetValueMap.put(false, "No");
		
		alignmentSelectItem = new SelectItem();
		alignmentSelectItem.setWidth("*");
		alignmentSelectItem.setTitle("Alignment Tool");
		alignmentSelectItem.setName("alignment");
		alignmentSelectItem.setValueMap(alignmnetValueMap);
		alignmentSelectItem.setDefaultValue(false);
		alignmentSelectItem.setRequired(true);
		
		ontologySelectItem = new SelectItem();
		ontologySelectItem.setWidth("*");
		ontologySelectItem.setTitle("Analysis Field");
		ontologySelectItem.setName("ontology");
		ontologySelectItem.setRequired(true);
		
		LinkedHashMap<String, String> iconValueMap = new LinkedHashMap<String, String>();
		iconValueMap.put("module_1", "MODULE_1");
		iconValueMap.put("module_2", "MODULE_2");
		iconValueMap.put("module_3", "MODULE_3");
		iconValueMap.put("module_4", "MODULE_4");
		iconValueMap.put("module_5", "MODULE_5");
		iconValueMap.put("module_6", "MODULE_6");
		iconValueMap.put("module_7", "MODULE_7");
		iconValueMap.put("module_8", "MODULE_8");
//		iconValueMap.put("module_9", "MODULE_9");
//		iconValueMap.put("module_10", "MODULE_10");
//		iconValueMap.put("module_11", "MODULE_11");
//		iconValueMap.put("module_12", "MODULE_12");
//		iconValueMap.put("module_13", "MODULE_13");
//		iconValueMap.put("module_14", "MODULE_14");
//		iconValueMap.put("module_15", "MODULE_15");
//		iconValueMap.put("module_16", "MODULE_16");
//		iconValueMap.put("module_17", "MODULE_17");
//		iconValueMap.put("module_18", "MODULE_18");
//		iconValueMap.put("module_19", "MODULE_19");
//		iconValueMap.put("module_20", "MODULE_20");
//		iconValueMap.put("module_21", "MODULE_21");
//		iconValueMap.put("module_22", "MODULE_22");
//		iconValueMap.put("module_23", "MODULE_23");
//		iconValueMap.put("module_24", "MODULE_24");
//		iconValueMap.put("module_25", "MODULE_25");
//		iconValueMap.put("module_29", "module_29");

		LinkedHashMap<String, String> iconValue = new LinkedHashMap<String, String>();
		iconValue.put("module_1", "module_1");
		iconValue.put("module_2", "module_2");
		iconValue.put("module_3", "module_3");
		iconValue.put("module_4", "module_4");
		iconValue.put("module_5", "module_5");
		iconValue.put("module_6", "module_6");
		iconValue.put("module_7", "module_7");
		iconValue.put("module_8", "module_8");
//		iconValue.put("module_9", "module_9");
//		iconValue.put("module_10", "module_10");
//		iconValue.put("module_11", "module_11");
//		iconValue.put("module_12", "module_12");
//		iconValue.put("module_13", "module_13");
//		iconValue.put("module_14", "module_14");
//		iconValue.put("module_15", "module_15");
//		iconValue.put("module_16", "module_16");
//		iconValue.put("module_17", "module_17");
//		iconValue.put("module_18", "module_18");
//		iconValue.put("module_19", "module_19");
//		iconValue.put("module_20", "module_20");
//		iconValue.put("module_21", "module_21");
//		iconValue.put("module_22", "module_22");
//		iconValue.put("module_23", "module_23");
//		iconValue.put("module_24", "module_24");
//		iconValue.put("module_25", "module_25");
//		iconValue.put("module_29", "module_29");
		
		iconSelectItem = new SelectItem();
		iconSelectItem.setWidth("*");
		iconSelectItem.setTitle("Icon");
		iconSelectItem.setName("iconType");
		iconSelectItem.setImageURLPrefix("closha/module/");
		iconSelectItem.setImageURLSuffix(".png");
		iconSelectItem.setValueMap(iconValueMap);
		iconSelectItem.setValueIcons(iconValue);
		iconSelectItem.setRequired(true);

		descTextItem = new TextAreaItem();
		descTextItem.setName("desc");
		descTextItem.setTitle("Description");
		descTextItem.setLength(1500);
		descTextItem.setColSpan(2);
		descTextItem.setWidth("*");
		descTextItem.setHeight(80);
		descTextItem.setRequired(true);
		
		urlLinkTextItem = new TextItem();
		urlLinkTextItem.setName("urlLink");
		urlLinkTextItem.setTitle("URL");
		urlLinkTextItem.setWidth("*");
		urlLinkTextItem.setRequired(true);

		moduleSelectItem = new SelectItem();
		moduleSelectItem.setWidth("*");
		moduleSelectItem.setName("type");
		moduleSelectItem.setTitle("Type");
		moduleSelectItem.setValueMap(Constants.WORKFLOW_TYPE());
		moduleSelectItem.setDefaultValue(Constants.LINUX);
		moduleSelectItem.setRequired(true);

		appFormatSelectItem = new SelectItem();
		appFormatSelectItem.setWidth("*");
		appFormatSelectItem.setName("appFormat");
		appFormatSelectItem.setTitle("Execution Environment");
		appFormatSelectItem.setValueMap(Constants.APP_FORMAT_TYPE());
		appFormatSelectItem.setDefaultValue(Constants.SCRIPT_TYPE);
		appFormatSelectItem.setRequired(true);

		LinkedHashMap<String, String> languageValueMap = new LinkedHashMap<String, String>();
		languageValueMap.put("java", "JAVA");
		languageValueMap.put("perl", "Perl");
		languageValueMap.put("python", "Python");
		languageValueMap.put("shell", "Shell");
		languageValueMap.put("r", "R");

		LinkedHashMap<String, String> languageValueIcon = new LinkedHashMap<String, String>();
		languageValueIcon.put("java", "java");
		languageValueIcon.put("perl", "perl");
		languageValueIcon.put("python", "python");
		languageValueIcon.put("shell", "shell");
		languageValueIcon.put("r", "r");

		languageSelectItem = new SelectItem();
		languageSelectItem.setWidth("*");
		languageSelectItem.setTitle("Programming Language");
		languageSelectItem.setName("languageType");
		languageSelectItem.setImageURLPrefix("closha/module/");
		languageSelectItem.setImageURLSuffix(".png");
		languageSelectItem.setValueMap(languageValueMap);
		languageSelectItem.setValueIcons(languageValueIcon);
		languageSelectItem.setRequired(true);
		
		scriptPathTextItem = new TextItem();
		scriptPathTextItem.setName("scriptPath");
		scriptPathTextItem.setTitle("Script Path");
		scriptPathTextItem.setStartRow(false);
		scriptPathTextItem.setWidth("*");
		scriptPathTextItem.setRequired(true);

		externalCmdTextItem = new TextItem();
		externalCmdTextItem.setName("extenal");
		externalCmdTextItem.setTitle("External Command");
		externalCmdTextItem.setWidth("*");
		externalCmdTextItem.setRequired(false);
		
		nextLinkedSelectItem = new SelectItem();
		nextLinkedSelectItem.setWidth("*");
		nextLinkedSelectItem.setTitle("Interlink Analysis Program");
		nextLinkedSelectItem.setName("nextLinked");
		nextLinkedSelectItem.setMultiple(true);  
		nextLinkedSelectItem.setMultipleAppearance(MultipleAppearance.PICKLIST);

		form = new DynamicForm();
		form.setIsGroup(false);
		form.setWidth(700);
		form.setHeight(300);
		form.setNumCols(2);
		form.setColWidths(150, 500);
		form.setPadding(5);
		form.setCanDragResize(false);
		form.setAutoFocus(true);
		form.setValidateOnChange(true);
		form.setFields(moduleNameTextItem, authorTextItem, createDateTextItem,
				versionSelectItem, openSelectItem, multiSelectItem,
				parallelSelectItem, alignmentSelectItem, ontologySelectItem,
				iconSelectItem, urlLinkTextItem, descTextItem,
				moduleSelectItem, appFormatSelectItem, scriptPathTextItem,
				languageSelectItem, externalCmdTextItem, nextLinkedSelectItem);

		ListGridField parameterTypeField = new ListGridField("parameterType", "Parameter Type");
		parameterTypeField.setCanEdit(false);
		parameterTypeField.setAlign(Alignment.CENTER);
		
		ListGridField dataTypeField = new ListGridField("dataType", "Data Type");
		dataTypeField.setCanEdit(false);
		dataTypeField.setAlign(Alignment.CENTER);
		
		ListGridField parameterNameField = new ListGridField("parameterName", "Parameter Name");
		parameterNameField.setCanEdit(true);
		parameterNameField.setRequired(true);
		parameterNameField.setAlign(Alignment.CENTER);
		
		ListGridField defaultValueField = new ListGridField("defaultValue", "Default Value");
		defaultValueField.setCanEdit(true);
		defaultValueField.setRequired(true);
		defaultValueField.setAlign(Alignment.CENTER);
		
		ListGridField descriptionField = new ListGridField("description", "Description");
		descriptionField.setCanEdit(true);
		descriptionField.setRequired(true);
		descriptionField.setAlign(Alignment.CENTER);
		
		ListGridField extentionField = new ListGridField("extensions", "Extension");
		extentionField.setCanEdit(true);
		extentionField.setRequired(false);
		extentionField.setAlign(Alignment.CENTER);
		extentionField.setEmptyCellValue("*");
		
		parameterListGrid = new ListGrid();
		parameterListGrid.setShowRowNumbers(true);
		parameterListGrid.setMargin(10);
		parameterListGrid.setCanEdit(true);
		parameterListGrid.setValidateOnChange(true);
		parameterListGrid.setEditEvent(ListGridEditEvent.CLICK);
		parameterListGrid.setEmptyMessage("There are no data.");
		parameterListGrid.setFields(parameterTypeField, dataTypeField, parameterNameField,
				defaultValueField, extentionField, descriptionField);

		addButton = new ImgButton();
		addButton.setSrc("[SKIN]actions/add.png");
		addButton.setSize(16);
		addButton.setShowFocused(false);
		addButton.setShowRollOver(false);
		addButton.setShowDown(false);

		removeButton = new ImgButton();
		removeButton.setSrc("[SKIN]actions/remove.png");
		removeButton.setSize(16);
		removeButton.setShowFocused(false);
		removeButton.setShowRollOver(false);
		removeButton.setShowDown(false);

		parameterTypeSelectItem = new SelectItem();
		parameterTypeSelectItem.setWidth(120);
		parameterTypeSelectItem.setName("parameterType");
		parameterTypeSelectItem.setShowTitle(false);
		parameterTypeSelectItem.setValueMap("INPUT", "OUTPUT");
		parameterTypeSelectItem.setDefaultValue("INPUT");
		
		dataTypeSelectItem = new SelectItem();
		dataTypeSelectItem.setWidth(120);
		dataTypeSelectItem.setName("dataType");
		dataTypeSelectItem.setShowTitle(false);
		dataTypeSelectItem.setValueMap("FILE", "FOLDER", "REFERENCE", "STRING", "INTEGER", "COMBO");
		dataTypeSelectItem.setDefaultValue("STRING");

		controllerForm = new DynamicForm();
		controllerForm.setHeight(1);
		controllerForm.setWidth(200);
		controllerForm.setNumCols(2);
		controllerForm.setFields(parameterTypeSelectItem, dataTypeSelectItem);

		SectionStackSection section1 = new SectionStackSection();
		section1.setTitle("Analysis Program Information");
		section1.setItems(form);
		section1.setExpanded(true);
		section1.setCanCollapse(false);

		SectionStackSection section2 = new SectionStackSection();
		section2.setTitle("Set Execution Parameter");
		section2.setItems(parameterListGrid);
		section2.setControls(controllerForm, addButton, removeButton);
		section2.setExpanded(true);
		section2.setCanCollapse(false);
		
		SectionStack sectionStack = new SectionStack();
		sectionStack.setSections(section1, section2);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setAnimateSections(true);
		sectionStack.setWidth100();
		sectionStack.setHeight100();
		sectionStack.setOverflow(Overflow.HIDDEN);

		submitButton = new ToolStripButton("Register");
		submitButton.setIcon("closha/icon/accept.png");

		resetButton = new ToolStripButton("Reset");
		resetButton.setIcon("closha/icon/new_refresh.png");
		
		toolstrip = new ToolStrip();
		toolstrip.addFill();
		toolstrip.addButton(resetButton);
		toolstrip.addButton(submitButton);
		
		addItem(sectionStack);
		addItem(toolstrip);
	}

	@Override
	public Window asWidget() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public TextItem getAuthorTextItem() {
		// TODO Auto-generated method stub
		return authorTextItem;
	}

	@Override
	public TextItem getCreateDateTextItem() {
		// TODO Auto-generated method stub
		return createDateTextItem;
	}

	@Override
	public SelectItem getOntologySelectItem() {
		// TODO Auto-generated method stub
		return ontologySelectItem;
	}

	@Override
	public SelectItem getTypeSelectItem() {
		// TODO Auto-generated method stub
		return moduleSelectItem;
	}

	@Override
	public TextItem getScriptPathTextItem() {
		// TODO Auto-generated method stub
		return scriptPathTextItem;
	}

	@Override
	public ImgButton getAddButton() {
		// TODO Auto-generated method stub
		return addButton;
	}

	@Override
	public ImgButton getRemoveButton() {
		// TODO Auto-generated method stub
		return removeButton;
	}

	@Override
	public ListGrid getParameterListGrid() {
		// TODO Auto-generated method stub
		return parameterListGrid;
	}

	@Override
	public SelectItem getDataTypeSelectItem() {
		// TODO Auto-generated method stub
		return dataTypeSelectItem;
	}

	@Override
	public SelectItem getNextLinkedSelectItem() {
		// TODO Auto-generated method stub
		return nextLinkedSelectItem;
	}

	@Override
	public TextItem getModuleNameTextItem() {
		// TODO Auto-generated method stub
		return moduleNameTextItem;
	}

	@Override
	public TextItem getExternalCmdTextItem() {
		// TODO Auto-generated method stub
		return externalCmdTextItem;
	}

	@Override
	public DynamicForm getForm() {
		// TODO Auto-generated method stub
		return form;
	}

	@Override
	public ToolStripButton getSubmitButton() {
		// TODO Auto-generated method stub
		return submitButton;
	}

	@Override
	public ToolStripButton getResetButton() {
		// TODO Auto-generated method stub
		return resetButton;
	}

	@Override
	public SelectItem getAppFormatSelectItem() {
		// TODO Auto-generated method stub
		return appFormatSelectItem;
	}

	@Override
	public SelectItem getLanguageSelectItem() {
		// TODO Auto-generated method stub
		return languageSelectItem;
	}

	@Override
	public SelectItem getParameterTypeSelectItem() {
		// TODO Auto-generated method stub
		return parameterTypeSelectItem;
	}

	@Override
	public SelectItem getOpenSelectItem() {
		// TODO Auto-generated method stub
		return openSelectItem;
	}

	@Override
	public SelectItem getMultiSelectItem() {
		// TODO Auto-generated method stub
		return multiSelectItem;
	}

	@Override
	public SelectItem getIconSelectItem() {
		// TODO Auto-generated method stub
		return iconSelectItem;
	}

	@Override
	public SelectItem getParallelSelectItem() {
		// TODO Auto-generated method stub
		return parallelSelectItem;
	}

	@Override
	public SelectItem getAlignmentSelectItem() {
		// TODO Auto-generated method stub
		return alignmentSelectItem;
	}	
}
