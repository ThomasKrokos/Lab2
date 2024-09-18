import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ProcessInterface extends Remote {
    void send(String messageDataString) throws RemoteException;
    void deliver(Message message) throws RemoteException;
    int getPID() throws RemoteException;

}
