package org.kobic.gwt.smart.closha.client.common.component;

import gwtupload.client.MultiUploader;

import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;

public class UploadRowSetLayout extends HLayout {

	public MultiUploader fileUploader;
	
	public MultiUploader getFileUploader() {
		return fileUploader;
	}

	public UploadRowSetLayout(String title) {
		// TODO Auto-generated constructor stub
		
		fileUploader = new MultiUploader();
		fileUploader.setMaximumFiles(10);
		fileUploader.setSize("300px", "200px");
		
		Label fileLabel = new Label(title);
		fileLabel.setMargin(5);
		fileLabel.setLeft(30);
		fileLabel.setHeight(5);

		this.setMargin(5);
		this.setHeight(5);
		this.setLeft(30);
		this.addMember(fileLabel);
		this.addMember(fileUploader);
	}

}