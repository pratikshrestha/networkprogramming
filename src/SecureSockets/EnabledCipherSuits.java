import java.net.*;
import javax.net.ssl.*;
import java.util.Arrays;

public class EnabledCipherSuits {
    public static void main(String[] args) {
        // Use a try-with-resources or traditional try-catch to handle network exceptions
        try {
            // 1. Get the default SSL Socket Factory
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            // 2. Create an un-connected SSL socket
            SSLSocket secureSocket = (SSLSocket) factory.createSocket();

            // 3. Connect to the host on port 443 (HTTPS)
            // Removed the 'void' keyword and fixed the syntax
            secureSocket.connect(new InetSocketAddress("www.swsc.edu.np", 443));

            // 4. Start the handshake (optional, but forces the connection to establish now)
            secureSocket.startHandshake();

            // 5. Output the Cipher Suite information
            System.out.println("--- Connection Successful ---");
            System.out.println("Enabled Cipher Suites: " + Arrays.toString(secureSocket.getEnabledCipherSuites()));
            System.out.println("\nSupported Cipher Suites: " + Arrays.toString(secureSocket.getSupportedCipherSuites()));

            // 6. Close the connection
            secureSocket.close();
            System.out.println("\nSocket closed.");

        } catch (Exception e) {
            System.err.println("Error connecting to the server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}