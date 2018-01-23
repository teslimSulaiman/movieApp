package com.example.user.moviesapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by USER on 1/20/2018.
 */

public class Utility {
    public static String BASE_URL = "https://api.themoviedb.org";

    final public static String API_KEY = "api_key";

    final public static String LOAD_MORE ="Load more.......";

    final public static String LOAD_MOVIE ="Loading movies.......";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String formatMovieImagePath(String imagePath) {
        imagePath.replace("\\","");
        final String MOVIE_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
        final String MOVIE_IMAGE_SIZE_OPTION = "w185/";
        return MOVIE_IMAGE_BASE_URL + MOVIE_IMAGE_SIZE_OPTION + imagePath;
    }
}
