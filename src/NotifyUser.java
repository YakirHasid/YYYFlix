public class NotifyUser {
    User user;
    Notification notification;

    public NotifyUser(User user, Notification notification)
    {
        this.user = user;
        this.notification = notification;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Notification getNotification() {
        return notification;
    }
    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public void sendMessage()
    {
        System.out.println("Sending message to user: " + getUser() + "\n"
                + "Message Details: "
                + "\n"
                + "\tMessage ID: " + notification.getMessageID()
                + "\n"
                + "\tMessage Text: " + notification.getMessageText());
    }

    @Override
    public String toString() {
        return "NotifyUser{" +
                "user=" + user +
                ", notification=" + notification +
                '}';
    }
}
