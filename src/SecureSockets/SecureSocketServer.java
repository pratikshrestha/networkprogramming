import java.net.*;
import javax.net.ssl.*;

public class SecureSocketServer {
    public static void main(String[] args) {
        try {
            // Create secure server socket on port 1422
            SSLServerSocket serverSocket = (SSLServerSocket)
                    ((SSLServerSocketFactory) SSLServerSocketFactory.getDefault())
                            .createServerSocket(1422);

            System.out.println("Server started. Waiting for client...");

            // Accept client connection
            Socket s = serverSocket.accept();
            System.out.println("Client Accepted and Connected...");

            // --- write and read operations here ---
            // Example: exchange data securely

            s.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
