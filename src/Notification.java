import java.io.Serializable;

public class Notification implements Serializable {
    // fields
    private int messageID;
    private String sender;
    private String messageTitle;
    private String messageText;
    private boolean validForDisplay;

    private static final int TEXT_DISPLAY_MAX_LEN = 100;

    // public constructor

    /**
     * Notification's public constructor
     * @param messageID the id of the message
     * @param messageTitle the title of the message
     * @param messageText the text of the message
     */
    public Notification(int messageID, String sender, String messageTitle, String messageText)
    {
        this.messageID = messageID;
        this.sender = sender;
        this.messageTitle = messageTitle;
        this.messageText = messageText;

        validForDisplay = isTextValid(messageText);
    }

    /**
     * checks that the message text is valid for display, is less than TEXT_DISPLAY_MAX_LEN
     * @param messageText the given text for check
     * @return true if the given text is valid, false if it is not valid
     */
    public boolean isTextValid(String messageText)
    {
        return messageText.length() <= TEXT_DISPLAY_MAX_LEN;
    }

    public String createMessage()
    {
        if(this.validForDisplay)
            return "Notification:\n" + 
                    "\t" + "From: " + this.sender + "\n" + 
                    "\t" + "Title: " + this.messageTitle + "\n" + 
                    "\t" + "Text: " + this.messageText + "\n";
        else
            return "[INVALID MESSAGE LENGTH FOR DISPLAY]";
    }

    public String getSender() {
        return this.sender;
    }
    public boolean setSender(String sender) {
        this.sender = sender;
        return false;
    }

    public int getMessageID() {
        return messageID;
    }
    public boolean setMessageID(int messageID) {
        this.messageID = messageID;
        return false;
    }

    public String getMessageText() {
        return messageText;
    }
    public boolean setMessageText(String messageText) {
        this.messageText = messageText;
        return false;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    @Override
    public String toString() {
        return  "Notification Details: " + "\n" +
                "ID: " + this.messageID + "\n" +
                "Title: " + this.messageTitle + "\n" +
                "Text: " + this.messageText + "\n" +
                "Is valid for display?: " + ((this.validForDisplay) ? "YES" : "NO") + "\n";
    }
}
