package org.kobic.gwt.smart.closha.shared.batch.sge;

import java.util.List;

public interface QueueManagerService {

	public String queueDelete(String jobID);

	public String queueDeletes(List<String> jobIDs);

}
