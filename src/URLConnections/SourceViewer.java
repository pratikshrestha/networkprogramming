package URLConnections;

import java.io.*;
import java.net.*;

public class SourceViewer {
    public static void main(String[] args) {
        try {
            URL u = new URL("https://www.example.com/");
            URLConnection uc = u.openConnection();
            InputStream stream = uc.getInputStream();
            int i;
            while ((i = stream.read()) != -1) {
                System.out.print((char) i);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}