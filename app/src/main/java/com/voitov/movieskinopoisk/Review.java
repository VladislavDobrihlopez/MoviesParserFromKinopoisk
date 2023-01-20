package com.voitov.movieskinopoisk;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("type")
    private final String type;
    @SerializedName("author")
    private final String author;
    @SerializedName("review")
    private final String review;
    @SerializedName("reviewLikes")
    private final int reviewLikes;
    @SerializedName("reviewDislikes")
    private final int reviewDislikes;

    public Review(String type, String author, String review, int reviewLikes, int reviewDislikes) {
        this.type = type;
        this.author = author;
        this.review = review;
        this.reviewLikes = reviewLikes;
        this.reviewDislikes = reviewDislikes;
    }

    public String getType() {
        return type;
    }

    public String getAuthor() {
        return author;
    }

    public String getReview() {
        return review;
    }

    public int getReviewLikes() {
        return reviewLikes;
    }

    public int getReviewDislikes() {
        return reviewDislikes;
    }

    @Override
    public String toString() {
        return "Review{" +
                "type='" + type + '\'' +
                ", author='" + author + '\'' +
                ", review='" + review + '\'' +
                ", reviewLikes=" + reviewLikes +
                ", reviewDislikes=" + reviewDislikes +
                '}';
    }
}
