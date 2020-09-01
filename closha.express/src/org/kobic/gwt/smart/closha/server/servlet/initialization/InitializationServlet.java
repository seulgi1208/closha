package org.kobic.gwt.smart.closha.server.servlet.initialization;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.file.service.FileSystemService;
import org.kobic.gwt.smart.closha.shared.file.service.impl.HadoopFileSystem;
import org.kobic.gwt.smart.closha.shared.utils.common.CommonUtils;
import org.kobic.ksso.thrift.autowise.service.UserModel;
import org.kobic.ksso.thrift.client.ClientHandler;

public class InitializationServlet implements ServletContextListener  {

	protected static final Logger logger = Logger.getLogger(InitializationServlet.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println( "=[STOP CLOSHA SERVER]=" );
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println( "=[START CLOSHA SERVER]=" );
		
		for (Map.Entry entry : System.getenv().entrySet())
			System.out.println(entry.getKey() + "=" + entry.getValue());
	}
	
	public InitializationServlet(){
	
		Map<String, String> config = CommonUtils.getConfiguration();
		
		FileSystemService fs = HadoopFileSystem.getInstance(config);
		
		Iterator<String> keys = config.keySet().iterator();
		
		while (keys.hasNext()) {
			String key = keys.next();
			System.out.println(String.format("KEY : %s, VALUE : %s", key, config.get(key)));
		}
		
		//HDFS 파일 경로가 없을 경우 생성해준다. + 하위 users 폴더 생성.
		//ksso에서 사용자 목록 조회 및 폴더 체크 진행
		ClientHandler handler = new ClientHandler();
		
		List<UserModel> userDto = null;
		
		try {
			userDto = handler.getUsers();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String path = config.get(Constants.HDFS_DIR_KEY) + File.separator
				+ "%s" + File.separator
				+ config.get(Constants.SERVICE_NAME_KEY) + File.separator + "%s";
		System.out.println("Checking User ID In Progress");
		
		for(UserModel dto : userDto){
			
			if(!fs.isExists(String.format(path, dto.getUser_id(), Constants.PROJECT))){
				if(fs.makeDirectory(String.format(path, dto.getUser_id(), Constants.PROJECT))) 
					System.out.println(dto.getUser_name() + "[" + dto.getUser_id() + "] 사용자 PROJECTS 폴더를 생성하였습니다.");
			}
			
			System.out.println(dto.getUser_name() + "[" + dto.getUser_id() + "] 확인 완료");
		}
		
		System.getProperties().list(System.out);
	}
}