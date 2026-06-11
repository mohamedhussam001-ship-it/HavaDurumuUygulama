package com.example.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WeatherDB";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE favorites (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "city TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS favorites");
        onCreate(db);
    }

    // Add city to favorites
    public void addFavorite(String city) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("city", city);

        db.insert("favorites", null, values);
        db.close();
    }

    // Get all favorite cities
    public ArrayList<String> getFavorites() {
        ArrayList<String> cities = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT city FROM favorites", null);

        if (cursor.moveToFirst()) {
            do {
                cities.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return cities;
    }

    // Delete city from favorites
    public void deleteFavorite(String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("favorites", "city=?", new String[]{city});
        db.close();
    }
}