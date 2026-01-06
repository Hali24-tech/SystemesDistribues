/*package Projet;

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
}*/

package Projet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ReceiverThread extends Thread {
    private int port;
    private BlockingQueue<Message> queue;
    private LamportClock clock;
    
    public ReceiverThread(int port, BlockingQueue<Message> queue, LamportClock clock) {
        this.port = port;
        this.queue = queue;
        this.clock = clock;
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
                
                // Parse format: "fromId|timestamp|content"
                String[] parts = text.split("\\|", 3);
                int fromId = Integer.parseInt(parts[0]);
                int receivedTime = Integer.parseInt(parts[1]);
                String content = parts[2];
                
                // Update local clock
                clock.update(receivedTime);
                
                Message msg = new Message(fromId, content, receivedTime);
                queue.put(msg);
                
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
