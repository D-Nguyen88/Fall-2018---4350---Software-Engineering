import java.rmi.Naming;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rootNum;
        String userName;
        try {
            AddServerInterface st = (AddServerInterface) Naming.lookup("rmi://"+ args[0] +"/AddService");
            System.out.println("Enter your name:");
            userName = scanner.nextLine();
            System.out.println(st.hello(userName));
            System.out.println(userName +", enter a number to be squared: ");
            rootNum = scanner.nextInt();
            System.out.println(rootNum +" squared is "+ st.squareNums(rootNum));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
