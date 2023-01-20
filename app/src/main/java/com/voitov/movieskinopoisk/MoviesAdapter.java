package com.voitov.movieskinopoisk;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private List<Movie> movies = new ArrayList<>();
    private OnReachEndListener onReachEndListener;
    private OnMovieClickListener onMovieClickListener;

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setOnReachEnd(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MoviesViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Glide.with(holder.itemView)
                .load(movie.getPoster().getUrl())
                .into(holder.imageViewPoster);
        TextView textViewRate = holder.textViewRate;

        textViewRate.setText(String.valueOf(movie.getRating().getKinopoisk()).substring(0, 3));

        double ratingOnKinopoisk = movie.getRating().getKinopoisk();
        int backgroundId;

        if (ratingOnKinopoisk >= 7) {
            backgroundId = R.drawable.awesome_rating_circle;
        } else if (ratingOnKinopoisk > 5) {
            backgroundId = R.drawable.normal_rating_circle;
        } else {
            backgroundId = R.drawable.awful_rating_circle;
        }

        Drawable ratingBackground = ContextCompat.getDrawable(holder.itemView.getContext(), backgroundId);
        textViewRate.setBackground(ratingBackground);

        if (position > movies.size() - 6 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }

        holder.imageViewPoster.setRenderEffect(null);
        holder.textViewBriefDescription.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMovieClickListener != null) {
                    onMovieClickListener.onMovieClick(movie);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.imageViewPoster.setRenderEffect(RenderEffect.createBlurEffect(50f, 50f, Shader.TileMode.MIRROR));
                holder.textViewBriefDescription.setVisibility(View.VISIBLE);
                holder.textViewRate.setVisibility(View.GONE);
                holder.textViewBriefDescription.setText(movie.getBriefDescription());
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MoviesViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewRate;
        private final TextView textViewBriefDescription;
        private final ImageView imageViewPoster;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRate = itemView.findViewById(R.id.textViewRate);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewBriefDescription = itemView.findViewById(R.id.textViewBriefDescription);
        }
    }

    public interface OnReachEndListener {
        public void onReachEnd();
    }

    public interface OnMovieClickListener {
        public void onMovieClick(Movie movie);
    }
}
