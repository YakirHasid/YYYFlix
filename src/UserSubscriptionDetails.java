import java.io.Serializable;
import java.time.LocalDate;

public class UserSubscriptionDetails implements Serializable {
    // fields
    String username;
    String paymentMethod;
    Subscription subscription;
    private int transactionNumber; // identifier
    public static int COUNTER = 0;
    LocalDate startDate;
    LocalDate endDate;

    // public constructor
    public UserSubscriptionDetails(User user, Subscription subscription){   
        this.username = user.getUsername();     
        this.paymentMethod = user.getPaymentMethod();
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

    public String getUsername() {
        return username;
    }

    public String printReciept(String name) {
        return name + "(" + username + ")" + "'s Subscription Details:" + "\n" +
                this.subscription + "\n" +
                "Paid/Free=" + ((this.subscription.getPrice()==0) ? "Free" : "Paid") + "\n" +
                "Transaction Number= " + this.transactionNumber + "\n" +
                "Paid With= " + this.paymentMethod + "\n" +
                "Start Date= " + this.startDate + "\n" +
                "End Date= " + this.endDate + "\n";
    }

    @Override
    public String toString() {
        return username + "'s Subscription Details:" + "\n" +
                this.subscription + "\n" +
                "Paid/Free=" + ((this.subscription.getPrice()==0) ? "Free" : "Paid") + "\n" +
                "Transaction Number= " + this.transactionNumber + "\n" +
                "Paid With= " + this.paymentMethod + "\n" +
                "Start Date= " + this.startDate + "\n" +
                "End Date= " + this.endDate + "\n";
    }
}
