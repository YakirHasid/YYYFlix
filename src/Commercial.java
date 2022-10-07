public class Commercial extends Content{
    // fields
    public String publisher;

    // public constructor that uses the super constructor first
    public Commercial(String format, String subtitlesFileName, String name, float length, String publisher) {
        super(format, subtitlesFileName, name, length);
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    // toString method of the object
    @Override
    public String toString() {
        return "Commercial{" +
                "publisher='" + publisher + '\'' +
                "} " + super.toString();
    }
}
