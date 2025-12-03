import java.net.*;
import javax.net.ssl.*;

public class SecureSocketClient {
    public static void main(String[] args) {
        try {
            // Create a secure socket connection to server
            Socket socket = ((SSLSocketFactory) SSLSocketFactory.getDefault())
                    .createSocket("127.0.0.1", 1422);

            System.out.println("Server Connected: " + socket);

            // --- write and read operations here ---
            // Example: send/receive data securely

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
