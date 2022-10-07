public class Notification {
    // fields
    private int messageID;
    private String messageText;

    // public constructor
    public Notification(int messageID, String messageText)
    {
        this.messageID = messageID;
        this.messageText = messageText;
    }

    // TODO: Add a logic function (MUST)

    public int getMessageID() {
        return messageID;
    }
    public boolean setMessageID(int messageID) {
        // TODO: Implement
        return false;
    }

    public String getMessageText() {
        return messageText;
    }
    public boolean setMessageText(String messageText) {
        // TODO: Implement
        return false;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "messageID=" + messageID +
                ", messageText='" + messageText + '\'' +
                '}';
    }
}
