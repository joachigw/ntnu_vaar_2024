package SocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


/**
 * Socket client with the purpose of communicating with the socket server.
 * The client can request the socket server to add or subtract two numbers specified by this client.
 */
public class SocketClient {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Welcome!");
        System.out.print("Enter the port you want to connect to: ");
        int portNumber = Integer.parseInt(in.nextLine());

        // Initializes the socket and establishes communication locally on 'localhost'
        try (Socket clientSocket = new Socket("localhost", portNumber);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            System.out.println("Connection established! Reading server welcome message...");
            writer.println("Hello from the client:)");

            // Reads the welcome-message from the server
            System.out.println(reader.readLine());

            // Enter server-communication-loop
            outerLoop:
            while (true) {

                Double firstNumber = null;
                Double secondNumber = null;
                int operator = 0;

                while (firstNumber == null) {
                    System.out.print("Enter the first number: ");
                    firstNumber = Double.parseDouble(in.nextLine());
                }

                while (secondNumber == null) {
                    System.out.print("Enter the second number: ");
                    secondNumber = Double.parseDouble(in.nextLine());
                }

                while (operator != 1 && operator != 2) {
                    System.out.print("Do you want to add (1) or subtract (2)? ");
                    operator = Integer.parseInt(in.nextLine());
                }

                // Send the data to the server and read its response
                System.out.print("Sending... ");
                writer.println(firstNumber);
                writer.println(secondNumber);
                writer.println(operator);
                System.out.println("done!");
                System.out.print("Waiting for response... ");
                String response = reader.readLine();
                System.out.println("done!");
                System.out.println("Received response: " + response);

                System.out.print("\nDo you want to continue? Yes (1) or No (2): ");
                while (true) {
                    String continueOrExit = in.nextLine();
                    if (continueOrExit.equals("1")) {
                        break;
                    } else if (continueOrExit.equals("2")) {
                        System.out.println("Bye bye! Exiting from the client...");
                        clientSocket.close();
                        break outerLoop;
                    } else {
                        System.out.print("Please enter either 1 for Yes,  or 2 for No: ");
                    }
                }
                System.out.println("\n");
            }
        } catch (IOException ioException) {
            System.out.println("There was a problem communicating with the server. Please try again later.");
        }

    }
}
