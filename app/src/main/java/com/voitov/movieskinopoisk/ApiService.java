package com.voitov.movieskinopoisk;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/movie?field=rating.kp&search=3-10&sortField=votes.kp&sortType=-1&limit=40&token=" + TOKEN)
    public Single<MovieResponse> loadMostPopularMovies(@Query("page") int page);

    @GET("/movie?field=id&token=" + TOKEN)
    public Single<MovieDetailsResponse> loadTrailers(@Query("search") int movieId);

    @GET("/review?field=movieId&token=" + TOKEN)
    public Single<ReviewResponse> loadReviews(@Query("search") int id);

    public static final String TOKEN = BuildConfig.API_KEY;
}
