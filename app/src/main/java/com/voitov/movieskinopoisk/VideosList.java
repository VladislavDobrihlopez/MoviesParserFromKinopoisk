package com.voitov.movieskinopoisk;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideosList {
    @SerializedName("trailers")
    private final List<Trailer> trailers;

    public VideosList(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    @Override
    public String toString() {
        return "Videos{" +
                "trailers=" + trailers +
                '}';
    }
}
