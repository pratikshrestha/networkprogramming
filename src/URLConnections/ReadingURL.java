import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ReadingURL {
    public static void main(String[] args) {
        try {
            URL u = new URL("https://www.example.com");
            URLConnection uc = u.openConnection();

            try (InputStream in = uc.getInputStream()) {
                in.transferTo(System.out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
