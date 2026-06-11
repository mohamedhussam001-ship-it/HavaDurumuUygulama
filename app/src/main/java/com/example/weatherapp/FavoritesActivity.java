package com.example.weatherapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    ListView listFavorites;
    DatabaseHelper databaseHelper;
    ArrayList<String> cities;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listFavorites = findViewById(R.id.listFavorites);
        databaseHelper = new DatabaseHelper(this);

        cities = databaseHelper.getFavorites();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                cities
        );

        listFavorites.setAdapter(adapter);

        listFavorites.setOnItemLongClickListener((parent, view, position, id) -> {

            String city = cities.get(position);

            databaseHelper.deleteFavorite(city);

            cities.remove(position);
            adapter.notifyDataSetChanged();

            Toast.makeText(
                    FavoritesActivity.this,
                    city + " deleted",
                    Toast.LENGTH_SHORT
            ).show();

            return true;
        });

    }
    @Override
    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }
}
