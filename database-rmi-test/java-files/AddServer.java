import java.rmi.Naming;

public class AddServer {
    public static void main(String[] args) {
        try {
            AddServerInterface addService = new Adder();
            Naming.rebind("AddService",addService);
            System.out.println("Server is up, comrade!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
