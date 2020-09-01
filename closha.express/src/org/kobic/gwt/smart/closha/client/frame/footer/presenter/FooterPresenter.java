package org.kobic.gwt.smart.closha.client.frame.footer.presenter;

import org.kobic.gwt.smart.closha.client.presenter.Presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class FooterPresenter implements Presenter{

	@SuppressWarnings("unused")
	private final HandlerManager eventBus;
	private final Display display;
	
	public interface Display{
		VLayout getBottomLayout();
	}
	
	public FooterPresenter(HandlerManager eventBus, Display view){
		this.eventBus = eventBus;
		this.display = view;
	}
	
	@Override
	public void bind(){
		
	}
	
	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.getBottomLayout());
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
