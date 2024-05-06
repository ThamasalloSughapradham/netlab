
import java.net.*;
import java.io.*;
import java.util.*;

class TCP2WAYCLIENT {
    public static void main(String[] args) throws Exception {
        try {
            Scanner scan = new Scanner(System.in);
            String serverHostname = "localhost";
            int serverPort = 5000;
            Socket clientSocket = new Socket(serverHostname, serverPort);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (true) {
                System.out.print("you: ");
                String message = scan.nextLine() + "\n";
                outToServer.writeBytes(message);
                if (message.equals("bye")) {
                    break;
                }
                String serverReply = inFromServer.readLine();
                System.out.println("server:" + serverReply);
            }
            scan.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
}
