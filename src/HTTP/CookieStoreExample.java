package HTTP;

import java.net.*;
import java.util.*;

public class CookieStoreExample {
    public static void main(String[] args) {
        // Create a CookieManager to manage cookies
        CookieManager cookieManager = new CookieManager();

        // Get the CookieStore from the CookieManager
        // CookieStore stores all cookies in memory
        CookieStore cookieStore = cookieManager.getCookieStore();

        // Create two HttpCookie objects
        HttpCookie cookieA = new HttpCookie("First", "1");   // cookie with name "First" and value "1"
        HttpCookie cookieB = new HttpCookie("Second", "2");  // cookie with name "Second" and value "2"

        // Create a URI for associating cookies
        URI uri = URI.create("https://www.example.com/");

        // --- Method 1: Add cookies to the CookieStore ---
        cookieStore.add(uri, cookieA); // associate cookieA with a specific URI
        cookieStore.add(null, cookieB); // cookieB is added without a specific URI (general-purpose)
        System.out.println("Cookies successfully added\n");

        // --- Method 2: Get cookies associated with a specific URI ---
        List cookiesWithURI = cookieStore.get(uri);
        System.out.println("Cookies associated with URI in CookieStore : " + cookiesWithURI + "\n");

        // --- Method 3: Get all cookies in the store ---
        List cookieList = cookieStore.getCookies();
        System.out.println("Cookies in CookieStore : " + cookieList + "\n");

        // --- Method 4: Get all URIs for which cookies are stored ---
        List uriList = cookieStore.getURIs();
        System.out.println("URIs in CookieStore : " + uriList + "\n");

        // --- Method 5: Remove a specific cookie ---
        System.out.println("Removal of Cookie : " + cookieStore.remove(uri, cookieA));
        List remainingCookieList = cookieStore.getCookies();
        System.out.println("Remaining Cookies : " + remainingCookieList + "\n");

        // --- Method 6: Remove all cookies ---
        System.out.println("Removal of all Cookies : " + cookieStore.removeAll());
        List emptyCookieList = cookieStore.getCookies();
        System.out.println("Empty CookieStore : " + emptyCookieList);
    }
}
