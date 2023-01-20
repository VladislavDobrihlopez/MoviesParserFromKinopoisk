package com.voitov.movieskinopoisk;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "favourite_movies")
public class Movie implements Serializable {
    @SerializedName("name")
    private final String name;
    @Embedded
    @SerializedName("poster")
    private final Poster poster;
    @Embedded
    @SerializedName("rating")
    private final Rating rating;
    @SerializedName("description")
    private final String description;
    @SerializedName("shortDescription")
    private final String briefDescription;
    @SerializedName("year")
    private final int year;
    @PrimaryKey
    @SerializedName("id")
    private final int id;
    @Ignore
    private boolean isBlurred;

    public Movie(String name, Poster poster, Rating rating, String description, String briefDescription, int year, int id) {
        this.name = name;
        this.poster = poster;
        this.rating = rating;
        this.description = description;
        this.briefDescription = briefDescription;
        this.year = year;
        this.id = id;
        isBlurred = false;
    }

    public void blur() {
        isBlurred = !isBlurred;
    }

    public boolean isBlurred() {
        return isBlurred;
    }

    public String getName() {
        return name;
    }

    public Poster getPoster() {
        return poster;
    }

    public Rating getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public int getYear() {
        return year;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return year == movie.year && id == movie.id && Objects.equals(name, movie.name) && Objects.equals(poster, movie.poster) && Objects.equals(rating, movie.rating) && Objects.equals(description, movie.description) && Objects.equals(briefDescription, movie.briefDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, poster, rating, description, briefDescription, year, id);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", poster=" + poster +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", briefDescription='" + briefDescription + '\'' +
                ", year=" + year +
                ", id=" + id +
                '}';
    }
}
