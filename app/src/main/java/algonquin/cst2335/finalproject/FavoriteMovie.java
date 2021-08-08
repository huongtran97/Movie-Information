package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovie extends AppCompatActivity {

//    MovieSQLite dataMovieFavorite;
//    List<MovieDetails> movieFavoriteList;
//    RecyclerView recyclerView;
//    MovieAdapter movieAdapter;
        FavoriteMovieFragment movieFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_empty_layout);

        FavoriteMovieFragment movieFragment = new FavoriteMovieFragment();
        FragmentManager fMgr = getSupportFragmentManager();
        FragmentTransaction tx = fMgr.beginTransaction();
        tx.add(R.id.fragmentMovie, movieFragment);
        tx.commit();


//        movieFavoriteList = new ArrayList<>();
//        dataMovieFavorite = new MovieSQLite(getApplicationContext());
//        movieFavoriteList.addAll(dataMovieFavorite.getAllFavoriteMovie());
//        recyclerView = findViewById(R.id.rec);
//        movieAdapter = new MovieAdapter(getApplicationContext(), movieFavoriteList);
//        recyclerView.setAdapter(movieAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void userCLickedItem(MovieDetails movie, int position) {
        FavoriteDetailsFragment favoriteDetailsFragment = new FavoriteDetailsFragment(movie, position);
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentMovie, favoriteDetailsFragment).commit();
    }

    public void notifyMovieItemDeleted(MovieDetails chosenMovie, int chosenPosition) {
        movieFragment.notifyMovieDeleted(chosenMovie,chosenPosition);
    }

}

