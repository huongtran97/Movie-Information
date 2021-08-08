package algonquin.cst2335.finalproject;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import java.util.List;

import static androidx.appcompat.widget.AppCompatDrawableManager.get;
import static java.security.AccessController.getContext;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context mContext;
    private List<MovieDetails> mData;

    
    /////
    public int getItemViewType(int position) {
       MovieDetails thisRow =  mData.get(position);
       return thisRow.getId();
    }

    public MovieAdapter(Context mContext, List<MovieDetails> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.movie_list,parent, false);
        return  new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(MovieAdapter.MyViewHolder holder, int position) {


        holder.movie_Name.setText(mData.get(position).getMovieTitle());
        holder.movie_Rating.setText(mData.get(position).getMovieRating());
        holder.movie_Year.setText(mData.get(position).getYear());
        holder.movie_Actors.setText(mData.get(position).getMainActor());
//        Log.d("a", mData.get(position).getMovieTitle());

        //Using glide to display the poster
        Glide.with(mContext).load(mData.get(position).getPoster()).into(holder.movie_Poster);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView movie_Name;
        TextView movie_Rating;
        TextView movie_Year;
        TextView movie_Actors;
        ImageView movie_Poster;
//        View clickView;
        int position = -1;


        public MyViewHolder(View itemView) {
            super(itemView);
//            clickView = itemView;
            movie_Name = itemView.findViewById(R.id.movie_recyclerview_name);
            movie_Rating = itemView.findViewById(R.id.movie_recyclerview_rating);
            movie_Year = itemView.findViewById(R.id.movie_recyclerview_year);
            movie_Actors = itemView.findViewById(R.id.movie_recyclerview_actors);
            movie_Poster = itemView.findViewById(R.id.movie_recyclerview_poster);
//           itemView.setOnClickListener(click -> {
//
//               Intent intent = new Intent(clickView.getContext(), ItemMovieDetails.class);
//               clickView.getContext().startActivity(intent);
//            });


        }
    }

}
