package algonquin.cst2335.finalproject;

public class MovieDetails{
    int id;
    String movieTitle;
    String year;
    String mainActor;
    String poster;
    String movieRating;
    String moviePlot;
    String runtime;




    public MovieDetails(int id, String movieTitle, String year, String mainActor, String poster, String movieRating, String moviePlot) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.year = year;
        this.mainActor = mainActor;
        this.poster = poster;
        this.movieRating = movieRating;
        this.moviePlot = moviePlot;
    }
    public void setId(int l){ id = l;}
    public int getId(){ return id;}


    public MovieDetails(String s) {
        s = s;
    }

    public MovieDetails() {
        this.movieTitle = movieTitle;
        this.year = year;
        this.mainActor = mainActor;
        this.poster = poster;
        this.movieRating = movieRating;
        this.moviePlot = moviePlot;
        this.runtime = runtime;
    }


    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMainActor() {
        return mainActor;
    }

    public void setMainActor(String mainActor) {
        this.mainActor = mainActor;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getMoviePlot() { return moviePlot; }

    public void setMoviePlot(String moviePlot) {
        this.moviePlot = moviePlot;
    }

    public String getRuntime() { return runtime; }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }
}
