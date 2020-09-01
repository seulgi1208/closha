package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.event.draw.CloseFunctionWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.CloseFunctionWindowEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.RemoveRegisterEvents;
import org.kobic.gwt.smart.closha.client.event.draw.RemoveRegisterEventsHandler;
import org.kobic.gwt.smart.closha.client.event.draw.ShowConnectionWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ShowConnectionWindowEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.ShowConsolLogWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ShowConsolLogWindowEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.ShowModuleDialogWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ShowModuleDialogWindowEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.ShowHistoryWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ShowHistoryWindowEventHandler;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.DropMoveEvent;
import com.smartgwt.client.widgets.events.DropMoveHandler;
import com.smartgwt.client.widgets.events.DropOutEvent;
import com.smartgwt.client.widgets.events.DropOutHandler;
import com.smartgwt.client.widgets.events.DropOverEvent;
import com.smartgwt.client.widgets.events.DropOverHandler;
import com.smartgwt.client.widgets.tile.TileLayout;

public class DesingSmartCanvasWidget extends Canvas {  
	  
    private Canvas crossHairX;  
    private Canvas crossHairY;  
//  private Window toolkitWindow;
    private Window linkedDataWindow;
    private Window historyDataWindow;
    private Window consolLogWindow;
    private Window moduleDialogWindow;
    private TileLayout tileLayout;
    
    private HandlerManager eventBus;
    private HandlerRegistration registerHandler[];
    private String projectName;
    private String instanceID;
    private UserDto userDto;
    private Map<String, String> config;
    
    public DesingSmartCanvasWidget(HandlerManager eventBus, Map<String, String> config, UserDto userDto, String projectName, String instanceID){
    	this.eventBus = eventBus;
    	this.projectName = projectName;
    	this.instanceID = instanceID;
    	this.userDto = userDto;
    	this.config = config;
    	this.registerHandler = new HandlerRegistration[6];
        moduleLinkedShowFireEvent();
    }
    
    private void moduleLinkedShowFireEvent(){
    	
    	registerHandler[5] = eventBus.addHandler(CloseFunctionWindowEvent.TYPE, new CloseFunctionWindowEventHandler() {
			@Override
			public void closeFunctionWindow(CloseFunctionWindowEvent event) {
				// TODO Auto-generated method stub
				if(event.getProjectName().equals(projectName)){
					if(event.getWindowID().equals(Constants.CONSOL_LOG_WINDOW_ID)){
						consolLogWindow = null;
					}else if(event.getWindowID().equals(Constants.CONNECTER_MODULE_WINDOW_ID)){
						linkedDataWindow = null;
					}else if(event.getWindowID().equals(Constants.EXCUTE_HISTORY_WINDOW_ID)){
						historyDataWindow = null;
					}
				}
			}
		});
    	
    	registerHandler[4] = eventBus.addHandler(ShowConsolLogWindowEvent.TYPE, new ShowConsolLogWindowEventHandler() {
			@Override
			public void showConsolLogViewer(ShowConsolLogWindowEvent event) {
				// TODO Auto-generated method stub
				if(event.getProjectName().equals(projectName)){
					if(consolLogWindow == null){
						consolLogWindow = new ConsolLogWindow(eventBus, config, projectName, userDto.getUserId());
						consolLogWindow.setLeft(50);
						consolLogWindow.setTop(50);
						consolLogWindow.show();
						addChild(consolLogWindow);
					}else{
						SC.warn("The window is already running.");
					}
				}
			}
		});
    	
    	registerHandler[3] = eventBus.addHandler(ShowHistoryWindowEvent.TYPE, new ShowHistoryWindowEventHandler() {
			@Override
			public void showHistoryWindowEvent(ShowHistoryWindowEvent event) {
				// TODO Auto-generated method stub
				if(event.getProjectName().equals(projectName)){
					if(historyDataWindow == null){
						historyDataWindow = new JobHistoryWindow(eventBus, projectName, instanceID);
						historyDataWindow.setLeft(50);
						historyDataWindow.setTop(50);
						historyDataWindow.show();
						addChild(historyDataWindow);
					}else{
						SC.warn("The window is already running.");
					}
				}
			}
		});
    	
    	registerHandler[2] = eventBus.addHandler(ShowModuleDialogWindowEvent.TYPE, new ShowModuleDialogWindowEventHandler() {
			@Override
			public void showModuleDialogWindow(ShowModuleDialogWindowEvent event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					moduleDialogWindow = new ModuleDialogWindow(userDto, projectName, 
							event.getXMLModuleModel().getKey(), event.getXMLModuleModel(), 
							event.getParameter(), eventBus, config);
					moduleDialogWindow.show();
				}
			}
		});
    	
