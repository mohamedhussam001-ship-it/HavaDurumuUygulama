package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnWeather, btnFavorites, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWeather = findViewById(R.id.btnWeather);
        btnFavorites = findViewById(R.id.btnFavorites);
        btnAbout = findViewById(R.id.btnAbout);

        btnWeather.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            startActivity(intent);
        });

        btnFavorites.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });

        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }
}