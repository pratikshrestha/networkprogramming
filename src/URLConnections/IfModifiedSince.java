import java.io.*;
import java.net.*;
import java.util.*;

public class IfModifiedSince {
    public static void main(String[] args) {
        // Initialize a Date object with the current date and time
        Date today = new Date();
        long millisecondsPerDay = 24 * 60 * 60 * 1000;

        try {
            URL u = new URL("https://www.google.com/");
            URLConnection uc = u.openConnection();

            System.out.println("Original if modified since: " + new Date(uc.getIfModifiedSince()));

            uc.setIfModifiedSince((new Date(today.getTime() - millisecondsPerDay)).getTime());

            System.out.println("Will retrieve file if it's modified since " + new Date(uc.getIfModifiedSince()));
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}