package org.kobic.gwt.smart.closha.client.monitoring.job.viewer;

import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.client.monitoring.job.presenter.JobMonitorPresenter;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class JobMonitorViewer extends VLayoutWidget implements
		JobMonitorPresenter.Display {

	private ListGrid jobListGrid;
	private ListGrid infoGrid;
	private ListGrid hostListGrid;

	private ImgButton jobDeleteButton;
	private ImgButton jobRefreshButton;
	private ImgButton hostRefreshButton;

	private SectionStack sectionStack;

	private SectionStackSection section1;
	private SectionStackSection section2;

	public JobMonitorViewer() {

		ListGridField stateImgField = new ListGridField("stateImg", "Status");
		stateImgField.setAlign(Alignment.CENTER);
		stateImgField.setType(ListGridFieldType.IMAGE);

		ListGridField qNameField = new ListGridField("queue", "Job");
		qNameField.setAlign(Alignment.CENTER);

		ListGridField slotField = new ListGridField("slot", "Job Node");
		slotField.setAlign(Alignment.CENTER);

		ListGridField jobIDField = new ListGridField("jobID", "Job ID");
		jobIDField.setAlign(Alignment.CENTER);

		ListGridField nameFiled = new ListGridField("name", "Job Name");
		nameFiled.setAlign(Alignment.CENTER);

		ListGridField priorityField = new ListGridField("priority", "Priority");
		priorityField.setAlign(Alignment.CENTER);

		ListGridField stateField = new ListGridField("state", "Status");
		stateField.setAlign(Alignment.CENTER);

		ListGridField submissionField = new ListGridField("submissionTime", "Job Start Time");
		submissionField.setAlign(Alignment.CENTER);

		jobListGrid = new ListGrid();
		jobListGrid.setWidth100();
		jobListGrid.setHeight100();
		jobListGrid.setShowAllRecords(true);
		jobListGrid.setShowRowNumbers(true);
		jobListGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		jobListGrid.setFields(new ListGridField[] { stateImgField, qNameField,
				slotField, jobIDField, nameFiled, priorityField, stateField,
				submissionField });

		jobDeleteButton = new ImgButton();
		jobDeleteButton.setSrc("closha/icon/delete.png"); 
		jobDeleteButton.setSize(16);  

		jobRefreshButton = new ImgButton();
		jobRefreshButton.setSrc("closha/icon/new_refresh.png"); 
		jobRefreshButton.setSize(16);  

		VLayout jobLayout = new VLayout();
		jobLayout.setHeight("70%");
		jobLayout.setWidth100();
		jobLayout.addMember(jobListGrid);
		
		ListGridField infoField_1 = new ListGridField("key", "Name");
		ListGridField valueFiled_1 = new ListGridField("value", "Value");

		infoGrid = new ListGrid();
		infoGrid.setWidth100();
		infoGrid.setHeight100();
		infoGrid.setShowAllRecords(true);
		infoGrid.setShowHeader(false);
		infoGrid.setEmptyMessage("There are no data.");
		infoGrid.setFields(new ListGridField[] { infoField_1, valueFiled_1 });

		ListGridField hostNameField = new ListGridField("host", "Host");
		hostNameField.setAlign(Alignment.CENTER);

		ListGridField loadField = new ListGridField("load", "Usage");
		loadField.setAlign(Alignment.CENTER);

		ListGridField cpuField = new ListGridField("cpu", "CPU");
		cpuField.setAlign(Alignment.CENTER);

		ListGridField memToField = new ListGridField("memTo", "Memory");
		memToField.setAlign(Alignment.CENTER);

		ListGridField memUseField = new ListGridField("memUse", "Memory Usage");
		memUseField.setAlign(Alignment.CENTER);

		ListGridField swapToField = new ListGridField("swapTo", "Swap");
		swapToField.setAlign(Alignment.CENTER);

		ListGridField swapUseField = new ListGridField("swapUse", "Swap Usage");
		swapUseField.setAlign(Alignment.CENTER);

		hostRefreshButton = new ImgButton();
		hostRefreshButton.setSrc("closha/icon/new_refresh.png"); 
		hostRefreshButton.setSize(16);  
		
		hostListGrid = new ListGrid();
		hostListGrid.setEmptyMessage("There are no data.");
		hostListGrid.setWidth100();
		hostListGrid.setHeight100();
		hostListGrid.setShowAllRecords(true);
		hostListGrid.setFields(new ListGridField[] { hostNameField, loadField,
				cpuField, memToField, memUseField, swapToField, swapUseField });

		HLayout hLayout = new HLayout();
		hLayout.setWidth100();
		hLayout.setHeight100();
		hLayout.setMembersMargin(5);
		hLayout.addMember(infoGrid);
		hLayout.addMember(hostListGrid);
		
		VLayout hostLayout = new VLayout();
		hostLayout.setHeight("30%");
		hostLayout.setWidth100();
		hostLayout.addMember(hLayout);

		section1 = new SectionStackSection();
		section1.setTitle("Job Information");
		section1.setIcon("closha/icon/chart_organisation.png");
		section1.setItems(jobLayout);
		section1.setControls(jobDeleteButton, jobRefreshButton);
		section1.setExpanded(true);

		section2 = new SectionStackSection();
		section2.setTitle("Detail Job Information and System Resource Information");
		section2.setIcon("closha/icon/application_view_tile.png");
		section2.setItems(hostLayout);
		section2.setControls(hostRefreshButton);
		section2.setExpanded(true);

		sectionStack = new SectionStack();
		sectionStack.setSections(section1, section2);
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
	public ListGrid getJobListGrid() {
		// TODO Auto-generated method stub
		return jobListGrid;
	}

	@Override
	public ListGrid getHostListGrid() {
		// TODO Auto-generated method stub
		return hostListGrid;
	}

	@Override
	public ListGrid getInfoGrid() {
		// TODO Auto-generated method stub
		return infoGrid;
	}

	@Override
	public ImgButton getDeleteButton() {
		// TODO Auto-generated method stub
		return jobDeleteButton;
	}

	@Override
	public ImgButton getJobRefreshButton() {
		// TODO Auto-generated method stub
		return jobRefreshButton;
	}

	@Override
	public ImgButton getHostRefreshButton() {
		// TODO Auto-generated method stub
		return hostRefreshButton;
	}
}
