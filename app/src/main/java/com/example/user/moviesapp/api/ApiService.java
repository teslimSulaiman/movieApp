package com.example.user.moviesapp.api;

import com.example.user.moviesapp.Utility;
import com.example.user.moviesapp.mvp.model.MovieResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by USER on 1/20/2018.
 */

public interface ApiService {

    @GET("/3/discover/movie")
    Observable<MovieResponse> getMovies(
            @Query(Utility.API_KEY) String key,
            @Query("release_date.lte") String releaseDate,
            @Query("page") int page);
}
