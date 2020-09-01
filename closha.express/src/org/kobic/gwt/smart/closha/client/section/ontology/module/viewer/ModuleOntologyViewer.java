package org.kobic.gwt.smart.closha.client.section.ontology.module.viewer;

import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.client.section.ontology.module.presenter.ModuleOntologyPresenter;
import org.kobic.gwt.smart.closha.client.section.ontology.module.record.ModuleOntologyRecord;

import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class ModuleOntologyViewer extends VLayoutWidget implements ModuleOntologyPresenter.Display{

	private TreeGridField moduleNameField;
	private TreeGridField pipelineIDField;
	private TreeGrid moduleTreeGrid;
	
	public ModuleOntologyViewer(){
		
		moduleNameField = new TreeGridField("moduleTreeName", "Bioinformatics Programs");
		moduleNameField.setHoverCustomizer(new HoverCustomizer() {  
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {  
            	ModuleOntologyRecord pRecord = (ModuleOntologyRecord) record;  
                return pRecord.getAttributeAsString("moduleDesc");  
            }  
        });
		
		pipelineIDField = new TreeGridField("otologyID", "ID");
		pipelineIDField.setHidden(true);
		
		moduleTreeGrid = new TreeGrid();
		moduleTreeGrid.setWidth100();
		moduleTreeGrid.setHeight100();
		moduleTreeGrid.setShowHeader(true);
		moduleTreeGrid.setSelectionType(SelectionStyle.SINGLE);
		moduleTreeGrid.setLeaveScrollbarGap(true);
		moduleTreeGrid.setAnimateFolders(true);
		moduleTreeGrid.setCanReparentNodes(true);
		moduleTreeGrid.setShowConnectors(true);
		moduleTreeGrid.setShowAllRecords(true);
		moduleTreeGrid.setCanReorderRecords(true);  
		moduleTreeGrid.setCanAcceptDroppedRecords(true);
		moduleTreeGrid.setCanDragRecordsOut(true);  
		moduleTreeGrid.setCanHover(true);  
		moduleTreeGrid.setDragDataAction(DragDataAction.COPY);
		moduleTreeGrid.setFields(moduleNameField, pipelineIDField);
		moduleTreeGrid.setEmptyMessage("There are no data.");
		
		addMember(moduleTreeGrid);
	}
	
	@Override
	public VLayout asLayout() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public TreeGrid getModuleTreeGrid() {
		// TODO Auto-generated method stub
		return moduleTreeGrid;
	}
}