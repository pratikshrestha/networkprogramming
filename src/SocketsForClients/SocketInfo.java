import java.io.IOException;
import java.net.Socket;

public class SocketInfo {
    public static final String SERVER = "time.nist.gov";
    public static final int PORT = 13;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER, PORT)) {
            System.out.println("Socket Info :");
            System.out.println("==============================");
            System.out.println("Connected to : " + socket.getInetAddress());
            System.out.println("Port : "+socket.getPort());
            System.out.println("------------------------------");
            System.out.println("From Port : "+socket.getLocalPort());
            System.out.println("Of : "+socket.getLocalAddress());
            System.out.println("==============================");

        } catch (IOException ex) {
            System.err.println("Could not connect to : "+ SERVER);
        }
    }
}