package org.kobic.gwt.smart.closha.client.monitoring.job.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class HostInformationListGridRecord extends ListGridRecord implements IsSerializable{
	
	public HostInformationListGridRecord(){}
	
	public HostInformationListGridRecord(String host, String load, String cpu, 
			String memTo, String memUse, String swapTo, String swapUse){
		setHost(host);
		setLoad(load);
		setCpu(cpu);
		setMemTo(memTo);
		setMemUse(memUse);
		setSwapTo(swapTo);
		setSwapUse(swapUse);
	}
	
	public String getHost() {
		return getAttributeAsString("host");
	}
	public void setHost(String host) {
		setAttribute("host", host);  
	}
	public String getLoad() {
		return getAttributeAsString("load");
	}
	public void setLoad(String load) {
		setAttribute("load", load);  
	}
	public String getCpu() {
		return getAttributeAsString("cpu");
	}
	public void setCpu(String cpu) {
		setAttribute("cpu", cpu);  
	}
	public String getMemTo() {
		return getAttributeAsString("memTo");
	}
	public void setMemTo(String memTo) {
		setAttribute("memTo", memTo);  
	}
	public String getMemUse() {
		return getAttributeAsString("memUse");
	}
	public void setMemUse(String memUse) {
		setAttribute("memUse", memUse);  
	}
	public String getSwapTo() {
		return getAttributeAsString("swapTo");
	}
	public void setSwapTo(String swapTo) {
		setAttribute("swapTo", swapTo);  
	}
	public String getSwapUse() {
		return getAttributeAsString("swapUse");
	}
	public void setSwapUse(String swapUse) {
		setAttribute("swapUse", swapUse);  
	}
}
