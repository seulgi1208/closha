package org.kobic.gwt.smart.closha.client.pipeline.composition.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PipelineCompositionTreeGridRecord extends ListGridRecord implements IsSerializable {
	
	public PipelineCompositionTreeGridRecord(){
		
	}
	
	public PipelineCompositionTreeGridRecord(String id, String pId, String name,
			String type, String desc, String status, String version,
			String script, String icon) {
		setId(id);
		setPId(pId);
		setName(name);
		setType(type);
		setDesc(desc);
		setStatus(status);
		setVersion(version);
		setScript(script);
		setIcon(icon);
	}
	
	public String getId() {
		// TODO Auto-generated method stub
		return getAttributeAsString("id");
	}
	
	public void setId(String id) {
		// TODO Auto-generated method stub
		setAttribute("id", id);  
	}
	
	public String getPId() {
		// TODO Auto-generated method stub
		return getAttributeAsString("pId");
	}
	
	public void setPId(String pId) {
		// TODO Auto-generated method stub
		setAttribute("pId", pId);  
	}

	public String getName() {
		// TODO Auto-generated method stub
		return getAttributeAsString("name");
	}
	
	public void setName(String name) {
		// TODO Auto-generated method stub
		setAttribute("name", name);  
	}
	
	public String getType() {
		// TODO Auto-generated method stub
		return getAttributeAsString("type");
	}
	
	public void setType(String type) {
		// TODO Auto-generated method stub
		setAttribute("type", type);  
	}
	
	public String getDesc() {
		// TODO Auto-generated method stub
		return getAttributeAsString("desc");
	}
	
	public void setDesc(String desc) {
		// TODO Auto-generated method stub
		setAttribute("desc", desc);  
	}
	
	public String getStatus() {
		// TODO Auto-generated method stub
		return getAttributeAsString("status");
	}
	
	public void setStatus(String status) {
		// TODO Auto-generated method stub
		setAttribute("status", status);  
	}
	
	public String getVersion() {
		// TODO Auto-generated method stub
		return getAttributeAsString("version");
	}
	
	public void setVersion(String version) {
		// TODO Auto-generated method stub
		setAttribute("version", version);  
	}
	
	public String getScript() {
		// TODO Auto-generated method stub
		return getAttributeAsString("script");
	}
	
	public void setScript(String script) {
		// TODO Auto-generated method stub
		setAttribute("script", script);  
	}
	public String getIcon() {
		return getAttributeAsString("icon");
	}
	public void setIcon(String icon) {
		setAttribute("icon", icon);  
	}
}