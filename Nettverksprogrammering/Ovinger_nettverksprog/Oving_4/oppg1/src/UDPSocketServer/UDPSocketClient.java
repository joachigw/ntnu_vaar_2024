package UDPSocketServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


/**
 * Socket client with the purpose of communicating with the socket server.
 * The client can request the socket server to add or subtract two numbers specified by this client.
 */
public class UDPSocketClient {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String iNetAddress = "localhost";

        System.out.println("Welcome!");
        System.out.print("\nEnter the port you want to connect to: ");
        int portNumber = Integer.parseInt(in.nextLine());

        // Initializes the socket and establishes communication locally on 'localhost'
        try {
            System.out.print("Initializing UDP-data... ");

            DatagramPacket sendPacket;
            byte[] sendData;
            DatagramPacket receivePacket;
            byte[] receiveData = new byte[1024];
            DatagramSocket clientSocket = new DatagramSocket();
            clientSocket.setSoTimeout(3000);

            System.out.println("done!");
            System.out.println("This client is using port " + clientSocket.getLocalPort());


            // Enter server-communication-loop
            outerLoop:
            while (true) {
                System.out.print("\n<---------------START---------------");


                Double firstNumber = null;
                Double secondNumber = null;
                int operator = 0;

                while (firstNumber == null) {
                    System.out.print("\nEnter the first number: ");
                    firstNumber = Double.parseDouble(in.nextLine());
                }

                while (secondNumber == null) {
                    System.out.print("Enter the second number: ");
                    secondNumber = Double.parseDouble(in.nextLine());
                }

                while (operator != 1 && operator != 2 && operator != 3 && operator != 4) {
                    System.out.print("\nDo you want to add (1), subtract (2), multiply (3) or divide (4)? ");
                    operator = Integer.parseInt(in.nextLine());
                }

                // Send the data to the server
                String sendDataString = firstNumber + "," + secondNumber + "," + operator;
                sendData = sendDataString.getBytes();
                sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(iNetAddress), portNumber);
                clientSocket.send(sendPacket);
                System.out.println("sent!");

                // Wait for the server's response
                System.out.print("Waiting for response... ");
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);

                // Print the contents of the packet
                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("done!");

                // Received response from the server
                System.out.println("\033[1m -> Received response: " + clientMessage + "\033[0m");
                System.out.println("---------------END--------------->");

                // Continue sending packets if the user wishes to
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
            }
        } catch (IOException ioException) {
            System.out.println("\nThere was a problem receiving a response from the server.");
        }

        System.out.println("Closing this client...");
    }
}
