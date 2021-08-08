package algonquin.cst2335.finalproject;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View movie_saved_layout = inflater.inflate(R.layout.movie_saved,container,false);

        movieFavoriteList = new ArrayList<>();
        dataMovieFavorite = new MovieSQLite(getContext());
        movieFavoriteList.addAll(dataMovieFavorite.getAllFavoriteMovie());
        recyclerView = movie_saved_layout.findViewById(R.id.rec);
        movieAdapter = new MovieAdapter(getContext(), movieFavoriteList);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

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
            movieFavoriteList.add(new MovieDetails(id, nameOfMovie, yearOfMovie, actorsOfMovie,posterOfMovie, ratingOfMovie , plotOfMovie));
        }

        return movie_saved_layout;
    }

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
}
