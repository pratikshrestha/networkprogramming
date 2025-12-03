package HTTP;

import java.net.*;
import java.util.*;

public class CustomCookieGOVreject {
    public static void main(String[] args) throws Exception {
        // Custom policy
        CookiePolicy blockGovPolicy = (uri, cookie) -> {
            String domain = cookie.getDomain();
            if (domain != null && domain.toLowerCase().endsWith(".gov")) {
                System.out.println("❌ Blocked cookie from: " + domain);
                return false;
            }
            System.out.println("✅ Accepted cookie from: " + domain);
            return true;
        };

        // Create CookieManager with the policy
        CookieManager manager = new CookieManager(null, blockGovPolicy);
        CookieHandler.setDefault(manager);

        // Simulate Set-Cookie headers
        Map<String, List<String>> headers1 = new HashMap<>();
        headers1.put("Set-Cookie", List.of("id=123; Domain=whitehouse.gov; Path=/"));

        Map<String, List<String>> headers2 = new HashMap<>();
        headers2.put("Set-Cookie", List.of("session=abc; Domain=example.com; Path=/"));

        // Pass them through CookieManager (this triggers the policy)
        manager.put(new URI("https://whitehouse.gov/"), headers1);
        manager.put(new URI("https://example.com/"), headers2);

        // Check what's actually stored
        CookieStore store = manager.getCookieStore();
        System.out.println("\n🍪 Cookies in store:");
        for (HttpCookie c : store.getCookies()) {
            System.out.println(c);
        }
    }
}
