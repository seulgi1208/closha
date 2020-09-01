package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kobic.gwt.smart.closha.client.common.component.ProgressWindow;
import org.kobic.gwt.smart.closha.client.controller.InstancePipelineController;
import org.kobic.gwt.smart.closha.client.controller.WorkFlowController;
import org.kobic.gwt.smart.closha.client.controller.WorkManagementController;
import org.kobic.gwt.smart.closha.client.event.content.AddPipelineModuleEvent;
import org.kobic.gwt.smart.closha.client.event.content.AddPipelineModuleEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.ConnectionDataWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ConnectionDataWindowEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.ConsolLogWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ConsolLogWindowEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.DeleteConnectionEvent;
import org.kobic.gwt.smart.closha.client.event.draw.DeleteConnectionEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.DeleteModuleEvent;
import org.kobic.gwt.smart.closha.client.event.draw.DeleteModuleEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.DrawXMLResetEvent;
import org.kobic.gwt.smart.closha.client.event.draw.DrawXMLResetEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.ExecuteProjectEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ExecuteProjectEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.ConnectionLinkedStraightEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ConnectionLinkedStraightEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.HistoryDataWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.HistoryDataWindowEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.ParameterSettingEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ParameterSettingEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.PipelineRegistrationEvent;
import org.kobic.gwt.smart.closha.client.event.draw.PipelineRegistrationEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.PipelineXMLSaveEvent;
import org.kobic.gwt.smart.closha.client.event.draw.PipelineXMLSaveEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.ReloadConnectionWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.RemoveRegisterEvents;
import org.kobic.gwt.smart.closha.client.event.draw.RemoveRegisterEventsHandler;
import org.kobic.gwt.smart.closha.client.event.draw.SettingRunButtonRegisterEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ShowConnectionWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ShowConsolLogWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ShowModuleDialogWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ShowHistoryWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ExpertFunctionStraightEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ExpertFunctionStraightEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.TabChangeParameterSenderEvent;
import org.kobic.gwt.smart.closha.client.event.draw.TabChangeParameterSenderEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.TabChangeStateReloadEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ToolBoxParameterWindowEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ToolBoxParameterWindowEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.ToolBoxWorkflowStatusEvent;
import org.kobic.gwt.smart.closha.client.event.draw.ToolBoxWorkflowStatusEventHandler;
import org.kobic.gwt.smart.closha.client.event.draw.WorkflowSendChangeDataEvent;
import org.kobic.gwt.smart.closha.client.event.gwt.GWTMessagePushFireEvent;
import org.kobic.gwt.smart.closha.client.event.gwt.GWTMessagePushFireEventHandler;
import org.kobic.gwt.smart.closha.client.event.service.GwtEventServiceConnectEvent;
import org.kobic.gwt.smart.closha.client.projects.explorer.event.TransmitParameterDataEvent;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.Messages;
import org.kobic.gwt.smart.closha.shared.model.design.ModuleConnectStoreModel;
import org.kobic.gwt.smart.closha.shared.model.design.DecorationLabelStoreModel;
import org.kobic.gwt.smart.closha.shared.model.design.IntegrationModuleConnectModel;
import org.kobic.gwt.smart.closha.shared.model.design.ModuleInfomModel;
import org.kobic.gwt.smart.closha.shared.model.design.ModuleConnectionInfomModel;
import org.kobic.gwt.smart.closha.shared.model.design.PointsModel;
import org.kobic.gwt.smart.closha.shared.model.design.ShapesModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlConnectLinkedModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;
import org.kobic.gwt.smart.closha.shared.utils.design.XmlDataParsingUtil;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.connection.Connection;
import com.orange.links.client.event.TieLinkEvent;
import com.orange.links.client.event.TieLinkHandler;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;

public class PipelineDesingCanvasWidget extends PipelineDesignWidget{
	
	private PickupDragController dragController;
	private Map<String, ShapesModel> shapeMap;
	private List<ModuleConnectStoreModel> connectionList;
	private List<DecorationLabelStoreModel> decorationList;
	private Multimap<String, DecorationLabelStoreModel> decorationMapList;
	private HandlerRegistration registerHandler[];
	private XmlDispatchModel xmlDispatchModel; 
	
	private Window parameterWindow;
	private Window registrationWindow;
	private Window progressBarWindow;
	
	private int ADD_MODULE_KEY = 0;
	
	Timer timer = null;
	
