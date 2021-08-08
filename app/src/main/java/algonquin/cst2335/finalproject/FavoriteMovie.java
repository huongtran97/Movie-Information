package algonquin.cst2335.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovie extends AppCompatActivity {

        FavoriteMovieFragment movieFragment;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_empty_layout);

        FavoriteMovieFragment movieFragment = new FavoriteMovieFragment();
        FragmentManager fMgr = getSupportFragmentManager();
        FragmentTransaction tx = fMgr.beginTransaction();
        tx.add(R.id.fragmentMovie, movieFragment);
        tx.commit();

    }

    /**
     *
     * @param movie
     * @param position
     */
    public void userCLickedItem(FavoriteMovieFragment.MovieDetails movie, int position) {
        FavoriteDetailsFragment favoriteDetailsFragment = new FavoriteDetailsFragment(movie, position);
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentMovie, favoriteDetailsFragment).commit();
    }

    public void notifyMovieItemDeleted(FavoriteMovieFragment.MovieDetails chosenMovie, int chosenPosition) {
        movieFragment.notifyMovieDeleted(chosenMovie,chosenPosition);
    }



}

