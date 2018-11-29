import java.rmi.Naming;

public class BackendServer {
    public static void main(String[] args) {
        try {
            BackendServerInterface addService = new LinkedInBackend();
            Naming.rebind("AddService",addService);
            System.out.println("Server is up, comrade!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