	private void statusTimer(){
		
		timer = new Timer() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				InstancePipelineController.Util.getInstance().getInstancePipeline(
						iModel.getInstanceID(), 
						new AsyncCallback<InstancePipelineModel>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}

					@Override	
					public void onSuccess(
							InstancePipelineModel result) {
						// TODO Auto-generated method stub
						
						System.out.println(projectName + "'s status : [" +  result.getStatus() + "]");
						
						if(result.getStatus() == Constants.RUNNING_STATUS){
							instancePipelineStatusDataReLoad(false);
						}else{
							timer.cancel();
							timer = null;
						}
					}
				});
			}
		};
		timer.scheduleRepeating(Integer.parseInt(config.get(Constants.WEB_SCHEDULER_TIMING_KEY)));
	}
	
	private void instancePipelineStatusDataReLoad(final boolean parameter){
		//instance pipeline XML data load
		InstancePipelineController.Util.getInstance().getInstancePipelineXMLDispatchModel(iModel.getInstanceID(), projectName, new AsyncCallback<XmlDispatchModel>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(XmlDispatchModel result) {
				// TODO Auto-generated method stub
				
				//replace xmlDispatchModel data
				xmlDispatchModel = result;

				//reload state decoration label
				for(XmlModuleModel xm : xmlDispatchModel.getModulesBeanList()){
					
					for(DecorationLabelStoreModel dm : decorationMapList.get(xm.getKey())){
						if(xm.getStatus().equals("Running")){
							logViewFireEvent(dm, "#93e3ff", "#76b6cc", "Running");
						}else if(xm.getStatus().equals("Complete")){
							logViewFireEvent(dm, "#fffddd", "#cccab1", "Complete");
						}else if(xm.getStatus().equals("Err")){
							logViewFireEvent(dm, "#ff8265", "#ff5938", "Error");
						}else{
							logViewFireEvent(dm, "#e8e8e8", "#bababa", "Waiting");
						}
					}
				}
				
				if(parameter){
					//project parameter window data load
					eventBus.fireEvent(new TransmitParameterDataEvent(projectName, xmlDispatchModel.getParametersBeanList()));
					
					//project parameter grid data load
					eventBus.fireEvent(new WorkflowSendChangeDataEvent(projectName, xmlDispatchModel));
				}
				
				//toolkit box state reload or change event
				eventBus.fireEvent(new TabChangeStateReloadEvent(projectName, iModel.getInstanceID()));
			}
		});
	}
	
	private XmlDispatchModel pipelineModuleStateReset(XmlDispatchModel xmlDispatchModel){
		for(XmlModuleModel xm : xmlDispatchModel.getModulesBeanList()){
			xm.setStatus("Waiting");
		}
		return xmlDispatchModel;
	}
	
	private void instancePipelineReSubmit(final String instanceOwner, final String projectName, final String instanceID, final String pipelineID,
			final String ownerEmail, final XmlDispatchModel xmlDispatchModel, final boolean execute, final int state){
		
		pipelineModuleStateReset(xmlDispatchModel);
		
		InstancePipelineController.Util.getInstance().updateInstancePipelineStatus(
				instanceOwner, instanceID, projectName, state, new AsyncCallback<Integer>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(Integer result) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new SettingRunButtonRegisterEvent(projectName, state));
				
				updateInstancePipelineXML(instanceOwner, projectName, instanceID, pipelineID, ownerEmail, xmlDispatchModel, execute, state);
			}
		});
		
