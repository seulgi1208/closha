package org.kobic.gwt.smart.closha.client.section.ontology.pipeline.viewer;

import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.client.section.ontology.pipeline.presenter.PipelineOntologyPresenter;
import org.kobic.gwt.smart.closha.client.section.ontology.pipeline.record.PipelineOntologyRecord;

import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class PipelineOntologyViewer extends VLayoutWidget implements PipelineOntologyPresenter.Display{

	private TreeGridField moduleNameField;
	private TreeGridField pipelineIDField;
	private TreeGrid moduleTreeGrid;
	
	public PipelineOntologyViewer(){
		
		moduleNameField = new TreeGridField("pipelineViewName", "Bioinformatics Pipelines");
		moduleNameField.setHoverCustomizer(new HoverCustomizer() {  
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {  
            	PipelineOntologyRecord pRecord = (PipelineOntologyRecord) record;  
                return pRecord.getAttributeAsString("pipelineDesc");  
            }  
        });
		
		pipelineIDField = new TreeGridField("pipelineID", "ID");
		pipelineIDField.setHidden(true);
		
		moduleTreeGrid = new TreeGrid();
		moduleTreeGrid.setWidth100();
		moduleTreeGrid.setHeight100();
		moduleTreeGrid.setShowHeader(true);
		moduleTreeGrid.setSelectionType(SelectionStyle.SINGLE);
		moduleTreeGrid.setLeaveScrollbarGap(false);
		moduleTreeGrid.setAnimateFolders(true);
		moduleTreeGrid.setCanReparentNodes(false);
		moduleTreeGrid.setShowConnectors(true);
		moduleTreeGrid.setShowAllRecords(true);
		moduleTreeGrid.setCanReorderRecords(false);  
		moduleTreeGrid.setCanAcceptDroppedRecords(false);
		moduleTreeGrid.setCanDragRecordsOut(false); 
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
