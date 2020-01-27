package com.example.flixster.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter","onCreateViewHolder");

        View movieView =LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
        //View movieView =LayoutInflater.from(context).inflate(R.layout.item_high,parent,false);

        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter","onBindViewHolder");

        //get the movie at position
        Movie movie=movies.get(position);
        //bind the movie data into the view holder
        holder.bind(movie);


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvOverview=itemView.findViewById(R.id.tvOverview);
            container=itemView.findViewById(R.id.container);
            ivPoster=itemView.findViewById(R.id.ivPoster);


        }

        public void bind(final Movie movie) { //edit for ratings here
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            //image in landscape then this should be backdrop image
            if(context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE ){
                imageUrl=movie.getBackdropPath();
            }
            else {
                imageUrl=movie.getPosterPath();

            }
            Glide.with(context).load(imageUrl).into(ivPoster);
             //register click listener on all movie components
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //move to new activity
                    Intent i= new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);

                }
            });

        }
    }
}
