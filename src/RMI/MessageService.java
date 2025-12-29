import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// Implement the remote interface
public class MessageService extends UnicastRemoteObject implements MessageInterface {

    // Constructor must throw RemoteException
    public MessageService() throws RemoteException {
        super();
    }

    // Implement remote method
    @Override
    public String HelloMessage() throws RemoteException {
        return "Hello from the RMI Message server!";
    }
}
