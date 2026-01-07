package Projet;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;

public class NetworkServer {

    private int port;
    private Node node;

    public NetworkServer(Node node) {
        this.node = node;
        this.port = node.getPort();
    }

    public void start() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println(
                    "[Server] Node " + node.getId() + " listening on port " + port
                );

                while (true) {
                    Socket clientSocket = serverSocket.accept();

                    ObjectInputStream in =
                            new ObjectInputStream(clientSocket.getInputStream());

                    Message msg = (Message) in.readObject();

                    handleMessage(msg);

                    clientSocket.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private synchronized void handleMessage(Message msg) {

        if (node.canDeliver(msg)) {
            deliver(msg);

            node.getVectorClock().update(
                    new VectorClock(msg.vectorClock)
            );

          
            Iterator<Message> it = node.getBuffer().iterator();
            while (it.hasNext()) {
                Message buffered = it.next();
                if (node.canDeliver(buffered)) {
                    deliver(buffered);
                    node.getVectorClock().update(
                            new VectorClock(buffered.vectorClock)
                    );
                    it.remove();
                }
            }

        } else {
            node.getBuffer().add(msg);
            System.out.println(
                "[BUFFERED] Node " + node.getId() +
                " buffered message from Node " + msg.senderId +
                " VC=" + java.util.Arrays.toString(msg.vectorClock)
            );
        }
    }

    private void deliver(Message msg) {
        System.out.println(
            "[DELIVERED] Node " + node.getId() +
            " received from Node " + msg.senderId +
            " : \"" + msg.content + "\" VC=" +
            java.util.Arrays.toString(msg.vectorClock)
        );
    }
}
