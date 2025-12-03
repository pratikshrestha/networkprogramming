import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.Permission;

public class UrlConnectionExamples {

    public static void main(String[] args) {
        demoGetPermission();
        System.out.println();
        demoGuessContentTypeFromName();
        System.out.println();
        demoGuessContentTypeFromStream();
    }

    // ============================
    // 1. getPermission() example
    // ============================
    private static void demoGetPermission() {
        System.out.println("=== getPermission() demo ===");

        try {
            // Example 1: HTTP URL
            URL httpUrl = new URL("https://www.swsc.com.np/");
            URLConnection httpConn = httpUrl.openConnection();
            Permission httpPerm = httpConn.getPermission();

            System.out.println("-- HTTP URL --");
            System.out.println("URL: " + httpUrl);
            if (httpPerm != null) {
                System.out.println("Permission class: " + httpPerm.getClass().getName());
                System.out.println("Name            : " + httpPerm.getName());
                System.out.println("Actions         : " + httpPerm.getActions());
            } else {
                System.out.println("No permission object returned (probably no security manager).");
            }

            // Example 2: file: URL (adjust path for your system)
            // On Windows, something like: file:/C:/temp/test.txt
            // On Linux/Mac: file:/home/user/test.txt
            URL fileUrl = new URL("file:/C:/temp/test.txt");
            URLConnection fileConn = fileUrl.openConnection();
            Permission filePerm = fileConn.getPermission();

            System.out.println("\n-- file: URL --");
            System.out.println("URL: " + fileUrl);
            if (filePerm != null) {
                System.out.println("Permission class: " + filePerm.getClass().getName());
                System.out.println("Name            : " + filePerm.getName());
                System.out.println("Actions         : " + filePerm.getActions());
            } else {
                System.out.println("No permission object returned.");
            }

        } catch (IOException e) {
            System.err.println("Error in demoGetPermission():");
            e.printStackTrace();
        }
    }

    // =================================================
    // 2. guessContentTypeFromName(String name) example
    // =================================================
    private static void demoGuessContentTypeFromName() {
        System.out.println("=== guessContentTypeFromName() demo ===");

        String[] names = {
                "index.html",
                "photo.jpg",
                "archive.zip",
                "script.js",
                "style.css",
                "document.pdf",
                "unknownfile.xyz"
        };

        for (String name : names) {
            String mimeType = URLConnection.guessContentTypeFromName(name);
            System.out.println(name + " -> " + mimeType);
        }
    }

    // ==================================================
    // 3. guessContentTypeFromStream(InputStream in) demo
    // ==================================================
    private static void demoGuessContentTypeFromStream() {
        System.out.println("=== guessContentTypeFromStream() demo ===");

        // A) Demo with a hard-coded "PNG" header in memory
        //    PNG files start with these bytes: 89 50 4E 47 0D 0A 1A 0A
        byte[] pngHeader = new byte[] {
                (byte) 0x89, 'P', 'N', 'G', '\r', '\n', 0x1A, '\n'
        };

        try (BufferedInputStream in =
                     new BufferedInputStream(new ByteArrayInputStream(pngHeader))) {

            if (in.markSupported()) {
                in.mark(16); // allow method to read up to 16 bytes and reset
                String mimeType = URLConnection.guessContentTypeFromStream(in);
                System.out.println("In-memory PNG header -> " + mimeType);
                in.reset();
            } else {
                System.out.println("Input stream does not support mark/reset.");
            }

        } catch (IOException e) {
            System.err.println("Error with in-memory PNG demo:");
            e.printStackTrace();
        }

        // B) Demo with a real file (optional)
        //    You need to have a file present, adjust the path accordingly.
        File file = new File("example.pdf"); // change to a real file on your system
        if (file.exists()) {
            try (BufferedInputStream in =
                         new BufferedInputStream(new FileInputStream(file))) {

                in.mark(16);
                String mimeType = URLConnection.guessContentTypeFromStream(in);
                System.out.println("File " + file.getName() + " -> " + mimeType);
                in.reset();

            } catch (IOException e) {
                System.err.println("Error reading file for content type:");
                e.printStackTrace();
            }
        } else {
            System.out.println("File example.pdf not found. Skipping real-file demo.");
        }
    }
}
