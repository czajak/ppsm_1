package com.example.weather;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("main")
    private Main main;

    @SerializedName("timezone")
    private int timezone;

    @SerializedName("weather")
    private List<Weather> weatherList;

    @SerializedName("sys")
    private Sys sys;

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        Log.i("test", String.valueOf(weatherList.get(0).getIcon())); return weatherList;
    }

    public int getTimezone() {
        return timezone;
    }

    public Sys getSys() {return sys;}

}
