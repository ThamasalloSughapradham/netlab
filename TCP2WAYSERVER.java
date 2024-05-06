
import java.net.*;
import java.io.*;
import java.util.*;

class TCP2WAYSERVER {
    public static void main(String[] args) throws Exception {
        try {
            Scanner scan = new Scanner(System.in);
            int serverPort = 5000;
            ServerSocket serverSocket = new ServerSocket(serverPort);
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client Connected Successfully.....");
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
            while (true) {
                String clientMessage = inFromClient.readLine();
                System.out.println("client: " + clientMessage);
                if (clientMessage.equals("bye")) {
                    break;
                }
                System.out.print("server: ");
                String replyMessage = scan.nextLine() + "\n";
                outToClient.writeBytes(replyMessage);
            }
            scan.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
}
