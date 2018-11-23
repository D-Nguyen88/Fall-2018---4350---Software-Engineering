package linkedin_adminRMIServer;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import controller.SignUpController;

public class RMIClientInitializer {
	


	private static String ip;   //ip of RMI server 
	
	
	public void RMILoader() {  //allows jvm communications 
		ClassLoader cl= SignUpController.class.getClassLoader();
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
			}}catch (Exception e) {
				System.out.println("Could not connect to server " + e);
			}
	}
	

	public static String getIp() {
		return ip;
	}
	public static void setIp(String ip1) {
		ip=ip1;
	}
	
	
}
