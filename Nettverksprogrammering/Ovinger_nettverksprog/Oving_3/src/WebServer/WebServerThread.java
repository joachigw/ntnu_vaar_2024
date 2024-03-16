package WebServer;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Base64;

public class WebServerThread extends Thread {

    private final int threadNumber;
    private final Socket webSocket;

    public WebServerThread(int threadNumber, Socket webSocket) {
        this.threadNumber = threadNumber;
        this.webSocket = webSocket;
    }

    @Override
    public void run() {

        System.out.println("Web-client connected on thread no. " + threadNumber);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(webSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(webSocket.getOutputStream(), true);
        ) {

            System.out.println("Link established with client residing on port " + webSocket.getPort() + "\n");

            // Read HTTP-header contents from the client
            System.out.println("Reading HTTP-header from the client...");
            String headerLine = reader.readLine();
            while (!headerLine.isEmpty()) {
                System.out.println("->" + headerLine);
                headerLine = reader.readLine();
            }
            System.out.println("HTTP-header read.\n");

            // Send HTTP-header and a message to the client
            System.out.println("Sending HTTP-header and some HTML to the client...");
            writer.println(
                    "HTTP/1.0 200 OK\n" +
                            "Content-Type: text/html; charset=utf-8\n\n" +
                            "<html><body>\n" +
                            "<h1>This is a message from the client!</h1>\n" +
                            "This client is running on thread no. " + threadNumber + "\n" +
                            "<ul>\n" +
                            "<li>Element 1</li>\n" +
                            "</ul>\n" +
                            "</body></html>"
            );
            System.out.println("HTML sent!\n");

            System.out.println("Sending image in HTML...");
            byte[] imageBytes = Files.readAllBytes(new File("src/WebServer/idurogjugem.jpg").toPath());
            String base64EncodedImage = Base64.getEncoder().encodeToString(imageBytes);

            writer.println(
                    "HTTP/1.0 200 OK\n" +
                            "Content-Type: text/html\n\n" +
                            "<html><body>" +
                            "<h1>Image title!!! <:) &#128511;&#128128;&#x1F480;</h1>" +
                            "<img src=\"data:image/jpeg;base64, " + base64EncodedImage + "\" " +
                            "width=450px alt=\"Your image\">\n" +
                            "</body></html>"
            );
            System.out.println("Image in HTML sent!\n");

            webSocket.close();
            System.out.println("Web-socket closed!");

        } catch (IOException ioException) {
            System.out.println("Unexpected IO-exception! Please try again.");
            System.exit(0);
        }
    }


}
