package org.kobic.gwt.smart.closha.client.frame.left.viewer;

import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.client.frame.left.presenter.LeftPresenter;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class LeftViewer extends VLayoutWidget implements LeftPresenter.Display{
	
	private SectionStack sectionStack;
	
	private SectionStackSection section1;
	private SectionStackSection section2;

	private VLayout projectTreeLayout;	
	private VLayout fileTreeLayout;
	
	private ImgButton projectReLoadButton;
	private ImgButton fileBrowserReLoadButton;
	
	public LeftViewer(String userName){
		
		projectTreeLayout = new VLayout();
		projectTreeLayout.setWidth100();
		projectTreeLayout.setHeight100();
		
		projectReLoadButton = new ImgButton();  
		projectReLoadButton.setSrc("closha/icon/new_refresh.png");  
		projectReLoadButton.setSize(16);  
		
		section1 = new SectionStackSection();
        section1.setTitle("[" + userName + "] Analysis Project");  
        section1.setIcon("closha/icon/circle_double.png");
        section1.setItems(projectTreeLayout);
        section1.setControls(projectReLoadButton);
        section1.setExpanded(true);  
        
        fileTreeLayout = new VLayout();
		fileTreeLayout.setWidth100();
		fileTreeLayout.setHeight100();
		
		fileBrowserReLoadButton = new ImgButton();  
		fileBrowserReLoadButton.setSrc("closha/icon/new_refresh.png");  
		fileBrowserReLoadButton.setSize(16);  
		
        section2 = new SectionStackSection();
        section2.setTitle("File Browser");  
        section2.setIcon("closha/icon/folder_magnify.png");
        section2.setItems(fileTreeLayout);
        section2.setControls(fileBrowserReLoadButton);
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
	public VLayout getProjectTreeLayout() {
		// TODO Auto-generated method stub
		return projectTreeLayout;
	}

	@Override
	public VLayout getFileTreeLayout() {
		// TODO Auto-generated method stub
		return fileTreeLayout;
	}

	@Override
	public ImgButton getFileBrowserReLoadButton() {
		// TODO Auto-generated method stub
		return fileBrowserReLoadButton;
	}

	@Override
	public ImgButton getProjectExplorerReLoadButton() {
		// TODO Auto-generated method stub
		return projectReLoadButton;
	}
}
