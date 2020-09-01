package org.kobic.gwt.smart.closha.client.board.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class BoardRecord extends ListGridRecord implements IsSerializable {
	
	public BoardRecord(){}
	
	public BoardRecord(String id, String writer, String type, String summary, String content, String hitCount, 
			String recommandCount, String email, String keyWord, String date){
		setId(id);
		setWriter(writer);
		setType(type);
		setSummary(summary);
		setContent(content);
		setHitCount(hitCount);
		setRecommandCount(recommandCount);
		setEmail(email);
		setKeyWord(keyWord);
		setDate(date);
	}
	
	public String getId() {
		// TODO Auto-generated method stub
		return getAttributeAsString("id");
	}
	
	public void setId(String id) {
		// TODO Auto-generated method stub
		setAttribute("id", id);  
	}
	
	public String getWriter() {
		// TODO Auto-generated method stub
		return getAttributeAsString("writer");
	}
	
	public void setWriter(String writer) {
		// TODO Auto-generated method stub
		setAttribute("writer", writer);  
	}
	
	public String getType() {
		// TODO Auto-generated method stub
		return getAttributeAsString("type");
	}
	
	public void setType(String type) {
		// TODO Auto-generated method stub
		setAttribute("type", type);  
	}
	
	public String getSummary() {
		// TODO Auto-generated method stub
		return getAttributeAsString("summary");
	}
	
	public void setSummary(String summary) {
		// TODO Auto-generated method stub
		setAttribute("summary", summary);  
	}
	
	public String getContent() {
		// TODO Auto-generated method stub
		return getAttributeAsString("content");
	}
	
	public void setContent(String content) {
		// TODO Auto-generated method stub
		setAttribute("content", content);  
	}
	
	public String getHitCount() {
		// TODO Auto-generated method stub
		return getAttributeAsString("hitCount");
	}
	
	public void setHitCount(String hitCount) {
		// TODO Auto-generated method stub
		setAttribute("hitCount", hitCount);  
	}
	
	public String getRecommandCount() {
		// TODO Auto-generated method stub
		return getAttributeAsString("recommandCount");
	}
	
	public void setRecommandCount(String recommandCount) {
		// TODO Auto-generated method stub
		setAttribute("recommandCount", recommandCount);  
	}
	
	public String getEmail() {
		// TODO Auto-generated method stub
		return getAttributeAsString("email");
	}
	
	public void setEmail(String email) {
		// TODO Auto-generated method stub
		setAttribute("email", email);  
	}
	
	public String getKeyWord() {
		// TODO Auto-generated method stub
		return getAttributeAsString("keyWord");
	}
	
	public void setKeyWord(String keyWord) {
		// TODO Auto-generated method stub
		setAttribute("keyWord", keyWord);  
	}
	
	public String getDate() {
		// TODO Auto-generated method stub
		return getAttributeAsString("date");
	}
	
	public void setDate(String date) {
		// TODO Auto-generated method stub
		setAttribute("date", date);  
	}
}
