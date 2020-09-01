package org.kobic.gbox.client.model;

import java.io.Serializable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FXTableTransferModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private SimpleStringProperty id;
	private SimpleIntegerProperty type;
	private StringProperty method;
	private StringProperty name;
	private StringProperty status;
	private StringProperty size;
	private StringProperty local;
	private DoubleProperty progressBar;
	private StringProperty speed;
	private StringProperty elapsedTime;
	private StringProperty transferSize;

	public FXTableTransferModel() {
		id = new SimpleStringProperty();
		type = new SimpleIntegerProperty();
		method = new SimpleStringProperty();
		name = new SimpleStringProperty();
		status = new SimpleStringProperty();
		size = new SimpleStringProperty();
		local = new SimpleStringProperty();
		progressBar = new SimpleDoubleProperty();
		speed = new SimpleStringProperty();
		elapsedTime = new SimpleStringProperty();
		transferSize = new SimpleStringProperty();
	}

	public FXTableTransferModel(String id, int type, String method, String name, String status, String size,
			String local, double progress, String speed, String elapsedTime, String transferSize) {

		this.id = new SimpleStringProperty(id);
		this.type = new SimpleIntegerProperty(type);
		this.method = new SimpleStringProperty(method);
		this.name = new SimpleStringProperty(name);
		this.status = new SimpleStringProperty(status);
		this.size = new SimpleStringProperty(size);
		this.local = new SimpleStringProperty(local);
		this.progressBar = new SimpleDoubleProperty(progress);
		this.speed = new SimpleStringProperty(speed);
		this.elapsedTime = new SimpleStringProperty(elapsedTime);
		this.transferSize = new SimpleStringProperty(transferSize);
	}

	public String getID() {
		return id.get();
	}

	public void setID(String id) {
		this.id.set(id);
	}

	public StringProperty idProperty() {
		return id;
	}

	public int getType() {
		return type.get();
	}

	public void setType(int type) {
		this.type.set(type);
	}

	public IntegerProperty typeProperty() {
		return type;
	}

	public String getMethod() {
		return method.get();
	}

	public void String(String method) {
		this.method.set(method);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status.set(status);
	}

	public StringProperty statusProperty() {
		return status;
	}

	public String getSize() {
		return size.get();
	}

	public void setSize(String size) {
		this.size.set(size);
	}

	public StringProperty sizeProperty() {
		return size;
	}

	public String getLocal() {
		return local.get();
	}

	public void setLocal(String local) {
		this.local.set(local);
	}

	public StringProperty localProperty() {
		return local;
	}

	public double getProgressBar() {
		return progressBar.get();
	}

	public void setProgressBar(double progress) {
		this.progressBar.set(progress);
	}

	public DoubleProperty progressBarProperty() {
		return progressBar;
	}

	public String getSpeed() {
		return speed.get();
	}

	public void setSpeed(String speed) {
		this.speed.set(speed);
	}

	public StringProperty speedProperty() {
		return speed;
	}

	public String getElapsedTime() {
		return elapsedTime.get();
	}

	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime.set(elapsedTime);
	}

	public StringProperty elapsedTimeProperty() {
		return elapsedTime;
	}

	public String getTransferSize() {
		return transferSize.get();
	}

	public void setTransferSize(String transferSize) {
		this.transferSize.set(transferSize);
	}

	public StringProperty transferSizeProperty() {
		return transferSize;
	}
}