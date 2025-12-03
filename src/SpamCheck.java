import java.net.*;

public class SpamCheck {

    public static final String BANNER = """
    +---------------------------------------------------------+
    |                       SpamCheck                         |
    |                       =========                         |
    +---------------------------------------------------------+
    | This Program is designed to check whether a given list  |
    | of IP addresses belongs to known spammers or legitimate |
    | sources. It does this by querying the Spamhaus Block    |
    | List (SBL) to determine if an IP address is listed as a |
    | spam source.                                            |
    +---------------------------------------------------------+
    
    java SpamCheck 2.0.0.127 192.168.1.1 10.0.0.1 172.16.0.1 185.93.3.114 85.159.237.75 98.159.226.19 98.159.226.181 192.175.48.42 127.0.0.2 192.12.12.12 216.239.32.0 172.217.167.228 216.58.196.100
    """;

    public static final String BLACKHOLE = "zen.spamhaus.org";

    private static boolean isSpammer(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            byte[] quad = address.getAddress();
            String query = BLACKHOLE;
            for (byte octet : quad) {
                int unsignedByte = octet < 0 ? octet + 256 : octet;
                query = unsignedByte + "." + query;
            }
            InetAddress.getByName(query);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        System.out.println(BANNER);
        System.out.printf(" %-17s | %s\n", "Host","Status");
        System.out.println("-------------------+-----------");
        for (String ip : args) {
            if (isSpammer(ip)) {
                System.out.printf(" %-17s | %s\n", ip,"Spammer");
            } else {
                System.out.printf(" %-17s | %s\n", ip,"Legitimate");
            }
        }
    }
}