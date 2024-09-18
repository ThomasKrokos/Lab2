import java.rmi.Remote;
import java.rmi.RemoteException;


public interface BSSManagerInterface extends Remote {

    void send(Message message) throws RemoteException;
    void releaseToken() throws RemoteException;

}
