package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;



public class FavoriteDetailsFragment extends Fragment {

    MovieDetails chosenMovie;
    int chosenPosition;

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
            FavoriteMovie parentActivity = (FavoriteMovie) getContext();
            parentActivity.notifyMovieItemDeleted(chosenMovie, chosenPosition);
            getParentFragmentManager().beginTransaction().remove(this).commit();
        });

        return details_movie;

    }


    /**
     *
     * @param movie
     * @param position
     */
    public FavoriteDetailsFragment(MovieDetails movie, int position) {
        chosenMovie = movie;
        chosenPosition = position;
    }





}