import java.io.*;
import java.net.Socket;

public class DictClient {
    public static void main(String[] args) {
        String server = "dict.org";
        int port = 2628;

        try (Socket socket = new Socket(server, port)) {
            System.out.println("Connected to " + socket.getInetAddress() + " on port " + port);

            socket.setSoTimeout(10000); // stop waiting after 10 seconds

            InputStream in = socket.getInputStream();

            //Write to server
            OutputStream out = socket.getOutputStream();

            // Send DEFINE command
            String command = "DEFINE * computer\r\n";
            out.write(command.getBytes());
            out.flush();

            // Read and print response
            StringBuilder response = new StringBuilder();
            int ch;
            while ((ch = in.read()) != -1) {
                response.append((char) ch);

                // stop if we reach the DICT end-of-response code "250 "
                if (response.toString().contains("250 ")) {
                    break;
                }
            }

            System.out.println("----- Server Response -----");
            System.out.println(response);

            // Send quit command and close
            String quit = "QUIT\r\n";
            out.write(quit.getBytes());
            out.flush();

            System.out.println("Connection closed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
