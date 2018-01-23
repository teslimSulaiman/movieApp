package com.example.user.moviesapp.mvp.view;

import com.example.user.moviesapp.mvp.model.Movie;

import java.util.List;

/**
 * Created by USER on 1/20/2018.
 */

public interface MainView extends BaseView {

    void onMovieLoaded(List<Movie> movies);

    void onShowDialog(String message);

    void onHideDialog();

    void onShowToast(String message);

    void onClearItems();

    void onLoadMore(String message);


}
