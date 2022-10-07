public class Subscription {
    // fields
    private int ID; // identifier
    private static int COUNTER = 0;
    public float price;
    public float duration; // in months

    // public constructor
    public Subscription(float price, float duration)
    {
        this.ID = ++COUNTER;
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

    // TODO: Add a logic function (MUST)

    @Override
    public String toString() {
        return "Subscription{" +
                "ID=" + ID +
                ", price=" + price +
                ", duration=" + duration +
                '}';
    }
}
