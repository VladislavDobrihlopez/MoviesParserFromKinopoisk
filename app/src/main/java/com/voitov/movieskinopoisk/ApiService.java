package com.voitov.movieskinopoisk;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/movie?field=rating.kp&search=3-10&sortField=votes.kp&sortType=-1&limit=40&token=75ZNPX3-R7P40DV-NEKX12T-Y8EQ0H9")
    public Single<MovieResponse> loadMostPopularMovies(@Query("page") int page);

    @GET("/movie?token=75ZNPX3-R7P40DV-NEKX12T-Y8EQ0H9&field=id")
    public Single<MovieDetailsResponse> loadTrailers(@Query("search") int movieId);

    @GET("/review?token=75ZNPX3-R7P40DV-NEKX12T-Y8EQ0H9&field=movieId")
    public Single<ReviewResponse> loadReviews(@Query("search") int id);
}
