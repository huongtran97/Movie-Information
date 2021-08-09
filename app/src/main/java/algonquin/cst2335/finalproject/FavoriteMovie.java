package algonquin.cst2335.finalproject;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author Ngoc Que Huong Tran
 * @version 1.0
 */
public class FavoriteMovie extends AppCompatActivity {

 //       FavoriteMovieFragment movieFragment;
        MovieSQLite db;

    /**
     * Pass data from SQLite to fragment
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_empty_layout);

        db = new MovieSQLite(getApplicationContext());

        FavoriteMovieFragment movieFragment = new FavoriteMovieFragment();
        FragmentManager fMgr = getSupportFragmentManager();
        FragmentTransaction tx = fMgr.beginTransaction();
        tx.add(R.id.fragmentMovie, movieFragment);
        tx.commit();
    }

    /**
     * Initialize the userClickedItem() method for clicking an item in the RecyclerView list
     * @param movie
     * @param position
     */
    public void userCLickedItem(MovieDetails movie, int position) {
        FavoriteDetailsFragment favoriteDetailsFragment = new FavoriteDetailsFragment(movie);
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentMovie, favoriteDetailsFragment).commit();
    }

}

