package com.example.user.moviesapp.modules.details;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.moviesapp.R;
import com.example.user.moviesapp.Utility;
import com.example.user.moviesapp.base.BaseActivity;
import com.example.user.moviesapp.mvp.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by USER on 1/20/2018.
 */

public class DetailActivity extends BaseActivity {
    @Bind(R.id.image) protected ImageView movieImage;
    @Bind(R.id.movie_name) protected TextView movieTitle;
    @Bind(R.id.year_of_release) protected TextView releaseYear;
    @Bind(R.id.rating) protected TextView rating;
    @Bind(R.id.overview) protected TextView overview;

    public static final String MOVIE = "movie";

    @Override
    protected int getContentView() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);


        Movie movie = (Movie) intent.getSerializableExtra(MOVIE);
        setTitle("Movie Detail");

        movieTitle.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        rating.setText((movie.getRating()+ "/10"));
        Picasso.with(this).load(Utility.formatMovieImagePath(movie.getPosterPath()))
                .into(movieImage);


    }

}
