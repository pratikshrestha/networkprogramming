import java.net.*;
import java.io.*;
import java.util.Date;
public class DayTimeServer {
    public final static int PORT = 8080;
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try (Socket connection = server.accept()) {
                    Writer out = new OutputStreamWriter(connection.getOutputStream());
                    Date now = new Date();
                    out.write(now.toString() +"\r\n");
                    out.flush();
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}