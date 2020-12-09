package com.example.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("weather?&APPID=06dee8162f98ef39e3a0146a1b7db911&units=metric")
    Call<Example> getWeather(@Query("q") String name);

}