//		updateInstancePipelineState(instanceOwner, projectName, instanceID, state);
	}
	
	private void instancePipelineForceStop(String instanceOwner, String instanceID, Map<String, String> config, String instanceName, String ownerEmail){
		
		WorkManagementController.Util.getInstance().pipelineForceStop(instanceOwner, instanceID, config, instanceName, ownerEmail, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(Boolean result) {
				// TODO Auto-generated method stub
				if(result){
					SC.say(projectName, "The execution of [" + projectName + "] analysis pipeline project has been forcefully terminated.");
					
					if(timer != null){
						timer.cancel();
						timer = null;
					}
					
				}else{
					SC.warn(projectName, "An error has occurred while terminating the execution of the [" + projectName + "] analysis pipeline project.");
				}
			}
		});
	}
	
	private void executePipeline(String instanceOwner, final String projectName,
			final String instanceID, String pipelineID, String ownerEmail, XmlDispatchModel xmlDispatchModel) {
		
		WorkManagementController.Util.getInstance().pipelineExecute(config, instanceOwner,
				projectName, instanceID, pipelineID, ownerEmail, 
				xmlDispatchModel, new AsyncCallback<Void>() {
			
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					System.out.println(caught.getCause() + ":" + caught.getMessage());
				}
				@Override
				public void onSuccess(Void result) {
					// TODO Auto-generated method stub
					SC.say(projectName, "The execution of ["+ projectName + "] analysis pipeline project has been completed.<br>" + CommonUtilsGwt.getDate()); 
					
					System.out.println("GWT event service connect");
					
					eventBus.fireEvent(new GwtEventServiceConnectEvent(instanceID));
					
					if(timer == null){
						statusTimer();
					}
					
				}
			});
	}
	
	private void updateInstancePipelineXML(final String instanceOwner, final String projectName, final String instanceID, final String pipelineID,
			final String ownerEmail, final XmlDispatchModel xmlDispatchModel, final boolean execute, final int state) {
		
		InstancePipelineController.Util.getInstance().updateWorkFlowXML( instanceOwner, projectName, instanceID, xmlDispatchModel,
				new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				
				if(execute){
					executePipeline(instanceOwner, projectName, instanceID, pipelineID, ownerEmail, xmlDispatchModel);
					eventBus.fireEvent(new TransmitParameterDataEvent(projectName, xmlDispatchModel.getParametersBeanList()));
					eventBus.fireEvent(new WorkflowSendChangeDataEvent(projectName, xmlDispatchModel));
					eventBus.fireEvent(new SettingRunButtonRegisterEvent(projectName, state));
				}
				
				reDrawCanvas();
			}
		});
	}
	
	private void updateInstancePipelineState(String instanceOwner, final String projectName,
			String instanceID, final int state){
		InstancePipelineController.Util.getInstance().updateInstancePipelineStatus(
				instanceOwner, instanceID, projectName, state, new AsyncCallback<Integer>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(Integer result) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new SettingRunButtonRegisterEvent(projectName, state));
			}
		});
	}

	private void reDrawCanvas(){
		//canvas remove child
		Set<String> keySet = shapeMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		
		while (iterator.hasNext()){
			String key = iterator.next();
			Object value = shapeMap.get(key).getShape();
			controller.deleteWidget((Widget) value);
		}
		
		//variable initialization
		shapeMap = new HashMap<String, ShapesModel>();
	    connectionList = new ArrayList<ModuleConnectStoreModel>();
	    decorationList = new ArrayList<DecorationLabelStoreModel>();
	    decorationMapList.clear();
	    
		//function call draw(XMLDispatchData)
		draw(xmlDispatchModel);
	}
	
	private void reLoadConnectionDataFireEvent(){
		String xml = controller.exportDiagram();
		IntegrationModuleConnectModel integrationLinkedModel = XmlDataParsingUtil.getIntegrationLinkedModel(xml);
		eventBus.fireEvent(new ReloadConnectionWindowEvent(projectName, integrationLinkedModel));
	}
	
	private void removeConnectionListFromArray(String key){
		for(Iterator<ModuleConnectStoreModel> it = connectionList.iterator(); it.hasNext();){
			ModuleConnectStoreModel model = it.next();
			if(model.getSource().equals(key) || model.getTarget().equals(key)){
				it.remove();
			}
		}
	}
	
	private void removeConnectionListFromArray(String startKey, String endKey){
		for(Iterator<ModuleConnectStoreModel> it = connectionList.iterator(); it.hasNext();){
			ModuleConnectStoreModel model = it.next();
			if(model.getSource().equals(startKey) && model.getTarget().equals(endKey)){
				Connection c = model.getConnection();
				c.delete();
				controller.deleteConnection(c);
				it.remove();
			}
		}
	}
	
	private void addConnectionLinkedEvent(){
		controller.addTieLinkHandler(new TieLinkHandler() {
			@Override
			public void onTieLink(TieLinkEvent event) {
				// TODO Auto-generated method stub
				String startKey = event.getStartWidget().getElement().getAttribute("key");
				String endKey = event.getEndWidget().getElement().getAttribute("key");
				String moduleName = event.getStartWidget().getElement().getAttribute("name");
				
				Connection connection = event.getConnection();
				ModuleConnectStoreModel connectionStoreModel = new ModuleConnectStoreModel();
				connectionStoreModel.setSource(startKey);
				connectionStoreModel.setTarget(endKey);
				connectionStoreModel.setConnection(connection);
				connectionStoreModel.setId(CommonUtilsGwt.getUUID());
				connectionStoreModel.setKey(startKey);
				connectionStoreModel.setSourceID(CommonUtilsGwt.getUUID());
				connectionStoreModel.setTargetID(CommonUtilsGwt.getUUID());
				
				connectionList.add(connectionStoreModel);
				
				reLoadConnectionDataFireEvent();
				
//				ProcessStatusLabel statusLabel = new ProcessStatusLabel(startKey, shapeMap.get(startKey).getXmlModuleModel().getStatus());
				ProcessStatusLabel statusLabel = new ProcessStatusLabel(startKey, "Connect");
				statusLabel.getElement().getStyle().setBorderColor("#ccbab1");
				statusLabel.getElement().getStyle().setBackgroundColor("#ffe9dd");
				
				controller.addDecoration(statusLabel, connection);
				DecorationLabelStoreModel dm = new DecorationLabelStoreModel();
				dm.setKey(startKey);
				dm.setStatusLabel(statusLabel);
				dm.setModuleName(moduleName);
				
				decorationList.add(dm);
				decorationMapList.put(endKey, dm);
			}
		});
	}
	
	private void logViewFireEvent(DecorationLabelStoreModel dm, String bg, String bc, String text){
		dm.getStatusLabel().getElement().getStyle().setBackgroundColor(bg);
		dm.getStatusLabel().getElement().getStyle().setBorderColor(bc);
		dm.getStatusLabel().getElement().setInnerText(text);
	}
	
	private void showConsolLogWindowFireEvent(){
		registerHandler[17] = eventBus.addHandler(ConsolLogWindowEvent.TYPE, new ConsolLogWindowEventHandler() {
			@Override
			public void consolLogWindow(ConsolLogWindowEvent event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					eventBus.fireEvent(new ShowConsolLogWindowEvent(projectName));
				}
			}
		});
	}
	
	private void showRegistrationWindowFireEvent(){
		registerHandler[16] = eventBus.addHandler(PipelineRegistrationEvent.TYPE, new PipelineRegistrationEventHandler() {
			@Override
			public void pipelineRegistration(PipelineRegistrationEvent event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					savePipelineXMLData(true);
				}
			}
		});
	}
	
	private void showHistoryListGridFireEvent(){
		registerHandler[15] = eventBus.addHandler(HistoryDataWindowEvent.TYPE, new HistoryDataWindowEventHandler() {
			@Override
			public void historyDataWindowEvent(HistoryDataWindowEvent event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					eventBus.fireEvent(new ShowHistoryWindowEvent(event.getProjectName()));
				}
			}
		});
	}
	
	private void instancePipelineStatusRefreshFireEvent(){
		registerHandler[14] = eventBus.addHandler(ToolBoxWorkflowStatusEvent.TYPE, new ToolBoxWorkflowStatusEventHandler() {			
			@Override
			public void toolBoxWorkflowStatusEvent(ToolBoxWorkflowStatusEvent event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					instancePipelineStatusDataReLoad(false);
				}
			}
		});
	}
	
	private void getPipelineStateDataPushFireEvent(){
		registerHandler[13] = eventBus.addHandler(GWTMessagePushFireEvent.TYPE, new GWTMessagePushFireEventHandler() {
			@Override
			public void gwtMessageFireEvent(GWTMessagePushFireEvent event) {
				// TODO Auto-generated method stub

				if(projectName.equals(event.getGwtMessagePushServiceModel().getProjectName())){
					
					String em = "";
					
					//decoration label change
					for(XmlModuleModel xm : event.getGwtMessagePushServiceModel().getXmlModulesModelList()){
						
						System.out.println(projectName + " execute state changed pipeline module key: " + xm.getKey());
						
						for(DecorationLabelStoreModel dm : decorationMapList.get(xm.getKey())){
							
							System.out.println(xm.getModuleName() + "(" + xm.getKey() + "): " + xm.getStatus() + "\t" + dm.getStatusLabel());
							
							if(xm.getStatus().equals("Running")){
								logViewFireEvent(dm, "#93e3ff", "#76b6cc", "Running");
							}else if(xm.getStatus().equals("Complete")){
								logViewFireEvent(dm, "#fffddd", "#cccab1", "Complete");
							}else if(xm.getStatus().equals("Err")){
								logViewFireEvent(dm, "#ff8265", "#ff5938", "Error");
								
								if(em.length() == 0){
									em = xm.getModuleName();
								}else{
									em += em + "," + xm.getModuleName();
								}
							}else{
								logViewFireEvent(dm, "#e8e8e8", "#bababa", "Waiting");
							}
						}
					}

					//pipeline complete ? error ? ==> state db update
					if(event.getGwtMessagePushServiceModel().getMessage().equals("complete")){
						eventBus.fireEvent(new SettingRunButtonRegisterEvent(projectName, 2));
					}else if(event.getGwtMessagePushServiceModel().getMessage().equals("error")){
						eventBus.fireEvent(new SettingRunButtonRegisterEvent(projectName, 3));
						SC.say("[" + em + "] An exception has occurred during the execution of the analysis pipeline. Check your input parameters and data.");
					}
				}
			}
		});
	}
	
	private void resetFireEvent(){
		registerHandler[12] = eventBus.addHandler(DrawXMLResetEvent.TYPE, new DrawXMLResetEventHandler() {
			@Override
			public void drawXMLResetEvent(DrawXMLResetEvent event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					SC.confirm("Warning", "Are you sure you want to initialize the pipeline?", new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							// TODO Auto-generated method stub
							if(value){
								
								System.out.println(projectName
										+ " reset file event info: ["
										+ iModel.getInstanceOwner()
										+ ":"
										+ iModel.getInstanceID()
										+ ":"
										+ iModel.getInstanceName()
										+ iModel.getPipelineID()
										+ "]");
								
								InstancePipelineController.Util.getInstance().resetWorkFlowXML(iModel.getInstanceOwner(), 
										iModel.getInstanceID(), iModel.getInstanceName(), iModel.getPipelineID(), iModel.isRegister(),
										new AsyncCallback<XmlDispatchModel>() {
									@Override
									
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub
										System.out.println(caught.getCause() + ":" + caught.getMessage());
									}
									@Override
									public void onSuccess(XmlDispatchModel result) {
										// TODO Auto-generated method stub
										xmlDispatchModel = result;
										reDrawCanvas();
										eventBus.fireEvent(new TransmitParameterDataEvent(projectName, xmlDispatchModel.getParametersBeanList()));
										eventBus.fireEvent(new SettingRunButtonRegisterEvent(projectName, 0));
									}
								});
							}
						}
					});
				}
			}
		});
	}
	
	private void tabChangeFireEvent(){
		registerHandler[11] = eventBus.addHandler(TabChangeParameterSenderEvent.TYPE, new TabChangeParameterSenderEventHandler() {			
			@Override
			public void tabChangeParameterSender(TabChangeParameterSenderEvent event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					instancePipelineStatusDataReLoad(true);
				}
			}
		});
	}
	
	private void fullParameterWindowFireEvent(){
		registerHandler[10] = eventBus.addHandler(ToolBoxParameterWindowEvent.TYPE, new ToolBoxParameterWindowEventHandler() {
			@Override
			public void toolBoxParameterWindowEvent(ToolBoxParameterWindowEvent event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					
					parameterWindow = new ParameterGroupWindow(projectName, xmlDispatchModel.getModulesBeanList(), xmlDispatchModel.getParametersBeanList(), xmlDispatchModel.getConnectionsBeanList(), 
							eventBus, iModel.getInstanceOwner(), config);
					parameterWindow.show();
				}
			}
		});
	}
	
	private void paramterSetting(List<XmlParameterModel> parameters){
		for(XmlParameterModel pm : xmlDispatchModel.getParametersBeanList()){
			for(XmlParameterModel cpm : parameters){
				if(pm.getId().equals(cpm.getId())){
					pm.setSetupValue(cpm.getSetupValue());
				}
			}
		}
	}
	
	private void parameterSettingFireEvent(){
		registerHandler[9] = eventBus.addHandler(ParameterSettingEvent.TYPE, new ParameterSettingEventHandler() {
			@Override
			public void parameterSettingEvent(ParameterSettingEvent event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					
					paramterSetting(event.getParameters());
				}
			}
		});
	}
	
	private List<XmlParameterModel> getModuleParameters(String key){
		List<XmlParameterModel> list = new ArrayList<XmlParameterModel>();
		
		for(XmlParameterModel xm : xmlDispatchModel.getParametersBeanList()){
			if(xm.getKey().equals(key)){
				list.add(xm);
			}
		}
		return  list;
	}
	
	private void removeParameterFromArray(String key){
		
		for(Iterator<XmlParameterModel> it = xmlDispatchModel.getParametersBeanList().iterator(); it.hasNext();){
			
			XmlParameterModel parameter = it.next();
			
			System.out.println(projectName + " remove parameters event info: ["
					+ parameter.getName() + ":" + parameter.getKey() + ":"
					+ parameter.getValue() + "]");
			
			if(parameter.getKey().equals(key)){
				it.remove();
			}
		}
	}
	
	private void expertStraightFireEvent(){
		registerHandler[8] = eventBus.addHandler(ExpertFunctionStraightEvent.TYPE, new ExpertFunctionStraightEventHandler() {
			@Override
			public void expertFunctionStraightEvent(ExpertFunctionStraightEvent event) {
				// TODO Auto-generated method stub
				
				if(projectName.equals(event.getProjectName())){
					
					String key = event.getKey();
					
					/*
					String xml = controller.exportDiagram();
					List<ModuleLinksModel> list = LinkedXMLDataParserUtils.getParserLinkedXmlLinksData(xml);
					*/
					
					for(ModuleConnectStoreModel connectionListModel : connectionList){
						if(connectionListModel.getSource().equals(key) || connectionListModel.getTarget().equals(key)){
							connectionListModel.getConnection().setStraight();
						}
					}
				}
			}
		});
	}
	
	private void executeProjectFireEvent(){
		
		registerHandler[7] = eventBus.addHandler(ExecuteProjectEvent.TYPE, new ExecuteProjectEventHandler() {
			@Override
			public void executeProjectEvent(ExecuteProjectEvent event) {
				// TODO Auto-generated method stub
				
				if(projectName.equals(event.getProjectName())){
					
					switch (event.getStatus()) {					
						case 0:
							
							SC.confirm(projectName, "Please, make sure to save the project before executing it.", 
									new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									// TODO Auto-generated method stub
									
									if(value){
										updateInstancePipelineXML(iModel.getInstanceOwner(), projectName, 
												iModel.getInstanceID(), iModel.getPipelineID(), iModel.getOwnerEmail(), xmlDispatchModel, true, 1);
									}
								}
							});
							
							break;
							
						case 1:
							
							SC.confirm(projectName, "Are you sure you want to terminate the running project?", 
									new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									// TODO Auto-generated method stub
									if(value){
										instancePipelineForceStop(iModel.getInstanceOwner() ,iModel.getInstanceID(), config, 
												iModel.getInstanceName(), iModel.getOwnerEmail());
										
										updateInstancePipelineState(iModel.getInstanceOwner(), 
												projectName, iModel.getInstanceID(), 3);
									}
								}
							});
							
							break;
							
						case 2:
							
							SC.confirm(projectName, "The previous execution results are deleted if the project is restarted.<BR>Do you want to proceed?", 
									new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									// TODO Auto-generated method stub
									
									if(value){
										instancePipelineReSubmit(iModel.getInstanceOwner(), projectName, 
												iModel.getInstanceID(), iModel.getPipelineID(), iModel.getOwnerEmail(), xmlDispatchModel, true, 1);
									}
								}
							});
							
							break;
							
						case 3:
							
							SC.confirm(projectName, "An error has occurred while running the project. <BR>Do you want to rerun the project after recovery?", 
									new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									// TODO Auto-generated method stub
									if(value){
										instancePipelineReSubmit(iModel.getInstanceOwner(), projectName, 
												iModel.getInstanceID(), iModel.getPipelineID(), iModel.getOwnerEmail(), xmlDispatchModel, false, 0);
									}
								}
							});
							
							break;
							
						default:
							
							System.out.println("[" + projectName + "] execution exception!!");
							
							break;
					}
				}
			}
		});
	}
	
	private void addPipelineModuleFireEvent(){
		registerHandler[6] = eventBus.addHandler(AddPipelineModuleEvent.TYPE, new AddPipelineModuleEventHandler() {	
			@Override
			public void addPipelineModule(final AddPipelineModuleEvent event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					
					ADD_MODULE_KEY++;
					
					int COUNT = 0;
					
					for(XmlModuleModel xx : xmlDispatchModel.getModulesBeanList()){
						
						if(xx.getModuleName().startsWith(event.getModuleModel().getModuleName())){
							COUNT++;
						}
					}
					
					if(COUNT != 0){
						event.getModuleModel().setModuleName(event.getModuleModel().getModuleName() + "_" + COUNT);
					}
					
					event.getModuleModel().setKey(String.valueOf(ADD_MODULE_KEY));
					event.getModuleModel().setX(event.getModuleModel().getX() - left);
					event.getModuleModel().setY(event.getModuleModel().getY() - top);
					
					//parameter parser add list
					InstancePipelineController.Util.getInstance().getPrameterParserData(
							event.getModuleModel().getKey(), event.getModuleModel().getModuleName(), 
							event.getModuleModel().getParameter(), 
							new AsyncCallback<List<XmlParameterModel>>() {
								
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println(caught.getCause() + ":" + caught.getMessage());
						}
						@Override
						public void onSuccess(List<XmlParameterModel> result) {
							// TODO Auto-generated method stub
							event.getModuleModel().setParameterNumber(String.valueOf(result.size()));
							xmlDispatchModel.getParametersBeanList().addAll(result);
						}
					});
					
					xmlDispatchModel.getModulesBeanList().add(event.getModuleModel());
					drawShape(event.getModuleModel());
					reLoadConnectionDataFireEvent();
				}
			}
		});
	}
	
	private void deleteConnectionFireEvent(){
		registerHandler[5] = eventBus.addHandler(DeleteConnectionEvent.TYPE, new DeleteConnectionEventHandler() {
			@Override
			public void deleteConnectionEvent(DeleteConnectionEvent event) {
				// TODO Auto-generated method stub

				String keys[] = null;

				for(String key : event.getKey()){
					keys = key.split("&");
					removeConnectionListFromArray(keys[0], keys[1]);
				}
				String xml = controller.exportDiagram();
				IntegrationModuleConnectModel integrationLinkedModel = XmlDataParsingUtil.getIntegrationLinkedModel(xml);
				eventBus.fireEvent(new ReloadConnectionWindowEvent(projectName, integrationLinkedModel));
			}
		});
	}
	
	private void deleteModuleFireEvent(){
		registerHandler[4] = eventBus.addHandler(DeleteModuleEvent.TYPE, new DeleteModuleEventHandler() {
			@Override
			public void deleteModuleEvent(DeleteModuleEvent event) {
				// TODO Auto-generated method stub
				
				for(String key : event.getKey()){
					controller.deleteWidget((WindowPanel)shapeMap.get(key).getShape());
					shapeMap.remove(key);
					removeConnectionListFromArray(key);
					removeModuleFromArray(key);
					removeParameterFromArray(key);
				}
				String xml = controller.exportDiagram();
				IntegrationModuleConnectModel integrationLinkedModel = XmlDataParsingUtil.getIntegrationLinkedModel(xml);
				
				eventBus.fireEvent(new ReloadConnectionWindowEvent(projectName, integrationLinkedModel));
			}
		});
	}
	
	private void removeModuleFromArray(String key){
		for(Iterator<XmlModuleModel> it = xmlDispatchModel.getModulesBeanList().iterator(); it.hasNext();){
			XmlModuleModel model = it.next();
			if(model.getKey().equals(key)){
				it.remove();
			}
		}
	}
	
	private void connectorStraightFireEvent(){
		registerHandler[3] = eventBus.addHandler(ConnectionLinkedStraightEvent.TYPE, new ConnectionLinkedStraightEventHandler() {
			@Override
			public void linkedConnectStraightEvent(ConnectionLinkedStraightEvent event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					for(ModuleConnectStoreModel connectionStoreModel : connectionList){
						if(connectionStoreModel.getSource().equals(event.getStartKey()) && 
								connectionStoreModel.getTarget().equals(event.getEndKey())){
							connectionStoreModel.getConnection().setStraight();
						}
					}
				}
			}
		});
	}
	
	private void pipelineSaveXMLFireEvent(){
		registerHandler[2] = eventBus.addHandler(PipelineXMLSaveEvent.TYPE, new PipelineXMLSaveEventHandler() {
			@Override
			public void saveXMLData(PipelineXMLSaveEvent event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					SC.confirm(projectName, "Do you want to save the project?", new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							// TODO Auto-generated method stub
							if(value){
								savePipelineXMLData(false);
							}
						}
					});
				}
			}
		});
	}
	
	private void showConnectionListGridFireEvent(){
		registerHandler[1] = eventBus.addHandler(ConnectionDataWindowEvent.TYPE, new ConnectionDataWindowEventHandler() {
			@Override
			public void connectionDataWindowEvent(ConnectionDataWindowEvent event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					String xml = controller.exportDiagram();
					IntegrationModuleConnectModel integrationLinkedModel = XmlDataParsingUtil.getIntegrationLinkedModel(xml);
					eventBus.fireEvent(new ShowConnectionWindowEvent(event.getProjectName(), integrationLinkedModel));
				}
			}
		});
	}
	
	private void removeRegisterFireEvent(){
		registerHandler[0] = eventBus.addHandler(RemoveRegisterEvents.TYPE, new RemoveRegisterEventsHandler() {
			@Override
			public void removeRegisterEventHandler(RemoveRegisterEvents event) {
				// TODO Auto-generated method stub
				if(projectName.equals(event.getProjectName())){
					for(int i = 0; i < registerHandler.length; i++){
						registerHandler[i].removeHandler();
					}
					
					//timer cacel
					System.out.println("timer cacel");
					if(timer != null){
						timer.cancel();
						timer = null;
					}
				}
			}
		});
	}
	
	private void changeEventPipelineXMLData(){
		
		//show progressBarWindow
		progressBarWindow = new ProgressWindow(Messages.PROJECT_CHANGE_EVENT_TITLE);
		progressBarWindow.show();
		
		String xml = controller.exportDiagram();
		IntegrationModuleConnectModel integrationLinkedModel = XmlDataParsingUtil.getIntegrationLinkedModel(xml);
		
		//module information
		for(XmlModuleModel mm : xmlDispatchModel.getModulesBeanList()){

			System.out.print(projectName
					+ " pipeline xml data save event info: [" + mm.getKey()
					+ " : " + mm.getModuleName() + " (" + mm.getX() + ", "
					+ mm.getY() + ") " + mm.getModuleDesc() + "]");
			
			for(ModuleInfomModel mf : integrationLinkedModel.getModuleFunctionModel()){
				if(mm.getKey().equals(mf.getIdentifier())){
					System.out.println(mm.getModuleName() + " position data: ["
							+ mf.getLeft() + ", " + mf.getTop() + "]");
					mm.setX(CommonUtilsGwt.changeType(mf.getLeft()));
					mm.setY(CommonUtilsGwt.changeType(mf.getTop()));
				}
			}
		}

		//connection information
		List<XmlConnectLinkedModel> xmlConnectionList = new ArrayList<XmlConnectLinkedModel>();
		List<PointsModel> points;
		for(ModuleConnectStoreModel cm : connectionList){
			XmlConnectLinkedModel xcm = new XmlConnectLinkedModel();
			points = new ArrayList<PointsModel>();
			
			xcm.setId(cm.getId());
			xcm.setKey(cm.getKey());
			xcm.setSource(cm.getSource());
			xcm.setTarget(cm.getTarget());
			xcm.setSourceId(cm.getSourceID());
			xcm.setTargetId(cm.getTargetID());
			
			for (int i = 0; i < integrationLinkedModel.getModuleLinksModel().size(); i++) {
				
				ModuleConnectionInfomModel mm = integrationLinkedModel.getModuleLinksModel().get(i);
				
				for (int j = 0; j < mm.getPoints().length; j++) {
					
					if(mm.getStartKey().equals(cm.getSource()) && mm.getEndKey().equals(cm.getTarget())){
						PointsModel pm = new PointsModel();
						pm.setLeft(mm.getPoints()[j][0]);
						pm.setTop(mm.getPoints()[j][1]);
						points.add(pm);
					}
				}
			}
			xcm.setPoints(points);
			xmlConnectionList.add(xcm);
		}
		
		xmlDispatchModel.setConnectionsBeanList(xmlConnectionList);
		
		for(XmlConnectLinkedModel c : xmlDispatchModel.getConnectionsBeanList()){
			
			System.out.print(projectName + " source: " + c.getSource() + " + tartget: " + c.getTarget() + "\t");
			
			for(PointsModel p : c.getPoints()){
				System.out.print("left position: " + p.getLeft() + ", top position: " + p.getTop() + "\t\n");
			}
		}

		//save event
		InstancePipelineController.Util.getInstance().updateWorkFlowXML(iModel.getInstanceOwner(), 
				iModel.getInstanceName(), iModel.getInstanceID(), xmlDispatchModel, 
				new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				//destory progressBarWindow
				progressBarWindow.destroy();
				reDrawCanvas();
				eventBus.fireEvent(new TransmitParameterDataEvent(projectName, xmlDispatchModel.getParametersBeanList()));
				eventBus.fireEvent(new WorkflowSendChangeDataEvent(projectName, xmlDispatchModel));
			}
		});
	}
	
	private void savePipelineXMLData(final boolean registeration){
		
		//show progressBarWindow
		progressBarWindow = new ProgressWindow(Messages.PROJECT_SAVE_TITLE);
		progressBarWindow.show();
		
		String xml = controller.exportDiagram();
		IntegrationModuleConnectModel integrationLinkedModel = XmlDataParsingUtil.getIntegrationLinkedModel(xml);
		
		//module information
		for(XmlModuleModel mm : xmlDispatchModel.getModulesBeanList()){

			System.out.print(projectName
					+ " pipeline xml data save event info: [" + mm.getKey()
					+ " : " + mm.getModuleName() + " (" + mm.getX() + ", "
					+ mm.getY() + ") " + mm.getModuleDesc() + "]");
			
			for(ModuleInfomModel mf : integrationLinkedModel.getModuleFunctionModel()){
				if(mm.getKey().equals(mf.getIdentifier())){
					System.out.println(mm.getModuleName() + " position data: ["
							+ mf.getLeft() + ", " + mf.getTop() + "]");
					mm.setX(CommonUtilsGwt.changeType(mf.getLeft()));
					mm.setY(CommonUtilsGwt.changeType(mf.getTop()));
				}
			}
		}

		//connection information
		List<XmlConnectLinkedModel> xmlConnectionList = new ArrayList<XmlConnectLinkedModel>();
		List<PointsModel> points;
		for(ModuleConnectStoreModel cm : connectionList){
			XmlConnectLinkedModel xcm = new XmlConnectLinkedModel();
			points = new ArrayList<PointsModel>();
			
			xcm.setId(cm.getId());
			xcm.setKey(cm.getKey());
			xcm.setSource(cm.getSource());
			xcm.setTarget(cm.getTarget());
			xcm.setSourceId(cm.getSourceID());
			xcm.setTargetId(cm.getTargetID());
			
			for (int i = 0; i < integrationLinkedModel.getModuleLinksModel().size(); i++) {
				
				ModuleConnectionInfomModel mm = integrationLinkedModel.getModuleLinksModel().get(i);
				
				for (int j = 0; j < mm.getPoints().length; j++) {
					
					if(mm.getStartKey().equals(cm.getSource()) && mm.getEndKey().equals(cm.getTarget())){
						PointsModel pm = new PointsModel();
						pm.setLeft(mm.getPoints()[j][0]);
						pm.setTop(mm.getPoints()[j][1]);
						points.add(pm);
					}
				}
			}
			xcm.setPoints(points);
			xmlConnectionList.add(xcm);
		}
		xmlDispatchModel.setConnectionsBeanList(xmlConnectionList);
		
		for(XmlConnectLinkedModel c : xmlDispatchModel.getConnectionsBeanList()){
			
			System.out.print(projectName + " source: " + c.getSource() + " + tartget: " + c.getTarget() + "\t");
			
			for(PointsModel p : c.getPoints()){
				System.out.print("left position: " + p.getLeft() + ", top position: " + p.getTop() + "\t\n");
			}
		}

		//save event
		InstancePipelineController.Util.getInstance().updateWorkFlowXML(iModel.getInstanceOwner(), 
				iModel.getInstanceName(), iModel.getInstanceID(), xmlDispatchModel, 
				new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				//destory progressBarWindow
				Timer timer = new Timer() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						progressBarWindow.destroy();
						reDrawCanvas();
						eventBus.fireEvent(new TransmitParameterDataEvent(projectName, xmlDispatchModel.getParametersBeanList()));
						eventBus.fireEvent(new WorkflowSendChangeDataEvent(projectName, xmlDispatchModel));
						
						if(registeration){
							registrationWindow = new PipelineRegisterWindow(eventBus, projectName, 
									xmlDispatchModel, iModel.getInstanceID(), iModel.isRegister());
							registrationWindow.show();
						}else{
							SC.say(projectName, "[" + projectName + "] has been saved.");
						}
					}
				};
				timer.schedule(800);
			}
		});
	}
	
	private void getPipelineXMLData(){
		WorkFlowController.Util.getInstance().drawParserXML(iModel.getInstanceXML(), new AsyncCallback<XmlDispatchModel>() {
			@Override
			public void onSuccess(XmlDispatchModel xmlDispatchModel) {
				// TODO Auto-generated method stub
				draw(xmlDispatchModel);
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
		});
	}
	
	private boolean is_move = false;
	
	private void registerShapeEvent(final WindowPanel shape, final XmlModuleModel xmlModuleModel){
		
		shape.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				// TODO Auto-generated method stub
				
				if(event.getAssociatedType().getName().toLowerCase().equals("dblclick")){
					List<XmlParameterModel> parameters = new ArrayList<XmlParameterModel>();
					parameters = getModuleParameters(xmlModuleModel.getKey());
					
					int relativeX = event.getRelativeX(controller.getDiagramCanvas().getElement());
					int relativeY = event.getRelativeY(controller.getDiagramCanvas().getElement());
					eventBus.fireEvent(new ShowModuleDialogWindowEvent(relativeX, relativeY, projectName, xmlModuleModel, parameters));
				}
				
			}
		});
		
		shape.getElement().setDraggable(Element.DRAGGABLE_TRUE);
		
		shape.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				// TODO Auto-generated method stub
				System.out.println(shape.getElement().getAttribute("name") + " moving..");
				is_move = true;
			}
		});
		
		shape.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event){
				// TODO Auto-generated method stub
				
				if(is_move){
					System.out.println(shape.getElement().getAttribute("name") + " has successfully been relocated.");
					changeEventPipelineXMLData();
					is_move = false;
				}	
			}
		});
	}
	
	private void drawShape(XmlModuleModel xmlModuleModel){

		Image image = new Image(xmlModuleModel.getImgSrc());
		image.setSize(xmlModuleModel.getImgWidth() + Constants.PX,
				xmlModuleModel.getImgHeight() + Constants.PX);
		/*image.getElement().getStyle().setCursor(Cursor.MOVE);*/
		
		Label label = new Label();
		label.setText(xmlModuleModel.getModuleName());
		/*label.getElement().getStyle().setCursor(Cursor.MOVE);*/
		
		WindowPanel shapeLayout = new WindowPanel(windowController, label, image, 
				false, label.getText(), xmlModuleModel.getKey());
		shapeLayout.getElement().setAttribute("key", xmlModuleModel.getKey());
		shapeLayout.getElement().setAttribute("name", xmlModuleModel.getModuleName());
		shapeLayout.getElement().getStyle().setCursor(Cursor.POINTER);
		
		registerShapeEvent(shapeLayout, xmlModuleModel);
		
		controller.addWidget(shapeLayout, xmlModuleModel.getX(), xmlModuleModel.getY());
		
		ShapesModel shapesModel = new ShapesModel();
		shapesModel.setShape(shapeLayout);
		shapesModel.setXmlModuleModel(xmlModuleModel);
		shapeMap.put(xmlModuleModel.getKey(), shapesModel);
		
		dragController = new PickupDragController(controller.getView(), true);
		dragController.setConstrainWidgetToBoundaryPanel(true);
		dragController.setBehaviorConstrainedToBoundaryPanel(true);
		
		dragController.makeDraggable(shapeLayout);	
	}
	
	@Override
	public void draw(XmlDispatchModel xmlDispatchModel){
		
		this.xmlDispatchModel= xmlDispatchModel;
		
		windowController.getPickupDragController().makeDraggable(controller.getView());
	    
		List<XmlModuleModel> activitysBeanList = xmlDispatchModel.getModulesBeanList();
		List<XmlConnectLinkedModel> transitionsBeanList = xmlDispatchModel.getConnectionsBeanList();

		for(XmlModuleModel moduleBean : activitysBeanList){
			if(CommonUtilsGwt.changeType(moduleBean.getKey()) > ADD_MODULE_KEY){
				ADD_MODULE_KEY = CommonUtilsGwt.changeType(moduleBean.getKey());
			}
			drawShape(moduleBean);
		}
		
		String source = "";
		String target = "";
		String sourceID = "";
		String targetID = "";
		String id = "";
		String key = "";
		
		for(XmlConnectLinkedModel transitionsBean : transitionsBeanList){
			
			source = transitionsBean.getSource();
			target = transitionsBean.getTarget();
			sourceID = transitionsBean.getSourceId();
			targetID = transitionsBean.getTargetId();
			id = transitionsBean.getId();
			key = transitionsBean.getKey();
			
			if(!transitionsBean.getTarget().startsWith("-1")){
				
				Connection connect = controller.drawStraightArrowConnection((WindowPanel)shapeMap.get(source).getShape(), (WindowPanel)shapeMap.get(target).getShape());
				
				for(PointsModel pm : transitionsBean.getPoints()){
					controller.addPointOnConnection(connect, pm.getLeft(), pm.getTop());
				}
				
				ProcessStatusLabel statusLabel = new ProcessStatusLabel(target, null);
				
				controller.addDecoration(statusLabel, connect);
				DecorationLabelStoreModel dm = new DecorationLabelStoreModel();
				dm.setKey(target);
				dm.setStatusLabel(statusLabel);
				dm.setModuleName(((WindowPanel)shapeMap.get(target).getShape()).getElement().getAttribute("name"));
				decorationList.add(dm);
				decorationMapList.put(target, dm);
				
				System.out.println("source decorationMapList : " + target + "[" + dm.getModuleName() + "]");
				
				ModuleConnectStoreModel connectionStoreModel = new ModuleConnectStoreModel();
				connectionStoreModel.setSource(source);
				connectionStoreModel.setTarget(target);
				connectionStoreModel.setConnection(connect);
				connectionStoreModel.setId(id);
				connectionStoreModel.setKey(key);
				connectionStoreModel.setSourceID(sourceID);
				connectionStoreModel.setTargetID(targetID);
				connectionList.add(connectionStoreModel);
			}
		}
		
		for(XmlModuleModel moduleBean : activitysBeanList){
			for(DecorationLabelStoreModel dm : decorationMapList.get(moduleBean.getKey())){
				if(moduleBean.getStatus().equals("Running")){
					logViewFireEvent(dm, "#93e3ff", "#76b6cc", "Running");
				}else if(moduleBean.getStatus().equals("Complete")){
					logViewFireEvent(dm, "#fffddd", "#cccab1", "Complete");
				}else if(moduleBean.getStatus().equals("Err")){
					logViewFireEvent(dm, "#ff8265", "#ff5938", "Error");
				}else{
					logViewFireEvent(dm, "#e8e8e8", "#bababa", "Waiting");
				}
			}
		}
		controller.registerDragController(dragController);		
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		getPipelineXMLData();
		
		shapeMap = new HashMap<String, ShapesModel>();
	    connectionList = new ArrayList<ModuleConnectStoreModel>();
	    decorationList = new ArrayList<DecorationLabelStoreModel>();
	    registerHandler = new HandlerRegistration[18];
	    decorationMapList = ArrayListMultimap.create();
	    
	    addPipelineModuleFireEvent();
		pipelineSaveXMLFireEvent();
		addConnectionLinkedEvent();
		showConnectionListGridFireEvent();
		deleteModuleFireEvent();
		deleteConnectionFireEvent();
		connectorStraightFireEvent();
		removeRegisterFireEvent();
		executeProjectFireEvent();
		expertStraightFireEvent();
		fullParameterWindowFireEvent();
		showRegistrationWindowFireEvent();
		parameterSettingFireEvent();
		tabChangeFireEvent();
		resetFireEvent();
		getPipelineStateDataPushFireEvent();
		instancePipelineStatusRefreshFireEvent();
		showHistoryListGridFireEvent();
		showConsolLogWindowFireEvent();
		statusTimer();
	}

	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		controller.setFrameSize(width, height);
		controller.setAllowingUserInteractions(true);
		controller.getDiagramCanvas().getElement().getStyle().setCursor(Cursor.DEFAULT);
		return controller.getViewAsScrollPanel();
	}
}