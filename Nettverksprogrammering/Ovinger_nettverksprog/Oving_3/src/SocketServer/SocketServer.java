package SocketServer;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Socket server that is responsible for threading instances of a socket server through the class SocketServerThread.
 * Listens for any incoming connections to the specified port and branches a new thread out to handle the
 * communication with the incoming client.
 */
public class SocketServer {

    public static void main(String[] args) {
        final int PORT_NUMBER = 9999;
        int threadCounter = 0;

        // Initialize server-socket on declared port
        try (ServerSocket ss = new ServerSocket(PORT_NUMBER)) {
            System.out.print("Waiting for connections...");

            // Listen for and handles new connections to the server
            // Separates communications into separate threads
            while (true) {
                threadCounter++;

                try {
                    SocketServerThread sst = new SocketServerThread(threadCounter, ss.accept());
                    sst.start();
                    System.out.println("-> Connection established in thread no. " + threadCounter);
                } catch (Exception exception) {
                    System.out.println("Exception caught. Closing server...\nMessage: " + exception.getMessage());
                    break;
                }
            }

        } catch (IOException ioException) {
            System.out.println("Unexpected IO-exception. Please try again later.\n" +
                    "Error message: " + ioException.getMessage());
        }

    }
}
