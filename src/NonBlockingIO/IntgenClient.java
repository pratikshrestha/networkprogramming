import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.io.IOException;

public class IntgenClient {
    public static String HOST = "localhost";
    //172.16.0.186
    public static int PORT = 8080;

    public static void main(String[] args) {
        try {
            SocketAddress address = new InetSocketAddress(HOST, PORT);
            SocketChannel client = SocketChannel.open(address);
            ByteBuffer buffer = ByteBuffer.allocate(4);
            IntBuffer view = buffer.asIntBuffer();
            for (int expected = 0; ; expected++) {
                client.read(buffer);
                int actual = view.get();
                buffer.clear();
                view.rewind();
                if (actual != expected) {
                    System.err.println("Expected " + expected + "; was " + actual);
                    break;
                }
                System.out.println(actual);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}