package org.kobic.gwt.smart.closha.client.projects.explorer.viewer;

import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.client.projects.explorer.presenter.ProjectsExplorerPresenter;
import org.kobic.gwt.smart.closha.client.projects.explorer.record.ProjectExplorerRecord;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class ProjectsExplorerViewer extends VLayoutWidget implements ProjectsExplorerPresenter.Display{
	
	private TreeGridField projectNameField;
	private TreeGridField descriptionField;
	private TreeGridField createDateField;
	private TreeGrid projectTreeGrid;
	
	
	public ProjectsExplorerViewer(){
				
		projectNameField = new TreeGridField("projectName", "Project Name");
		projectNameField.setHoverCustomizer(new HoverCustomizer() {  
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {  
                ProjectExplorerRecord pRecord = (ProjectExplorerRecord) record;  
                return pRecord.getAttributeAsString("projectDesc");  
            }  
        });
		
		descriptionField = new TreeGridField("projectDesc", "Project Description");
		descriptionField.setHidden(true);

		createDateField = new TreeGridField("createDate", "Created Date");
		createDateField.setAlign(Alignment.CENTER);
		
		projectTreeGrid = new TreeGrid();          
		projectTreeGrid.setWidth100(); 
		projectTreeGrid.setHeight100();
		projectTreeGrid.setShowHeader(true);
		projectTreeGrid.setSelectionType(SelectionStyle.SINGLE);
		projectTreeGrid.setLeaveScrollbarGap(false);
		projectTreeGrid.setAnimateFolders(false);
		projectTreeGrid.setCanAcceptDroppedRecords(false);
		projectTreeGrid.setCanReparentNodes(false);
		projectTreeGrid.setShowConnectors(true);
		projectTreeGrid.setCanHover(true);  
		projectTreeGrid.setCanDragRecordsOut(false);
//		projectTreeGrid.setNodeIcon("closha/icon/application.png");
		projectTreeGrid.setFields(projectNameField, createDateField);
		projectTreeGrid.setEmptyMessage("There are no data.");
		
		addMember(projectTreeGrid);
	}

	@Override
	public VLayout asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public TreeGrid getProjectTreeGrid() {
		// TODO Auto-generated method stub
		return projectTreeGrid;
	}
}
