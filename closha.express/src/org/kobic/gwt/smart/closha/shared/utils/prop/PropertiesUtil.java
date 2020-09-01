package org.kobic.gwt.smart.closha.shared.utils.prop;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	private String JDBC_PROPERTIES = PropertiesUtil.class.getResource(
			"/conf/jdbc.properties").getPath();
	
	// 프로퍼티 파일 저장 경로

	private File profile = null;
	private FileInputStream fis = null;
	private FileOutputStream fos = null;
	private Properties pros = null;

	// 클레스 생성자
	public PropertiesUtil() {
		this.init();
	}

	public void init() {
		profile = new File(JDBC_PROPERTIES);
		// 프로퍼티 객체 생성
		pros = new Properties();
		try {
			// 파일이 없다면 새로 만든다.
			if (!profile.exists())
				profile.createNewFile();
			// 프로퍼티 파일 인 스트림
			fis = new FileInputStream(profile);
			// 프로퍼티 파일 아웃 스트림
			fos = new FileOutputStream(profile);
			// 프로퍼티 파일을 메모리에 올린다.(파일을 읽어 온다.)
			pros.load(new BufferedInputStream(fis));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPropertyFile(String path) {
		// 프로퍼티 파일 경로를 변경 한다.
		this.JDBC_PROPERTIES = path;
	}

	// 키(key)로 값(value)을 가져온다.
	public String getProperty(String key) throws IOException {
		return pros.getProperty(key);
	}

	// 키(key)로 값(value)을 저장한다.
	public void setProperty(String key, String value) throws IOException {
		pros.setProperty(key, value);
	}

	// 프로퍼티 파일에 최종 저장 한다.
	public void storeProperty() throws IOException {
		pros.store(fos, "");
	}
}