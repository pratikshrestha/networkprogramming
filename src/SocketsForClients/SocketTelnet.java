package SocketsForClients;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketTelnet {
    public static final String SERVER = "time-b-g.nist.gov";
    public static final int PORT = 13;
    public static final int TIMEOUT = 15000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER, PORT)) {
            socket.setSoTimeout(TIMEOUT);
            InputStream in = socket.getInputStream();
            StringBuilder time = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(in, "ASCII");
            for (int c = reader.read(); c != -1; c = reader.read()) {
                time.append((char) c);
            }
            System.out.println(time);
        } catch (IOException ex) {
            System.err.println("Could not connect to : "+ SERVER);
        }
    }
}
