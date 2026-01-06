package Projet;

import java.util.concurrent.BlockingQueue;

public class ProcessorThread extends Thread {

    private BlockingQueue<Message> queue;

    public ProcessorThread(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (true) {
                Message msg = queue.take();   // bloque si vide
                System.out.println("[Processor] Processing: " + msg.getContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
