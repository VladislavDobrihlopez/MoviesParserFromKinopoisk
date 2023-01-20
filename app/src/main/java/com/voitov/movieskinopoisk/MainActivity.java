package com.voitov.movieskinopoisk;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private MainViewModel viewModel;
    private RecyclerView recyclerViewMovies;
    private ProgressBar progressBarMoviesAreLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        MoviesAdapter moviesAdapter = new MoviesAdapter();
        recyclerViewMovies.setAdapter(moviesAdapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewMovies.setLayoutManager(layoutManager);

        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, movies.toString());
                moviesAdapter.submitList(movies);
            }
        });

        moviesAdapter.setOnReachEnd(new MoviesAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                viewModel.loadMovies();
            }
        });

        moviesAdapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                startActivity(MovieDetailsActivity.newIntent(MainActivity.this, movie));
            }
        });

        viewModel.getMoviesAreLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean moviesAreLoading) {
                if (moviesAreLoading) {
                    progressBarMoviesAreLoading.setVisibility(View.VISIBLE);
                } else {
                    progressBarMoviesAreLoading.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initViews() {
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        progressBarMoviesAreLoading = findViewById(R.id.progressBarMoviesAreLoading);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemFavouriteMovies) {
            startActivity(FavouriteMoviesActivity.newIntent(this));
        }
        return super.onOptionsItemSelected(item);
    }
}