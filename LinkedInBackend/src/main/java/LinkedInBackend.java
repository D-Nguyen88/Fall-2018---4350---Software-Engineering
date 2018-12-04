import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.UUID;

public class LinkedInBackend extends UnicastRemoteObject implements BackendServerInterface {
    LinkedInBackend() throws RemoteException {
        super();
    }

    public String sendQuery(FrontendQuery frontendQuery) {
        StringBuilder queryAsString = new StringBuilder("SELECT * FROM `TestData` WHERE ");

        // conditionally appending where clause values
        boolean isFirst = true;
        StringBuilder buildFileName = new StringBuilder();
        if(!frontendQuery.getFirstName().equals("")) {
            queryAsString.append("firstName='")
                    .append(frontendQuery.getFirstName())
                    .append("' ");
            buildFileName.append(frontendQuery.getFirstName());
            isFirst = false;
        }

        if(!frontendQuery.getLastName().equals("")) {
            if(!isFirst) {
                queryAsString.append("AND ");
            } else {
                isFirst = false;
            }
            queryAsString.append("lastName='")
                    .append(frontendQuery.getLastName())
                    .append("' ");
            buildFileName.append(frontendQuery.getLastName());
        }

        if(!frontendQuery.getIndustry().equals("")) {
            if(!isFirst) {
                queryAsString.append("AND ");
            } else {
                isFirst = false;
            }
            queryAsString.append("industry='")
                    .append(frontendQuery.getIndustry())
                    .append("' ");
            buildFileName.append(frontendQuery.getIndustry());
        }

        if(!frontendQuery.getPosition().equals("")) {
            if(!isFirst) {
                queryAsString.append("AND ");
            } else {
                isFirst = false;
            }
            queryAsString.append("position='")
                    .append(frontendQuery.getPosition())
                    .append("' ");
            buildFileName.append(frontendQuery.getPosition());
        }

        if(!frontendQuery.getState().equals("")) {
            if(!isFirst) {
                queryAsString.append("AND ");
            }
            queryAsString.append("state='")
                    .append(frontendQuery.getState())
                    .append("' ");
            buildFileName.append(frontendQuery.getState());
        }

        System.out.println(queryAsString);
        String fileName = buildFileName.toString().replaceAll("\\s","")+".xml";
        System.out.println(fileName);

        // execute query on db
        try (
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(queryAsString.toString());
        ) {

            if(rs.isBeforeFirst()) {
                storeXML(rs,fileName);
            } else {
                return "Your search returned no results";
            }

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Results stored in XML";
    }

    public void storeXML(ResultSet resultSet, String fileName) {
        UUID uuid = UUID.randomUUID();
        String queryId = uuid.toString();
        XMLGenerator xmlGenerator = new XMLGenerator();
        String filePath = xmlGenerator.resultSetToXML(resultSet, fileName);

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO `QueryResults` (queryId,resultsFilePath,isReady)" +
                                "VALUES ('"+queryId+"','"+ filePath +"',1)"
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
