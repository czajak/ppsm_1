package com.example.weather;

import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    String temp;

    @SerializedName("temp_min")
    String temp_min;

    @SerializedName("temp_max")
    String temp_max;

    @SerializedName("pressure")
    String pressure;

    @SerializedName("humidity")
    String humidity;

    public String getTemp() {
        return temp;
    }
    

    public String getTemp_min() {
        return temp_min;
    }


    public String getTemp_max() {
        return temp_max;
    }


    public String getPressure() {
        return pressure;
    }


    public String getHumidity() {
        return humidity;
    }

}
