import java.net.*;
import java.io.*;
import java.util.Date;
public class MultithreadedDaytimeServer {
    public final static int PORT = 8080;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started. Listening on port: " + PORT);
            while (true) {
                try {
                    Socket connection = server.accept();
                    System.out.println("New client connected: "
                              + connection.getInetAddress());
                    Thread task = new DaytimeThread(connection);
                    task.start();
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        } catch (IOException ex) {
            System.err.println("Couldn't start server");
        }
    }
}
class DaytimeThread extends Thread {
    private Socket connection;
    DaytimeThread(Socket connection) {
        this.connection = connection;
    }
    @Override
    public void run() {
        try {
            Writer out = new OutputStreamWriter(connection.getOutputStream());
            Date now = new Date();
            out.write(now.toString() + "\r\n");
            out.flush();
        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}