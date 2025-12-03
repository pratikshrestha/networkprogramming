import java.io.*;
import java.net.*;

public class WritingOut {
    public static void main(String[] args) {
        try {
            // Create a URL object pointing to a server that accepts POST requests.
            // Example.com rejects POST (404/403), so instead we use a test endpoint.
            URL u = new URL("https://postman-echo.com/post");

            // Open a connection to the URL. This returns a URLConnection,
            // but we cast it to HttpURLConnection to use HTTP-specific methods.
            HttpURLConnection uc = (HttpURLConnection) u.openConnection();

            // Set the HTTP method to POST (default is GET).
            uc.setRequestMethod("POST");

            // We intend to send data to the server, so we enable output.
            // IMPORTANT: This must be done BEFORE calling getOutputStream().
            uc.setDoOutput(true);

            // Declare what kind of data we are sending (plain text, UTF-8).
            uc.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");

            // Send data to the server through the connection's output stream.
            // Using try-with-resources automatically closes the stream.
            try (OutputStream out = uc.getOutputStream()) {
                out.write("hello".getBytes());  // Send the body of the POST request
            }

            // Read the server's response.
            // Because the request succeeded with HTTP 200, getInputStream() is safe.
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(uc.getInputStream()))) {

                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);  // Print each line of the response
                }
            }

        } catch (Exception e) {
            // Print any errors (connection issues, I/O problems, etc.)
            e.printStackTrace();
        }
    }
}
