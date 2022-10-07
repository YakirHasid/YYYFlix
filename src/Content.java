public class Content {
    // fields
    private int ID; // identifier
    private static int COUNTER = 0;
    public String format;
    public String subtitlesFileName;
    public String name;
    public float length;

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
        this.length = length;
    }

    // TODO: Add a logic function (MUST)

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
        return "Content{" +
                "ID=" + ID +
                ", format='" + format + '\'' +
                ", subtitlesFileName='" + subtitlesFileName + '\'' +
                ", name='" + name + '\'' +
                ", length=" + length +
                '}';
    }
}
