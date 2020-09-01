package org.kobic.gbox.client.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.server.UID;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

import org.kobic.gbox.utils.client.GBoxUtilsClient;
import org.kobic.sso.client.model.Member;

public class CommonsUtil {
	
	protected static CommonsUtil instance = null;

	public static final CommonsUtil getInstance() {

		if (instance == null) {
			instance = new CommonsUtil();
		} else {
			return instance;
		}

		return instance;
	}
	
	public String getUID(){
		return new UID().toString().toUpperCase();
	}

	public String timeStampToDate(long mills){
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_PATTERN);
		return formatter.format(mills);
	}
	
	public String calculation(float value){
		DecimalFormat format = new DecimalFormat(".#");
		
		if(value == 0){
			return String.valueOf(value);
		}else{
			return format.format(value);
		}
	}
	
	public String humanReadableByteCount(long bytes, boolean si) {
	    int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "KMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
	
	public String getDate(){
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd-HH:mm", Locale.KOREAN);
		return format.format(now);
	}
	
	public String getTime(){
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("[HH:mm:ss]: ", Locale.KOREAN);
		return format.format(now);
	}
	
	public String getIpAdress() {

		String os = System.getProperty("os.name").toLowerCase();
		String ip = "";

		if (os.startsWith("window")) {
			try {
				ip = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		} else {

			try {
				boolean isLoopBack = true;
				Enumeration<NetworkInterface> en;
				en = NetworkInterface.getNetworkInterfaces();

				while (en.hasMoreElements()) {
					NetworkInterface ni = en.nextElement();
					if (ni.isLoopback()) {
						continue;
					}

					Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();

					while (inetAddresses.hasMoreElements()) {
						InetAddress ia = inetAddresses.nextElement();
						if (ia.getHostAddress() != null && ia.getHostAddress().indexOf(".") != -1) {
							ip = ia.getHostAddress();
							isLoopBack = false;
							break;
						}
					}

					if (!isLoopBack) {
						break;
					}
				}
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ip;
	}
	
	public void send(String type, long total, long transfer, double speed, String code, String res, Member member, String name){
		
		String title = "[" + Constants.TITLE + "] Auto Mail Sender [%s]";
		String msg = Constants.COMMONSUTIL_SEND_MSG;
		
		GBoxUtilsClient gBoxUtilsClient = new GBoxUtilsClient();
		
		if(gBoxUtilsClient.isAlive()){
			gBoxUtilsClient.alarmMail(String.format(title, type),
					String.format(msg, member.getMemberNm(), member.getMemberId(), member.getMemberPstinst(), getIpAdress(),
							humanReadableByteCount(total, true),
							humanReadableByteCount(transfer, true), humanReadableByteCount((long)speed, true), name, code, res));
		}else{
			System.out.println("Data synchronization system failure");
		}
		
		gBoxUtilsClient.close();
	}
}
