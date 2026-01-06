package Projet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ReceiverThread extends Thread {

    private int port;
    private BlockingQueue<Message> queue;

    public ReceiverThread(int port, BlockingQueue<Message> queue) {
        this.port = port;
        this.queue = queue;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[Receiver] Listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                String text = in.readLine();
                Message msg = new Message(-1, text); // from inconnu
                queue.put(msg);   // THREAD-SAFE
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
