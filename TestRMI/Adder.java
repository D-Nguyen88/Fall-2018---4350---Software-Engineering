import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Adder extends UnicastRemoteObject implements AddServerInterface {
    Adder() throws RemoteException {
        super();
    }

    public String hello(String userName) {
        return "Hello "+ userName +"!";
    }

    public int squareNums(int rootNum) {
        return rootNum*rootNum;
    }
}