package linkedin_adminRMIServer;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class testclass {
	//client app 

	public static void main (String args []) throws MalformedURLException, RemoteException, NotBoundException {
		String ip="192.168.1.104";  //ip of server machine 
		
		ClassLoader cl= testclass.class.getClassLoader();
		try {
		URL policyURL =cl.getResource("res/security.policy");     
		System.setProperty("java.security.policy", policyURL.toString());
		} catch (NullPointerException e) {
			URL policyURL =cl.getResource("security.policy");     
			System.setProperty("java.security.policy", policyURL.toString());
		}
		
				try {																	// security manager needed for communication between jvms. 
			if (System.getSecurityManager()== null) {
				System.setSecurityManager(new SecurityManager());
			}
		
	adminServerinterface hi = (adminServerinterface) Naming.lookup("rmi://"+ ip +"/binded");  //calling registerUser method from server 
	if (hi.registerUser("julian", "dsilva","adsfdsfs@yahoo.com", "rmitest3", "fsdsdfd")) {
					System.out.println("User Registered Succesfully");
	}
	else {
					System.out.println("Username already exists. Please pick another username");
	}
	
		}catch (Exception e) {
			System.out.println("Could not connect to server " + e);
		}
}
}
