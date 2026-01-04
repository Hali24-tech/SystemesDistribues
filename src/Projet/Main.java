package Projet;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Node node1 = new Node(1, "127.0.0.1", 5001);
        Node node2 = new Node(2, "127.0.0.1", 5002);

        NetworkServer server1 = new NetworkServer(node1.getPort());
        NetworkServer server2 = new NetworkServer(node2.getPort());

        server1.start();
        server2.start();

        Thread.sleep(1000); // laisser les serveurs démarrer

        NetworkClient.sendTo(node1, "Hello Node 1 from Node 2");
        NetworkClient.sendTo(node2, "Hello Node 2 from Node 1");
    }
}

