package com.voitov.movieskinopoisk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    public static final String TYPE_POSITIVE_REVIEW = "Позитивный";
    public static final String TYPE_NEGATIVE_REVIEW = "Негативный";
    public static final String TYPE_NEUTRAL_REVIEW = "Нейтральный";

    private List<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);

        int resBackgroundColor;
        switch (review.getType()) {
            case TYPE_POSITIVE_REVIEW:
                resBackgroundColor = android.R.color.holo_green_light;
                break;
            case TYPE_NEUTRAL_REVIEW:
                resBackgroundColor = android.R.color.holo_orange_light;
                break;
            case TYPE_NEGATIVE_REVIEW:
                resBackgroundColor = android.R.color.holo_red_light;
                break;
            default:
                resBackgroundColor = android.R.color.holo_blue_light;
        }

        int color = ContextCompat.getColor(holder.itemView.getContext(), resBackgroundColor);
        holder.constraintLayoutReviews.setBackgroundColor(color);

        holder.textViewAuthor.setText(review.getAuthor());
        holder.textViewReview.setText(review.getReview());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout constraintLayoutReviews;
        private final TextView textViewAuthor;
        private final TextView textViewReview;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayoutReviews = itemView.findViewById(R.id.constraintLayoutReviews);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewReview = itemView.findViewById(R.id.textViewReview);
        }
    }
}
