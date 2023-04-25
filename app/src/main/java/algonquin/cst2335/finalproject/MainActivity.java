package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView movieBtn = findViewById(R.id.moviebtn);
        Log.w("MainActivity", "In onCreate() - Load Widgets");

        movieBtn.setOnClickListener(click -> {
            Intent moviePage = new Intent(MainActivity.this, MovieLogin.class);
            startActivity(moviePage);
        });
    }
}




