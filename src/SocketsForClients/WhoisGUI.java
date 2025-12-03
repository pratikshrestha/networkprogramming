import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class WhoisGUI {

    private static final String WHOIS_SERVER = "whois.verisign-grs.com";
    private static final int WHOIS_PORT = 43;

    public static void main(String[] args) {
        JFrame frame = new JFrame("WHOIS Lookup");

        JTextField input = new JTextField("example.com", 20);
        JButton button = new JButton("Lookup");
        JTextArea output = new JTextArea();
        output.setEditable(false);

        // TOP PANEL (input + button)
        JPanel top = new JPanel();
        top.add(new JLabel("Domain:"));
        top.add(input);
        top.add(button);

        frame.setLayout(new BorderLayout());
        frame.add(top, BorderLayout.NORTH);       // TOP
        frame.add(new JScrollPane(output), BorderLayout.CENTER);  // BIG CENTER AREA

        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        button.addActionListener(e -> {
            output.setText(""); // clear old

            String domain = input.getText().trim();
            if (domain.isEmpty()) {
                output.setText("Please enter a domain.");
                return;
            }

            output.append("Looking up: " + domain + "\n\n");

            try (Socket socket = new Socket(WHOIS_SERVER, WHOIS_PORT)) {

                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                // MUST SEND CRLF
                writer.print(domain + "\r\n");
                writer.flush();

                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }

            } catch (Exception ex) {
                output.append("\nERROR: " + ex.getMessage());
            }
        });
    }
}
