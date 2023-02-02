package com.voitov.movieskinopoisk;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class Rating implements Serializable {
    @SerializedName("kp")
    private final double kinopoisk;
    @SerializedName("imdb")
    private final double imdb;
    @SerializedName("filmCritics")
    private final double filmCritics;

    public Rating(double kinopoisk, double imdb, double filmCritics) {
        this.kinopoisk = kinopoisk;
        this.imdb = imdb;
        this.filmCritics = filmCritics;
    }

    public double getKinopoisk() {
        return kinopoisk;
    }

    public double getImdb() {
        return imdb;
    }

    public double getFilmCritics() {
        return filmCritics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Double.compare(rating.kinopoisk, kinopoisk) == 0 && Double.compare(rating.imdb, imdb) == 0 && Double.compare(rating.filmCritics, filmCritics) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kinopoisk, imdb, filmCritics);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "kinopoisk=" + kinopoisk +
                ", imdb=" + imdb +
                ", filmCritics=" + filmCritics +
                '}';
    }
}
