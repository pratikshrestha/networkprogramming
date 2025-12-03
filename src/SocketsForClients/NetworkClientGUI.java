import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class NetworkClientGUI extends JFrame implements ActionListener {

    private JTextField hostField, portField, messageField;
    private JButton connectButton, sendButton, clearButton;
    private JTextArea outputArea;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public NetworkClientGUI() {
        setTitle("Network Client Library");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---- Top panel (Connection section) ----
        JPanel topPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        topPanel.setBorder(BorderFactory.createTitledBorder("Connection Settings"));

        topPanel.add(new JLabel("Host:"));
        hostField = new JTextField("localhost");
        topPanel.add(hostField);

        connectButton = new JButton("Connect");
        connectButton.addActionListener(this);
        topPanel.add(connectButton);

        topPanel.add(new JLabel("Port:"));
        portField = new JTextField("12345");
        topPanel.add(portField);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        topPanel.add(clearButton);

        add(topPanel, BorderLayout.NORTH);

        // ---- Center panel (Output area) ----
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createTitledBorder("Server Messages"));
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // ---- Bottom panel (Message sending) ----
        JPanel bottomPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        messageField.addActionListener(this);

        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Send Message"));
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == connectButton) {
            connectToServer();
        } else if (source == sendButton || source == messageField) {
            sendMessage();
        } else if (source == clearButton) {
            outputArea.setText("");
        }
    }

    private void connectToServer() {
        String host = hostField.getText().trim();
        int port = Integer.parseInt(portField.getText().trim());

        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            outputArea.append("Connected to " + host + " on port " + port + "\n");

            // Start a background thread to listen for server messages
            new Thread(() -> {
                String response;
                try {
                    while ((response = in.readLine()) != null) {
                        outputArea.append("Server: " + response + "\n");
                    }
                } catch (IOException ex) {
                    outputArea.append("Connection closed.\n");
                }
            }).start();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Connection Failed: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendMessage() {
        if (socket == null || socket.isClosed()) {
            JOptionPane.showMessageDialog(this, "Not connected to any server.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String msg = messageField.getText().trim();
        if (!msg.isEmpty()) {
            out.println(msg);
            outputArea.append("You: " + msg + "\n");
            messageField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NetworkClientGUI::new);
    }
}
