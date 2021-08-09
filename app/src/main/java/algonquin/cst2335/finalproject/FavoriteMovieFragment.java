package algonquin.cst2335.finalproject;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * @author Ngoc Que Huong Tran
 * @version 1.0
 */
public class FavoriteMovieFragment extends Fragment  {
    MovieSQLite dataMovieFavorite;
    ArrayList<MovieDetails> movieFavoriteList;
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    SQLiteDatabase db;

    /**
     * Write data from SQLite to RecyclerView when save button is pressed
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
        movieAdapter = new MovieAdapter(getContext(), movieFavoriteList);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        return movie_saved_layout;
    }


}
