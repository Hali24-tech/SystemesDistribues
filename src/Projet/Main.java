package Projet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Projet.Main <nodeId> <configFilePath>");
            return;
        }
        int myId = Integer.parseInt(args[0]);
        String configPath = args[1];
        List<Node> allNodes = new ArrayList<>();
        // Pas 1 : Lecture de la configuration
        try (Scanner scanner = new Scanner(new File(configPath))) {
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    int id = scanner.nextInt();
                    String ip = scanner.next();
                    int port = scanner.nextInt();
                    allNodes.add(new Node(id, ip, port, 0));
                } else {
                    scanner.next(); 
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Config file not found: " + configPath);
            return;
        }
        int totalNodes = allNodes.size();
        System.out.println("Configuration loaded. Total nodes: " + totalNodes);
        // Pas 2 : Identifier mon noeud et l'initialiser correctement
        Node me = null;
        for (int i = 0; i < allNodes.size(); i++) {
            Node n = allNodes.get(i);
            if (n.getId() == myId) {
                me = new Node(n.getId(), n.getIp(), n.getPort(), totalNodes);
                allNodes.set(i, me); 
                break;
            }
        }
        if (me == null) {
            System.err.println("Node ID " + myId + " not found in config.");
            return;
        }
        // Pas 3 : Demarrer le serveur
        new NetworkServer(me).start();
        // Pas 4 : CLI
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Node " + me.getId() + " ready. Commands: send <text>, status, exit");
            while (true) {
                System.out.print("[" + me.getId() + "]> ");
                String line = input.nextLine().trim();
                if (line.isEmpty())
                    continue;
                String[] parts = line.split("\\s+", 2);
                String cmd = parts[0];
                switch (cmd.toLowerCase()) {
                    case "exit":
                        System.out.println("Shutting down.");
                        System.exit(0);
                        break;
                    case "status":
                        System.out.println("=== Node Status ===");
                        System.out.println("ID: " + me.getId());
                        System.out.println("Vector Clock: " + me.getVectorClock());
                        System.out.println("Buffer size: " + me.getBuffer().size());
                        if (!me.getBuffer().isEmpty()) {
                            System.out.println("Buffered Messages:");
                            for (Message m : me.getBuffer()) {
                                System.out.println("  From " + m.senderId + ": " + m.content + " "
                                        + java.util.Arrays.toString(m.vectorClock));
                            }
                        }
                        System.out.println("===================");
                        break;
                    case "send":
                        if (parts.length < 2) {
                            System.out.println("Usage: send <message text>");
                        } else {
                            String content = parts[1];
                            me.broadcast(allNodes, content);
                        }
                        break;
                    default:
                        System.out.println("Unknown command. commands: send, status, exit");
                }
            }
        }
    }
}
