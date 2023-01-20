package com.voitov.movieskinopoisk;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavouriteMoviesViewModel extends AndroidViewModel {
    private final MovieDao movieDao;

    public FavouriteMoviesViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDatabase.getInstance(application).getMovieDao();
    }

    public LiveData<List<Movie>> getMovies() {
        return movieDao.getAllFavouriteMovies();
    }
}
