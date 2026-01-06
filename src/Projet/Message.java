package Projet;

/*public class Message {
    private int fromId;
    private String content;
    private int lamportTime;

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
}*/

public class Message {
    private int fromId;
    private String content;
    private int lamportTime;

    public Message(int fromId, String content, int lamportTime) {
        this.fromId = fromId;
        this.content = content;
        this.lamportTime = lamportTime;
    }

    public int getFromId() {
        return fromId;
    }

    public String getContent() {
        return content;
    }
    
    public int getLamportTime() {
        return lamportTime;
    }
}
