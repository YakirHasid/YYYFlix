public class Subscription {
    // fields
    private int ID; // identifier
    private static int COUNTER = 0;
    public float price; // total price
    public float duration; // in months

    private static final int FREE_PRICE = 0;

    // public constructor
    public Subscription(float price, float duration)
    {
        this.ID = ++COUNTER;
        this.price = price;
        this.duration = duration;
    }

    // TODO: Add a logic function (MUST)

    /**
     * calculates the price per month by total price / months
     * @return the price per month, or 0 if free subscription
     */
    public float pricePerMonth()
    {
        // free subscription
        if(this.duration==0)
            return FREE_PRICE;

        // calculates the price per month by total price / months
        return this.price/this.duration;
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID){
        this.ID=ID;
    }
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDuration() {
        return duration;
    }
    public void setDuration(float duration){
        this.duration=duration;
    }

    /**
     * toString method of the Subscription
     * @return a string that represents the Subscription
     */
    @Override
    public String toString() {
        return "YYYFlix Subscription for " + this.duration + " months for " + this.price + " NIS (" + this.pricePerMonth() + " NIS per month)";
    }
}
