package org.kobic.gwt.smart.closha.client.instantce.pipeline.viewer;

import org.kobic.gwt.smart.closha.client.instantce.pipeline.presenter.InstancePipelinePresenter;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class InstancePipelineViewer extends Window implements
		InstancePipelinePresenter.Display {

	private DynamicForm makeInstancePipelineForm;
	private TextItem instancePipelineNameTextITem;
	private TextAreaItem descTextItem;
	private ToolStripButton submintButton;
	private ToolStripButton resetButton;
	private SelectItem pipelineSelectItem;
	private Label descLabel;

	public InstancePipelineViewer() {

		setWidth(650);
		setHeight(450);
		setIsModal(true);
		setShowModalMask(true);
		setShowShadow(true);
		setShowMinimizeButton(false);
		setShowCloseButton(true);
		setCanDragReposition(false);
		setCanDragResize(true);
		centerInPage();
		setAutoCenter(true);
		setHeaderIcon("closha/icon/application_add.png");
		setTitle("Create New Analysis Pipeline Project");

		makeInstancePipelineForm = new DynamicForm();
		makeInstancePipelineForm.setWidth(570);
		makeInstancePipelineForm.setHeight(150);
		makeInstancePipelineForm.validate(true);
		makeInstancePipelineForm.setAlign(Alignment.CENTER);
			
		instancePipelineNameTextITem = new TextItem("pName");
		instancePipelineNameTextITem.setRequired(true);
		instancePipelineNameTextITem.setWrapTitle(false);
		instancePipelineNameTextITem.setTitle("Project Name");
		instancePipelineNameTextITem.setWidth("*");

		descTextItem = new TextAreaItem("pDesc");
		descTextItem.setWidth("*");
		descTextItem.setRequired(true);
		descTextItem.setTitle("Project Description");
		descTextItem.setWrapTitle(false);

		pipelineSelectItem = new SelectItem();
		pipelineSelectItem.setWidth("*");
		pipelineSelectItem.setTitle("Project Type");
		pipelineSelectItem.setName("pipeline");
		pipelineSelectItem.setRequired(true);

		makeInstancePipelineForm.setFields(instancePipelineNameTextITem,
				descTextItem, pipelineSelectItem);

		descLabel = new Label();
		descLabel.setWidth(570);
		descLabel.setHeight(200);
		descLabel.setBorder("1px solid gray");

		submintButton = new ToolStripButton("Confirm");
		submintButton.setIcon("closha/icon/accept.png");

		resetButton = new ToolStripButton("Reset");
		resetButton.setIcon("closha/icon/field_reset.png");

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.addFill();
		toolStrip.addButton(resetButton);
		toolStrip.addButton(submintButton);
		
		HLayout wrap_1 = new HLayout();
		wrap_1.setWidth100();
		wrap_1.setHeight100();
		wrap_1.setAlign(Alignment.CENTER);
		wrap_1.addMember(makeInstancePipelineForm);
		
		HLayout wrap_2 = new HLayout();
		wrap_2.setWidth100();
		wrap_2.setHeight100();
		wrap_2.setAlign(Alignment.CENTER);
		wrap_2.addMember(descLabel);
		
		VLayout wrapperLayout = new VLayout();
		wrapperLayout.setHeight100();
		wrapperLayout.setWidth100();
		wrapperLayout.setMargin(10);
		wrapperLayout.setMembersMargin(10);
		wrapperLayout.setAlign(Alignment.CENTER);
		wrapperLayout.addMembers(wrap_1, wrap_2);
		
		addItem(wrapperLayout);
		addItem(toolStrip);
	}

	@Override
	public Window asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ToolStripButton getSubmintButton() {
		// TODO Auto-generated method stub
		return submintButton;
	}

	@Override
	public ToolStripButton getResetButton() {
		// TODO Auto-generated method stub
		return resetButton;
	}

	@Override
	public DynamicForm getProjectForm() {
		// TODO Auto-generated method stub
		return makeInstancePipelineForm;
	}

	@Override
	public TextItem getProjectNameTextItem() {
		// TODO Auto-generated method stub
		return instancePipelineNameTextITem;
	}

	@Override
	public SelectItem getPipelineSelectITem() {
		// TODO Auto-generated method stub
		return pipelineSelectItem;
	}

	@Override
	public Label getDescLabel() {
		// TODO Auto-generated method stub
		return descLabel;
	}	
}