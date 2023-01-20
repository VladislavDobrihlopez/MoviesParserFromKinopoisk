package com.voitov.movieskinopoisk;

import com.google.gson.annotations.SerializedName;

public class MovieDetailsResponse {
    @SerializedName("videos")
    private final VideosList videosList;
    @SerializedName("budget")
    private final Budget budget;

    public MovieDetailsResponse(VideosList videosList, Budget budget) {
        this.videosList = videosList;
        this.budget = budget;
    }

    public VideosList getVideos() {
        return videosList;
    }

    public Budget getBudget() {
        return budget;
    }

    @Override
    public String toString() {
        return "MovieDetailsResponse{" +
                "videos=" + videosList +
                ", budget=" + budget +
                '}';
    }
}
