package Projet;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

      
        Node node0 = new Node(0, "127.0.0.1", 5001, 3);
        Node node1 = new Node(1, "127.0.0.1", 5002, 3);
        Node node2 = new Node(2, "127.0.0.1", 5003, 3);

        List<Node> allNodes = Arrays.asList(node0, node1, node2);

        
        new NetworkServer(node0).start();
        new NetworkServer(node1).start();
        new NetworkServer(node2).start();

        Thread.sleep(1000); 

        System.out.println("STEP 1: Node 0 sends M1 : ");
        node0.broadcast(allNodes, "M1 from Node 0");

        Thread.sleep(500); 

        System.out.println("\n STEP 2: Node 1 sends M2 :");
        node1.broadcast(allNodes, "M2 from Node 1");

        Thread.sleep(500); 

        System.out.println("\n STEP 3: Node 2 sends M3 :");
        node2.broadcast(allNodes, "M3 from Node 2");

      
        System.out.println("\n STEP 4: Simulate Node 2 receiving M2 BEFORE M1 :");

       
        Message m1 = new Message(node0.getId(), "M1 from Node 0", node0.getVectorClock().copy());
        Message m2 = new Message(node1.getId(), "M2 from Node 1", node1.getVectorClock().copy());

     
        System.out.println("\n[FORCED DELIVERY] Node 2 receives M2 first");
        node2.getBuffer().add(m2); 
        node2.getBuffer().forEach(msg ->
                System.out.println("[BUFFERED] Node 2 buffered message from Node " + msg.senderId)
        );


        System.out.println("\n[FORCED DELIVERY] Node 2 receives M1 now");
        if (node2.canDeliver(m1)) {
            System.out.println("[DELIVERED] Node 2 delivered M1: " + m1.content);
            node2.getVectorClock().update(new VectorClock(m1.vectorClock));
        } else {
            node2.getBuffer().add(m1);
        }

 
        System.out.println("\n[CHECK BUFFER] Node 2 attempts delivery from buffer");
        node2.getBuffer().removeIf(msg -> {
            if (node2.canDeliver(msg)) {
                System.out.println("[DELIVERED FROM BUFFER] Node 2 delivered: " + msg.content);
                node2.getVectorClock().update(new VectorClock(msg.vectorClock));
                return true;
            }
            return false;
        });
    }
}
