package org.kobic.gwt.smart.closha.shared.model.board;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ReplyModel implements IsSerializable{
	
	private String id;
	private String writer;
	private String comment;
	private String date;
	private String email;
	private String linkedNum;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLinkedNum() {
		return linkedNum;
	}
	public void setLinkedNum(String linkedNum) {
		this.linkedNum = linkedNum;
	}
}
