package org.kobic.gwt.smart.closha.client.pipeline.design.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ModuleConnectionListGridRecord extends ListGridRecord implements IsSerializable {
	
	public ModuleConnectionListGridRecord(){
		
	}
	
	public ModuleConnectionListGridRecord(String startID, String endID, String startModuleName, 
			String endModuleName, String startKey, String endKey, int xPoint, int yPoint){
		
		setStartID(startID);
		setEndID(endID);
		setStartModuleName(startModuleName);
		setEndModuleName(endModuleName);
		setStartKey(startKey);
		setEndKey(endKey);
		setXPoint(xPoint);
		setYPoint(yPoint);
		setAttribute("linked", "closha/icon/connect.png");
	}
	
	public String getStartID() {
		// TODO Auto-generated method stub
		return getAttributeAsString("startID");
	}
	
	public void setStartID(String startID) {
		// TODO Auto-generated method stub
		setAttribute("startID", startID);  
	}
	
	
	public String getEndID() {
		// TODO Auto-generated method stub
		return getAttributeAsString("endID");
	}
	
	public void setEndID(String endID) {
		// TODO Auto-generated method stub
		setAttribute("endID", endID);  
	}
	
	public String getStartModuleName() {
		// TODO Auto-generated method stub
		return getAttributeAsString("startModuleName");
	}
	
	public void setStartModuleName(String startModuleName) {
		// TODO Auto-generated method stub
		setAttribute("startModuleName", startModuleName);  
	}
	
	public String getEndModuleName() {
		// TODO Auto-generated method stub
		return getAttributeAsString("endModuleName");
	}
	
	public void setEndModuleName(String endModuleName) {
		// TODO Auto-generated method stub
		setAttribute("endModuleName", endModuleName);  
	}
	
	public String getStartKey() {
		// TODO Auto-generated method stub
		return getAttributeAsString("startKey");
	}
	
	public void setStartKey(String startKey) {
		// TODO Auto-generated method stub
		setAttribute("startKey", startKey);  
	}
	
	public String getEndKey() {
		// TODO Auto-generated method stub
		return getAttributeAsString("endKey");
	}
	
	public void setEndKey(String endKey) {
		// TODO Auto-generated method stub
		setAttribute("endKey", endKey);  
	}
	
	public int getXPoint() {
		// TODO Auto-generated method stub
		return getAttributeAsInt("xPoint");
	}
	
	public void setXPoint(int xPoint) {
		// TODO Auto-generated method stub
		setAttribute("xPoint", xPoint);  
	}
	
	public int getYPoint() {
		// TODO Auto-generated method stub
		return getAttributeAsInt("yPoint");
	}
	
	public void setYPoint(int yPoint) {
		// TODO Auto-generated method stub
		setAttribute("yPoint", yPoint);  
	}
}
