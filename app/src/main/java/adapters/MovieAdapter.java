package adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.List;

import models.Movie;
import samantha.stewart.flixster.DetailActivity;
import samantha.stewart.flixster.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies)
    {
        this.context = context;
        this.movies =movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie,parent, false);

        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Movie movie = movies.get(position);
        holder.bind(movie);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView tvPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview  = itemView.findViewById(R.id.tvOverview);
            tvPoster = itemView.findViewById(R.id.tvPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Movie movie) {

            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverView());
            String imageUrl;
            //if phone is in landscape view
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //then imageUrl = backdrop image
                imageUrl = movie.getBackdropPath();
            } else {
                //else imageUr; = poster image
                imageUrl = movie.getPoster_Path();
            }
            Glide.with(context).load(imageUrl).into(tvPoster);



            // 1. register click listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 2. navigate to a new activity when tapped
                    //Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show(); no longer needed
                    Intent i = new Intent(context, DetailActivity.class);
                    // putting the movie data on the empty activity
                    //i.putExtra("title", movie.getTitle());
                    i.putExtra("movie", Parcels.wrap(movie));

                    context.startActivity(i);
                }
            });

        }
    }
}
