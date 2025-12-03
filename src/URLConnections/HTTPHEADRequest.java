package URLConnections;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPHEADRequest {
    public static void main(String[] args) {
        String url = "https://www.example.com/";
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            connection.setRequestMethod("HEAD");

            connection.setRequestProperty("Host", "www.example.com");
            connection.setRequestProperty("Accept",
                    "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");
            connection.setRequestProperty("Connection", "close");

            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();

            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Message: " + responseMessage);

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
