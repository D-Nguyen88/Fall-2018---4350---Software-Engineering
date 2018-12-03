import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.Scanner;

public class LinkedInBackend extends UnicastRemoteObject implements BackendServerInterface {
    LinkedInBackend() throws RemoteException {
        super();
    }

    public String sendQuery(FrontendQuery frontendQuery) {

        StringBuilder queryResults = new StringBuilder();
        StringBuilder queryAsString = new StringBuilder("select ");
//        boolean isFirst = true;

        // select all fields
        queryAsString.append("*");

        // appending from clause to query
        queryAsString.append(" from `TestData` where ");

        // appending where clause values
        queryAsString.append("firstName='"+ frontendQuery.getFirstName() +"' " +
                "AND lastName='"+ frontendQuery.getLastName() +"' " +
                "AND industry='"+ frontendQuery.getIndustry() +"' " +
                "AND position='"+ frontendQuery.getPosition() +"' " +
                "AND state='"+ frontendQuery.getState() +"'");

        String fileName;
        StringBuilder buildFileName = new StringBuilder();
        if(frontendQuery.getFirstName()!=null) {
            buildFileName.append(frontendQuery.getFirstName());
        }
        if(frontendQuery.getLastName()!=null) {
            buildFileName.append(frontendQuery.getLastName());
        }
        if(frontendQuery.getIndustry()!=null) {
            buildFileName.append(frontendQuery.getIndustry());
        }
        if(frontendQuery.getPosition()!=null) {
            buildFileName.append(frontendQuery.getPosition());
        }
        if(frontendQuery.getState()!=null) {
            buildFileName.append(frontendQuery.getState());
        }
        fileName = buildFileName.toString()+".xml";


//      execute query on db
        try (
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(queryAsString.toString());
        ) {

            storeXML(rs,fileName);

            // iterate through result set and retrieve each data field
            while(rs.next()) {
                queryResults
                        .append(rs.getString("firstName"))
                        .append(",")
                        .append(rs.getString("lastName"))
                        .append(",")
                        .append(rs.getString("industry"))
                        .append(",")
                        .append(rs.getString("position"))
                        .append(",")
                        .append(rs.getString("state"))
                        .append("\n");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryResults.toString();
    }

    public void storeXML(ResultSet resultSet, String fileName) {
        int queryId;
        Scanner scanner = new Scanner(System.in);
        queryId = scanner.nextInt();
        XMLGenerator xmlGenerator = new XMLGenerator();
        String filePath = xmlGenerator.resultSetToXML(resultSet, fileName);

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO `QueryResults` (queryId,resultsFilePath,isReady)" +
                                "VALUES ("+queryId+",'"+ filePath +"',1)"
                )
        ) {
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://linkedin.cctcghvmt1au.us-east-2.rds.amazonaws.com:3306/LinkedInDB",
                    "csc4350root",
                    "LinkedIn2daDB!"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
