package com.example.weather;

import com.google.gson.annotations.SerializedName;

public class Weather2 {

    @SerializedName("main")
    String main;

    public String getMainWeather() {
        return main;
    }

}
