import java.io.*;
import java.net.*;

public class ServerWithReadWrite {
    public static void main(String[] args) {
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Listening on port: " + port);

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New client connected: " + clientSocket.getInetAddress());

                    // Create input and output streams for the client socket
                    InputStream inputStream = clientSocket.getInputStream();
                    OutputStream outputStream = clientSocket.getOutputStream();

                    // Create readers and writers for client communication
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    PrintWriter writer = new PrintWriter(outputStream, true);

                    // Read from the client and write back
                    String clientMessage = reader.readLine();
                    System.out.println("Received from client: " + clientMessage);

                    // Echo the message back to the client
                    writer.println("Server: " + clientMessage);

                    // Close the client socket
                    clientSocket.close();
                    System.out.println("Client disconnected.");
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        } catch (IOException ex) {
            System.err.println("Couldn't start server");
        }
    }
}