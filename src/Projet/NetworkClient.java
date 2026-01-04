package Projet;

import java.io.PrintWriter;
import java.net.Socket;

public class NetworkClient {

    public static void sendTo(Node node, String message) {
        try (
            Socket socket = new Socket(node.getIp(), node.getPort());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println(message);
            System.out.println("[Sent to Node " + node.getId() + "] " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
