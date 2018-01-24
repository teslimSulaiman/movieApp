package com.example.user.moviesapp.modules.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import java.util.Calendar;
import com.example.user.moviesapp.R;
import com.example.user.moviesapp.Utility;
import com.example.user.moviesapp.base.BaseActivity;
import com.example.user.moviesapp.di.components.DaggerMovieComponent;
import com.example.user.moviesapp.di.module.MovieModule;

import com.example.user.moviesapp.modules.details.DetailActivity;
import com.example.user.moviesapp.modules.home.adapter.MovieAdapter;
import com.example.user.moviesapp.mvp.model.Movie;

import com.example.user.moviesapp.mvp.presenter.MoviePresenter;
import com.example.user.moviesapp.mvp.view.BaseView;
import com.example.user.moviesapp.mvp.view.MainView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements MainView, EndlessScrollListener.ScrollToBottomListener {

    public static final String LOG = MainActivity.class.getSimpleName();
    @Bind(R.id.movie_list) protected RecyclerView movieRecyclerView;
    private MovieAdapter movieAdapter;
    @Inject
    protected MoviePresenter mPresenter;
    private EndlessScrollListener endlessScrollListener;

    private int pageNumber =1;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private String dateChosen;



    @Override
    protected void onViewReady(Bundle savedInstantState, Intent intent){
        super.onViewReady(savedInstantState, intent);

        initializeList();
        if(Utility.isNetworkAvailable(this)){
            mPresenter.getLoadedMovies("",pageNumber, Utility.LOAD_MOVIE);
        }else {
            onShowToast(getString(R.string.no_internet_connection));
        }

    }

    private void initializeList() {
        movieRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        movieRecyclerView.setLayoutManager(layoutManager);
        endlessScrollListener = new EndlessScrollListener(layoutManager, this);
        movieAdapter = new MovieAdapter(getLayoutInflater());
        movieAdapter.setMovieClickListener(movieClickListener);
        movieRecyclerView.setAdapter(movieAdapter);
        movieRecyclerView.addOnScrollListener(endlessScrollListener);

    }
    @Override
    protected void resolveDaggerDependency() {
        DaggerMovieComponent.builder()
                .applicationComponent(getApplicationComponent())
                .movieModule(new MovieModule(this))

                .build().inject(this);
    }

    private MovieAdapter.OnMovieClickListener movieClickListener = new MovieAdapter.OnMovieClickListener() {
        @Override
        public void onClick(View v, Movie movie, int position) {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.MOVIE, movie);
            Toast.makeText(MainActivity.this, movie.getTitle(), Toast.LENGTH_SHORT).show();
            startActivity(intent);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.date_setting) {
            alertDatePicker();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void alertDatePicker() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        onClearItems();
                        dateChosen = mPresenter.formatDate(i,i1,i2);
                        pageNumber =1;
                        mPresenter.getLoadedMovies(dateChosen, pageNumber,Utility.LOAD_MOVIE);
                        endlessScrollListener.onRefresh();
                    }
                } ,year, month, day);
            datePickerDialog.show();

    }

    @Override
    public void onScrollToBottom() {
        pageNumber++;
        mPresenter.getLoadedMovies(dateChosen,pageNumber,Utility.LOAD_MORE);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }



    @Override
    public void onMovieLoaded(List<Movie> movies) {
        movieAdapter.addMovies(movies);
    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }

    @Override
    public void onShowToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClearItems() {
        movieAdapter.clearMovies();
    }

    @Override
    public void onLoadMore(String message) {
        showDialog(message);
    }
}
