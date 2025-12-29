import java.rmi.Remote;
import java.rmi.RemoteException;

// Remote interface extends java.rmi.Remote
public interface MessageInterface extends Remote {
    // Remote method
    String HelloMessage() throws RemoteException;
}
