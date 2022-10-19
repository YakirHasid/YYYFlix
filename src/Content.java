import java.io.Serializable;
import java.util.Objects;

abstract public class Content implements Serializable {
    // fields
    private final int ID; // identifier
    public static int COUNTER = 0;
    public String format;
    public String subtitlesFileName;
    public String name;
    public float length;

    // defines
    private static final int MINUTES_IN_HOUR = 60; // 60 minutes in 1 hour
    public static enum VALID_CONTENT_TYPES {
        Commercial,
        Movie,
        TVShow
      }    


    /**
     * Content's public constructor
     * @param format represents the format of the content
     * @param subtitlesFileName represents the subtitles filename of the content
     * @param name represents the name of the content
     * @param length represents the length of the content
     */
    public Content(String format, String subtitlesFileName, String name, float length)
    {
        this.ID = ++COUNTER;
        this.format = format;
        this.subtitlesFileName = subtitlesFileName;
        this.name = name;
        this.length = length; // minutes
    }

    /**
     * calculates the length of the content in hours by dividing 60 the minutes
     * @return the length of the content in minutes
     */
    public float calcHours() {
        return this.length/MINUTES_IN_HOUR;
    }

    // ID getter
    public int getID() {
        return ID;
    }

    // format getter
    public String getFormat(){
        return format;
    }

    // format setter
    public void setFormat(String format){
        this.format=format;
    }

    // subtitles filename getter
    public String getSubtitlesFileName(){
        return subtitlesFileName;
    }

    // subtitles filename setter
    public void setSubtitles(String sub){
        subtitlesFileName=sub;
    }

    // name getter
    public String getName(){
        return name;
    }

    // name setter
    public void setName(String name){
        this.name=name;
    }

    // length getter
    public float getLength() {
        return length;
    }

    // length setter
    public void setLength(float length){
        this.length=length;
    }

    /**
     * checks if the given content is a valid form of content from the VALID_CONTENT_TYPES array
     * @param content represents the given content
     * @return a valid matching content (case corrected if necessary), null if given content is not valid
     */
    public static VALID_CONTENT_TYPES isContentValid(String content)
    {
        
        for (VALID_CONTENT_TYPES contentType :
             VALID_CONTENT_TYPES.values()) {
            if(content.equalsIgnoreCase(contentType.toString()))
                return contentType;
        }
        return null;
    }    

    /**
     * toString method of the Content
     * @return a string that represents the Content
     */
    @Override
    public String toString() {
        return  "ID: " + this.ID + "\n" +
                "Name: " + this.name + "\n" +
                "Length: " + this.length + " Minutes (" + this.calcHours() + " Hours) \n" +
                "Format: " + this.format + "\n" +
                "Subtitles Filename: " + this.subtitlesFileName + "\n";
    }

    /**
     * override equals for HashSet usage
     * @param o compared to object
     * @return true if the objects are equals, false otherwise (checked on all fields)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return Objects.equals(ID, content.getID());
    }

    /**
     * override hashCode for HashSet usage
     * @return hash of the current object
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.ID, this.format, this.subtitlesFileName, this.name, this.length);
    }
}
