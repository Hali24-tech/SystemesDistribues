package Projet;

public class Node {
    private int id;
    private String ip;
    private int port;
    private LamportClock clock;

    public Node(int id, String ip, int port) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.clock = new LamportClock();
    }

    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
    
    public LamportClock getClock() {
        return clock;
    }
}