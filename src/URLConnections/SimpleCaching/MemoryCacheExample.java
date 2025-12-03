import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

// Simple in-memory HTTP cache example
public class MemoryCacheExample {

    public static void main(String[] args) throws Exception {
        // -------------------------------
        // 1. Install our custom MemoryCache globally
        // -------------------------------
        // ResponseCache is a Java mechanism to cache HTTP responses.
        // By setting MemoryCache as default, all URLConnections
        // will check this cache first before making real network requests.
        ResponseCache.setDefault(new MemoryCache(100));

        // The URL we want to request
        URL url = new URL("https://www.example.com/");

        // Loop three times to demonstrate caching
        for (int i = 0; i < 3; i++) {
            System.out.println("Request #" + (i + 1));

            // Open connection to the URL
            URLConnection connection = url.openConnection();

            // Check if the URL is already cached
            SimpleCacheResponse cachedResponse = MemoryCache.responses.get(url.toURI());

            if (cachedResponse != null && !cachedResponse.isExpired()) {
                // -------------------------------
                // Case 1: Cached response is available and not expired
                // -------------------------------
                System.out.println("From cache! Length: " + cachedResponse.getBody().available());

                // Read and print the cached content
                try (InputStream is = cachedResponse.getBody();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line); // Print each line of HTML
                    }
                }
            } else {
                // -------------------------------
                // Case 2: Not in cache or expired
                // -------------------------------
                System.out.println("Not from cache. Downloading...");

                // Trigger caching by reading the InputStream
                // MemoryCache.put() will automatically be called internally
                connection.getInputStream();

                // Immediately read from the cache to show content
                SimpleCacheResponse newResponse = MemoryCache.responses.get(url.toURI());
                if (newResponse != null) {
                    System.out.println("Downloaded content:\n");
                    try (InputStream is = newResponse.getBody();
                         BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line); // Print each line of HTML
                        }
                    }
                }
            }
            System.out.println(); // Blank line between requests
        }
    }

    // ===============================
    // MemoryCache class
    // ===============================
    public static class MemoryCache extends ResponseCache {

        // The cache storage: Map of URI -> Cached response
        // Static so it can be accessed anywhere (like main)
        public static final Map<URI, SimpleCacheResponse> responses = new ConcurrentHashMap<>();
        private final int maxEntries; // Maximum number of cache entries

        public MemoryCache(int maxEntries) {
            this.maxEntries = maxEntries;
        }

        // -------------------------------
        // put() is called when a response is received from the server
        // -------------------------------
        @Override
        public CacheRequest put(URI uri, URLConnection conn) throws IOException {
            // Do not cache if max entries reached
            if (responses.size() >= maxEntries) return null;

            // Only cache HTTP connections
            if (!(conn instanceof HttpURLConnection)) return null;

            // Read the full response bytes from the server
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (InputStream is = conn.getInputStream()) {
                byte[] buffer = new byte[8192]; // 8 KB buffer
                int len;
                while ((len = is.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }

            // Wrap the downloaded bytes into a SimpleCacheRequest
            SimpleCacheRequest request = new SimpleCacheRequest(out.toByteArray());

            // Create a SimpleCacheResponse to store in memory
            SimpleCacheResponse response = new SimpleCacheResponse(request, conn);

            // Store in cache
            responses.put(uri, response);

            // Return the cache request (not used in this simple example)
            return request;
        }

        // -------------------------------
        // get() is called when making a new request
        // -------------------------------
        @Override
        public CacheResponse get(URI uri, String requestMethod, Map<String, List<String>> requestHeaders) throws IOException {
            // Only cache GET requests
            if (!"GET".equalsIgnoreCase(requestMethod)) return null;

            // Retrieve cached response
            SimpleCacheResponse response = responses.get(uri);

            // Remove expired responses
            if (response != null && response.isExpired()) {
                responses.remove(uri);
                return null;
            }

            // Return cached response
            return response;
        }
    }

    // ===============================
    // SimpleCacheRequest class
    // ===============================
    // Represents the cached request body (in our case, the downloaded bytes)
    public static class SimpleCacheRequest extends CacheRequest {
        private final byte[] data;

        public SimpleCacheRequest(byte[] data) {
            this.data = data; // Store downloaded bytes
        }

        @Override
        public OutputStream getBody() {
            // Not used because we already have the bytes
            return new ByteArrayOutputStream();
        }

        @Override
        public void abort() {
            // Do nothing
        }

        public byte[] getData() {
            return data; // Provide access to the cached bytes
        }
    }

    // ===============================
    // SimpleCacheResponse class
    // ===============================
    // Represents a cached HTTP response
    public static class SimpleCacheResponse extends CacheResponse {
        private final Map<String, List<String>> headers;
        private final SimpleCacheRequest request;
        private final long timestamp; // When this response was cached

        public SimpleCacheResponse(SimpleCacheRequest request, URLConnection conn) throws IOException {
            this.request = request;
            this.headers = Collections.unmodifiableMap(conn.getHeaderFields()); // Store headers
            this.timestamp = System.currentTimeMillis(); // Cache time
        }

        @Override
        public InputStream getBody() {
            // Return an InputStream from cached bytes
            byte[] data = request.getData();
            return data != null ? new ByteArrayInputStream(data) : new ByteArrayInputStream(new byte[0]);
        }

        @Override
        public Map<String, List<String>> getHeaders() throws IOException {
            return headers;
        }

        // Simple expiration: 60 seconds
        public boolean isExpired() {
            return System.currentTimeMillis() - timestamp > 60_000;
        }
    }
}