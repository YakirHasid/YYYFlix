public class Movie extends Content {
    // fields
    public String director;

    /**
     * public constructor that uses the super constructor first
     * @param format represents the format of the content
     * @param subtitlesFileName represents the subtitles filename of the content
     * @param name represents the name of the content
     * @param length represents the length of the content
     * @param director represents the director of the content
     */
    public Movie(String format, String subtitlesFileName, String name, float length, String director) {
        super(format, subtitlesFileName, name, length);
        this.director = director;
    }

    // director getter
    public String getDirector() {
        return director;
    }

    // director setter
    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "director='" + director + '\'' +
                "} " + super.toString();
    }
}
