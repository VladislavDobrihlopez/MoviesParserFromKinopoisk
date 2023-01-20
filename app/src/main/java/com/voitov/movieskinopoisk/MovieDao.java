package com.voitov.movieskinopoisk;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM favourite_movies")
    public LiveData<List<Movie>> getAllFavouriteMovies();

    @Query("SELECT * FROM favourite_movies WHERE id=:movieId LIMIT 1")
    public LiveData<Movie> getMovie(int movieId);

    @Insert(onConflict = ABORT)
    public Completable insertMovie(Movie movie);

    @Query("DELETE FROM favourite_movies WHERE id=:movieId")
    public Completable removeMovie(int movieId);
}
