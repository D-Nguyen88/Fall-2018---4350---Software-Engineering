package linkedin_adminRMIServer;

import java.net.URL;
import java.rmi.Naming;
import java.util.Scanner;

public class adminServerstart {

	public static void main(String[] args) {
		String serverIp;  //changes according to your server ip
		
		
		@SuppressWarnings("unused")
		String os;
		Scanner s1 = new Scanner(System.in);
		
		try {
			ClassLoader cl= adminServerstart.class.getClassLoader();  //loads the security policy file to allow jvms to communicate
			
			if (System.getProperty("os.name").startsWith("Windows") ) {// on windows set the rmi server ip address since server sometimes returns loopback address. So client cannot connect.
			System.out.print("Windows OS detected. In order for RMI server to establish connection with clients please enter your current IPV4 address: ");
			serverIp=s1.nextLine();
			System.setProperty("java.rmi.server.hostname", serverIp); 
			s1.close();
			}
			
			try {
			URL policyURL= cl.getResource("res/security.policy");    // if running from command line 
			System.setProperty("java.security.policy", policyURL.toString());
			}catch (NullPointerException e) {
				URL policyURL= cl.getResource("security.policy");  // if running from eclipse 
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
