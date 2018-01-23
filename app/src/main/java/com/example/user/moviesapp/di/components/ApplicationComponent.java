package com.example.user.moviesapp.di.components;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.user.moviesapp.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by USER on 1/20/2018.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    Retrofit exposeRetrofit();

}
