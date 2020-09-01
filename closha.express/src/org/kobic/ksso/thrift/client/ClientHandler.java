package org.kobic.ksso.thrift.client;

import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.kobic.ksso.thrift.autowise.service.KSSOServices;
import org.kobic.ksso.thrift.autowise.service.UserModel;

public class ClientHandler {
	
	private KSSOServices.Client client = null;
	private TTransport transport = null;
	
	public static final String IP = "210.218.222.32";
	public static final int PORT = 9937;
	
	private KSSOServices.Client getInstance(){
		
		if(client == null){
			try {
				transport = new TFramedTransport(new TSocket(IP, PORT));
				transport.open();

				TProtocol protocol = new TBinaryProtocol(transport);
				
				client = new KSSOServices.Client(protocol);
			} catch (TException e) {
				e.printStackTrace();
			}
		}
		
		return client;
	}	
	
	private void close(){
		client = null;
		transport.close();
	}
	
	public int isAlive() throws TException {
		
		int res = -1;
		res = getInstance().isAlive();
		close();
		
		return res;
	}
	
	public String addUser(UserModel user) throws TException {
		
		String res = null;
		res = getInstance().addUser(user);
		close();
		
		return res;
	}
	
	public int deleteUser(String user_id) throws TException {
		
		int res = -1;
		res = getInstance().deleteUser(user_id);
		close();
		
		return res;
	}
	
	public boolean userCheck(String user_id) throws TException {
		
		boolean res = false;
		res = getInstance().userCheck(user_id);
		close();
		
		return res;
	}
	
	public UserModel getUser(String user_id) throws TException {
		
		UserModel res = getInstance().getUser(user_id);
		close();
		
		return res;
		
	}
	
	public List<UserModel> getUserLimit(int start_row, int range_num) throws TException {
		
		List<UserModel> res = getInstance().getUserLimit(start_row, range_num);
		close();
		
		return res;
		
	}
	
	public List<UserModel> getUsers() throws TException {
		
		List<UserModel> user = getInstance().getUsers();
		close();
		
		return user;
	}
	
	public boolean login(String user_id, String password) throws TException {
		
		boolean res = false;
		res = getInstance().login(user_id, password);
		close();
		return res; 
	}
	
	public int logout(String user_id) throws TException {
		
		int res = -1;
		res = getInstance().logout(user_id);
		close();
		
		return res;
	}
	
	public int userCount() throws TException {
		
		int res = -1;
		res = getInstance().userCount();
		close();
		
		return res;
	}

	public int organizationCount(String organization) throws TException {
		
		int res = -1;
		res = getInstance().organizationCount(organization);
		close();
		
		return res;
	}
	
	public int updateId(String user_id, String new_id) throws TException {
		
		int res = -1;
		res = getInstance().updateId(user_id, new_id);
		close();
		
		return res;
	}
	
	public int updatePassword(String user_id, String email_adress, String password) throws TException {
		
		int res = -1;
		res = getInstance().updatePassword(user_id, email_adress, password);
		close();
		
		return res;
	}
	
	public int updateEmailAdress(String user_id, String email_adress) throws TException {
		
		int res = -1;
		res = getInstance().updateEmailAdress(user_id, email_adress);
		close();
		
		return res;
	}
	
	public int updateIsAdministrator(String user_id, boolean is_admin) throws TException {
		
		int res = -1;
		res = getInstance().updateIsAdministrator(user_id, is_admin);
		close();
		
		return res;
		
	}
	
	public int updateLastLogin(String user_id) throws TException {
		
		int res = -1;
		res = getInstance().updateLastLogin(user_id);
		close();
		
		return res;
		
	}
	
	public UserModel searchUser(String user_id) throws TException {
		
		UserModel res = getInstance().searchUser(user_id);
		close();
		
		return res;
	}
	
	public UserModel findUser(String user_name, String email_adress) throws TException {
		
		UserModel res = getInstance().findUser(user_name, email_adress);
		close();
		
		return res;
		
	}
	
	public String getTempPassword(String user_id, String email_adress) throws TException {
		
		String res = getInstance().getTempPassword(user_id, email_adress);
		close();
		
		return res;
	}
	
	public List<UserModel> searchOrganization(String organization) throws TException {
		
		List<UserModel> res = getInstance().searchOrganization(organization);
		close();
		
		return res;
	}
	
	public UserModel searchUserWithIdentityNumber(String identity_number) throws TException {
		
		UserModel res = getInstance().searchUserWithIdentityNumber(identity_number);
		close();
		
		return res;
	}
	
	public boolean userCheckWithMail(String user_id, String email_adress) throws TException {
		
		boolean res = getInstance().userCheckWithMail(user_id, email_adress);
		close();
		
		return res;
	}
}
