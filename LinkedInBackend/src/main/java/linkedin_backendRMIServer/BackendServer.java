package linkedin_backendRMIServer;

import java.net.URL;
import java.rmi.Naming;
import java.util.Scanner;

public class BackendServer {
    public static void main(String[] args) {
        String serverIp;
        Scanner input = new Scanner(System.in);

        try {
            ClassLoader cl= BackendServer.class.getClassLoader();  //loads the security policy file to allow jvms to communicate

            System.out.print("In order for RMI server to establish connection with clients please enter your current IPV4 address: ");
            serverIp=input.nextLine();
            System.getProperties().setProperty("java.rmi.server.hostname", serverIp);
            input.close();


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


            BackendServerInterface backendService = new LinkedInBackend();
            Naming.rebind("BackendService",backendService);
            System.out.println("Server is up, comrade!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
