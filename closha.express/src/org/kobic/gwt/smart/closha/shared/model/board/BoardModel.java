package org.kobic.gwt.smart.closha.shared.model.board;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BoardModel implements IsSerializable{
	
	private String id;
	private String writer;
	private String type;
	private String summary;
	private String content;
	private String hitCount;
	private String recommandCount;
	private String email;
	private String keyWord;
	private String date;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHitCount() {
		return hitCount;
	}
	public void setHitCount(String hitCount) {
		this.hitCount = hitCount;
	}
	public String getRecommandCount() {
		return recommandCount;
	}
	public void setRecommandCount(String recommandCount) {
		this.recommandCount = recommandCount;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
}
