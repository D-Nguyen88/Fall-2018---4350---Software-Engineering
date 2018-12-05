package linkedin_backendRMIServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;

public interface BackendServerInterface extends Remote {
    String sendQuery(FrontendQuery frontendQuery, String queryId) throws RemoteException;
    void setQueryId() throws RemoteException;
    String getQueryId() throws RemoteException;
    Connection getConnection() throws RemoteException;
}
