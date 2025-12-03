import java.net.*;

public class CookieFilter {

    public static void main(String[] args) throws Exception {
        URI uri1 = new URI("https://example.com/login");
        URI uri2 = new URI("https://service.gov.np/auth");
        URI uri3 = new URI("https://sub.test.gov.uk/path");

        test(uri1);
        test(uri2);
        test(uri3);
    }

    private static void test(URI uri) {
        if (isCookieAllowed(uri)) {
            System.out.println(uri + " -> Cookies ALLOWED");
        } else {
            System.out.println(uri + " -> Cookies BLOCKED");
        }
    }

    // ------------------------------------
    // Allow cookie if domain does NOT contain ".gov"
    // ------------------------------------
    private static boolean isCookieAllowed(URI uri) {

        String host = uri.getHost();   // example.com, service.gov.np, sub.test.gov.uk

        if (host == null) {
            return false; // invalid host
        }

        // Normalize
        host = host.toLowerCase();

        // Block all *.gov.*, .gov, gov domains
        if (host.contains(".gov")) {
            return false;
        }

        return true;
    }
}
