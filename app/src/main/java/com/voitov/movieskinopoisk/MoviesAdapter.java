package com.voitov.movieskinopoisk;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class MoviesAdapter extends ListAdapter<Movie, MoviesAdapter.MoviesViewHolder> {
    private OnReachEndListener onReachEndListener;
    private OnMovieClickListener onMovieClickListener;

    public MoviesAdapter() {
        super(new MovieItemCallback());
    }

    public void setOnReachEnd(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public int getItemViewType(int position) {
        Movie movie = getItem(position);
        if (movie.isBlurred()) {
            return VIEW_TYPE_BLURRED_CARD;
        } else {
            return VIEW_TYPE_DEFAULT_CARD;
        }
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "OnCreateViewHolder count1: " + (++count1));
        int layoutResId;

        if (viewType == VIEW_TYPE_BLURRED_CARD) {
            layoutResId = R.layout.movie_item_blurred;
        } else if (viewType == VIEW_TYPE_DEFAULT_CARD) {
            layoutResId = R.layout.movie_item;
        } else {
            throw new RuntimeException("There is no view type exists: " + viewType);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        MoviesViewHolder holder = new MoviesViewHolder(view);

        if (viewType == VIEW_TYPE_BLURRED_CARD) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                {
                    holder.imageViewPoster.setRenderEffect(RenderEffect.createBlurEffect(15f, 15f, Shader.TileMode.MIRROR));
                }
            } else {
                holder.imageViewPoster.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.white));
                holder.imageViewPoster.setImageAlpha(75);
            }
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Log.d(TAG, "OnBindViewHolder count2: " + (++count2));
        Movie movie = getItem(position);
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

        if (movie.isBlurred()) {
            holder.textViewBriefDescription.setText(movie.getBriefDescription());
        }

        if (position > getItemCount() - 6 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movie.isBlurred()) {
                    movie.blur();
                    notifyItemChanged(holder.getAdapterPosition());
                } else {
                    if (onMovieClickListener != null) {
                        onMovieClickListener.onMovieClick(movie);
                    }
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                movie.blur();
                notifyItemChanged(holder.getAdapterPosition());
                return true;
            }
        });
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

    private static final int VIEW_TYPE_DEFAULT_CARD = 100;
    private static final int VIEW_TYPE_BLURRED_CARD = 101;
    public static final String TAG = "MoviesAdapter";
    public static int count1 = 0;
    public static int count2 = 0;
}
