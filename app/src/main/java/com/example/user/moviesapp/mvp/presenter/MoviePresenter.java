package com.example.user.moviesapp.mvp.presenter;

import android.content.SharedPreferences;

import com.example.user.moviesapp.BuildConfig;
import com.example.user.moviesapp.api.ApiService;
import com.example.user.moviesapp.base.BasePresenter;
import com.example.user.moviesapp.mvp.model.Movie;
import com.example.user.moviesapp.mvp.model.MovieResponse;
import com.example.user.moviesapp.mvp.view.MainView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

/**
 * Created by USER on 1/20/2018.
 */

public class MoviePresenter extends BasePresenter<MainView>  implements Observer<MovieResponse> {

    @Inject
    protected ApiService apiService;

    @Inject
    public  MoviePresenter(){

    }
    public void getLoadedMovies(String releaseDate, int page, String message) {

        getView().onShowDialog(message);
        Observable<MovieResponse> moviesResponseObservable = apiService.getMovies(BuildConfig.MOVIE_API_KEY, releaseDate, page);
        subscribe(moviesResponseObservable, this);
    }

    @Override
    public void onCompleted() {
        getView().onHideDialog();
        getView().onShowToast("Movies loading complete!");
    }

    public void alertDatePicker(){
        getView().onShowToast("Movies loading complete!");
    }

    @Override
    public void onError(Throwable e) {
        getView().onHideDialog();
        getView().onShowToast("Error loading movies " + e.getMessage());
    }

    @Override
    public void onNext(MovieResponse movieResponse) {
        List<Movie> movies = movieResponse.getMovies();
        getView().onMovieLoaded(movies);
    }

    public String formatDate(int year, int month, int day){
        String dateChosen ;
        String dateValue = String.format("%02d", day);
        String monthValue = String.format("%02d", month+1);
        dateChosen = year+"-"+monthValue+"-"+dateValue;
        return dateChosen;
    }

}
