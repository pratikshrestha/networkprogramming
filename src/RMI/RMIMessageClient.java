import java.rmi.Naming;

public class RMIMessageClient {
    public static void main(String[] args) {
        try {
            MessageInterface remoteService = (MessageInterface) Naming.lookup("rmi://localhost/RMIMessageService");
            String response = remoteService.HelloMessage();
            System.out.println("Response from server: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}