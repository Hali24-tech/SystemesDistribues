package Projet;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkClient {

    public static void sendTo(Node destination, Message message) {
        try (
            Socket socket = new Socket(destination.getIp(), destination.getPort());
            ObjectOutputStream out =
                    new ObjectOutputStream(socket.getOutputStream())
        ) {
            out.writeObject(message);
            out.flush();

            System.out.println(
                "[Sent] From Node " + message.senderId +
                " to Node " + destination.getId() +
                " : \"" + message.content + "\" VC=" +
                java.util.Arrays.toString(message.vectorClock)
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
