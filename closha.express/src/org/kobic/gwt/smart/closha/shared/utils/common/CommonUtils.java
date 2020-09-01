package org.kobic.gwt.smart.closha.shared.utils.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.file.service.impl.HadoopFileSystem;

public class CommonUtils {
	
	public static Map<String, String> getConfiguration(){
		
		Map<String, String> config = new HashMap<String, String>();
		
		try{
			FileInputStream file = new FileInputStream(new File(CommonUtils.class.getResource("/conf/configuration.xml").getPath()));
             
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
             
            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
             
            Document xmlDocument = builder.parse(file);
 
            XPath xPath =  XPathFactory.newInstance().newXPath();
          
            String expression = "//property";
            
            NodeList nodeList = null;
            
            nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
            
            String name = "";
            String value = "";
            
            for (int i = 0; i < nodeList.getLength(); i++) {
            	name = nodeList.item(i).getTextContent().trim().split("\n")[0];
            	value = nodeList.item(i).getTextContent().trim().split("\n")[1];
            	
            	config.put(name, value);
            }
            
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
		
		return config;
	}
	
	public static String getRootPath(Map<String, String> config, String userId){
		
		String path = config.get(Constants.HDFS_DIR_KEY) + File.separator
				+ userId + File.separator
				+ config.get(Constants.SERVICE_NAME_KEY);
		
		return HadoopFileSystem.getInstance(config).getRootDirectoryPath(path);
	}
	
	public static void projectSharedCopy(Map<String, String> config, String userID, String sharedUser, String instanceName){
		String sourcePaht = "";
		String targetPath = "";
		
		sourcePaht = config.get(Constants.HDFS_DIR_KEY) + File.separator + userID + File.separator + config.get(Constants.SERVICE_NAME_KEY) + File.separator
				+ Constants.PROJECT + File.separator + instanceName;
		
		targetPath = config.get(Constants.HDFS_DIR_KEY) + File.separator + sharedUser + File.separator + config.get(Constants.SERVICE_NAME_KEY) + File.separator
				+ Constants.PROJECT + File.separator + instanceName;
		
		HadoopFileSystem.getInstance(config).copy(sourcePaht, targetPath);
	}
	
	public static String getSummarySubString(String summary){
		return summary.substring(0, 50);
	}
	
	public static String convertMillisecondsToDate(long milliseconds){		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY.MM.dd.HH:mm");
		Date resultdate = new Date(milliseconds);		
		return sdf.format(resultdate);
	}
	
	public static String getProperties(String path, String key){
		String value = "";
		
		File f = new File(path);
		
		if(!f.exists()){
			return "file not found";
		}
		
		try{
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream(path);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			props.load(bis);
			value = props.getProperty(key);
			
			bis.close();
			fis.close();
			
			return value;
		}catch(Exception e){
			System.err.println(e.toString());
			return key;
		}
	}
	
	public static void setProperties(String path, String key, String value, String title){

		File f = new File(path);
		
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		
		try{
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream(path);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			props.load(bis);
			props.setProperty(key, value);
			
			FileOutputStream fos = new FileOutputStream(path);
			props.store(fos, title);
			
			fos.close();
			bis.close();
			fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void sendEmail(Map<String, String> config, String title, String msg, String email){
		
		System.out.println(title + ":" + email);
		
		MultiPartEmail mail = new MultiPartEmail();
		mail.setHostName(config.get(Constants.MAIL_HOST_SERVER_KEY));
//		mail.setAuthentication(config.get(Constants.MAIL_AUTH_KEY), config.get(Constants.MAIL_PASSWD_KEY));

		try{
			mail.setSubject(title);
			mail.setMsg(msg);
			mail.setFrom(config.get(Constants.MAIL_AUTH_KEY), Constants.TITLE);
			mail.addTo(email);
			mail.addTo("log@kobic.kr");
			mail.send();
		}catch(EmailException e){
			e.printStackTrace();
		}
	}
	
	public static String getTempPasswd(int len){
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z' };

		int idx = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			idx = (int) (charSet.length * Math.random());
			sb.append(charSet[idx]);
		}
		return sb.toString();
	}
	
	public static String getCurruntTime(){
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd-HH:mm:ss", Locale.UK);
		return format.format(now);
	}
	
	public static String getUUID(){
		return UUID.uuid();
	}
}
