public class Movie extends Content {
    // fields
    public String director;

    // public constructor that uses the super constructor first
    public Movie(String format, String subtitlesFileName, String name, float length, String director) {
        super(format, subtitlesFileName, name, length);
        this.director = director;
    }

    public String getDirector() {
        return director;
    }

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
