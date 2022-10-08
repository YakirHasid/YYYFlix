import java.time.Instant;
import java.util.Date;

public class UserSubscriptionDetails {
    // fields
    User user;
    Subscription subscription;
    private int transactionNumber; // identifier
    private static int COUNTER = 0;
    Date startDate;
    Date endDate;

    // public constructor
    public UserSubscriptionDetails(User user, Subscription subscription){
        this.user = user;
        this.subscription = subscription;
        this.transactionNumber = ++COUNTER;
        startDate = Date.from(Instant.now());
        // TODO: FIX END DATE TO MATCH SUBSCRIPTION DURATION!!!
        endDate = Date.from(Instant.now()); //endDate = Date.from(Instant.now() + subscribtion.getDuration())
    }

    // TODO: Add a logic function (MUST)

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
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
