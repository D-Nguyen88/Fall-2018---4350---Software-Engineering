package linkedin_adminRMIServer;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class adminServerstart {
	
	static {System.setProperty("logback.configurationFile", "res/logback.xml");}
	 

	public static void main(String[] args) {
		String serverIp;  //changes according to your server ip
		 Logger logger = (Logger) LoggerFactory.getLogger(adminServerstart.class);

		@SuppressWarnings("unused")
		String os;
		Scanner s1 = new Scanner(System.in);
		
		try {
			ClassLoader cl= adminServerstart.class.getClassLoader();  //loads the security policy file to allow jvms to communicate
			
			System.out.print("In order for RMI server to establish connection with clients please enter your current IPV4 address: ");
			serverIp=s1.nextLine();
			System.getProperties().setProperty("java.rmi.server.hostname", serverIp); 
			logger.info("Hostname Set", serverIp);
			s1.close();
			
			
			try {
			URL policyURL= cl.getResource("res/security.policy");    // if running from command line 
			System.setProperty("java.security.policy", policyURL.toString());
			logger.info("Security Policy Set");
			}catch (NullPointerException e) {
				URL policyURL= cl.getResource("security.policy");  // if running from eclipse 
				System.setProperty("java.security.policy", policyURL.toString());
				logger.info("Security Policy Set");
						} 
			
			if (System.getSecurityManager()== null) {   //need in order for RMI to establish connection 
				System.setSecurityManager(new SecurityManager());
			}
			adminServerinterface bind = new admin_server(); 
			Naming.rebind("binded", bind);
			logger.info("Admin Server is running");
			
		} catch ( RemoteException | MalformedURLException e) {
			logger.error("Make sure RMI Registry is running" , e);
		}
	}

}
