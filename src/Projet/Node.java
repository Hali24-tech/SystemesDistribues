package Projet;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private int id;
    private String ip;
    private int port;

    private VectorClock vectorClock;
    private List<Message> buffer = new ArrayList<>();

    public Node(int id, String ip, int port, int totalNodes) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.vectorClock = new VectorClock(new int[totalNodes]); // initialize zeroed VC
    }


    public void broadcast(List<Node> nodes, String content) {
    this.vectorClock.tick(this.id); // increment local event before sending
    Message msg = new Message(this.id, content, this.vectorClock.copy());
    for (Node n : nodes) {
        if (n.getId() != this.id) {
            NetworkClient.sendTo(n, msg);
        }
    }
}

    // ---- Check if a message can be delivered ----
    public boolean canDeliver(Message m) {
        for (int i = 0; i < vectorClock.size(); i++) {
            if (i == m.senderId) {
                if (m.vectorClock[i] != vectorClock.get(i) + 1)
                    return false;
            } else {
                if (m.vectorClock[i] > vectorClock.get(i))
                    return false;
            }
        }
        return true;
    }

    // ---- Getters ----
    public VectorClock getVectorClock() {
        return vectorClock;
    }

    public List<Message> getBuffer() {
        return buffer;
    }

    public int getId() { return id; }
    public String getIp() { return ip; }
    public int getPort() { return port; }
}
