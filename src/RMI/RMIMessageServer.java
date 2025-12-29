import java.rmi.Naming;

public class RMIMessageServer{
    public static void main(String[] args) {
        try {
            MessageInterface server = new MessageService();
            Naming.rebind("RMIMessageService", server);
            System.out.println("RMI message server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}