import java.net.*;
import java.util.Date;
import java.io.*;

public class UDPDaytimeServer {
    private final static int PORT = 8080;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            while (true) {
                DatagramPacket request =
                        new DatagramPacket(new byte[1024], 1024);
                socket.receive(request);

                String daytime = new Date().toString();
                byte[] data = daytime.getBytes("US-ASCII");

                DatagramPacket response =
                        new DatagramPacket(data, data.length,
                                request.getAddress(), request.getPort());

                socket.send(response);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
