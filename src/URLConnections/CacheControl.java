import java.util.Date;
import java.util.Locale;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

// This class parses and interprets the "Cache-Control" header from an HTTP response.
// It helps determine caching policies like max-age, no-cache, public, private, etc.
public class CacheControl {
    // These fields store cache control settings
    private Date maxAge = null;           // How long the resource can be cached (for clients)
    private Date sMaxAge = null;          // How long the resource can be cached (for shared proxies)
    private boolean mustRevalidate = false;   // True if the cache must revalidate before using
    private boolean noCache = false;          // True if the cache must NOT use cached data without validation
    private boolean noStore = false;          // True if the cache should not store the response at all
    private boolean proxyRevalidate = false;  // True if shared caches (like proxies) must revalidate
    private boolean publicCache = false;      // True if the response can be cached by any cache (public)
    private boolean privateCache = false;     // True if the response is for a single user (private)

    // Constructor takes a "Cache-Control" header string and parses it
    public CacheControl(String s) {
        // If header is missing or invalid, do nothing (use default values)
        if (s == null || !s.contains(":")) {
            return; // default policy (no caching info)
        }

        // Extract the part after the colon, which contains the cache directives
        String value = s.split(":")[1].trim();

        // Split the directives by commas (e.g., "max-age=3600", "no-cache", etc.)
        String[] components = value.split(",");

        // Get the current date/time
        Date now = new Date();

        // Loop through each directive to interpret its meaning
        for (String component : components) {
            try {
                // Remove extra spaces and make lowercase for easier comparison
                component = component.trim().toLowerCase(Locale.US);

                // Example: max-age=3600 means content can be cached for 3600 seconds
                if (component.startsWith("max-age=")) {
                    int secondsInTheFuture = Integer.parseInt(component.substring(8));
                    maxAge = new Date(now.getTime() + 1000 * secondsInTheFuture);

                    // s-maxage is similar, but applies to shared caches (like proxy servers)
                } else if (component.startsWith("s-maxage=")) {
                    int secondsInTheFuture = Integer.parseInt(component.substring(8));
                    sMaxAge = new Date(now.getTime() + 1000 * secondsInTheFuture);

                    // must-revalidate: the cache must check with the server before using stale data
                } else if (component.equals("must-revalidate")) {
                    mustRevalidate = true;

                    // proxy-revalidate: shared caches must revalidate before using cached data
                } else if (component.equals("proxy-revalidate")) {
                    proxyRevalidate = true;

                    // no-cache: data cannot be reused without server validation
                } else if (component.equals("no-cache")) {
                    noCache = true;

                    // public: cacheable by any cache (browser, proxy, etc.)
                } else if (component.equals("public")) {
                    publicCache = true;

                    // private: only the end user’s browser can cache it (not shared caches)
                } else if (component.equals("private")) {
                    privateCache = true;
                }

                // If any error occurs (like invalid number format), skip it
            } catch (RuntimeException ex) {
                continue;
            }
        }
    }

    // Getter methods to access parsed values
    public Date getMaxAge() {
        return maxAge;
    }

    public Date getSharedMaxAge() {
        return sMaxAge;
    }

    public boolean mustRevalidate() {
        return mustRevalidate;
    }

    public boolean proxyRevalidate() {
        return proxyRevalidate;
    }

    public boolean noStore() {
        return noStore;
    }

    public boolean noCache() {
        return noCache;
    }

    public boolean publicCache() {
        return publicCache;
    }

    public boolean privateCache() {
        return privateCache;
    }

    // Main method for testing the class
    public static void main(String[] args) {
        try {
            // The URL you want to check cache info for
            URL url = new URL("https://www.example.com");

            // Open a connection to the URL
            URLConnection uc = url.openConnection();

            // Get the "Cache-Control" header value from the response
            String header = uc.getHeaderField("Cache-Control");

            // Display the Cache-Control header
            if (header != null) {
                System.out.println("Cache-Control header: " + header);
            } else {
                System.out.println("No Cache-Control header found for this URL.");
            }

            // Example HTTP Cache-Control header
            //String header = "Cache-Control: max-age=3600, no-cache, must-revalidate, public";

            // Create a CacheControl object by parsing the header
            CacheControl cache = new CacheControl(header);

            // Print out the parsed caching rules
            System.out.println("Max age expires at: " + cache.getMaxAge());         // When cache expires
            System.out.println("No-cache? " + cache.noCache());                     // true if no-cache directive found
            System.out.println("Must revalidate? " + cache.mustRevalidate());       // true if must-revalidate found
            System.out.println("Public cache? " + cache.publicCache());             // true if public directive found
            System.out.println("Private cache? " + cache.privateCache());           // true if private directive found

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}