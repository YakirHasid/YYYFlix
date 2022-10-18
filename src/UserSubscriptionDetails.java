import java.io.Serializable;
import java.time.LocalDate;

public class UserSubscriptionDetails implements Serializable {
    // fields
    User user;
    Subscription subscription;
    private int transactionNumber; // identifier
    public static int COUNTER = 0;
    LocalDate startDate;
    LocalDate endDate;

    // public constructor
    public UserSubscriptionDetails(User user, Subscription subscription){
        this.user = user;
        this.subscription = subscription;
        this.transactionNumber = ++COUNTER;
        startDate = LocalDate.now();
        
        endDate = startDate.plusMonths((long) subscription.getDuration());
    }

    // TODO: Add a logic function (MUST)

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return this.user.getName() + "'s Subscription Details:" + "\n" +
                this.subscription + "\n" +
                "Paid/Free=" + ((this.subscription.getPrice()==0) ? "Free" : "Paid") + "\n" +
                "Transaction Number= " + this.transactionNumber + "\n" +
                "Start Date= " + this.startDate + "\n" +
                "End Date= " + this.endDate + "\n";
    }
}
