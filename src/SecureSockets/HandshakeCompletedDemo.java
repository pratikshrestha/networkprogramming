import javax.net.ssl.*;
import java.net.*;

// Custom listener to handle the event when an SSL handshake finishes
class MyHandshakeListener implements HandshakeCompletedListener {
    @Override
    public void handshakeCompleted(HandshakeCompletedEvent e) {
        // Output the session details once the connection is secured
        System.out.println("Handshake successful!");
        System.out.println("Using cipher suite: " + e.getSession().getCipherSuite());
    }
}

public class HandshakeCompletedDemo {
    public static void main(String[] args) throws Exception {
        // 1. Get the default SSL Socket Factory
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

        // 2. Create an SSL socket to a specific host and port
        // Note: Replace "127.0.0.1" and "8080" with a real server (e.g., "www.swsc.edu.np", 443) to test
        SSLSocket socket = (SSLSocket) factory.createSocket("www.swsc.edu.np", 443);

        // 3. Enable all supported cipher suites
        String[] suites = socket.getSupportedCipherSuites();
        socket.setEnabledCipherSuites(suites);

        // 4. Register the custom listener to the socket
        socket.addHandshakeCompletedListener(new MyHandshakeListener());

        // 5. Manually start the handshake process
        socket.startHandshake();

        // 6. Print connection status
        System.out.println("Just connected to " + socket.getRemoteSocketAddress());

        // Keep the program alive long enough to see the listener output
        Thread.sleep(2000);
        socket.close();
    }
}