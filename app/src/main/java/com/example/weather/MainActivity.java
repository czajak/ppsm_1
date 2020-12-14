package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {

    Button checkWeatherButton;
    EditText editText;
    TextView errorMessageMain;
    public static String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        checkWeatherButton = findViewById(R.id.id_checkWeatherButton);
        editText = findViewById(R.id.editText);
        errorMessageMain = findViewById(R.id.id_err_main);

        checkWeatherButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(checkConnection())
                {
                    errorMessageMain.setVisibility(View.INVISIBLE);
                    openWeatherActivity();
                }
                else
                {
                    errorMessageMain.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    protected void openWeatherActivity() {
        Intent intent = new Intent(this, weatherActivity.class);
        cityName = editText.getText().toString().trim();
        intent.putExtra("cityName", cityName);
        startActivity(intent);
    }
    public boolean checkConnection()
    {
        ConnectivityManager manager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null;
    }
}