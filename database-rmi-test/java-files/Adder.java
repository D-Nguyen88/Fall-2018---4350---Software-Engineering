import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class Adder extends UnicastRemoteObject implements AddServerInterface {
    Adder() throws RemoteException {
        super();
    }

    public String sendQuery(FrontendQuery[] frontendQueryFields) {
        StringBuilder queryResults = new StringBuilder();
        StringBuilder queryAsString = new StringBuilder("select ");
        boolean isFirst = true;

        // select all fields
        queryAsString.append("*");

        // appending from clause to query
        queryAsString.append(" from `user-data` where ");

        // appending where clause values
//        isFirst = true;       // necessary if selecting only client-specified data fields
        for(FrontendQuery dataField: frontendQueryFields) {
            if(!isFirst) {
                queryAsString
                        .append(" AND ")
                        .append(dataField.getQueryFieldName())
                        .append(" = '")
                        .append(dataField.getQueryValue())
                        .append("'");
            } else {
                queryAsString
                        .append(dataField.getQueryFieldName())
                        .append(" = '")
                        .append(dataField.getQueryValue())
                        .append("'");
            }
            isFirst = false;
        }

        // display query
        System.out.println(queryAsString);

        // execute query on db
        try (
                Connection conn = getConnection();
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
                        .append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryResults.toString();
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://csc4330linkedin.czzwcn7vqihl.us-east-1.rds.amazonaws.com:3306/testdb1",
                    "csc4330root",
                    "LinkedIn2daDB!"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
