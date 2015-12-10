package com.example.utilisateur.geoloc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by godevin on 03/12/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GeoLoc.db";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE city (_id INTEGER PRIMARY KEY, name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS city");
        onCreate(db);
    }

    public void addCity(String ville){
        SQLiteDatabase db = getWritableDatabase();
        // Vérifie que la ville n'existe pas déjà
        int count = db.rawQuery("SELECT * FROM city WHERE name = '" + ville + "'", null).getCount();
        if (count == 0) {
            ContentValues cv = new ContentValues();
            cv.put("name", ville);
            db.insert("city", null, cv);
        }
    }

    public Cursor getCities() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM city", null);
        return cursor;
    }


}
