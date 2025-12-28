import java.net.*;
import java.io.*;

public class DatagramPacketSetterDemo {

    public static void main(String[] args) {
        try {
            // Initial data
            byte[] data = "Initial Message".getBytes("UTF-8");

            // Create DatagramPacket without destination
            DatagramPacket packet = new DatagramPacket(data, data.length);

            // ----- Using Setter Methods -----

            // 1. setData()
            byte[] newData = "Updated Message".getBytes("UTF-8");
            packet.setData(newData);

            // 2. setData() with offset and length
            packet.setData(newData, 0, newData.length);

            // 3. setAddress()
            InetAddress address = InetAddress.getByName("localhost");
            packet.setAddress(address);

            // 4. setPort()
            packet.setPort(8080);

            // 5. setSocketAddress()
            SocketAddress socketAddress =
                    new InetSocketAddress("localhost", 8080);
            packet.setSocketAddress(socketAddress);

            // 6. setLength()
            packet.setLength(newData.length);

            // Display values (just to verify)
            System.out.println("Message        : " +
                    new String(packet.getData(), packet.getOffset(),
                            packet.getLength(), "UTF-8"));
            System.out.println("Destination IP : " + packet.getAddress());
            System.out.println("Port           : " + packet.getPort());
            System.out.println("Data Length    : " + packet.getLength());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
