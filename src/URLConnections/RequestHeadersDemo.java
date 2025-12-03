import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class RequestHeadersDemo {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.example.com/");
            URLConnection uc = url.openConnection();

            uc.setRequestProperty("User-Agent", "MySimpleClient/1.0");
            uc.setRequestProperty("Accept", "text/html");

            // Opening the connection triggers header creation
            uc.getInputStream().close();

            Map<String, List<String>> headers = uc.getRequestProperties();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
