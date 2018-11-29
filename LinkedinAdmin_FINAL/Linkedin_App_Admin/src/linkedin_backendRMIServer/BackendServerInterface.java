package linkedin_backendRMIServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;

public interface BackendServerInterface extends Remote {
    String sendQuery(FrontendQuery frontendQuery) throws RemoteException;
    Connection getConnection(String url, String user, String password) throws RemoteException;
}
