package org.kobic.gwt.smart.closha.client;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.application.ApplicationController;
import org.kobic.gwt.smart.closha.client.common.component.ShowMessageWindow;
import org.kobic.gwt.smart.closha.client.common.controller.CommonController;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VLayout;

public class CloshaEntryPoint implements EntryPoint {
	
	public static native void disableContextMenu(Element e) /*-{
		e.oncontextmenu = function() {
			return false;
		};
	}-*/;
	
	public static native String getUserAgent() /*-{
 		//return $wnd.navigator.appVersion;
 		return navigator.userAgent.toLowerCase();
	}-*/;

	public void onModuleLoad() {

		CommonController.Util.getInstance().getConfiguration(new AsyncCallback<Map<String,String>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}

			@Override
			public void onSuccess(Map<String, String> config) {
				// TODO Auto-generated method stub
				HandlerManager eventBus = new HandlerManager(null);
				ApplicationController applicationController = new ApplicationController(eventBus, config);
				
				VLayout canvas = new VLayout(0);
				canvas.setHeight100();
				canvas.setWidth100();
				canvas.setOverflow(Overflow.AUTO);
				
				applicationController.go(canvas);
				
				canvas.draw();
				
				CommonController.Util.getInstance().userBrowserChecker(getUserAgent(), new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println(caught.getCause() + ":" + caught.getMessage());
					}
					@Override
					public void onSuccess(Boolean result) {
						// TODO Auto-generated method stub
						if(result){}
						
						Window alertWindow = new ShowMessageWindow();
						alertWindow.show();
					}
				});
				
				RootPanel.getBodyElement().removeChild(RootPanel.get("loadingWrapper").getElement());
			}
		});
	}
}