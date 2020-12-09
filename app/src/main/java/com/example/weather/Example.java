package com.example.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("main")
    private Main main;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("weather")
    private List<Weather> weatherList;


    public Main getMain() {
        return main;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public String getTimezone() {
        return timezone;
    }

}
