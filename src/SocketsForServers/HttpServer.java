import java.io.*;
import java.net.*;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Server running at http://localhost:8080");

        while (true) {
            Socket socket = server.accept();
            OutputStream out = socket.getOutputStream();

            // Write directly to the socket output
            out.write("HTTP/1.1 200 OK\r\n".getBytes());
            out.write("Content-Type: text/html\r\n".getBytes());
            out.write("\r\n".getBytes());
            out.write("<html><body><h1>Hello from Simple HTTP Server</h1></body></html>".getBytes());

            out.flush();
            socket.close();
        }
    }
}
