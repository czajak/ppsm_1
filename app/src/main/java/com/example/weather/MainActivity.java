package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button checkWeatherButton;
    EditText editText;
    public static String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        checkWeatherButton = findViewById(R.id.id_checkWeatherButton);
        editText = findViewById(R.id.editText);

        checkWeatherButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openWeatherActivity();
            }
        });

    }

    protected void openWeatherActivity() {
        Intent intent = new Intent(this, Weather.class);
        cityName = editText.getText().toString().trim();
        intent.putExtra("cityName", cityName);
        startActivity(intent);
    }
}