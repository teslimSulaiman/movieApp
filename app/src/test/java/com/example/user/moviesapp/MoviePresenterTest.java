package com.example.user.moviesapp;

import android.os.Looper;

import com.example.user.moviesapp.api.ApiService;
import com.example.user.moviesapp.mvp.model.Movie;
import com.example.user.moviesapp.mvp.model.MovieResponse;
import com.example.user.moviesapp.mvp.presenter.MoviePresenter;
import com.example.user.moviesapp.mvp.view.MainView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by USER on 1/23/2018.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({Observable.class, AndroidSchedulers.class, Looper.class, MovieResponse.class})
public class MoviePresenterTest {

    public static final String TEST_ERROR_MESSAGE = "error_message";

    @InjectMocks
    private MoviePresenter presenter;
    @Mock
    private ApiService mApiService;
    @Mock private Movie movie;
    @Mock private MainView mView;
    @Mock private Observable<MovieResponse> mObservable;

    @Captor
    private ArgumentCaptor<Subscriber<MovieResponse>> captor;

    private final RxJavaSchedulersHook mRxJavaSchedulersHook = new RxJavaSchedulersHook() {
        @Override
        public Scheduler getIOScheduler() {
            return Schedulers.immediate();
        }

        @Override
        public Scheduler getNewThreadScheduler() {
            return Schedulers.immediate();
        }
    };

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie());

    }

    @Test
    public void getMovies() throws Exception {
        PowerMockito.mockStatic(Looper.class);
        when(AndroidSchedulers.mainThread()).thenReturn(mRxJavaSchedulersHook.getComputationScheduler());

        when(mApiService.getMovies(BuildConfig.MOVIE_API_KEY,"",1)).thenReturn(mObservable);
        presenter.getLoadedMovies("",1,"Loading movies....");
        verify(mView, atLeastOnce()).onShowDialog("Loading movies....");
    }

    @Test
    public void onCompleted() throws Exception {
        presenter.onCompleted();
        verify(mView, times(1)).onHideDialog();
        verify(mView, times(1)).onShowToast("Movies loading complete!");
    }

    @Test
    public void onError() throws Exception {
        presenter.onError(new Throwable(TEST_ERROR_MESSAGE));
        verify(mView, times(1)).onHideDialog();
        verify(mView, times(1)).onShowToast("Error loading movies " + TEST_ERROR_MESSAGE);
    }

    @Test
    public void onNext() throws Exception {
        MovieResponse response = mock(MovieResponse.class);
        when(response.getMovies()).thenReturn(new ArrayList<Movie>());
        presenter.onNext(response);

    }

}
