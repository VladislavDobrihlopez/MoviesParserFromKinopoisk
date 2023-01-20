package com.voitov.movieskinopoisk;

import com.google.gson.annotations.SerializedName;

public class Trailer {
    @SerializedName("url")
    private final String url;
    @SerializedName("name")
    private final String name;

    public Trailer(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
