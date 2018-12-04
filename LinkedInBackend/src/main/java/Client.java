import java.rmi.Naming;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            BackendServerInterface st = (BackendServerInterface) Naming.lookup("rmi://127.0.0.1/BackendService");

            FrontendQuery frontendQuery = new FrontendQuery();
            System.out.println("First name:");
            frontendQuery.setFirstName(scanner.nextLine());
            System.out.println("Last name:");
            frontendQuery.setLastName(scanner.nextLine());
            System.out.println("Industry:");
            frontendQuery.setIndustry(scanner.nextLine());
            System.out.println("Position:");
            frontendQuery.setPosition(scanner.nextLine());
            System.out.println("State:");
            frontendQuery.setState(scanner.nextLine());

            // run sendQuery method w/ data fields array as argument
            String result = st.sendQuery(frontendQuery);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
