package org.kobic.gwt.smart.closha.client.frame.center.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.board.component.BoardTabPanel;
import org.kobic.gwt.smart.closha.client.common.component.ProgressWindow;
import org.kobic.gwt.smart.closha.client.configuration.component.ConfigurationTabPanel;
import org.kobic.gwt.smart.closha.client.controller.InstancePipelineController;
import org.kobic.gwt.smart.closha.client.event.content.AddPipelineModuleEvent;
import org.kobic.gwt.smart.closha.client.event.content.PreferencesRemoveRegisterEvents;
import org.kobic.gwt.smart.closha.client.event.draw.RemoveRegisterEvents;
import org.kobic.gwt.smart.closha.client.event.draw.ShowSourceCodeTabEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ShowSourceCodeTabEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.ShowSubSourceCodeTabEvent;
import org.kobic.gwt.smart.closha.client.event.draw.SourceCodeViewerRemoveRegisterEvent;
import org.kobic.gwt.smart.closha.client.event.draw.TabChangeParameterSenderEvent;
import org.kobic.gwt.smart.closha.client.event.gwt.GWTMessagePushFireEvent;
import org.kobic.gwt.smart.closha.client.event.gwt.GWTMessagePushServiceEvent;
import org.kobic.gwt.smart.closha.client.event.service.GwtEventServiceConnectEvent;
import org.kobic.gwt.smart.closha.client.event.service.GwtEventServiceConnecttHandler;
import org.kobic.gwt.smart.closha.client.file.browser.component.FileBrowserTabPanel;
import org.kobic.gwt.smart.closha.client.file.browser.event.FileDataBindEvent;
import org.kobic.gwt.smart.closha.client.file.explorer.controller.HadoopFileSystemController;
import org.kobic.gwt.smart.closha.client.file.explorer.event.FileBrowserDataLoadEvent;
import org.kobic.gwt.smart.closha.client.file.explorer.event.ShowFileBrowserEvent;
import org.kobic.gwt.smart.closha.client.file.explorer.event.ShowFileBrowserEventHandler;
import org.kobic.gwt.smart.closha.client.frame.center.event.FileBrowserDataRefreshEvent;
import org.kobic.gwt.smart.closha.client.frame.center.event.ShowIntroTabEvent;
import org.kobic.gwt.smart.closha.client.frame.center.event.ShowIntroTabEventHandler;
import org.kobic.gwt.smart.closha.client.frame.center.event.RemoveFileBrowserRegisterEvents;
import org.kobic.gwt.smart.closha.client.instantce.pipeline.presenter.InstancePipelinePresenter;
import org.kobic.gwt.smart.closha.client.instantce.pipeline.viewer.InstancePipelineViewer;
import org.kobic.gwt.smart.closha.client.introduce.component.IntroduceTabPanel;
import org.kobic.gwt.smart.closha.client.monitoring.job.component.JobMonitorTabPanel;
import org.kobic.gwt.smart.closha.client.pipeline.composition.presenter.PipelineCompositionPresenter;
import org.kobic.gwt.smart.closha.client.pipeline.composition.viewer.PipelineCompositionViewer;
import org.kobic.gwt.smart.closha.client.pipeline.design.component.DesingSmartCanvasWidget;
import org.kobic.gwt.smart.closha.client.pipeline.design.component.DesignTabPanel;
import org.kobic.gwt.smart.closha.client.pipeline.design.component.ToolKitToolStrip;
import org.kobic.gwt.smart.closha.client.preference.component.PreferenceTabPanel;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.OpenProjectEvent;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.OpenProjectEventHandler;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.TransmitParameterDataEvent;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.ProjectExplorerDataLoadEvent;
import org.kobic.gwt.smart.closha.client.register.module.presenter.ModuleRegisterPresenter;
import org.kobic.gwt.smart.closha.client.register.module.viewer.ModuleRegisterViewer;
import org.kobic.gwt.smart.closha.client.script.code.component.ScriptCodeTabPanel;
import org.kobic.gwt.smart.closha.client.section.ontology.module.event.ModuleDragEvent;
import org.kobic.gwt.smart.closha.client.section.ontology.module.event.ModuleEventHandler;
import org.kobic.gwt.smart.closha.client.statistic.component.StatisticTabPanel;
import org.kobic.gwt.smart.closha.client.unix.file.controller.UnixFileSystemController;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.Messages;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;

