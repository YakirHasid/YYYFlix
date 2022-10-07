public class Commercial extends Content{
    // fields
    public String publisher;

    /**
     * public constructor that uses the super constructor first
     * @param format represents the format of the content
     * @param subtitlesFileName represents the subtitles filename of the content
     * @param name represents the name of the content
     * @param length represents the length of the content
     * @param publisher represents the publisher of the content
     */
    public Commercial(String format, String subtitlesFileName, String name, float length, String publisher) {
        super(format, subtitlesFileName, name, length);
        this.publisher = publisher;
    }

    // publisher getter
    public String getPublisher() {
        return publisher;
    }

    // publisher setter
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * toString method of the Commercial
     * @return a string that represents the Commercial
     */
    @Override
    public String toString() {
        return "Commercial{" +
                "publisher='" + publisher + '\'' +
                "} " + super.toString();
    }
}
