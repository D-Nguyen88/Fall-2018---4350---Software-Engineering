import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;

public interface AddServerInterface extends Remote {
    String hello(String userName) throws RemoteException;
    int squareNums(int rootNum) throws RemoteException;
    String sendQuery(FrontendQuery[] frontendQuery) throws RemoteException;
    Connection getConnection() throws RemoteException;
}
