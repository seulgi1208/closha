package org.kobic.gwt.smart.closha.client.script.code.viewer;

import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.client.script.code.presenter.ScriptCodePresenter;

import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ScriptCodeViewer extends VLayoutWidget implements
		ScriptCodePresenter.Display {
	
	private TabSet tabSet;
	private ToolStrip toolstrip;
	private ToolStripButton saveButton;
	private ToolStripButton closeButton;
	private ToolStripButton redoButton;
	private ToolStripButton undoButton;

	public ScriptCodeViewer(){
		
		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setWidth100();
		tabSet.setHeight100();
		
		undoButton = new ToolStripButton("Undo");
		undoButton.setIcon("closha/icon/undo.png");
		
		redoButton = new ToolStripButton("Redo");
		redoButton.setIcon("closha/icon/redo.png");
		
		saveButton = new ToolStripButton("Save");
		saveButton.setIcon("silk/table_save.png");
		
		closeButton = new ToolStripButton("Close");
		closeButton.setIcon("silk/table_row_delete.png");
		
		toolstrip = new ToolStrip();
		toolstrip.addFill();
		
		toolstrip.addButton(undoButton);
		toolstrip.addButton(redoButton);
		toolstrip.addButton(saveButton);
		toolstrip.addButton(closeButton);
		
		addMembers(tabSet, toolstrip);
//		addMembers(tabSet);
	}
	
	@Override
	public VLayout asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public TabSet getTabSet() {
		// TODO Auto-generated method stub
		return tabSet;
	}

	@Override
	public ToolStripButton getSaveButton() {
		// TODO Auto-generated method stub
		return saveButton;
	}

	@Override
	public ToolStripButton getCloseButton() {
		// TODO Auto-generated method stub
		return closeButton;
	}

	@Override
	public ToolStripButton getRedoButton() {
		// TODO Auto-generated method stub
		return redoButton;
	}

	@Override
	public ToolStripButton getUndoButton() {
		// TODO Auto-generated method stub
		return undoButton;
	}
}