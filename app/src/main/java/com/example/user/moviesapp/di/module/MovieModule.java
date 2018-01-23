package com.example.user.moviesapp.di.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.user.moviesapp.api.ApiService;
import com.example.user.moviesapp.di.scope.PerActivity;
import com.example.user.moviesapp.mvp.model.MovieResponse;
import com.example.user.moviesapp.mvp.view.MainView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by USER on 1/20/2018.
 */
@Module
public class MovieModule {

    private MainView mView;

    public MovieModule(MainView view) {
        mView = view;
    }

    @PerActivity
    @Provides
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }


    @PerActivity
    @Provides
    MainView provideView() {
        return mView;
    }
}
