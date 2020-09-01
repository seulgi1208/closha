package org.kobic.gwt.smart.closha.client.pipeline.design.component;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasAllTouchHandlers;
import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Label;
import com.orange.links.client.save.IsDiagramSerializable;

public class ProcessStatusLabel extends Label implements HasAllTouchHandlers,IsDiagramSerializable{

	String identifier;
	String content;
	
	public ProcessStatusLabel(String key, String status){
		super(status);
		this.content = status;
		this.identifier = key;
		
		getElement().getStyle().setPadding(2, Unit.PX);
		getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		getElement().getStyle().setBorderWidth(1, Unit.PX);
		/*getElement().getStyle().setBorderColor("#CCCC00");
		getElement().getStyle().setBackgroundColor("#FFFF99");*/
	}
	
	@Override
	public HandlerRegistration addTouchStartHandler(TouchStartHandler handler){
		return addDomHandler(handler, TouchStartEvent.getType());
	}
	
	@Override
	public HandlerRegistration addTouchEndHandler(TouchEndHandler handler){
		return addDomHandler(handler, TouchEndEvent.getType());
	}
	
	@Override
	public HandlerRegistration addTouchMoveHandler(TouchMoveHandler handler){
		return addDomHandler(handler, TouchMoveEvent.getType());
	}
	
	@Override
	public HandlerRegistration addTouchCancelHandler(TouchCancelHandler handler){
		return addDomHandler(handler, TouchCancelEvent.getType());
	}

	@Override
	public String getType() {
		return this.identifier;
	}

	@Override
	public String getContentRepresentation() {
		return this.content;
	}

	@Override
	public void setContentRepresentation(String contentRepresentation) {
		this.content = contentRepresentation;
	}
}
