package algonquin.cst2335.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class FindMovie extends AppCompatActivity {
    String stringURL;
    Bitmap image = null;
    List<MovieDetails> movieDetailsList;
    TextView name;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_layout);

        name = findViewById(R.id.nameLogin);
        EditText movieType =findViewById(R.id.movie_search);
        ImageButton searchMovieBtn = findViewById(R.id.searchBtn);

        //SharedPreferences to get data type from login page
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPreferences.edit();

        //Insert data type from login page to find movie page
        String nameLogin = mPreferences.getString(getString(R.string.name), "");
        name.setText("Hello " + nameLogin);

        Toolbar myToolbar = findViewById(R.id.movie_toolbar);
        setSupportActionBar(myToolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.popout_menu);
        navigationView.setNavigationItemSelectedListener((item) ->{

            onOptionsItemSelected(item);
            drawer.closeDrawer(GravityCompat.START);

            return false;
        });



        searchMovieBtn.setOnClickListener((clk) -> {

            String movieName = movieType.getText().toString();

            Executor newThread = Executors.newSingleThreadExecutor();
            newThread.execute(() -> {

                try{

                    stringURL = "http://www.omdbapi.com/?apikey=6c9862c2&rxml=&t="
                            + URLEncoder.encode(movieName, "UTF-8");

                    URL url = new URL(stringURL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    String text = (new BufferedReader(
                            new InputStreamReader(in, StandardCharsets.UTF_8)))
                            .lines()
                            .collect(Collectors.joining("\n"));

                    JSONObject theDocument = new JSONObject(text);
                    JSONArray ratingArray = theDocument.getJSONArray("Ratings");
                    JSONObject position0 = ratingArray.getJSONObject(0);
                    String value = position0.getString("Value");
                    String movie_title = theDocument.getString("Title");
                    String year_movie = theDocument.getString("Year");
                    String runtime_movie = theDocument.getString("Runtime");
                    String actors = theDocument.getString("Actors");
                    String plot_movie = theDocument.getString("Plot");
                    String poster = theDocument.getString("Poster");

                    File file = new File(getFilesDir(),poster);
                    if(file.exists()){
                        image = BitmapFactory.decodeFile(getFilesDir() + poster);
                    } else {
                        URL imgUrl = new URL(poster );
                        HttpURLConnection connection = (HttpURLConnection) imgUrl.openConnection();
                        connection.connect();
                        int responseCode = connection.getResponseCode();


                        //check image
                        if (responseCode != 200) {
                            image = BitmapFactory.decodeStream(connection.getInputStream());
                            image.compress(Bitmap.CompressFormat.JPEG,100, openFileOutput(poster, Activity.MODE_PRIVATE));

                        }
                    }

                    runOnUiThread(() -> {


                        TextView  movie = findViewById(R.id.movie_Name);
                        movie.setText("Movie: "+ movie_title);
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
        });
    }


}
