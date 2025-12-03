import java.net.*;
import java.io.*;

public class GETHEADER {
    public static void main(String[] args) {
        try {
            // Create URL object
            URL url = new URL("https://www.google.com");

            // Open URL connection
            URLConnection connection = url.openConnection();

            // Opening input stream triggers the loading of headers
            connection.getInputStream();

            // Loop through headers
            int index = 0;
            String key;
            while ((key = connection.getHeaderFieldKey(index)) != null ||
                    connection.getHeaderField(index) != null) {

                System.out.println(
                        (key != null ? key : "Status") + ": " +
                                connection.getHeaderField(index)
                );
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
