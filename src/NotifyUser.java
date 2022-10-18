import java.io.Serializable;
import java.util.ArrayList;

public class NotifyUser implements Serializable {
    // fields
    private String username;
    private ArrayList<Notification> notificationList;

    // public constructor

    /**
     * NotifyUser's public constructor
     * @param user the given user, which owns this Notification Center
     */
    public NotifyUser(String username)
    {
        this.username = username;
        this.notificationList = new ArrayList<>();
    }

    /**
     * adds a content to the user's library
     * @param notification represents the content that is being requested to be added into the library
     * @return false if the content is already in the library, true if the content has been added successfully (no validation)
     */
    public boolean addNotification(Notification notification){
        // checks if the notification is already inside the notification list
        if (notificationList.contains(notification))
            return false;

        // adds the notification to the notification list
        notificationList.add(notification);

        return true;
    }

    /**
     * deletes a content from the user's library
     * @param notification represents the content that is being requested to be removed from the library
     * @return false if the content is not in the library, true if the content has been added successfully (no validation)
     */
    public boolean deleteNotification(Notification notification){
        // checks if the notification is not inside the notification list
        if (!notificationList.contains(notification))
            return false;

        // removes the content to the notification list
        notificationList.remove(notification);

        return true;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Notification> getNotificationList() {
        return notificationList;
    }
    public Notification getLatestNotification() {
        Notification notification = null;

        // get latest notification only if not empty
        if(notificationList.size() > 0)
            notification =  notificationList.get(notificationList.size() - 1);

        // remove from list only if the received notification is not null
        if(notification != null)
            this.notificationList.remove(notification);
            
        return notification;
    }

    /**
     * toString method of the NotifyUser
     * @return a string that represents the NotifyUser
     */
    @Override
    public String toString() {
        return "Hey " + username + ", you have " + this.notificationList.size() + " Notifications.\n";
    }
}
