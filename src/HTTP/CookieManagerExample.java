package HTTP;

import java.io.IOException;
import java.net.*;
import java.util.List;
public class CookieManagerExample {
    public static void main(String[] args) throws IOException {
        //String uri = "https://www.example.com/";
        //String uri = "https://www.google.com/";
        String uri = "https://httpbin.org/cookies/set?name=value&color=blue";
        // Create a CookieManager to store and manage cookies
        CookieManager cookieManager = new CookieManager();

        // Set this CookieManager as the default for all HTTP connections
        CookieHandler.setDefault(cookieManager);

        // Set cookie policy to accept cookies only from the original server
        CookiePolicy cookiePolicy = CookiePolicy.ACCEPT_ORIGINAL_SERVER;
        cookieManager.setCookiePolicy(cookiePolicy);

        // Create a URL object from the string
        URL url = new URL(uri);

        // Open a connection to the URL
        URLConnection connection = url.openConnection();

        // Perform the request and read content (this also receives cookies from the server)
        connection.getContent();

        // Get the CookieStore from the CookieManager
        // CookieStore keeps all cookies received from servers
        CookieStore cookieStore = cookieManager.getCookieStore();

        // Retrieve all cookies from the store
        List<HttpCookie> cookieList = cookieStore.getCookies();

        // Print details of each cookie
        for (HttpCookie cookie : cookieList) {
            System.out.println("Domain name is: " + cookie.getDomain());
            System.out.println("Cookie name is: " + cookie.getName());
            System.out.println("Cookie value is: " + cookie.getValue());
            System.out.println("--------");
        }
    }
}
