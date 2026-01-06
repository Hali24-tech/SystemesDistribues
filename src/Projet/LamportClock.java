package Projet;

public class LamportClock {
    private int time;
    
    public LamportClock() {
        this.time = 0;
    }
    
    // Called before sending a message
    public synchronized int tick() {
        return ++time;
    }
    
    // Called when receiving a message
    public synchronized void update(int receivedTime) {
        time = Math.max(time, receivedTime) + 1;
    }
    
    public synchronized int getTime() {
        return time;
    }
}