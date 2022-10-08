abstract public class Content {
    // fields
    private final int ID; // identifier
    private static int COUNTER = 0;
    public String format;
    public String subtitlesFileName;
    public String name;
    public float length;

    // defines
    private static final int SECONDS_IN_MINUTE = 60; // 60 seconds in 1 minute
    private static final int MINUTES_IN_HOUR = 60; // 60 minutes in 1 hour


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
        this.length = length; // seconds
    }

    /**
     * calculates the length of the content in minutes by dividing 60 the seconds, as the length field is in seconds
     * @return the length of the content in minutes
     */
    public float calcMinutes() {
        return this.length/SECONDS_IN_MINUTE;
    }

    /**
     * calculates the length of the content in hours by dividing 60 the minutes
     * @return the length of the content in minutes
     */
    public float calcHours() {
        return this.calcMinutes()/MINUTES_IN_HOUR;
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
     * toString method of the Content
     * @return a string that represents the Content
     */
    @Override
    public String toString() {
        return  "ID: " + this.ID + "\n" +
                "Name: " + this.name + "\n" +
                "Length: " + this.calcMinutes() + " Minutes \n" +
                "Format: " + this.format + "\n" +
                "Subtitles Filename: " + this.subtitlesFileName + "\n";
    }
}
