package linkedin_adminRMIServer;

import java.net.URL;
import java.rmi.Naming;

public class adminServerstart {

	public static void main(String[] args) {
		try {
			ClassLoader cl= adminServerstart.class.getClassLoader();  //loads the security policy file to allow jvms to communicate
			try {
			URL policyURL= cl.getResource("res/security.policy");    // running from command line 
			System.setProperty("java.security.policy", policyURL.toString());
			}catch (NullPointerException e) {
				URL policyURL= cl.getResource("security.policy");  //running from eclipse 
				System.setProperty("java.security.policy", policyURL.toString());
			}
			
			
			if (System.getSecurityManager()== null) {   //need in order for RMI to establish connection 
				System.setSecurityManager(new SecurityManager());
			}
			adminServerinterface bind = new admin_server(); 
			Naming.rebind("binded", bind);
			System.out.println("Admin Server is running");
		} catch (Exception e) {
			System.out.println("Server setup failed "+ e);
		}
	}

}
