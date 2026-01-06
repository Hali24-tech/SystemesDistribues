package Projet;

public class Message {
    private int fromId;
    private String content;

    public Message(int fromId, String content) {
        this.fromId = fromId;
        this.content = content;
    }

    public int getFromId() {
        return fromId;
    }

    public String getContent() {
        return content;
    }
}
