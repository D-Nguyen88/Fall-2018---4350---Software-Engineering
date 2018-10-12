import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AddServerInterface extends Remote {
    String hello(String userName) throws RemoteException;
    int squareNums(int rootNum) throws RemoteException;
}
