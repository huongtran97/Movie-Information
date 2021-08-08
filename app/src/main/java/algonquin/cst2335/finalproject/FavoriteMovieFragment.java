package algonquin.cst2335.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;


public class FavoriteMovieFragment extends Fragment {
    MovieSQLite dataMovieFavorite;
    List<MovieDetails> movieFavoriteList = new ArrayList<>();
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    SQLiteDatabase db;
    ImageButton deleteBtn;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View movie_saved_layout = inflater.inflate(R.layout.movie_saved,container,false);

        movieFavoriteList = new ArrayList<>();
        dataMovieFavorite = new MovieSQLite(getContext());
        movieFavoriteList.addAll(dataMovieFavorite.getAllFavoriteMovie());
        movieAdapter = new MovieAdapter(getContext(), movieFavoriteList);
        recyclerView = movie_saved_layout.findViewById(R.id.rec);

        db = dataMovieFavorite.getWritableDatabase();
        db.rawQuery("SELECT * FROM " + MovieSQLite.TABLE_NAME + ";",null );
        Cursor results = db.rawQuery("SELECT * FROM " + MovieSQLite.TABLE_NAME + ";", null);

        int _idCOL = results.getColumnIndex("_id");
        int nameCOL = results.getColumnIndex(MovieSQLite.COL_MOVIE_NAME);
        int yearCOL = results.getColumnIndex(MovieSQLite.COL_YEAR);
        int plotCOl = results.getColumnIndex(MovieSQLite.COL_PLOT);
        int actorsCOL = results.getColumnIndex(MovieSQLite.COL_ACTORS);
        int posterCOL = results.getColumnIndex(MovieSQLite.COL_POSTER);
        int ratingCOL = results.getColumnIndex(MovieSQLite.COL_RATING);

        while(results.moveToNext()) {
            int id = results.getInt(_idCOL);
            String nameOfMovie = results.getString(nameCOL);
            String yearOfMovie = results.getString(yearCOL);
            String plotOfMovie = results.getString(plotCOl);
            String actorsOfMovie = results.getString(actorsCOL);
            String posterOfMovie = results.getString(posterCOL);
            String ratingOfMovie = results.getString(ratingCOL);
            movieFavoriteList.add(new MovieDetails(id,nameOfMovie,yearOfMovie, actorsOfMovie,posterOfMovie,ratingOfMovie ,plotOfMovie));
        }
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        return movie_saved_layout;
    }

    /**
     *
     * @param chosenMovie
     * @param chosenPosition
     */
    public void notifyMovieDeleted(MovieDetails chosenMovie, int chosenPosition) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want to delete this movie? " + chosenMovie.getMovieTitle())
                .setTitle("Are you sure? ")
                .setNegativeButton("Cancel", (dgl,cl) -> {})
                .setPositiveButton("Delete", (dlg, cl) -> {
                    MovieDetails removeMovie = movieFavoriteList.get(chosenPosition);
                    movieFavoriteList.remove(chosenPosition);
                    db.delete(MovieSQLite.TABLE_NAME,"_id=?" , new String[] {
                            String.valueOf(removeMovie.getId())
                    }) ;
                     Long.toString(removeMovie.getId());

                    Snackbar.make(deleteBtn,"You deleted movie #" + chosenPosition, Snackbar.LENGTH_LONG)
                            .setAction("Undo", clk -> {
                                movieFavoriteList.add(chosenPosition,removeMovie);
                                movieAdapter.notifyItemRemoved(chosenPosition);
                                db.execSQL("INSERT INTO " + MovieSQLite.TABLE_NAME + " VALUES('" + removeMovie.getId() +
                                        "','" + removeMovie.getMovieTitle() +
                                        "','" + removeMovie.getYear() +
                                        "','" + removeMovie.getMoviePlot() +
                                        "')");
                            })
                            .show();
                })
                .create()
                .show();
    }




    public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

        private Context mContext;
        private  List<MovieDetails> mData;

        /**
         *
         * @param position
         * @return
         */
        public int getItemViewType(int position) {
            MovieDetails thisRow =  mData.get(position);
            return thisRow.getId();
        }

        /**
         *
         * @param mContext
         * @param mData
         */
        public MovieAdapter(Context mContext, List<MovieDetails> mData) {
            this.mContext = mContext;
            this.mData = mData;

        }

        /**
         *
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public MovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v;

            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(R.layout.movie_list,parent, false);
            return  new MovieAdapter.MyViewHolder(v);

        }

        /**
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(MovieAdapter.MyViewHolder holder, int position) {


            holder.movie_Name.setText(mData.get(position).getMovieTitle());
            holder.movie_Rating.setText(mData.get(position).getMovieRating());
            holder.movie_Year.setText(mData.get(position).getYear());
//            holder.movie_Actors.setText(mData.get(position).getMainActor());
            holder.movie_Plot.setText(mData.get(position).getMainActor());
            holder.itemView.setOnClickListener(click -> {
                FavoriteMovie parentActivity = ( FavoriteMovie) getContext();
                parentActivity.userCLickedItem(movieFavoriteList.get(position),position);
            });
//        Log.d("a", mData.get(position).getMovieTitle());

            //Using glide to display the poster
            Glide.with(mContext).load(mData.get(position).getPoster()).into(holder.movie_Poster);


        }

        /**
         *
         * @return
         */
        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            List<MovieDetails> movieFavoriteList = new ArrayList<>();
            TextView movie_Name;
            TextView movie_Rating;
            TextView movie_Year;
//            TextView movie_Actors;
            TextView movie_Plot;
            ImageView movie_Poster;




            /**
             *
             * @param itemView
             */
            public MyViewHolder(View itemView) {
                super(itemView);



                movie_Name = itemView.findViewById(R.id.movie_recyclerview_name);
                movie_Rating = itemView.findViewById(R.id.movie_recyclerview_rating);
                movie_Year = itemView.findViewById(R.id.movie_recyclerview_year);
//                movie_Actors = itemView.findViewById(R.id.movie_recyclerview_actors);
                movie_Poster = itemView.findViewById(R.id.movie_recyclerview_poster);
                movie_Plot = itemView.findViewById(R.id.movie_recyclerview_plot);
            }

        }

    }
    public static class MovieDetails{
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
}
