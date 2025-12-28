import java.io.*;
import java.net.*;

public class UDPDaytimeClient {
//    private static final String HOSTNAME = "localhost";
    private static final String HOSTNAME = "10.0.0.17";
    private final static int PORT = 8080;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(0)) {
            socket.setSoTimeout(10000);

            InetAddress host = InetAddress.getByName(HOSTNAME);
            DatagramPacket request =
                    new DatagramPacket(new byte[1], 1, host, PORT);
            DatagramPacket response =
                    new DatagramPacket(new byte[1024], 1024);

            socket.send(request);
            socket.receive(response);

            String result = new String(
                    response.getData(), 0,
                    response.getLength(), "US-ASCII");

            System.out.println(result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}