package algonquin.cst2335.finalproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author Ngoc Que Huong Tran
 * @version 1.0
 */
public class FindMovie extends AppCompatActivity {
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "password";
    SharedPreferences sharedPreferences;
    MovieSQLite dataMovieFavorite;
    String stringURL;
    Bitmap image = null;
    TextView name;
    String movieName;
    String movie_title="";
    String value="";
    String year_movie="";
    String actors="";
    String runtime_movie="";
    String plot_movie="";
    String poster="";

    /**
     * Item popup menu, assign an activity to the menu by click
     * @param item
     * @return
     */
    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        TextView  movieItem = findViewById(R.id.movie_Name);
        TextView ratingItem = findViewById(R.id.movie_ValueRating);
        TextView actorItem = findViewById(R.id.movie_Actor);
        TextView yrItem = findViewById(R.id.movie_Year);
        TextView rtItem = findViewById(R.id.movie_Runtime);
        TextView plItem = findViewById(R.id.movie_Plot);
        ImageView imItem = findViewById(R.id.movie_Image);
        EditText movieTextField = findViewById(R.id.movie_search);
        ImageButton saveBtnItem = findViewById(R.id.saveBtn);

        switch (item.getItemId()) {
            case R.id.save:
                Intent movieFavorite = new Intent(FindMovie.this, FavoriteMovie.class);
                startActivity(movieFavorite);
                break;
            case R.id.clean:
                movieItem.setVisibility(View.INVISIBLE);
                ratingItem.setVisibility(View.INVISIBLE);
                actorItem.setVisibility(View.INVISIBLE);
                yrItem.setVisibility(View.INVISIBLE);
                rtItem.setVisibility(View.INVISIBLE);
                plItem.setVisibility(View.INVISIBLE);
                imItem.setVisibility(View.INVISIBLE);
                saveBtnItem.setVisibility(View.INVISIBLE);
                movieTextField.setText("");
                break;
            case 5:
                String movieName = item.getTitle().toString();
                runSearchBtn(movieName);
                break;


        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * Create menu options
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    /**
     * Get data user by share preferences
     * @param savedInstanceState
     */
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_layout);

        dataMovieFavorite = new MovieSQLite(getApplicationContext());
        dataMovieFavorite.createTable();

        name = findViewById(R.id.nameLogin);
        EditText movieType = findViewById(R.id.movie_search);
        ImageButton searchMovieBtn = findViewById(R.id.searchBtn);

        //SharedPreferences to get data type from login page
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = mPreferences.edit();

        //Insert data type from login page to find movie page
        String nameLogin = mPreferences.getString(getString(R.string.name), "");
        name.setText("Hi " + nameLogin);



        Toolbar myToolbar = findViewById(R.id.movie_toolbar);
        setSupportActionBar(myToolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.popout_menu);
        navigationView.setNavigationItemSelectedListener((item) -> {
            onOptionsItemSelected(item);
            drawer.closeDrawer(GravityCompat.START);
            return false;
        });


        searchMovieBtn.setOnClickListener((click) -> {
            movieName = movieType.getText().toString();
            myToolbar.getMenu().add(1,5,10,movieName).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            runSearchBtn(movieName);
        } );

    }

    /**
     * Method to get address parameter and display poster from JSON data using Bitmap when doing movie info search
     * @param movieTitle
     * @param posters
     * @return
     */
       public Bitmap getImage(String movieTitle, String posters ) {
        Bitmap img = null;
           try{
               Log.d("abc", posters);
                File file = new File(getFilesDir(),posters);
                    if(file.exists()){
                        img = BitmapFactory.decodeFile(getFilesDir() + posters);
                    } else {
                        URL imgUrl = new URL(posters );
                        HttpURLConnection connection = (HttpURLConnection) imgUrl.openConnection();
                        connection.connect();
                        int responseCode = connection.getResponseCode();

                        if (responseCode == 200) {
                            img = BitmapFactory.decodeStream(connection.getInputStream());
                            String filename = movieTitle + posters.substring(posters.length()-4);
                            img.compress(Bitmap.CompressFormat.JPEG,100, openFileOutput(filename, Activity.MODE_PRIVATE));

                        }
                    }
           } catch (IOException e) {
               e.printStackTrace();
           }
           return img;
       }

    /**
     * Get JSON data and display on screen by click
     */

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void runSearchBtn(String movieName) {

        Executor newThread = Executors.newSingleThreadExecutor();
            newThread.execute(() -> {

                try{
                    //Connect to the server
                    stringURL = "https://www.omdbapi.com/?apikey=6c9862c2&rxml=&t="
                            + URLEncoder.encode(movieName, "UTF-8");

                    URL url = new URL(stringURL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    String text = (new BufferedReader(
                            new InputStreamReader(in, StandardCharsets.UTF_8)))
                            .lines()
                            .collect(Collectors.joining("\n"));

                    //Get JSON data from the server
                    JSONObject theDocument = new JSONObject(text);
                    JSONArray ratingArray = theDocument.getJSONArray("Ratings");
                    JSONObject position0 = ratingArray.getJSONObject(0);
                     value = position0.getString("Value");
                     movie_title = theDocument.getString("Title");
                     year_movie = theDocument.getString("Year");
                     runtime_movie = theDocument.getString("Runtime");
                     actors = theDocument.getString("Actors");
                     plot_movie = theDocument.getString("Plot");
                     poster = theDocument.getString("Poster");

                    image = getImage(movie_title, poster);

                    //Display JSON data using runOnUiThread method
                    runOnUiThread(() -> {

                        TextView  movie = findViewById(R.id.movie_Name);
                        movie.setText(movie_title);
                        movie.setVisibility(View.VISIBLE);

                        TextView rating = findViewById(R.id.movie_ValueRating);
                        rating.setText("Rating: " + value);
                        rating.setVisibility(View.VISIBLE);

                        TextView actor = findViewById(R.id.movie_Actor);
                        actor.setText("Main actors: " + actors);
                        actor.setVisibility(View.VISIBLE);

                        TextView yr = findViewById(R.id.movie_Year);
                        yr.setText("Year: " + year_movie);
                        yr.setVisibility(View.VISIBLE);

                        TextView rt = findViewById(R.id.movie_Runtime);
                        rt.setText("Runtime: " + runtime_movie);
                        rt.setVisibility(View.VISIBLE);

                        TextView pl = findViewById(R.id.movie_Plot);
                        pl.setText("Plot: " + plot_movie);
                        pl.setVisibility(View.VISIBLE);

                        ImageView im = findViewById(R.id.movie_Image);
                        im.setImageBitmap(image);
                        im.setVisibility(View.VISIBLE);

                    });

             } catch (IOException | JSONException ioException) {
                    ioException.printStackTrace();
                }
            });

            // If the user presses the search button without entering the movie name, Toast() will display on the screen to remind
            if(movieName.equals("")){
                Toast.makeText(FindMovie.this, "Oops, something went wrong!" ,
                        Toast.LENGTH_LONG).show();
            }

            //When the user presses the save button, the JSON data is saved to SQLite and shows the movie in the favorites list, and Toast() to confirm that the user clicked
            ImageButton ib = findViewById(R.id.saveBtn);
            ib.setOnClickListener(view -> {
                dataMovieFavorite.insertMovie(movie_title, value, year_movie, actors, plot_movie, poster);
                Toast.makeText(FindMovie.this, " Your saved it!" , Toast.LENGTH_SHORT).show();
            });
            ib.setVisibility(View.VISIBLE);

        }
    }







