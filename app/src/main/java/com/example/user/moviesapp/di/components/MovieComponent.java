package com.example.user.moviesapp.di.components;

import android.content.SharedPreferences;


import com.example.user.moviesapp.modules.home.MainActivity;
import com.example.user.moviesapp.di.module.MovieModule;
import com.example.user.moviesapp.di.scope.PerActivity;


import dagger.Component;

/**
 * Created by USER on 1/20/2018.
 */

@PerActivity
@Component(modules ={MovieModule.class}, dependencies = ApplicationComponent.class)
public interface MovieComponent {

    void inject(MainActivity activity);

}
