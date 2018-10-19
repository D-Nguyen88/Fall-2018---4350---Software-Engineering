import java.rmi.Naming;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int howManyFields;
        String input;
        try {
            AddServerInterface st = (AddServerInterface) Naming.lookup("rmi://"+ args[0] +"/AddService");

            // determine size of array with quantity of data fields desired for querying
            System.out.println("How many data fields would you like to include in your search? (max 4)");
            input = scanner.nextLine();
            howManyFields = Integer.parseInt(input);
            FrontendQuery[] queryFieldsArray = new FrontendQuery[howManyFields];
            System.out.println("New array with "+ howManyFields +" elements created.");

            // populate array with data fields to be used
            System.out.println("Now let's populate that array with FrontendQuery objects.");
            int i=0;
            while(i<queryFieldsArray.length) {
                FrontendQuery oneDataField = new FrontendQuery();
                System.out.println("Enter a data field name:");
                oneDataField.setQueryFieldName(scanner.nextLine());
                System.out.println("Great, now enter a value for that field:");
                oneDataField.setQueryValue(scanner.nextLine());
                queryFieldsArray[i] = oneDataField;
                i++;
            }

            // run sendQuery method w/ data fields array as argument
            String result = st.sendQuery(queryFieldsArray);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
