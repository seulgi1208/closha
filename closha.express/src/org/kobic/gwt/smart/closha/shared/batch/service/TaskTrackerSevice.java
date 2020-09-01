package org.kobic.gwt.smart.closha.shared.batch.service;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.kobic.gwt.smart.closha.shared.batch.sge.QueueStateService;
import org.kobic.gwt.smart.closha.shared.batch.sge.QueueStateServiceImpl;

public class TaskTrackerSevice extends TimerTask {

	private List<String> exeIDs;
	private List<String> jobIDs;
	private Timer timer;
	private String jobID;

	private QueueStateService queueStateService;

	public TaskTrackerSevice(List<String> exeIDs, Timer timer) {
		this.exeIDs = exeIDs;
		this.timer = timer;

		queueStateService = new QueueStateServiceImpl();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (exeIDs.size() == 0) {
			timer.cancel();
		} else {
			jobIDs = queueStateService.getJobIDs();
			for (int i = 0; i < exeIDs.size(); i++) {
				if (!jobIDs.contains(exeIDs.get(i))) {
					jobID = exeIDs.get(i);
					for (Iterator<String> it = exeIDs.iterator(); it.hasNext();) {
						String id = it.next();
						if (id.equals(jobID)) {
							it.remove();
						}
					}
				}
			}
		}
	}
}
