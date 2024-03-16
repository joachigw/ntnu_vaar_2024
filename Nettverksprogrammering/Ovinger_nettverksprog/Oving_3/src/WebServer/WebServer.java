package WebServer;

import java.io.IOException;
import java.net.ServerSocket;

public class WebServer {
    public static void main(String[] args) {
        final int PORT_NUMBER = 80;
        int threadCounter = 0;

        try (ServerSocket ss = new ServerSocket(PORT_NUMBER)) {
            System.out.println("Waiting for connections...");

            while (true) {
                try {
                    threadCounter++;
                    WebServerThread wst = new WebServerThread(threadCounter, ss.accept());
                    wst.start();
                    System.out.println("-> Connection established in thread no. " + threadCounter);
                } catch (Exception exception) {
                    System.out.println("Exception caught. Closing server...\nMessage: " + exception.getMessage());
                    break;
                }
            }

        } catch (IOException ioException) {
            System.out.println("The server socket could not be configured correctly! Please try again. Exiting...");
            System.exit(0);
        }
    }
}
