package org.kobic.gwt.smart.closha.client.board.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class BoardCommentRecord extends ListGridRecord implements IsSerializable {
	
	public BoardCommentRecord(){}
	
	public BoardCommentRecord(String id, String writer, String comment, String email, String date, String linkedNum){
		setId(id);
		setWriter(writer);
		setComment(comment);
		setDate(date);
		setEmail(email);
		setLinkedNum(linkedNum);
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
	
	public String getComment() {
		// TODO Auto-generated method stub
		return getAttributeAsString("comment");
	}
	
	public void setComment(String comment) {
		// TODO Auto-generated method stub
		setAttribute("comment", comment);  
	}
	
	public String getDate() {
		// TODO Auto-generated method stub
		return getAttributeAsString("date");
	}
	
	public void setDate(String date) {
		// TODO Auto-generated method stub
		setAttribute("date", date);  
	}
	
	public String getEmail() {
		// TODO Auto-generated method stub
		return getAttributeAsString("email");
	}
	
	public void setEmail(String email) {
		// TODO Auto-generated method stub
		setAttribute("email", email);  
	}
	
	public String getLinkedNum() {
		// TODO Auto-generated method stub
		return getAttributeAsString("linkedNum");
	}
	
	public void setLinkedNum(String linkedNum) {
		// TODO Auto-generated method stub
		setAttribute("linkedNum", linkedNum);  
	}
}