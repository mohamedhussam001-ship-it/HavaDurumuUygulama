package com.example.weatherapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {

    EditText edtCity;
    Button btnGetWeather, btnSaveFavorite;
    TextView txtResult;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        databaseHelper = new DatabaseHelper(this);
        edtCity = findViewById(R.id.edtCity);
        btnGetWeather = findViewById(R.id.btnGetWeather);
        btnSaveFavorite = findViewById(R.id.btnSaveFavorite);
        txtResult = findViewById(R.id.txtResult);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnGetWeather.setOnClickListener(v -> {

            String city = edtCity.getText().toString().trim();

            if (city.isEmpty()) {
                Toast.makeText(this, "Please enter city name", Toast.LENGTH_SHORT).show();
            } else {
                getWeather(city);
            }
        });
        btnSaveFavorite.setOnClickListener(v -> {

            String city = edtCity.getText().toString().trim();

            if (!city.isEmpty()) {

                databaseHelper.addFavorite(city);

                Toast.makeText(
                        WeatherActivity.this,
                        "City Saved Successfully",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                Toast.makeText(
                        WeatherActivity.this,
                        "Please Enter City Name",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void getWeather(String city) {

        String apiKey = "16cb1bd89c581605ddd52ef688e3947c";

        String urlString =
                "https://api.openweathermap.org/data/2.5/weather?q="
                        + city
                        + "&appid="
                        + apiKey
                        + "&units=metric";

        new Thread(() -> {
            try {

                java.net.URL url = new java.net.URL(urlString);

                java.net.HttpURLConnection connection =
                        (java.net.HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");

                java.io.BufferedReader reader =
                        new java.io.BufferedReader(
                                new java.io.InputStreamReader(
                                        connection.getInputStream()
                                )
                        );

                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                org.json.JSONObject json =
                        new org.json.JSONObject(result.toString());

                org.json.JSONObject main =
                        json.getJSONObject("main");

                double temp = main.getDouble("temp");
                int humidity = main.getInt("humidity");

                String description =
                        json.getJSONArray("weather")
                                .getJSONObject(0)
                                .getString("description");

                runOnUiThread(() -> {
                    txtResult.setText("📍 City: " + city + "\n\n🌡 Temperature: " + temp + " °C" + "\n\n💧 Humidity: " + humidity + "%" + "\n\n☁ Weather: " + description
                    );
                });

            } catch (Exception e) {

                runOnUiThread(() ->
                        txtResult.setText("Error loading weather data")
                );
            }
        }).start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
