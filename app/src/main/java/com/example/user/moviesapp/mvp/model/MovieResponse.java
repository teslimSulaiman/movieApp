package com.example.user.moviesapp.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by USER on 1/20/2018.
 */

public class MovieResponse {

    private int page;

    @SerializedName("results")
    private List<Movie> movies;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
