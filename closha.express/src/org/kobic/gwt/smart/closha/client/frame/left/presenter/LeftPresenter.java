package org.kobic.gwt.smart.closha.client.frame.left.presenter;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.file.explorer.event.FileExplorerDataReLoadEvent;
import org.kobic.gwt.smart.closha.client.file.explorer.presenter.FileExplorerPresenter;
import org.kobic.gwt.smart.closha.client.file.explorer.viewer.FileExplorerViewer;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.ProjectExplorerDataLoadEvent;
import org.kobic.gwt.smart.closha.client.projects.explorer.presenter.ProjectsExplorerPresenter;
import org.kobic.gwt.smart.closha.client.projects.explorer.viewer.ProjectsExplorerViewer;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LeftPresenter implements Presenter{

	private final HandlerManager eventBus;
	private final Display display;
	private final UserDto userDto;
	private Map<String, String> config;
	private Presenter projecsPresenter;
	private Presenter filePresenter;
	
	public interface Display{
		VLayout asWidget();
		VLayout getProjectTreeLayout();
		VLayout getFileTreeLayout();
		ImgButton getFileBrowserReLoadButton();
		ImgButton getProjectExplorerReLoadButton();
	}
	
	public LeftPresenter(HandlerManager eventBus, Map<String, String> config, UserDto userDto, Display view){
		this.eventBus = eventBus;
		this.userDto = userDto;
		this.display = view;
		this.config =config;
	}
	
	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		projecsPresenter = new ProjectsExplorerPresenter(eventBus, new ProjectsExplorerViewer(), userDto, config);
		projecsPresenter.go(display.getProjectTreeLayout());
		
		filePresenter = new FileExplorerPresenter(eventBus, config, userDto, new FileExplorerViewer());
		filePresenter.go(display.getFileTreeLayout());
		
		container.addMember(display.asWidget());
		init();
	}
	
	private void fileExplorerDataReLoadFireEvent(){
		display.getFileBrowserReLoadButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new FileExplorerDataReLoadEvent());
			}
		});
	}
	
	private void projectExplorerDataReLoadFireEvent(){
		display.getProjectExplorerReLoadButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new ProjectExplorerDataLoadEvent());
			}
		});
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		bind();
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		fileExplorerDataReLoadFireEvent();
		projectExplorerDataReLoadFireEvent();
	}
}