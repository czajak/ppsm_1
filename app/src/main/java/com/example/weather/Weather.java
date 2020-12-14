package com.example.weather;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("main")
    String main;

    @SerializedName("icon")
    String icon;

    public String getMainWeather() {
        return main;
    }

    public String getIcon(){ return icon;}
}
