package org.kobic.gwt.smart.closha.client.frame.center.viewer;

import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.client.frame.center.presenter.CenterPresenter;

import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class CenterViewer extends VLayoutWidget implements CenterPresenter.Display{
	
	private ToolStrip toolStrip;
	
	private TabSet designTabSet;
	
	private ToolStripButton fileButton;
	private ToolStripButton addProjectButton;
	private ToolStripButton delProjectButton;
	private ToolStripButton registrationButton;
	private ToolStripButton shareButton;
	private ToolStripButton	staticButton;
	private ToolStripButton jobMonitorButton;
	private ToolStripButton boardButton;
	private ToolStripButton configurationButton;
	private ToolStripButton removeTabButton;
	
	public CenterViewer(){
		
		setMembersMargin(5);
		
		designTabSet = new TabSet();
		designTabSet.setTabBarPosition(Side.TOP);
		designTabSet.setWidth100();
		designTabSet.setHeight100();
		
		addMember(getMadeToolStrip());
		addMember(designTabSet);
	}
	
	private ToolStrip getMadeToolStrip(){
		toolStrip = new ToolStrip();  
        toolStrip.setHeight(30);
        toolStrip.setWidth100();
        toolStrip.setMembersMargin(5);
        toolStrip.setPadding(5);
        toolStrip.setVertical(false);
        
        fileButton = new ToolStripButton ();  
        fileButton.setTitle("File Browser");
        fileButton.setIcon("closha/icon/folder_magnify.png");  
        toolStrip.addMember(fileButton); 
        
        toolStrip.addSeparator();  
          
        addProjectButton = new ToolStripButton ();  
        addProjectButton.setTitle("New Project");
        addProjectButton.setIcon("closha/icon/application_add.png");  
        toolStrip.addMember(addProjectButton); 
        
        delProjectButton = new ToolStripButton();
        delProjectButton.setTitle("Delete Project");
        delProjectButton.setIcon("closha/icon/application_delete.png");
        toolStrip.addMember(delProjectButton);
        
        registrationButton = new ToolStripButton();
        registrationButton.setTitle("Register Analysis Program");
        registrationButton.setIcon("silk/application_form_add.gif");

        toolStrip.addSeparator();  
        
        shareButton = new ToolStripButton();
        shareButton.setIcon("closha/icon/cluster16.gif");
		shareButton.setTitle("Share Project");
		toolStrip.addMember(shareButton);
        
        toolStrip.addSeparator();  
       
        removeTabButton = new ToolStripButton(); 
        removeTabButton.setTitle("Close");
        removeTabButton.setIcon("closha/icon/cross.png");
        toolStrip.addMember(removeTabButton);
        
        staticButton = new ToolStripButton();
        staticButton.setTitle("Usage Statistics");
        staticButton.setIcon("closha/icon/chart_bar.gif");

        jobMonitorButton = new ToolStripButton ();
        jobMonitorButton.setTitle("System Monitor");
        jobMonitorButton.setIcon("closha/icon/monitor.png");    
        
        boardButton = new ToolStripButton ();
        boardButton.setTitle("Board");
        boardButton.setIcon("closha/icon/paste_plain.png");    
        
        configurationButton = new ToolStripButton ();
        configurationButton.setTitle("System Settings");
        configurationButton.setIcon("closha/icon/cog.png");      
        
        return toolStrip;
	}

	@Override
	public VLayout asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public TabSet getTabSet() {
		// TODO Auto-generated method stub
		return designTabSet;
	}

	@Override
	public ToolStripButton getFileButton() {
		// TODO Auto-generated method stub
		return fileButton;
	}

	@Override
	public ToolStripButton getAddProjectButton() {
		// TODO Auto-generated method stub
		return addProjectButton;
	}

	@Override
	public ToolStripButton getDelProjectButton() {
		// TODO Auto-generated method stub
		return delProjectButton;
	}

	@Override
	public ToolStripButton getJobMonitorButton() {
		// TODO Auto-generated method stub
		return jobMonitorButton;
	}

	@Override
	public ToolStripButton getStaticButton() {
		// TODO Auto-generated method stub
		return staticButton;
	}

	@Override
	public ToolStripButton getRemoveTabButton() {
		// TODO Auto-generated method stub
		return removeTabButton;
	}

	@Override
	public ToolStripButton getRegistrationButton() {
		// TODO Auto-generated method stub
		return registrationButton;
	}

	@Override
	public ToolStripButton getBoardButton() {
		// TODO Auto-generated method stub
		return boardButton;
	}

	@Override
	public ToolStripButton getConfigurationButton() {
		// TODO Auto-generated method stub
		return configurationButton;
	}

	@Override
	public ToolStripButton getShareButton() {
		// TODO Auto-generated method stub
		return shareButton;
	}

	@Override
	public ToolStrip getToolStrip() {
		// TODO Auto-generated method stub
		return toolStrip;
	}
}