package org.kobic.gwt.smart.closha.shared.model.job.scheduler;

import com.google.gwt.user.client.rpc.IsSerializable;

public class HostsModel implements IsSerializable{

	private String host;
	private String load;
	private String cpu;
	private String memTo;
	private String memUse;
	private String swapTo;
	private String swapUse;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getLoad() {
		return load;
	}
	public void setLoad(String load) {
		this.load = load;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getMemTo() {
		return memTo;
	}
	public void setMemTo(String memTo) {
		this.memTo = memTo;
	}
	public String getMemUse() {
		return memUse;
	}
	public void setMemUse(String memUse) {
		this.memUse = memUse;
	}
	public String getSwapTo() {
		return swapTo;
	}
	public void setSwapTo(String swapTo) {
		this.swapTo = swapTo;
	}
	public String getSwapUse() {
		return swapUse;
	}
	public void setSwapUse(String swapUse) {
		this.swapUse = swapUse;
	}
	
}
