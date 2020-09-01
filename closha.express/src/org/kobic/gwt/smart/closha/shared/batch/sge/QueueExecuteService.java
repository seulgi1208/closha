package org.kobic.gwt.smart.closha.shared.batch.sge;

import java.io.IOException;
import java.util.List;

public interface QueueExecuteService {

	public Process runScript() throws IOException, InterruptedException;

	public List<Integer> extractJobID(Process process) throws IOException, InterruptedException;

	public List<String> execute(String[] script) throws IOException, InterruptedException;

}
