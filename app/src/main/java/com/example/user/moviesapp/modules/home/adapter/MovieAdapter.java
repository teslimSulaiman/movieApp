package com.example.user.moviesapp.modules.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.moviesapp.R;
import com.example.user.moviesapp.Utility;
import com.example.user.moviesapp.mvp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by USER on 1/21/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.Holder> {

    private LayoutInflater mLayoutInflater;
    private List<Movie> movieList = new ArrayList<>();

    public MovieAdapter(LayoutInflater inflater) {
        mLayoutInflater = inflater;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_items, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void addMovies(List<Movie> movies) {
        movieList.addAll(movies);
        notifyDataSetChanged();
    }

    public void clearMovies() {
        movieList.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.image)
        protected ImageView movieIcon;


        private Context mContext;
        private Movie mMovie;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bind(Movie movie) {
            mMovie = movie;

            Picasso.with(mContext).load(Utility.formatMovieImagePath(mMovie.getPosterPath()))
             .into(movieIcon);
        }

        @Override
        public void onClick(View v) {
            if (movieClickListener != null) {
                movieClickListener.onClick(movieIcon, mMovie, getAdapterPosition());
            }
        }
    }

    public void setMovieClickListener(OnMovieClickListener listener) {
        movieClickListener = listener;
    }

    private OnMovieClickListener movieClickListener;

    public interface OnMovieClickListener {

        void onClick(View v, Movie movie, int position);
    }
}