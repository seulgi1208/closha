package org.kobic.gwt.smart.closha.client.event.gwt;

import org.kobic.gwt.smart.closha.shared.model.event.service.GWTMessagePushServiceModel;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.domain.Domain;

public class GWTMessagePushServiceEvent implements Event{

	private static final long serialVersionUID = 1L;
	
	public Domain domain;
	public GWTMessagePushServiceModel gwtMessagePushServiceModel; 
	
	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public GWTMessagePushServiceModel getGwtMessagePushServiceModel() {
		return gwtMessagePushServiceModel;
	}

	public void setGwtMessagePushServiceModel(
			GWTMessagePushServiceModel gwtMessagePushServiceModel) {
		this.gwtMessagePushServiceModel = gwtMessagePushServiceModel;
	}
}
