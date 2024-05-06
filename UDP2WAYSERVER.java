
import java.net.*;
import java.util.*;

class UDP2WAYSERVER {
    public static void main(String[] args) throws Exception {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9876);
            byte[] receiveData = new byte[1024];
            byte[] sendData;
            Scanner scan = new Scanner(System.in);
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("client: " + message);
                if (message.equals("bye")) {
                    System.out.println("Server Is Exiting .... BYE");
                    break;
                }
                System.out.print("server: ");
                String str = scan.nextLine();
                sendData = str.getBytes();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
            }
            scan.close();
            serverSocket.close();

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        }
    }
}
