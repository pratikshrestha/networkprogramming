import java.net.*;

public class IPv4IPv6Identifier {
    public static void main(String[] args) {
        String hostName = "test-ipv6.com"; // or any domain

        try {
            InetAddress[] inetAddresses = InetAddress.getAllByName(hostName);
            System.out.println("Resolved addresses for " + hostName + ":");

            for (InetAddress inetAddress : inetAddresses) {
                System.out.println(" - " + inetAddress.getHostAddress());

                if (inetAddress instanceof Inet4Address) {
                    System.out.println("   → This is an IPv4 address.");
                } else if (inetAddress instanceof Inet6Address) {
                    System.out.println("   → This is an IPv6 address.");
                } else {
                    System.out.println("   → Unknown IP version.");
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Unable to resolve the hostname.");
        }
    }
}
