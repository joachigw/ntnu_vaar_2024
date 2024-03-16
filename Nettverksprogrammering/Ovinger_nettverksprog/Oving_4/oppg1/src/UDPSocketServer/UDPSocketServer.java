package UDPSocketServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Socket server that is responsible for threading instances of a socket server through the class SocketServerThread.
 * Listens for any incoming connections to the specified port and branches a new thread out to handle the
 * communication with the incoming client.
 */
public class UDPSocketServer {

    public static void main(String[] args) {
        final int PORT_NUMBER = 9999;
        DatagramPacket receivePacket;
        byte[] receiveData = new byte[1024];

        double firstNumber;
        double secondNumber;
        int operator;
        double result;
        String[] operators = {"+", "-", "*", "/"};


        // Initialize server-socket on declared port
        try (DatagramSocket ss = new DatagramSocket(PORT_NUMBER)) {
            System.out.println("Listening for UDP-packets...");

            while (true) {
                System.out.print("\n<---------------START---------------");

                // Receive UDP-packet and read its values
                System.out.print("\nWaiting for a packet... ");
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                ss.receive(receivePacket);
                System.out.println("received!");
                System.out.print("Reading packet... ");
                InetAddress clientIPAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Print the contents of the packet
                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // Retrieve the numbers and operator from the packet
                String[] clientMessageData = clientMessage.split(",");
                firstNumber = Double.parseDouble(clientMessageData[0]);
                secondNumber = Double.parseDouble(clientMessageData[1]);
                operator = Integer.parseInt(clientMessageData[2]);

                // Calculate the expression
                switch (operator) {
                    case 1: {
                        result = firstNumber + secondNumber;
                        break;
                    }
                    case 2: {
                        result = firstNumber - secondNumber;
                        break;
                    }
                    case 3: {
                        result = firstNumber * secondNumber;
                        break;
                    }
                    case 4: {
                        result = firstNumber / secondNumber;
                        break;
                    }
                    default: result = 0;
                }

                System.out.println("done!");
                System.out.println("expression {\n  firstNumber: " + firstNumber + ",\n  secondNumber: " + secondNumber +
                        ",\n  operator: " + operators[operator - 1] + "\n}");

                // Respond to the client
                System.out.print("Sending response... ");
                String resultString = String.format("%.2f", result);
                byte[] responseMessage = resultString.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseMessage, responseMessage.length,
                        clientIPAddress, clientPort);
                ss.send(responsePacket);
                System.out.println("sent!");
                System.out.println("---------------END--------------->");
            }

        } catch (IOException ioException) {
            System.out.println("Unexpected IO-exception. Please try again later.\n" +
                    "Error message: " + ioException.getMessage());
        }

    }
}
