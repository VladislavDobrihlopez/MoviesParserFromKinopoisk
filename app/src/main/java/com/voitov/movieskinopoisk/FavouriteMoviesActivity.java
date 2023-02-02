package com.voitov.movieskinopoisk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavouriteMoviesActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMovies;
    private FavouriteMoviesViewModel viewModel;
    private MoviesAdapter adapter;
    private List<Movie> bundledMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);
        initViews();

        Log.d(TAG, "onCreate");

        viewModel = new ViewModelProvider(this).get(FavouriteMoviesViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (bundledMovies == null) {
                    adapter.submitList(movies);
                } else if (!movies.equals(bundledMovies)) {
                    List<Movie> moviesForAdapter = new ArrayList<>();
                    for (Movie bundledMovie : bundledMovies) {
                        boolean contains = movies.contains(bundledMovie);
                        if (contains) {
                            moviesForAdapter.add(bundledMovie);
                        }
                    }
                    adapter.submitList(moviesForAdapter);
                } else {
                    adapter.submitList(bundledMovies);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState");
        parseBundle(savedInstanceState);
    }

    private void parseBundle(Bundle savedInstance) {
        if (savedInstance == null) {
            return;
        }
        bundledMovies = Arrays.asList(new GsonBuilder().create().fromJson(savedInstance.get(EXTRA_MOVIES).toString(), Movie[].class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        //bundledMovies = new ArrayList<>(adapter.getCurrentList());
        outState.putSerializable(EXTRA_MOVIES, (new GsonBuilder().create().toJson(adapter.getCurrentList())));
    }

    private void initViews() {
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        adapter = new MoviesAdapter();
        adapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                startActivity(MovieDetailsActivity.newIntent(FavouriteMoviesActivity.this, movie));
            }
        });
        recyclerViewMovies.setAdapter(adapter);
        int columns = getResources().getInteger(R.integer.movie_columns);
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, columns));
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FavouriteMoviesActivity.class);
    }

    private static final String EXTRA_MOVIES = "extra_movies";
    public static final String TAG = "FavouriteMoviesActivity";
}