package Projet;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkClient {
    // sender = the node that is sending
    // receiver = the node that will receive
    public static void sendTo(Node sender, Node receiver, String message) {
        // Increment SENDER's clock before sending
        int timestamp = sender.getClock().tick();
        
        try (
            Socket socket = new Socket(receiver.getIp(), receiver.getPort());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            // Send format: "fromId|timestamp|content"
            String formattedMsg = sender.getId() + "|" + timestamp + "|" + message;
            out.println(formattedMsg);
            System.out.println("[Sent to Node " + receiver.getId() + "] " + message + " (T=" + timestamp + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}