package turbo.cache.lite.thrift.service.client;

import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import turbo.cache.lite.thrift.service.TurboCacheThriftService;

public class CacheLiteClient {

	public static final String master_ip = "192.168.154.52";
	public static final int master_port = 7912;
	
	public boolean loading_cache(List<String> path){
		
		boolean res = false;
		
		try {
			TTransport transport;
			transport = new TFramedTransport(new TSocket(master_ip, master_port));
			TProtocol protocol = new TBinaryProtocol(transport);
			TurboCacheThriftService.Client client = new TurboCacheThriftService.Client(protocol);

			transport.open();
			
			res = client.loadingCache(path);

			transport.close();
		} catch (TException e) {
			System.out.println("not connection..");
		}
		
		return res;
		
	}
	
	public boolean downloading_cache(List<String> path){
		
		boolean res = false;
		
		try {
			TTransport transport;
			transport = new TFramedTransport(new TSocket(master_ip, master_port));
			TProtocol protocol = new TBinaryProtocol(transport);
			TurboCacheThriftService.Client client = new TurboCacheThriftService.Client(protocol);

			transport.open();
			
			res = client.downloadingCache(path);

			transport.close();
		} catch (TException e) {
			System.out.println("not connection..");
		}
		
		return res;
	}
	
	public void delete_cache(List<String> path){
		
		try {
			TTransport transport;
			transport = new TFramedTransport(new TSocket(master_ip, master_port));
			TProtocol protocol = new TBinaryProtocol(transport);
			TurboCacheThriftService.Client client = new TurboCacheThriftService.Client(protocol);

			transport.open();
			
			client.deleteCache(path);

			transport.close();
		} catch (TException e) {
			System.out.println("not connection..");
		}
	}
	
	public void new_instance_pipeline(String pipeline_id, String project_name, String user_id, String email,
			Map<String, String> params) {
		
		try {
			TTransport transport;
			transport = new TFramedTransport(new TSocket(master_ip, master_port));
			TProtocol protocol = new TBinaryProtocol(transport);
			TurboCacheThriftService.Client client = new TurboCacheThriftService.Client(protocol);

			transport.open();
			
			client.newInstance(pipeline_id, project_name, user_id, email, params);

			transport.close();
		} catch (TException e) {
			System.out.println("not connection..");
		}
	}
	
	public void exe_instance_pipeline(String instance_id, String user_id, Map<String, String> params){
		
		try {
			TTransport transport;
			transport = new TFramedTransport(new TSocket(master_ip, master_port));
			TProtocol protocol = new TBinaryProtocol(transport);
			TurboCacheThriftService.Client client = new TurboCacheThriftService.Client(protocol);

			transport.open();
			
			client.exeInstance(user_id, instance_id, params);
			
			transport.close();
		} catch (TException e) {
			System.out.println("not connection..");
		}
	}
	
	public int isAlive(){
		
		int res = 0;
		
		try {
			TTransport transport;
			transport = new TFramedTransport(new TSocket(master_ip, master_port));
			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			TurboCacheThriftService.Client client = new TurboCacheThriftService.Client(protocol);

			res = client.isAlive();
			
			transport.close();
		} catch (TException e) {
			System.out.println("not connection..");
		}
		
		return res;
	}
	
	public String write(String data, String project_name, String user_id){
		
		try {
			TTransport transport;
			transport = new TFramedTransport(new TSocket(master_ip, master_port));
			TProtocol protocol = new TBinaryProtocol(transport);
			TurboCacheThriftService.Client client = new TurboCacheThriftService.Client(protocol);

			transport.open();
			
			client.write(data, user_id, project_name);
			
			transport.close();
		} catch (TException e) {
			System.out.println("not connection..");
		}
		
		return null;
	}
	
	public boolean isExist(String path){
		
		boolean res = false;
		
		try {
			TTransport transport;
			transport = new TFramedTransport(new TSocket(master_ip, master_port));
			TProtocol protocol = new TBinaryProtocol(transport);
			TurboCacheThriftService.Client client = new TurboCacheThriftService.Client(protocol);

			transport.open();
			
			res = client.isExistDFS(path);
			
			transport.close();
		} catch (TException e) {
			return false;
		}
		
		return res;
	}
	
	public boolean isCacheExist(String path){
		
		boolean res = false;
		
		try {
			TTransport transport;
			transport = new TFramedTransport(new TSocket(master_ip, master_port));
			TProtocol protocol = new TBinaryProtocol(transport);
			TurboCacheThriftService.Client client = new TurboCacheThriftService.Client(protocol);

			transport.open();
			
			res = client.isExistCache(path);
			
			transport.close();
		} catch (TException e) {
			return false;
		}
		
		return res;
		
	}
	
	public boolean copyingFileFromLocalToHDFS(String source, String target){
		
		boolean res = false;
		
		try {
			TTransport transport;
			transport = new TFramedTransport(new TSocket(master_ip, master_port));
			TProtocol protocol = new TBinaryProtocol(transport);
			TurboCacheThriftService.Client client = new TurboCacheThriftService.Client(protocol);

			transport.open();
			
			res = client.copyingFileFromLocalToHDFS(source, target);
			
			transport.close();
		} catch (TException e) {
			System.out.println("not connection..");
		}
		
		return res;
		
	}
	
	public boolean copyingFileFromHDFSToLocal(String source, String target){
		
		boolean res = false;
		
		try {
			TTransport transport;
			transport = new TFramedTransport(new TSocket(master_ip, master_port));
			TProtocol protocol = new TBinaryProtocol(transport);
			TurboCacheThriftService.Client client = new TurboCacheThriftService.Client(protocol);

			transport.open();
			
			res = client.copyingFileFromHDFSToLocal(source, target);
			
			transport.close();
		} catch (TException e) {
			System.out.println("not connection..");
		}		
		return res;
	}
}