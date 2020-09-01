package org.kobic.gbox.client.common;

public class TimeChecker {

	private long startTime;
	private long endTime;
	private long term;

	public void setStartTime() {
		startTime = System.currentTimeMillis();
	}

	public void setEndTime() {
		endTime = System.currentTimeMillis();
	}

	public double calcTerm() {
		term = endTime - startTime;
		return term;
	}

	public double getCalcTermSecond() {
		term = endTime - startTime;
		return term / 1000.0;
	}
}
