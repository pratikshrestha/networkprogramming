import java.net.InetAddress;
import java.net.NetworkInterface;
import java.io.IOException;

public class ReachabilityTester {
    public static void main(String[] args) {
        String host = "www.example.org"; // Replace with the hostname or IP address you want to test
        int timeout = 10000; // Timeout in milliseconds

        try {
            InetAddress address = InetAddress.getByName(host);

            // Test reachability with default timeout
            boolean isReachableDefault = address.isReachable(timeout);
            System.out.println("Is " + host + " reachable (default timeout)? " + isReachableDefault);

            // Test reachability with specified network interface and time-to-live (TTL)
            NetworkInterface networkInterface = NetworkInterface.getByName("wlp5s0"); // Replace with your network interface name
            int ttl = 30; // Maximum number of network hops
            boolean isReachableWithInterface = address.isReachable(networkInterface, ttl, timeout);
            System.out.println("Is " + host + " reachable (with interface and TTL)? " + isReachableWithInterface);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}