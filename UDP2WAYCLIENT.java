
import java.net.*;
import java.util.*;

class UDP2WAYCLIENT {
    public static void main(String[] args) throws Exception {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            byte[] sendData;
            Scanner scan = new Scanner(System.in);
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 9876;
            while (true) {
                System.out.print("client: ");
                String str = scan.nextLine();
                sendData = str.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                clientSocket.send(sendPacket);
                if (str.equals("bye")) {
                    break;
                }
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("server: "+response);
            }
            clientSocket.close();
            scan.close();
            clientSocket.close();
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        }
    }
}
