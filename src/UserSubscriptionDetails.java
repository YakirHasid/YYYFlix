import java.time.Instant;
import java.util.Date;

public class UserSubscriptionDetails {

    User user;
    Subscription subscription;
    private int transactionNumber; // identifier
    private static int COUNTER = 0;
    Date startDate;
    Date endDate;

    public UserSubscriptionDetails(User user, Subscription subscribtion){
        this.user = user;
        this.subscription = subscribtion;
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
        return "UserSubscriptionDetails{" +
                "user=" + user +
                ", subscription=" + subscription +
                ", transactionNumber=" + transactionNumber +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
