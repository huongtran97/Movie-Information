package algonquin.cst2335.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class FavoriteDetailsFragment extends Fragment {
    MovieSQLite db;
    MovieDetails chosenMovie;
    ArrayList<MovieDetails> movieFavoriteList;
    RecyclerView listMovie;




    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View details_movie = inflater.inflate(R.layout.movie_details_layout, container, false);
        listMovie = details_movie.findViewById(R.id.rec);
        db = new MovieSQLite(getContext());


        TextView details_movie_name = details_movie.findViewById(R.id.movie_details_name);
        TextView details_movie_rating = details_movie.findViewById(R.id.movie_details_rating);
        TextView details_movie_plot = details_movie.findViewById(R.id.movie_details_plot);

        details_movie_name.setText("Movie: " + chosenMovie.getMovieTitle());
        details_movie_rating.setText("Year: " + chosenMovie.getYear());
        details_movie_plot.setText("Plot: " + chosenMovie.getMoviePlot());


        ImageButton details_movie_closedBtn = details_movie.findViewById(R.id.movie_details_closeBtn);
        details_movie_closedBtn.setOnClickListener(closeClicked -> {
            getParentFragmentManager().beginTransaction().remove(this).commit();
        });


        ImageButton details_movie_deletedBtn = details_movie.findViewById(R.id.movie_details_deleteBtn);
        details_movie_deletedBtn.setOnClickListener(deleteClicked -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Do you want to delete this movie? " + chosenMovie.getMovieTitle())
                    .setTitle("Are you sure? ")
                    .setNegativeButton("Cancel", (dgl, cl) -> {
                    })
                    .setPositiveButton("Delete", (new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d("name", "a");
                           db.deleteItem(chosenMovie.getMovieTitle());
                            Intent intent = new Intent(getContext(),FavoriteMovie.class);
                            startActivity(intent);

                        }


                    }))
                    .create()
                    .show();

        });
        return details_movie;
    }


    /**
     *
     * @param movie

     */
//    public FavoriteDetailsFragment(MovieDetails movie, int position) {
//        chosenMovie = movie;
//        chosenPosition = position;
//    }
    public FavoriteDetailsFragment(MovieDetails movie) {
        chosenMovie = movie;

    }
}