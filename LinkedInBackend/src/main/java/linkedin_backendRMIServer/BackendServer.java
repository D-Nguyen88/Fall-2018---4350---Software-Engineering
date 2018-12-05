package linkedin_backendRMIServer;

import java.rmi.Naming;

public class BackendServer {
    public static void main(String[] args) {
        try {
            BackendServerInterface backendService = new LinkedInBackend();
            Naming.rebind("BackendService",backendService);
            System.out.println("Server is up, comrade!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
