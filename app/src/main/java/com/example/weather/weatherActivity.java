package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class weatherActivity extends AppCompatActivity {
    SwipeRefreshLayout refreshLayout;
    TextView cityStringName, cityStringTime, cityStringTemp, cityStringPressure, cityStringHumidity, cityStringMinTemp, cityStringMaxTemp, errorMessage;
    ImageView weatherImage,flagImage;
    String cityName;
    SimpleDateFormat city;
    private static final int AUTO_REFRESH_TIME = 5000; // auto refreshing timer (in miliseconds)
    private static final int IMAGE_TYPE_FLAG = 24612235;
    private static final int IMAGE_TYPE_WEATHER_PIC = 39125671;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flagImage = findViewById(R.id.id_flag);
        weatherImage = findViewById(R.id.id_weatherImage);
        cityStringName = findViewById(R.id.id_cityName);
        cityStringTime = findViewById(R.id.id_cityTime);
        cityStringTemp = findViewById(R.id.id_tempValue);
        cityStringPressure = findViewById(R.id.id_pressureValue);
        cityStringHumidity = findViewById(R.id.id_humidityValue);
        cityStringMinTemp = findViewById(R.id.id_tempMinValue);
        cityStringMaxTemp = findViewById(R.id.id_tempMaxValue);
        refreshLayout = findViewById(R.id.refreshLayout);
        errorMessage = findViewById(R.id.id_err_main);
        city = new SimpleDateFormat("HH:mm:ss");

        Handler handler = new Handler();
        Runnable timedTask = new Runnable(){
            @Override
            public void run() {
                refresh();
                handler.postDelayed(this, AUTO_REFRESH_TIME);
            }};
        handler.post(timedTask);

        Intent intent = getIntent();
        cityName = intent.getStringExtra("cityName");
        Log.i("test",cityName);
        getWeatherData(cityName.trim());
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
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
                if(response.body() != null)
                    showWeather(response);
            }


            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.i("test","Callback onFailure",t.getCause());
            }
        });
    }
    private void showWeather(Response<Example> response)
    {
        Thread thread = new Thread(new Runnable () {
            @Override
            public void run() {
                try{
                    flagImage.setImageDrawable(LoadImageFromWebOperations(imageUrlBuilder(IMAGE_TYPE_FLAG,response.body().getSys().getCountry())));
                    weatherImage.setImageDrawable(LoadImageFromWebOperations(imageUrlBuilder(IMAGE_TYPE_WEATHER_PIC,String.valueOf(response.body().getWeather().get(0).getIcon()))));
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        int timezoneInSeconds = response.body().getTimezone();
        timezoneInSeconds /= 3600;

        if(timezoneInSeconds > 0)
            city.setTimeZone(TimeZone.getTimeZone("GMT+" + timezoneInSeconds));
        else
            city.setTimeZone(TimeZone.getTimeZone("GMT" + timezoneInSeconds));

        cityName = cityName.substring(0,1).toUpperCase() + cityName.substring(1);

        cityStringName.setText(cityName);
        cityStringTime.setText((city.format(new Date())));
        cityStringTemp.setText(response.body().getMain().getTemp() + " °C");
        cityStringPressure.setText(response.body().getMain().getPressure() + " hPa");
        cityStringHumidity.setText(response.body().getMain().getHumidity() + " %");
        cityStringMinTemp.setText(response.body().getMain().getTemp_min() + " °C");
        cityStringMaxTemp.setText(response.body().getMain().getTemp_max() + " °C");
    }

    public boolean checkConnection()
    {
        ConnectivityManager manager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if(activeNetwork != null) {
            return true;
        }
        return false;
    }

    private void refresh(){
        if(checkConnection()){
            errorMessage.setVisibility(View.INVISIBLE);
            refreshLayout.setRefreshing(false);
            getWeatherData(cityName);
        }
        else
        {
            errorMessage.setVisibility(View.VISIBLE);
        }
    }
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private String imageUrlBuilder(int FLAG, String param)
    {
        String url;
        if(FLAG == 24612235)
        {
            url = "https://www.countryflags.io/" + param + "/shiny/64.png";
        }
        else
        {
            url = "https://openweathermap.org/img/wn/"+ param + "@2x.png";
        }
        return url;
    }
}