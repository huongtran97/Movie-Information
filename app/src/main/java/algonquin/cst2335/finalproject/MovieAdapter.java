package algonquin.cst2335.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;



public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<MovieDetails> mData;


    /**
     *
     * @param position
     * @return
     */
    public int getItemViewType(int position) {
        MovieDetails thisRow =  mData.get(position);
        return thisRow.getId();
    }

    /**
     *  @param mContext
     * @param mData*/
    public MovieAdapter(Context mContext, ArrayList<MovieDetails> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.movie_list,parent, false);
        return  new MovieAdapter.MyViewHolder(v);

    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MovieAdapter.MyViewHolder holder, int position) {


        holder.movie_Name.setText(mData.get(position).getMovieTitle());
        holder.movie_Rating.setText(mData.get(position).getMovieRating());
        holder.movie_Year.setText(mData.get(position).getYear());
//            holder.movie_Actors.setText(mData.get(position).getMainActor());
        holder.movie_Plot.setText(mData.get(position).getMainActor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoriteMovie parentActivity = ( FavoriteMovie) view.getContext();
                parentActivity.userCLickedItem(mData.get(position),position);

            }
        });


        //Using glide to display the poster
        Glide.with(mContext).load(mData.get(position).getPoster()).into(holder.movie_Poster);


    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        List<MovieDetails> movieFavoriteList = new ArrayList<>();
        TextView movie_Name;
        TextView movie_Rating;
        TextView movie_Year;
        //            TextView movie_Actors;
        TextView movie_Plot;
        ImageView movie_Poster;




        /**
         *
         * @param itemView
         */
        public MyViewHolder(View itemView)  {
            super(itemView);



            movie_Name = itemView.findViewById(R.id.movie_recyclerview_name);
            movie_Rating = itemView.findViewById(R.id.movie_recyclerview_rating);
            movie_Year = itemView.findViewById(R.id.movie_recyclerview_year);
//                movie_Actors = itemView.findViewById(R.id.movie_recyclerview_actors);
            movie_Poster = itemView.findViewById(R.id.movie_recyclerview_poster);
            movie_Plot = itemView.findViewById(R.id.movie_recyclerview_plot);
        }


    }

}