    	registerHandler[1] = eventBus.addHandler(ShowConnectionWindowEvent.TYPE, new ShowConnectionWindowEventHandler() {				
			@Override
			public void showConnectionWindow(ShowConnectionWindowEvent event) {
				// TODO Auto-generated method stub
				if(event.getProjectName().equals(projectName)){
					if(linkedDataWindow == null){
						linkedDataWindow = new ModuleLinkedWindow(eventBus, projectName, event.getIntegrationLinkedModel());
				        linkedDataWindow.setLeft(50);
				        linkedDataWindow.setTop(50);
						linkedDataWindow.show();
						addChild(linkedDataWindow);
					}else{
						SC.warn("The window is already running.");
					}
				}
			}
		});
    	
    	registerHandler[0] = eventBus.addHandler(RemoveRegisterEvents.TYPE, new RemoveRegisterEventsHandler() {
			@Override
			public void removeRegisterEventHandler(RemoveRegisterEvents event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					for(int i = 0; i < registerHandler.length; i++){
						registerHandler[i].removeHandler();
					}
				}
			}
		});
	}

    @Override   
    protected void onInit() {
    	
        crossHairX = createCrossHair();  
        crossHairY = createCrossHair();  
        
        /*
		toolkitWindow = new ToolkitBoxWindow(eventBus, projectName, instanceID, userID, config);
        toolkitWindow.setShowShadow(true);
        toolkitWindow.setLeft("80%");
        toolkitWindow.setTop(20);
        toolkitWindow.show();
        */
        
        tileLayout = new PipelineExeLogTileLayout();
        
        addChild(crossHairX);  
        addChild(crossHairY);
//      addChild(toolkitWindow);
        addChild(tileLayout);
        
        this.addDropOverHandler(new DropOverHandler() {  
            public void onDropOver(DropOverEvent event) {  
                getCrossHairX().show();  
                getCrossHairY().show();  
                updateCrossHairs();  
            }  
        });  

        this.addDropMoveHandler(new DropMoveHandler() {  
            public void onDropMove(DropMoveEvent event) {  
                updateCrossHairs();  
            }  
        });  

        this.addDropOutHandler(new DropOutHandler() {  
            public void onDropOut(DropOutEvent event) {  
                getCrossHairX().hide();  
                getCrossHairY().hide();  
            }  
        });    
    }  

    private Canvas createCrossHair() {  
        Canvas canvas = new Canvas();  
        canvas.setWidth(2002);  
        canvas.setHeight(2002);  
        canvas.setBorder("1px solid #4169E1");  
        canvas.setVisibility(Visibility.HIDDEN);  
        return canvas;  
    }  
    
    public Canvas getCrossHairX() {  
        return crossHairX;  
    }  

    public Canvas getCrossHairY() {  
        return crossHairY;  
    }  

    public void updateCrossHairs() {  
        int x = getOffsetX();  
        int y = getOffsetY();  
        
        // crossHairX is the -X and +Y axis of the crossHair  
        crossHairX.setLeft(x - 2001);  
        crossHairX.setTop(y - 2001);
        
        // crossHairY is +X, -Y  
        crossHairY.setLeft(x);  
        crossHairY.setTop(y);  
    }  
}  