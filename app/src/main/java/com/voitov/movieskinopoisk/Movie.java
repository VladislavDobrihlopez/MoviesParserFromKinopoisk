package com.voitov.movieskinopoisk;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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

    public Movie(String name, Poster poster, Rating rating, String description, String briefDescription, int year, int id) {
        this.name = name;
        this.poster = poster;
        this.rating = rating;
        this.description = description;
        this.briefDescription = briefDescription;
        this.year = year;
        this.id = id;
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
