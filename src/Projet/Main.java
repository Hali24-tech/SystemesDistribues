/*package Projet;

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
}*/

package Projet;

public class Main {
    public static void main(String[] args) throws Exception {
        Node node1 = new Node(1, "127.0.0.1", 5001);
        Node node2 = new Node(2, "127.0.0.1", 5002);

        new NetworkServer(node1.getPort(), node1.getClock());
        new NetworkServer(node2.getPort(), node2.getClock());

        Thread.sleep(1000);

        // Node1 sends to Node2
        NetworkClient.sendTo(node1, node2, "Hello from Node1");
        
        // Node2 sends to Node1
        NetworkClient.sendTo(node2, node1, "Hello from Node2");
        
        // Node1 sends another message to Node2
        NetworkClient.sendTo(node1, node2, "Second message from Node1");
        
        Thread.sleep(2000);
    }
}

/*Explication de la procedure de LamportClock

Node1 clock = 0
Node2 clock = 0

Event 1: Node1 sends to Node2

Node1: tick() → clock becomes 1
Sends message with T=1
Node2 receives: update(1) → clock becomes max(0,1)+1 = 2

Event 2: Node2 sends to Node1

Node2: tick() → clock becomes 3 (was 2, now incremented)
Sends message with T=3
Node1 receives: update(3) → clock becomes max(1,3)+1 = 4

Event 3: Node1 sends to Node2 again

Node1: tick() → clock becomes 5 (was 4, now incremented)
Sends message with T=5
Node2 receives: update(5) → clock becomes max(2,5)+1 = 6
 */
