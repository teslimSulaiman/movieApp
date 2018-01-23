package com.example.user.moviesapp.application;

import android.app.Application;

import com.example.user.moviesapp.Utility;
import com.example.user.moviesapp.di.components.ApplicationComponent;
import com.example.user.moviesapp.di.components.DaggerApplicationComponent;
import com.example.user.moviesapp.di.components.MovieComponent;
import com.example.user.moviesapp.di.module.ApplicationModule;


/**
 * Created by USER on 1/19/2018.
 */

public class MovieApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplicationComponent();
    }

    private void initializeApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this, Utility.BASE_URL))

                .build();
    }


    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