public class CenterPresenter implements Presenter{
	
	private final HandlerManager eventBus;
	private final Display display;
	private UserDto userDto;
	
	private Window progressBarWindow = null;
	
	private Tab fileBrowserTab;
	private Tab virtaulizationTab;
	private Tab monitorTab;
	private Tab bugTab;
	private Tab preferencesTab;
	private Tab configurationTab;
	private Tab sourceCodeViewerTab;
	
	private int percentValue = 0;
	private Map<String, String> instanceIDMap;
	private Map<String, String> config;
	
	private RemoteEventService theRemoteEventService;
	
	public interface Display{
		VLayout asWidget();
		
		TabSet getTabSet();
		
		ToolStrip getToolStrip();
		
		ToolStripButton getFileButton();
		ToolStripButton getAddProjectButton();
		ToolStripButton getDelProjectButton();
		ToolStripButton getRegistrationButton();
		ToolStripButton getShareButton();
		ToolStripButton getStaticButton();
		ToolStripButton getJobMonitorButton();
		ToolStripButton getBoardButton();
		ToolStripButton getConfigurationButton();
		ToolStripButton getRemoveTabButton();
	}
	
	public CenterPresenter(HandlerManager eventBus, Map<String, String> config,  UserDto userDto, RemoteEventService theRemoteEventService, Display view){
		this.eventBus = eventBus;
		this.display = view;
		this.config = config;
		this.userDto = userDto;
		this.theRemoteEventService = theRemoteEventService;
	}
	
	@Override
	public void init(){
		instanceIDMap = new HashMap<String, String>();
		
		display.getToolStrip().addMember(display.getRegistrationButton());
		
		if(userDto.isAdmin()){
			display.getToolStrip().addSeparator();
			display.getToolStrip().addMember(display.getJobMonitorButton());
			display.getToolStrip().addMember(display.getConfigurationButton());
		}
		
		bind();
	}
	
	@Override
	public void bind(){
		clickOpenFileButtonEvent();
		clickAddButtonEvent();
		clickDeleteButtonEvent();
		clickRegistrationButtonEvent();
		clickJobMonitorButtonEvent();
		clickSystemMonitorButtonEvent();
		clickConfigurationButtonEvent();
		clickRemoveTabButtonEvent();
		onChangeTabEvent();
		selectProjectTreeGridFireEvent();
		showFileBrowserFireEvent();
		showNoticeBoardFireEvent();
		showPreferencesFireEvent();
		showSourceCodeViewerFireEvent();
		moduleExplorerDragFireEvent();
		showIntroTabFireEvent();
		setGwtEventServiceFireEvent();
	}
	
	private void dataServiceConnector(String connectDomain){
		if(theRemoteEventService.isActive()){
			forceDisConnectDataPushRemoteService();
		}
		connectDataPushRemoteService(connectDomain);
	}
	
	private Domain makeDomainAress(String domain){
		return DomainFactory.getDomain(domain);
	}
	
	private void forceDisConnectDataPushRemoteService(){
		theRemoteEventService.removeListeners(theRemoteEventService.getActiveDomains());
	}
	
	private void disconnectDataPushRemoteService(String disConnectDomain){
		theRemoteEventService.removeListeners(makeDomainAress(disConnectDomain));
	}
	
	private void reconnectDataPushRemoteService(String connectDomain, String disConnectDomain){
		disconnectDataPushRemoteService(disConnectDomain);
		
		if(theRemoteEventService.isActive()){
			reconnectDataPushRemoteService(connectDomain, disConnectDomain);
		}else{
			connectDataPushRemoteService(connectDomain);
		}
	}
	
