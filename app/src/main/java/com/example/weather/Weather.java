package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Weather extends AppCompatActivity {
    TextView cityStringName, cityStringTime, cityStringTemp, cityStringPressure, cityStringHumidity, cityStringMinTemp, cityStringMaxTemp;
    ImageView weatherImage;
    String cityName;
    SimpleDateFormat city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherImage = findViewById(R.id.id_weatherImage);
        cityStringName = findViewById(R.id.id_cityName);
        cityStringTime = findViewById(R.id.id_cityTime);
        cityStringTemp = findViewById(R.id.id_tempValue);
        cityStringPressure = findViewById(R.id.id_pressureValue);
        cityStringHumidity = findViewById(R.id.id_humidityValue);
        cityStringMinTemp = findViewById(R.id.id_tempMinValue);
        cityStringMaxTemp = findViewById(R.id.id_tempMaxValue);

        city = new SimpleDateFormat("HH:mm");


        Intent intent = getIntent();
        cityName = intent.getStringExtra("cityName");
        Log.i("test",cityName);
        getWeatherData(cityName.trim());

    }

    private void getWeatherData(String name) {

        WeatherApi weatherApi = ApiClient.getClient().create(WeatherApi.class);

        Call<Example> call = weatherApi.getWeather(name);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (!response.isSuccessful()) {
                    finish();
                }
                try {
                    city.setTimeZone(TimeZone.getTimeZone(response.body().getTimezone()));
                    cityStringName.setText(cityName);
                    cityStringTime.setText(city.format(new Date()));
                    cityStringTemp.setText(response.body().getMain().getTemp());
                    cityStringPressure.setText(response.body().getMain().getPressure());
                    cityStringHumidity.setText(response.body().getMain().getHumidity());
                    cityStringMinTemp.setText(response.body().getMain().getTemp_min());
                    cityStringMaxTemp.setText(response.body().getMain().getTemp_max());
/*
                    switch (response.body().getWeatherList().get(0).getMainWeather()) {
                        case "Clouds":
                            weatherImage.setImageResource(R.drawable.ic_wi_cloudy);
                            break;
                        case "Clear":
                            weatherImage.setImageResource(R.drawable.ic_wi_night_clear);
                            break;
                    }
*/

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }
}