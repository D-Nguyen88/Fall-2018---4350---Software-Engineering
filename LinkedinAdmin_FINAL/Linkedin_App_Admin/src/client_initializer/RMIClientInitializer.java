package client_initializer;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import controller.SignUpController;

public class RMIClientInitializer {

	private static String adminIp,backendIp;   //ip of RMI server

	public void RMILoader() {  //allows jvm communications 
		ClassLoader cl = SignUpController.class.getClassLoader();
		try {
			URL policyURL = cl.getResource("res/security.policy");
			System.setProperty("java.security.policy", policyURL.toString());
		} catch (NullPointerException e) {
			URL policyURL = cl.getResource("security.policy");
			System.setProperty("java.security.policy", policyURL.toString());
		}

		try {																	// security manager needed for communication between jvms. 
			if (System.getSecurityManager()== null) {
				System.setSecurityManager(new SecurityManager());
			}}catch (Exception e) {
				System.out.println("Could not connect to server " + e);
			}
	}


	public static String getAdminIp() {
		return adminIp;
	}

	public static void setAdminIp(String adminIp) {
		RMIClientInitializer.adminIp = adminIp;
	}

	public static String getBackendIp() {
		return backendIp;
	}

	public static void setBackendIp(String backendIp) {
		RMIClientInitializer.backendIp = backendIp;
	}
}
