public class ModelCreateContent {
    private String contentType;
    private String format;
    private String subtitlesFileName;
    private String name;
    private String length;

    public ModelCreateContent(String contentType, String format, String subtitlesFileName, String name, String length) {
     this.contentType = contentType;
     this.format = format;
     this.subtitlesFileName = subtitlesFileName;
     this.name = name;
     this.length = length;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSubtitlesFileName() {
        return subtitlesFileName;
    }

    public void setSubtitlesFileName(String subtitlesFileName) {
        this.subtitlesFileName = subtitlesFileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
   }