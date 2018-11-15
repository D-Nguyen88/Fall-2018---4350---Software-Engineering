import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class Adder extends UnicastRemoteObject implements AddServerInterface {
    Adder() throws RemoteException {
        super();
    }

    public String sendQuery(FrontendQuery frontendQuery) {

        StringBuilder queryResults = new StringBuilder();
        StringBuilder queryAsString = new StringBuilder("select ");
        boolean isFirst = true;

        // select all fields
        queryAsString.append("*");

        // appending from clause to query
        queryAsString.append(" from `v_FilteredTestData` where ");

        // appending where clause values
        queryAsString.append("firstName='"+ frontendQuery.getFirstName() +"' " +
                "AND lastName='"+ frontendQuery.getLastName() +"' " +
                "AND industry='"+ frontendQuery.getIndustry() +"' " +
                "AND position='"+ frontendQuery.getPosition() +"' " +
                "AND state='"+ frontendQuery.getState() +"'");

        // code for sendQuery method passing FrontendQuery object array as parameter instead of a single FrontendQuery object
//        

        // display query
        System.out.println(queryAsString);

        // execute query on db
        try (
                Connection conn = getConnection("" +
                        "jdbc:mysql://linkedin.cctcghvmt1au.us-east-2.rds.amazonaws.com:3306/LinkedInDB",
                        "csc4350root",
                        "LinkedIn2daDB!");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(queryAsString.toString());
        ) {

            // iterate through result set and retrieve each data field
            while(rs.next()) {
                queryResults
                        .append(rs.getString("firstName"))
                        .append(",")
                        .append(rs.getString("lastName"))
                        .append(",")
                        .append(rs.getString("workplace"))
                        .append(",")
                        .append(rs.getString("jobTitle"))
                        .append(",")
                        .append(rs.getString("state"))
                        .append("\n");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryResults.toString();
    }

    public Connection getConnection(String url, String user, String password) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    url,
                    user,
                    password
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
