package linkedin_adminRMIServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

public interface adminServerinterface extends Remote{
	
	Connection getDBConnection() throws RemoteException;
	boolean registerUser(String fname, String lname,String email, String uname, String pass) throws RemoteException;
	boolean loginuser(String uname, String pass) throws RemoteException;
	String passwordHash(String pass) throws RemoteException, NoSuchAlgorithmException;
}
