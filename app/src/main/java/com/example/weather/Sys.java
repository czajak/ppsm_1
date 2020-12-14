package com.example.weather;

import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("type")
    int type;

    @SerializedName("id")
    int id;

    @SerializedName("country")
    String country;

    @SerializedName("sunrise")
    int sunrise;

    @SerializedName("sunset")
    int sunset;

    public int getId() {
        return id;
    }

    public int getSunrise() {
        return sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public String getCountry() {
        return country;
    }

    public int getType() {
        return type;
    }
}