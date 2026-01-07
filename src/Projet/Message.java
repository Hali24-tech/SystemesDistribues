package Projet;

import java.io.Serializable;

public class Message implements Serializable {
    public int senderId;
    public String content;
    public int[] vectorClock;

    public Message(int senderId, String content, int[] vectorClock) {
        this.senderId = senderId;
        this.content = content;
        this.vectorClock = vectorClock;
    }
}
