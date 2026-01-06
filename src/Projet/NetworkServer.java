/*package Projet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkServer {

    private int port;

    public NetworkServer(int port) {
        this.port = port;
    }

    public void start() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("[Server] Listening on port " + port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream())
                    );

                    String message = in.readLine();
                    System.out.println("[Received] " + message);

                    clientSocket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}*/
package Projet;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkServer {

    private BlockingQueue<Message> queue = new LinkedBlockingQueue<>();

    public NetworkServer(int port) {
        new ReceiverThread(port, queue).start();
        new ProcessorThread(queue).start();
    }
}


