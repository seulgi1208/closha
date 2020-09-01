package org.kobic.gbox.client.model;

import java.io.Serializable;

public class TableTransferModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private int type;
	private String method;
	private String name;
	private String status;
	private String size;
	private String local;
	private double progress;
	private String speed;

	private int transferStatus;
	private long elapsedTime;
	private long totalSize;
	private long transferSize;
	private double transferSpeed;
	private float progressedRatio;

	private long length;

	public TableTransferModel() {

	}

	public TableTransferModel(String id, int type, String method, String name, String status, String size, String local,
			double progress, String speed, long elapsedTime, long transferSize, long length) {
		this.id = id;
		this.type = type;
		this.method = method;
		this.name = name;
		this.status = status;
		this.size = size;
		this.local = local;
		this.progress = progress;
		this.speed = speed;
		this.elapsedTime = elapsedTime;
		this.transferSize = transferSize;
		this.length = length;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public int getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(int transferStatus) {
		this.transferStatus = transferStatus;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public long getTransferSize() {
		return transferSize;
	}

	public void setTransferSize(long transferSize) {
		this.transferSize = transferSize;
	}

	public double getTransferSpeed() {
		return transferSpeed;
	}

	public void setTransferSpeed(double transferSpeed) {
		this.transferSpeed = transferSpeed;
	}

	public float getProgressedRatio() {
		return progressedRatio;
	}

	public void setProgressedRatio(float progressedRatio) {
		this.progressedRatio = progressedRatio;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
