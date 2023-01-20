package com.voitov.movieskinopoisk;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailsViewModel extends AndroidViewModel {
    public static final String TAG = "MovieDetailsViewModel";
    private final MutableLiveData<MovieDetailsResponse> movieResponse = new MutableLiveData<>();
    private final MutableLiveData<List<Review>> reviewsLD = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MovieDao movieDao;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDatabase.getInstance(application).getMovieDao();
    }

    public LiveData<MovieDetailsResponse> getMovieResponse() {
        return movieResponse;
    }

    public LiveData<List<Review>> getReviewsLD() {
        return reviewsLD;
    }

    public LiveData<Movie> getFavouriteMovie(int movieId) {
        return movieDao.getMovie(movieId);
    }

    public void remove(int movieId) {
        Disposable disposable = movieDao.removeMovie(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public void insert(Movie movie) {
        Disposable disposable = movieDao.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public void loadMovieDetailsInfo(int movieId) {
        Disposable disposable = ApiFactory.getInstance().loadTrailers(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieDetailsResponse>() {
                    @Override
                    public void accept(MovieDetailsResponse movieDetailsResponse) throws Throwable {
                        Log.d(TAG, movieDetailsResponse.toString());
                        movieResponse.setValue(movieDetailsResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadReviews(int movieId) {
        Disposable disposable = ApiFactory.getInstance().loadReviews(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ReviewResponse, List<Review>>() {
                    @Override
                    public List<Review> apply(ReviewResponse reviewResponse) throws Throwable {
                        return reviewResponse.getReviews();
                    }
                })
                .subscribe(new Consumer<List<Review>>() {
                    @Override
                    public void accept(List<Review> reviews) throws Throwable {
                        reviewsLD.setValue(reviews);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
