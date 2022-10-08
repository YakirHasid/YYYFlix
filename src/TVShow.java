public class TVShow extends Content {
    // fields
    public int season;
    public int episode;

    // public constructor that uses the super constructor first
    public TVShow(String format, String subtitlesFileName, String name, float length, int season, int episode) {
        super(format, subtitlesFileName, name, length);
        this.season = season;
        this.episode = episode;
    }

    public int getSeason() {
        return season;
    }
    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return episode;
    }
    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public boolean isPilot() {
        return (season==1 && episode==1);
    }

    /**
     * toString method of the TVShow
     * @return a string that represents the TVShow
     */
    @Override
    public String toString() {
        return "TVShow Details: \n" +
                "Season: " + this.season + "\n" +
                "Episode: " + this.episode + "\n" +
                super.toString();
    }
}
