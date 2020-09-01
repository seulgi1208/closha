package org.kobic.gwt.smart.closha.client.frame.right.viewer;

import java.util.LinkedHashMap;

import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.client.frame.right.presenter.RightPresenter;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class RightViewer extends VLayoutWidget implements
		RightPresenter.Display {
	
	private SectionStack sectionStack;
	
	private SectionStackSection section1;
	private SectionStackSection section2;
	private SectionStackSection section3;
	private SectionStackSection section4;
	
	private ImgButton addPiplineOntologyButton;
	private ImgButton addModuleOntologyButton;
	private ImgButton historyReLoadButton;
	
	private ImgButton refreshPiplineOntologyButton;
	private ImgButton refreshModuleOntologyButton;
	
	private VLayout registerPipelineLayout;
	private VLayout moduleExplorerLayout;
	private VLayout parameterViewLayout;
	private VLayout historyLayout;
	
	private SelectItem selectItem;
	
	public RightViewer() {
		
		addPiplineOntologyButton = new ImgButton();  
		addPiplineOntologyButton.setSrc("closha/icon/add.png");  
		addPiplineOntologyButton.setSize(16);  
		addPiplineOntologyButton.setVisible(false);
		
		refreshPiplineOntologyButton = new ImgButton();  
		refreshPiplineOntologyButton.setSrc("closha/icon/new_refresh.png");
		refreshPiplineOntologyButton.setSize(16);  
		
		registerPipelineLayout = new VLayout();
		registerPipelineLayout.setHeight("30%");
		registerPipelineLayout.setWidth100();
		
		addModuleOntologyButton = new ImgButton();  
		addModuleOntologyButton.setSrc("closha/icon/add.png"); 
		addModuleOntologyButton.setSize(16);  
		addModuleOntologyButton.setVisible(false);
		
		refreshModuleOntologyButton = new ImgButton();  
		refreshModuleOntologyButton.setSrc("closha/icon/new_refresh.png"); 
		refreshModuleOntologyButton.setSize(16);  
		
		moduleExplorerLayout = new VLayout();
		moduleExplorerLayout.setHeight("30%");
		moduleExplorerLayout.setWidth100();
		
		parameterViewLayout = new VLayout();
		parameterViewLayout.setHeight("40%");
		parameterViewLayout.setWidth100();
		
		historyReLoadButton = new ImgButton();  
		historyReLoadButton.setSrc("closha/icon/new_refresh.png");  
		historyReLoadButton.setSize(16);  
	
        LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("Running", "<b>Running</b>");
		valueMap.put("Standby", "<b>Wait</b>");
		valueMap.put("Complete", "<b>Complete</b>");
		valueMap.put("Error", "<b>Error</b>");
		
		LinkedHashMap<String, String> valueIcons = new LinkedHashMap<String, String>();
		valueIcons.put("Running", "closha/icon/history_running.png");
		valueIcons.put("Standby", "closha/icon/history_wait.png");
		valueIcons.put("Complete", "closha/icon/history_done.png");
		valueIcons.put("Error", "closha/icon/history_error.png");
		
		selectItem = new SelectItem();  
        selectItem.setWidth(120);  
        selectItem.setShowTitle(false);  
        selectItem.setDefaultValue("Running");
        selectItem.setValueMap(valueMap);
		selectItem.setValueIcons(valueIcons);
        
		DynamicForm form = new DynamicForm();  
        form.setHeight(1);  
        form.setWidth(75);  
        form.setNumCols(1);  
        form.setFields(selectItem);  
		
		historyLayout = new VLayout();
		historyLayout.setHeight100();
		historyLayout.setWidth100();
		  
        section1 = new SectionStackSection();
        section1.setTitle("Analysis Pipeline Section");  
        section1.setIcon("closha/icon/chart_organisation.png");
        section1.setItems(registerPipelineLayout);
        section1.setControls(addPiplineOntologyButton, refreshPiplineOntologyButton);
        section1.setExpanded(true);  
        
        section2 = new SectionStackSection();
        section2.setTitle("Analysis Program Section");  
        section2.setIcon("closha/icon/application_view_tile.png");
        section2.setItems(moduleExplorerLayout);
        section2.setControls(addModuleOntologyButton, refreshModuleOntologyButton);
        section2.setExpanded(true);  
  
        section3 = new SectionStackSection();  
        section3.setTitle("Pipeline Settings List");  
        section3.setIcon("closha/icon/application_form_magnify.png");
        section3.setItems(parameterViewLayout);
        section3.setExpanded(true);  
  
        section4 = new SectionStackSection();  
        section4.setTitle("Job History Section");  
        section4.setIcon("closha/icon/time.png");
        section4.setItems(historyLayout);
        section4.setControls(form, historyReLoadButton);
        section4.setExpanded(true);  
        
        sectionStack = new SectionStack();  
        sectionStack.setSections(section1, section2, section4);  
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);  
        sectionStack.setAnimateSections(true);  
        sectionStack.setWidth100();  
        sectionStack.setHeight100();  
        sectionStack.setOverflow(Overflow.HIDDEN); 
        
        addMember(sectionStack);
	}

	@Override
	public VLayout asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public VLayout getRegisterPipelineLayout() {
		// TODO Auto-generated method stub
		return registerPipelineLayout;
	}

	@Override
	public VLayout getParameterViewLayout() {
		// TODO Auto-generated method stub
		return parameterViewLayout;
	}

	@Override
	public VLayout getHistoryLayout() {
		// TODO Auto-generated method stub
		return historyLayout;
	}

	@Override
	public ImgButton getHistoryReLoadButton() {
		// TODO Auto-generated method stub
		return historyReLoadButton;
	}

	@Override
	public SelectItem getSelectItem() {
		// TODO Auto-generated method stub
		return selectItem;
	}

	@Override
	public VLayout getModuleExplorerLayout() {
		// TODO Auto-generated method stub
		return moduleExplorerLayout;
	}

	@Override
	public ImgButton getAddModuleOntologyButton() {
		// TODO Auto-generated method stub
		return addModuleOntologyButton;
	}

	@Override
	public ImgButton getAddPipelineOntologyButton() {
		// TODO Auto-generated method stub
		return addPiplineOntologyButton;
	}

	@Override
	public ImgButton getRefreshPipelineOntologyButton() {
		// TODO Auto-generated method stub
		return refreshPiplineOntologyButton;
	}

	@Override
	public ImgButton getRefreshModuleOntologyButton() {
		// TODO Auto-generated method stub
		return refreshModuleOntologyButton;
	}
}