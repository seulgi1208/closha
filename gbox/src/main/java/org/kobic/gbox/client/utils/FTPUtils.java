package org.kobic.gbox.client.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.io.CopyStreamAdapter;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;
import org.apache.log4j.Logger;
import org.kobic.gbox.client.common.CommonsUtil;
import org.kobic.gbox.client.common.Constants;
import org.kobic.gbox.client.common.TimeChecker;
import org.kobic.gbox.client.fire.event.FileTransferStatusFireEvent;
import org.kobic.gbox.client.model.TableTransferModel;

import com.pennychecker.eventbus.HandlerManager;

public class FTPUtils {

	protected static final Logger logger = Logger.getLogger(FTPUtils.class);

	private HandlerManager eventBus;

	private Map<String, TableTransferModel> map;
	private TimeChecker mTimeChecker;

	private DecimalFormat format = new DecimalFormat("#.#");

	private int cp = 0;
	private double totalSpeed = 0;

	public double getTotalSpeed() {
		return totalSpeed;
	}

	private FTPClient ftpClient;

	public FTPUtils(FTPClient ftpClient, Map<String, TableTransferModel> map, TimeChecker mTimeChecker,
			HandlerManager eventBus) {
		this.ftpClient = ftpClient;
		this.map = map;
		this.mTimeChecker = mTimeChecker;
		this.eventBus = eventBus;
	}

	public FTPFile[] list() {

		FTPFile[] files = null;
		try {
			files = ftpClient.listFiles();
			return files;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public File get(String source, String target) {

		OutputStream output = null;

		try {
			File local = new File(source);
			output = new FileOutputStream(local);
		} catch (Exception e) {
			e.printStackTrace();
		}

		File file = new File(source);

		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			boolean flag = ftpClient.retrieveFile(target, output);

			output.flush();
			output.close();

			if (flag == true) {
				return file;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean upload(final File file, String target) {

		boolean resultCode = false;

		mTimeChecker.setStartTime();

		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(4096);

			FileInputStream fis = new FileInputStream(file);

			System.out.println(ftpClient.getStatus());

			CopyStreamAdapter adapter = new CopyStreamAdapter();

			adapter.addCopyStreamListener(new CopyStreamListener() {

				@Override
				public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
					// TODO Auto-generated method stub

					int percent = (int) (totalBytesTransferred * 100 / file.length());

					if (cp != percent) {

						mTimeChecker.setEndTime();

						if (totalBytesTransferred != 0) {

							totalSpeed = ((totalBytesTransferred * 1024.0) / mTimeChecker.calcTerm());

							TableTransferModel tm = map.get(file.getAbsolutePath());
							tm.setSpeed(CommonsUtil.getInstance().humanReadableByteCount(
									(long) ((totalBytesTransferred * 1024.0) / mTimeChecker.calcTerm()), true));
							tm.setTransferStatus(Constants.RUN);
							tm.setElapsedTime((int) mTimeChecker.calcTerm());
							tm.setTotalSize(file.length());
							tm.setTransferSize(totalBytesTransferred);
							tm.setTransferSpeed(totalSpeed);
							tm.setProgressedRatio(Float.valueOf(
									String.valueOf((format.format((double) totalBytesTransferred / file.length())))));

							eventBus.fireEvent(new FileTransferStatusFireEvent(tm));
						}

						cp = percent;
					}
				}

				@Override
				public void bytesTransferred(CopyStreamEvent event) {
					// TODO Auto-generated method stub
					logger.info(event.getBytesTransferred());
				}
			});

			ftpClient.setCopyStreamListener(adapter);

			boolean isSuccess = ftpClient.storeFile(target + "/" + file.getName(), fis);

			logger.info(ftpClient.getStatus());

			if (isSuccess == true) {
				resultCode = true;
			}

			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultCode;
	}

	public boolean uploadDirectory(String localDirectory, String remoteDirectoryPath) {

		File src = new File(localDirectory);

		try {
			remoteDirectoryPath = remoteDirectoryPath + src.getName() + "/";
			boolean makeDirFlag = this.ftpClient.makeDirectory(remoteDirectoryPath);
			System.out.println("localDirectory : " + localDirectory);
			System.out.println("remoteDirectoryPath : " + remoteDirectoryPath);
			System.out.println("src.getName() : " + src.getName());
			System.out.println("remoteDirectoryPath : " + remoteDirectoryPath);
			System.out.println("makeDirFlag : " + makeDirFlag);

		} catch (IOException e) {
			e.printStackTrace();
			logger.info(remoteDirectoryPath + " Directory creation failed ");
		}

		File[] allFile = src.listFiles();

		for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
			if (!allFile[currentFile].isDirectory()) {
				String srcName = allFile[currentFile].getPath().toString();
				upload(new File(srcName), remoteDirectoryPath);
			}
		}

		for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
			if (allFile[currentFile].isDirectory()) {
				// recursion
				uploadDirectory(allFile[currentFile].getPath().toString(), remoteDirectoryPath);
			}
		}

		return true;
	}

	public boolean mkdir(String path) {

		boolean res = false;

		try {
			res = ftpClient.makeDirectory(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	public boolean cd(String path) {

		boolean res = false;

		try {
			res = ftpClient.changeWorkingDirectory(path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
}