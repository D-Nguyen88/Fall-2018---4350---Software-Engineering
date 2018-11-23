package linkedin_adminRMIServer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public interface adminServerinterface {
	Connection getDBConnection() throws RemoteException;
	boolean registerUser(String fname, String lname,String email, String uname, String pass) throws RemoteException;
	boolean loginuser(String uname, String pass) throws RemoteException;
	String passwordHash(String pass) throws RemoteException, NoSuchAlgorithmException;
	void XML2CSV(String xml, String xsl, String CSV) throws TransformerException, ParserConfigurationException, SAXException, IOException; //xml2csv
	void setKey(String key)throws RemoteException;
	String getKey() throws RemoteException;
}


