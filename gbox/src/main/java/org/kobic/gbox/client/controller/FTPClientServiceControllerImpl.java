package org.kobic.gbox.client.controller;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.kobic.gbox.client.common.Constants;

public class FTPClientServiceControllerImpl implements FTPClientServiceController {

	final static Logger logger = Logger.getLogger(FTPClientServiceControllerImpl.class);

	public FTPClientServiceControllerImpl() {
	}

	@Override
	public FTPClient connection() {
		// TODO Auto-generated method stub

		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);

		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding("euc-kr");
		ftpClient.configure(conf);

		try {
			ftpClient.connect(Constants.FTP_ADRESS, Constants.FTP_PORT);

			int reply = 0;

			reply = ftpClient.getReplyCode();

			if (FTPReply.isPositiveCompletion(reply) == false) {
				ftpClient.disconnect();
				logger.info("The connection from the server was denied.");
			}

			ftpClient.enterLocalPassiveMode();

		} catch (Exception e1) {
			if (ftpClient.isConnected() == true) {
				try {
					ftpClient.disconnect();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			e1.printStackTrace();
		}

		try {
			if (ftpClient.isConnected() == true) {
				return ftpClient.login(Constants.FTP_ID, Constants.FTP_PASSWD) ? ftpClient : null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public FTPClient connection(FTPClient ftpClient) {
		// TODO Auto-generated method stub

		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);

		ftpClient = new FTPClient();
		ftpClient.setControlEncoding("euc-kr");
		ftpClient.configure(conf);

		try {
			ftpClient.connect(Constants.FTP_ADRESS, Constants.FTP_PORT);

			int reply = 0;

			reply = ftpClient.getReplyCode();

			if (FTPReply.isPositiveCompletion(reply) == false) {
				ftpClient.disconnect();
				logger.info("The connection from the server was denied.");
			}

			ftpClient.enterLocalPassiveMode();

		} catch (Exception e1) {
			if (ftpClient.isConnected() == true) {
				try {
					ftpClient.disconnect();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			e1.printStackTrace();
		}

		try {
			if (ftpClient.isConnected() == true) {
				return ftpClient.login(Constants.FTP_ID, Constants.FTP_PASSWD) ? ftpClient : null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean disconnection(FTPClient ftpClient) {
		// TODO Auto-generated method stub

		if (ftpClient.isAvailable() && ftpClient.isConnected()) {
			try {
				if (ftpClient.logout()) {
					try {
						if (ftpClient.isConnected()) {
							ftpClient.disconnect();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return !ftpClient.isConnected();
	}
}
