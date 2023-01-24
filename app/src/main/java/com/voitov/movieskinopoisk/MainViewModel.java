package com.voitov.movieskinopoisk;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {
    public static final String TAG = "MainViewModel";
    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> moviesAreLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int page = 1;

    public MainViewModel(@NonNull Application application) {
        super(application);
        loadMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<Boolean> getMoviesAreLoading() {
        return moviesAreLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void loadMovies() {
        if (isLoadingGoingOn()) {
            return;
        }

        Disposable disposable = ApiFactory.getInstance().loadMostPopularMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        moviesAreLoading.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        moviesAreLoading.setValue(false);
                    }
                })
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse response) throws Throwable {
                        page++;
                        List<Movie> loadedMovies = movies.getValue();
                        List<Movie> resultMovies = new ArrayList<>();
                        if (loadedMovies != null) {
                            resultMovies.addAll(loadedMovies);

                        }
                        resultMovies.addAll(response.getMovies());
                        movies.setValue(resultMovies);
                        Log.d(TAG, "loaded page №" + (page - 1));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                        error.setValue(throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    private boolean isLoadingGoingOn() {
        Boolean value = moviesAreLoading.getValue();
        return (value != null) && value;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
