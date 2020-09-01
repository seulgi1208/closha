package org.kobic.gbox.client;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kobic.gbox.client.common.Constants;

import rapidant.model.client.RapidantClient;
import rapidant.model.client.RapidantClientFactory;
import rapidant.model.client.command.file.RapidantClientRemoteFileListCommand;
import rapidant.model.client.event.file.RapidantClientFileListEvent;
import rapidant.model.client.value.RapidantClientTCPConnection;
import rapidant.model.common.value.RapidantAnonymousAuthentication;
import rapidant.model.common.value.RapidantFile;

public class GBoxTest {
	
	@Test
	public void getDir() {

		RapidantClient client = RapidantClientFactory.newRapidantClient();

		RapidantClientTCPConnection connection = new RapidantClientTCPConnection();
		connection.setHost(Constants.SERVER_ADDRESS);
		connection.setPort(Constants.SERVER_TCP_PORT);
		connection.setFairnessPort(Constants.SERVER_TCP_FAIRNESS_PORT);
		connection.setConnectionTimeout(10000);
		connection.setAuthentication(new RapidantAnonymousAuthentication());
		connection.setUseSecurity(false);
		connection.setSocketBufferSize(1024 * 1024 * 8);
		connection.setDiskBufferSize(1024 * 1024 * 1);
		connection.setNumberOfStream(10);

		RapidantClientRemoteFileListCommand command = new RapidantClientRemoteFileListCommand();
		command.setDirectory("/BiO/rapidant/data/group/AnonymousGroup/kogun82");

		RapidantClientFileListEvent event = client.execute(command).await();

		List<String> dn = null;

		if (event.isSuccessful()) {

			dn = new ArrayList<String>();

			for (RapidantFile rf : event.getFiles()) {
				if (rf.isDirectory()) {
					dn.add(rf.getName());
				}
			}

		} else {
			System.out.println("ERR Reason: " + event.getReason());
		}

		System.out.println(dn.toString());
	}
}