	private void connectDataPushRemoteService(String connectDomain){
		theRemoteEventService.addListener(makeDomainAress(connectDomain), new RemoteEventListener() {
			@Override
			public void apply(Event anEvent) {
				// TODO Auto-generated method stub
				if(anEvent instanceof GWTMessagePushServiceEvent) {
					GWTMessagePushServiceEvent theGWTMessagePushServiceEvent = (GWTMessagePushServiceEvent)anEvent;
					System.out.println("["
							+ theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel().getProjectName()
							+ "] == "
							+ theGWTMessagePushServiceEvent.getDomain()
							+ " ("
							+ anEvent.toString()
							+ ") ==> "
							+ theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel().getMessage());
					
					eventBus.fireEvent(new GWTMessagePushFireEvent(theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel()));
                }
			}
		});
	}
	
	private void showNewMakeProject(){
		
		InstancePipelinePresenter instancePipelinePresenter = 
				new InstancePipelinePresenter(eventBus, config, userDto, new InstancePipelineViewer());
		instancePipelinePresenter.go();
		
	}
	
	private void showSourceCodeViewerFireEvent(){
		eventBus.addHandler(ShowSourceCodeTabEvent.TYPE, new ShowSourceCodeTabEventHandler() {
			@Override
			public void showSourceCodeViewer(ShowSourceCodeTabEvent event) {
				// TODO Auto-generated method stub
				if(sourceCodeViewerTab == null){
					sourceCodeViewerTab = new ScriptCodeTabPanel(eventBus, config, event.getProjectName(), event.getXmlModuleModel());
					display.getTabSet().addTab(sourceCodeViewerTab);
					display.getTabSet().selectTab(Constants.SOURCE_CODE_VIEWER_WINDOW_ID);
				}else{
					eventBus.fireEvent(new ShowSubSourceCodeTabEvent(event.getXmlModuleModel(),  event.getProjectName()));
					
					int index = openTabSetChecked(Constants.SOURCE_CODE_VIEWER_WINDOW_TITLE);
					display.getTabSet().selectTab(index);
				}
			}
		});
	}
	
