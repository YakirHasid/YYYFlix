public class Content {
    // fields
    private int ID; // identifier
    private static int COUNTER = 0;
    public String format;
    public String subtitlesFileName;
    public String name;
    public float length;

    /**
     * public constructor
     * @param format
     * @param subtitlesFileName
     * @param name
     * @param length
     */
    public Content(String format, String subtitlesFileName, String name, float length)
    {
        this.ID = ++COUNTER;
        this.format = format;
        this.subtitlesFileName = subtitlesFileName;
        this.name = name;
        this.length = length;
    }

    // TODO: Add a logic function (MUST)

    /**
     * gets the ID of the content
     * @return
     */
    public int getID() {
        return ID;
    }

    /**
     * sets the ID
     * @param ID
     */
    public void setID(int ID){
        this.ID=ID;
    }
    public String getFormat(){
        return format;
    }
    public void setFormat(String format){
        this.format=format;
    }
    public String getSubtitlesFileName(){
        return subtitlesFileName;
    }
    public void setSubtitles(String sub){
        subtitlesFileName=sub;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public float getLength() {
        return length;
    }
    public void setLength(float length){
        this.length=length;
    }

    @Override
    public String toString() {
        return "Content{" +
                "ID=" + ID +
                ", format='" + format + '\'' +
                ", subtitlesFileName='" + subtitlesFileName + '\'' +
                ", name='" + name + '\'' +
                ", length=" + length +
                '}';
    }
}
