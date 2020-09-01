package org.kobic.gbox.client.transfer.asynchronous.task;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

public class TransferTask extends Task<Void> {
	
	public static final int NUM_ITERATIONS = 100;
	
	public TransferTask(){
		
	}
	
	@Override
	protected Void call() throws Exception {
		// TODO Auto-generated method stub
		
		this.updateProgress(ProgressIndicator.INDETERMINATE_PROGRESS, 1);	
		
		this.updateMessage("Waiting...");
		
		Thread.sleep(1500);
		
		this.updateMessage("Running...");
		
		for (int i = 0; i < NUM_ITERATIONS; i++) {
			updateProgress((1.0 * i) / NUM_ITERATIONS, 1);
			Thread.sleep(1500);
		}
		
		this.updateMessage("Done");
		
		this.updateProgress(1, 1);
		
		return null;
	}
}
