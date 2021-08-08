package algonquin.cst2335.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MovieSQLite extends SQLiteOpenHelper {
    private  Context mContext;
    public static final String name = " TheDatabase";
    public static final int version = 1;
    public static final String TABLE_NAME = "Favorite";
    public static final String COL_MOVIE_NAME = "movieTitle";
    public static final String COL_RATING= "movieRating";
    public static final String COL_YEAR = "year";
    public static final String COL_ACTORS = "mainActor";
    public static final String COL_PLOT = "moviePlot";
    public static final String COL_POSTER = "poster";





    public MovieSQLite(Context context) {
        super(context, name, null, version);
        this.mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase movieDB) {

    }

    public void createTable() {
          String movieSQL = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_MOVIE_NAME + " TEXT,"
                + COL_RATING + " TEXT,"
                + COL_YEAR + " TEXT,"
                + COL_ACTORS + " TEXT,"
                + COL_PLOT + " TEXT,"
                + COL_POSTER + " TEXT)");
          QueryData(movieSQL);

    }


    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


     public Collection<? extends FavoriteMovieFragment.MovieDetails> getAllFavoriteMovie() {

         List<FavoriteMovieFragment.MovieDetails> list = new ArrayList();

         String sql = "SELECT * FROM " + TABLE_NAME;

         Cursor data =  GetData(sql);

         while (data.moveToNext()) {
             int id = data.getInt(0);
             String movieTitle = data.getString(1);
             String movieRating = data.getString(2);
             String year = data.getString(3);
             String mainActor = data.getString(4);
             String moviePlot = data.getString(5);
             String poster = data.getString(6);
             list.add(new FavoriteMovieFragment.MovieDetails(id,movieTitle,movieRating,year,mainActor,moviePlot,poster));
         }

        return list;
     }


    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();

        database.execSQL(sql);
    }


    public void insertMovie(String movieTitle, String movieRating,String year ,String mainActor, String moviePlot, String poster ) {
        String sql = "INSERT INTO  Favorite(movieTitle,movieRating,year,mainActor,moviePlot,poster) VALUES ('" + movieTitle + "','" + movieRating + "','" + year + "','" + mainActor + "','"+ moviePlot + "','" + poster +"')";
        QueryData(sql);

    }

    public void dropTable(String tableName) {
        String sql = "DROP TABLE IF EXIST " + tableName;
        QueryData(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase movieDB, int i, int i1) {


    }
}
