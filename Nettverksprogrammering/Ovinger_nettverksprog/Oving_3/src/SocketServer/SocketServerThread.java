package SocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * An instance of a socket server that is responsible for the communication with and handling of the client and its
 * requests.
 * The class has functionality for reading two numbers at a time from a client and adding or subtracting the numbers
 * before sending the result back to the client.
 * <p>
 * The class implements AutoCloseable to be able to be instantiated inside a try-with-resources method.
 */
public class SocketServerThread extends Thread {
    private final int threadNumber;
    private final Socket clientSocket;

    public SocketServerThread(int threadNumber, Socket clientSocket) {
        this.threadNumber = threadNumber;
        this.clientSocket = clientSocket;
    }


    /**
     * Implementation of this class' capability of being a single independent thread.
     */
    @Override
    public void run() {
        Double firstNumber;
        Double secondNumber;
        Double result = null;
        int operator;

        System.out.println("Communication established on port " + clientSocket.getLocalPort() +
                " with client on thread no. " + threadNumber);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            // Send welcome message to client
            writer.println("### Welcome to the server on localhost:" + clientSocket.getLocalPort() + " running on thread " +
                    threadNumber + "! ###");

            // Read acknowledgment from client
            System.out.println(reader.readLine());

            // Enter client-handling-loop
            while (true) {

                // Read inputs from user
                try {
                    firstNumber = Double.parseDouble(reader.readLine());
                    secondNumber = Double.parseDouble(reader.readLine());
                    operator = Integer.parseInt(reader.readLine());
                } catch (NullPointerException npe) {
                    System.out.println("Client disconnected. Closing socket on thread no. " + threadNumber + ".");
                    break;
                }

                // Respond with results
                if (operator == 1) {
                    result = firstNumber + secondNumber;
                } else if (operator == 2) {
                    result = firstNumber - secondNumber;
                } else {
                    System.out.println("Invalid operator received!");
                }
                writer.println(result);
                System.out.println("The result '" + result + "' was sent to the client in thread no. " + threadNumber);
            }

        } catch (IOException ioException) {
            System.out.println("Unexpected IO-exception! Please try again.");
            System.exit(0);
        }

    }
}