	private void showPreferencesFireEvent(){
		
		display.getShareButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(preferencesTab == null){
					preferencesTab = new PreferenceTabPanel(eventBus, config, userDto);
					display.getTabSet().addTab(preferencesTab);
					display.getTabSet().selectTab(Constants.PREFERENCES_WINDOW_ID);
				}else{
					int index = openTabSetChecked(Constants.PREFERENCES_WINDOW_TITLE);
					display.getTabSet().selectTab(index);
				}
			}
		});
	}
	
	private void showNoticeBoardFireEvent(){
		display.getBoardButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(bugTab == null){
					bugTab = new BoardTabPanel(eventBus, userDto);
					display.getTabSet().addTab(bugTab);
					display.getTabSet().selectTab(Constants.BOARD_WINDOW_ID);
				}else{
					int index = openTabSetChecked(Constants.BOARD_WINDOW_TITLE);
					display.getTabSet().selectTab(index);
				}
			}
		});
	}
	
	private void showFileBrowser(){
		if(fileBrowserTab == null){
			fileBrowserTab = new FileBrowserTabPanel(eventBus, config, userDto);
			display.getTabSet().addTab(fileBrowserTab);
			display.getTabSet().selectTab(Constants.FILE_WINDOW_ID);
		}else{
			int index = openTabSetChecked(Constants.FILE_WINDOW_TITLE);
			display.getTabSet().selectTab(index);
		}
	}
	
	private void showFileBrowserFireEvent(){
		eventBus.addHandler(ShowFileBrowserEvent.TYPE, new ShowFileBrowserEventHandler() {
			@Override
			public void focusOnFileBrowser(ShowFileBrowserEvent event) {
				// TODO Auto-generated method stub
				showFileBrowser();
				
				/*
				 * 관련 CLASS ==> FileExplorerPresenter(transmitSelectDirectoryData()), JobHistoryListGrid(createRecordComponent())
				 */
				
				System.out.println("File browser call event occurred. " + event.isLoad());
				
				if(event.isLoad()){
					eventBus.fireEvent(new FileBrowserDataLoadEvent(event.getPath()));
				}
			}
		});
	}
	
	private void clickOpenFileButtonEvent(){
		display.getFileButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				showFileBrowser();
			}
		});
	}
	
	private void clickAddButtonEvent(){
		display.getAddProjectButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				showNewMakeProject();
			}
		});
	}
	
	private boolean getProjectTabCheck(String tabTitle){
		if(tabTitle.equals(Constants.FILE_WINDOW_TITLE) || tabTitle.equals(Constants.BOARD_WINDOW_TITLE) || 
				tabTitle.equals(Constants.JOB_WINDOW_TITLE) || tabTitle.equals(Constants.MONITOR_WINDOW_TITLE) || 
				tabTitle.equals(Constants.MAKE_PROJECT_WINDOW_TITLE) || tabTitle.equals(Constants.PREFERENCES_WINDOW_TITLE) ||
				tabTitle.equals(Constants.CONFIGURATION_WINDOW_TITLE) || tabTitle.equals(Constants.SOURCE_CODE_VIEWER_WINDOW_TITLE)){
			return false;
		}else{
			return true;
		}
	}
	
	private boolean getFileBrowserTabCheck(){
		for(int i = 0; i < display.getTabSet().getTabs().length; i++){
			if(display.getTabSet().getTab(i).getID().equals(Constants.FILE_WINDOW_ID)){
				return true;
			}
		}
		return false;
	}
	
	private void clickDeleteButtonEvent(){
		display.getDelProjectButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				final String projectName = display.getTabSet().getTab(display.getTabSet().getSelectedTabNumber()).getTitle();
				final String instanceID = instanceIDMap.get(projectName);
				
				if(getProjectTabCheck(projectName)){
					SC.confirm("Delete Project", "Are you sure you want to delete this <B>" + projectName + "</B> project?", new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							// TODO Auto-generated method stub
							if(value){
								//delete database project + instance-pipeline + project folder
								HadoopFileSystemController.Util.getInstance().deleteProjectDirectory(config, projectName, userDto.getUserId(), instanceID,
										new AsyncCallback<Void>() {
									@Override
									public void onSuccess(Void result) {
										// TODO Auto-generated method stub
										//remove draw instanceID from map
										instanceIDMap.remove(projectName);
										
										//project explorer data reload fire event
										eventBus.fireEvent(new ProjectExplorerDataLoadEvent());
										
										//file browser tree data reload fire event
										eventBus.fireEvent(new FileDataBindEvent());
										
										//file browser grid data reload fire event
										if(getFileBrowserTabCheck()){
											eventBus.fireEvent(new FileBrowserDataRefreshEvent(projectName));
										}
										
										//parameters list grid data initialization
										List<XmlParameterModel> parameters = new ArrayList<XmlParameterModel>();
										eventBus.fireEvent(new TransmitParameterDataEvent(projectName, parameters));
										
										//close project tab
										display.getTabSet().removeTab(display.getTabSet().getSelectedTabNumber());
										
										//remove register fire event remove
										eventBus.fireEvent(new RemoveRegisterEvents(projectName));
										
										//로그 폴더 삭제
										final String logDirPath = config.get(Constants.UNIX_DIR_KEY) + "/" + 
												Constants.LOG + "/" + userDto.getUserId() + "/" 
												+ projectName + Constants.LOG_DIR_EXTENSION;
										
										UnixFileSystemController.Util.getInstance().deleteDirectory(logDirPath, new AsyncCallback<Void>() {
											@Override
											public void onFailure(
													Throwable caught) {
												// TODO Auto-generated method stub
												System.out.println(caught.getCause() + ":" + caught.getMessage());
											}

											@Override
											public void onSuccess(Void result) {
												// TODO Auto-generated method stub
												System.out.println("Log directory on the " + logDirPath + " has been deleted.");
											}
										});
									}
									
									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub
										System.out.println(caught.getCause() + ":" + caught.getMessage());
									}
								});
							}
						}
					});
				}
			}
		});
	}
	
	private void clickRegistrationButtonEvent(){
		display.getRegistrationButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				ModuleRegisterPresenter registrationPresenter = 
						new ModuleRegisterPresenter(eventBus, config, userDto, new ModuleRegisterViewer());
				registrationPresenter.go();
			}
		});
	}
	
	private void clickJobMonitorButtonEvent(){
		display.getJobMonitorButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(virtaulizationTab == null){
					virtaulizationTab = new JobMonitorTabPanel(eventBus);
					display.getTabSet().addTab(virtaulizationTab);
					display.getTabSet().selectTab(Constants.JOB_WINDOW_ID);
				}else{
					int index = openTabSetChecked(Constants.JOB_WINDOW_TITLE);
					display.getTabSet().selectTab(index);
				}
			}
		});
	}
	
	private void clickSystemMonitorButtonEvent(){
		display.getStaticButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(monitorTab == null){
					monitorTab = new StatisticTabPanel(eventBus);
					display.getTabSet().addTab(monitorTab);
					display.getTabSet().selectTab(Constants.MONITOR_WINDOW_ID);
				}else{
					int index = openTabSetChecked(Constants.MONITOR_WINDOW_TITLE);
					display.getTabSet().selectTab(index);
				}
			}
		});
	}
	
	private void clickConfigurationButtonEvent(){
		display.getConfigurationButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(configurationTab == null){
					configurationTab = new ConfigurationTabPanel(eventBus, config, userDto);
					display.getTabSet().addTab(configurationTab);
					display.getTabSet().selectTab(Constants.CONFIGURATION_WINDOW_ID);
				}else{
					int index = openTabSetChecked(Constants.CONFIGURATION_WINDOW_TITLE);
					display.getTabSet().selectTab(index);
				}
			}
		});
	}
	
	
	private void clickRemoveTabButtonEvent(){
		display.getRemoveTabButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(display.getTabSet().getSelectedTabNumber() != 0 && display.getTabSet().getSelectedTabNumber() != 1){
					
					if(display.getTabSet().getSelectedTab().getTitle().equals(Constants.FILE_WINDOW_TITLE)){
						fileBrowserTab = null;
						eventBus.fireEvent(new RemoveFileBrowserRegisterEvents());
					}else if(display.getTabSet().getSelectedTab().getTitle().equals(Constants.BOARD_WINDOW_TITLE)){
						bugTab = null;
					}else if(display.getTabSet().getSelectedTab().getTitle().equals(Constants.JOB_WINDOW_TITLE)){
						virtaulizationTab = null;
					}else if(display.getTabSet().getSelectedTab().getTitle().equals(Constants.MONITOR_WINDOW_TITLE)){
						monitorTab = null;
					}else if(display.getTabSet().getSelectedTab().getTitle().equals(Constants.PREFERENCES_WINDOW_TITLE)){
						preferencesTab = null;
						eventBus.fireEvent(new PreferencesRemoveRegisterEvents());
					}else if(display.getTabSet().getSelectedTab().getTitle().equals(Constants.CONFIGURATION_WINDOW_TITLE)){
						configurationTab = null;
					}else if(display.getTabSet().getSelectedTab().getTitle().equals(Constants.SOURCE_CODE_VIEWER_WINDOW_TITLE)){
						sourceCodeViewerTab = null;
						eventBus.fireEvent(new SourceCodeViewerRemoveRegisterEvent());
					}else{
						String projectName = display.getTabSet().getTab(display.getTabSet().getSelectedTabNumber()).getTitle();		
						
						instanceIDMap.remove(projectName);

						eventBus.fireEvent(new RemoveRegisterEvents(projectName));
					}
					
					display.getTabSet().removeTab(display.getTabSet().getSelectedTabNumber());
					
				}else{
					SC.say("You cannot close this active window.");
				}
			}
		});
	}
	
	//project tree double click fire event
	private void selectProjectTreeGridFireEvent(){
		eventBus.addHandler(OpenProjectEvent.TYPE, new OpenProjectEventHandler() {
			@Override
			public void getOpenUserProject(OpenProjectEvent event) {
				// TODO Auto-generated method stub
				drawWorkFlowOnCanvars(event.getInstancePipelineModel());
			}
		});
	}
	
	private void moduleExplorerDragFireEvent(){
		eventBus.addHandler(ModuleDragEvent.TYPE, new ModuleEventHandler() {
			@Override
			public void closhaModuleDragEvent(ModuleDragEvent event) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new AddPipelineModuleEvent(event.getModuleModel(), 
						display.getTabSet().getSelectedTab().getTitle()));
			}
		});
	}
	
	private void showIntroTabFireEvent(){
		eventBus.addHandler(ShowIntroTabEvent.TYPE, new ShowIntroTabEventHandler() {
			@Override
			public void introTabCallEvent(ShowIntroTabEvent event) {
				// TODO Auto-generated method stub
				int index = openTabSetChecked(Constants.INTRO_WINDOW_TITLE);
				display.getTabSet().selectTab(index);
			}
		});
	}
	
	private int openTabSetChecked(String projectName){
		for(int i = 0; i < display.getTabSet().getTabs().length; i++){
			if(display.getTabSet().getTabs()[i].getTitle().equals(projectName)){
				return i;
			}
		}
		return -1;
	}
	
	boolean isConnect = false;
	
	private void onChangeTabEvent(){
		display.getTabSet().addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				// TODO Auto-generated method stub
				for(int i = 0; i < display.getTabSet().getTabs().length; i++){
					if(i != 0 && !display.getTabSet().getTabs()[i].getID().equals(Constants.FILE_WINDOW_ID) 
							&& !display.getTabSet().getTabs()[i].getID().equals(Constants.JOB_WINDOW_ID)
							&& !display.getTabSet().getTabs()[i].getID().equals(Constants.REGISTRATION_WINDOW_ID)
							&& !display.getTabSet().getTabs()[i].getID().equals(Constants.BOARD_WINDOW_ID)
							&& !display.getTabSet().getTabs()[i].getID().equals(Constants.MONITOR_WINDOW_ID)
							&& !display.getTabSet().getTabs()[i].getID().equals(Constants.MAKE_PROJECT_WINDOW_ID)
							&& !display.getTabSet().getTabs()[i].getID().equals(Constants.PREFERENCES_WINDOW_ID)
							&& !display.getTabSet().getTabs()[i].getID().equals(Constants.CONFIGURATION_WINDOW_ID)
							&& !display.getTabSet().getTabs()[i].getID().equals(Constants.SOURCE_CODE_VIEWER_WINDOW_ID)){
						if(i != event.getTabNum()){
							display.getTabSet().getTabs()[i].setIcon("closha/icon/application_off.png");
						}else if(i == event.getTabNum()){
							display.getTabSet().getTabs()[i].setIcon("closha/icon/application_on.png");
							
							//parameter data send parameter list grid fire event
							eventBus.fireEvent(new TabChangeParameterSenderEvent(display.getTabSet().getTabs()[i].getTitle()));

							//data push service connect
							
							InstancePipelineController.Util.getInstance().getInstancePipeline(
									instanceIDMap.get(display.getTabSet().getTabs()[i].getTitle()), 
									new AsyncCallback<InstancePipelineModel>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void onSuccess(
										InstancePipelineModel result) {
									// TODO Auto-generated method stub
									System.out.println(result.getStatus());
									if(result.getStatus() == 1) isConnect = true;
								}
							});
							
							System.out.println("==>" + isConnect);
							
							if(isConnect){
								dataServiceConnector(instanceIDMap.get(display.getTabSet().getTabs()[i].getTitle()));	
							}
							
						}
					}else{
						//disconnect data push service
						forceDisConnectDataPushRemoteService();
					}
				}
			}
		});
	}
	
	private void setGwtEventServiceFireEvent(){
		eventBus.addHandler(GwtEventServiceConnectEvent.TYPE, new GwtEventServiceConnecttHandler() {
			
			@Override
			public void connect(GwtEventServiceConnectEvent event) {
				// TODO Auto-generated method stub
				dataServiceConnector(event.getInstanceID());
			}
		});
	}
	
	private void drawWorkFlowOnCanvars(final InstancePipelineModel iModel){
		
		instanceIDMap.put(iModel.getInstanceName(), iModel.getInstanceID());
		
		int checked = openTabSetChecked(iModel.getInstanceName());
				
		if(checked != -1){
			display.getTabSet().selectTab(checked);
		}else{
			
			final DesingSmartCanvasWidget boundaryLayout = new DesingSmartCanvasWidget(eventBus, config, userDto,
					iModel.getInstanceName(), iModel.getInstanceID());
			
			boundaryLayout.setBorder("solid 1px #DCDCDC");
			boundaryLayout.setHeight100();
			boundaryLayout.setWidth100();
			boundaryLayout.setCanAcceptDrop(true);  
//			boundaryLayout.setShowResizeBar(true);
			boundaryLayout.setOverflow(Overflow.AUTO);
			boundaryLayout.setBackgroundImage("closha/img/grid_20.png");
			
			HLayout workflowInfoLayout = new HLayout();
			workflowInfoLayout.setWidth100();
			workflowInfoLayout.setHeight100();
			
			final Presenter workflowMoniteringPresenter = new PipelineCompositionPresenter(
					eventBus, new PipelineCompositionViewer(), iModel);
			workflowMoniteringPresenter.go(workflowInfoLayout);
			
			/**
			 * 
			 */
			
			ToolStrip strip = new ToolKitToolStrip(eventBus, iModel.getInstanceName(), iModel.getInstanceID(), userDto, config);
			
			final SectionStack sectionStack = new SectionStack();  
	        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);  
	        sectionStack.setWidth100();  
	        sectionStack.setHeight100();  
	  
	        SectionStackSection designSection = new SectionStackSection("Project ID: " + iModel.getInstanceID());  
	        designSection.setExpanded(true);   
	        designSection.addItem(strip);
	        designSection.addItem(boundaryLayout);
	        sectionStack.addSection(designSection);  
	  
	        SectionStackSection workflowInfoSection = new SectionStackSection("Details");  
	        workflowInfoSection.setExpanded(true);  
	        workflowInfoSection.addItem(workflowInfoLayout);  
	        sectionStack.addSection(workflowInfoSection);  
			
	        /*
			VLayout workflowLayout =  new VLayout();
			workflowLayout.setHeight100();
			workflowLayout.setWidth100();
			workflowLayout.addMembers(boundaryLayout, workflowInfoLayout);
			*/
	        
			Tab designTab = new Tab(iModel.getInstanceName());
			designTab.setWidth(250);
			designTab.setIcon("closha/icon/application_on.png");
			designTab.setPane(sectionStack);
			
			display.getTabSet().addTab(designTab);
			display.getTabSet().selectTab(display.getTabSet().getTabs().length-1);
			
			progressBarWindow = new ProgressWindow(Messages.WORKFLOW_DRAW_PROGRESS_TITLE);
			progressBarWindow.show();
			
			Timer timer = new Timer() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					@SuppressWarnings("unused")
					DesignTabPanel designTabPanel = new DesignTabPanel(boundaryLayout, eventBus, 
							3840, 2160, iModel, config, progressBarWindow);
//					DesignTabPanel designTabPanel = new DesignTabPanel(boundaryLayout, eventBus, 
//							getScreenWidth(), getScreenHeight(), iModel, config, progressBarWindow);
					
					/*
					progressBarWindow.close();
					progressBarWindow.destroy();
					*/
				}
			};
			timer.schedule(800);
		}
	}
	
	public static native int getScreenWidth() /*-{ 
    	return $wnd.screen.width;
 	}-*/;
	
	public static native int getScreenHeight() /*-{ 
	 return $wnd.screen.height;
	}-*/;
	
	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.asWidget());
		
		init();
		
		if(display.getTabSet().getTabs().length == 0){
			display.getTabSet().addTab(new IntroduceTabPanel());
			
			fileBrowserTab = new FileBrowserTabPanel(eventBus, config, userDto);
			display.getTabSet().addTab(fileBrowserTab);
		}
	}
}