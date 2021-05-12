package com.example.nguyenduchuy18086681;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "visitPlace";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "place";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_students_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT)", TABLE_NAME, KEY_ID, KEY_NAME);
        db.execSQL(create_students_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_students_table);

        onCreate(db);
    }
    public void addPlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, place.getName());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public Place getPlace(int placeID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, KEY_ID + " = ?", new String[] { String.valueOf(placeID) },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        Place place = new Place(cursor.getInt(0), cursor.getString(1));
        return place;
    }
    public List<Place> getAllPlace() {
        List<Place>  placeList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            Place place = new Place(cursor.getInt(0), cursor.getString(1));
            placeList.add(place);
            cursor.moveToNext();
        }
        return placeList;
    }

    public void updateTravel(String name1, List<Place> places, int position) {
        String name = places.get(position).getName();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name1);
        db.update(TABLE_NAME, values, KEY_NAME + " = ?", new String[] { name });
        db.close();
    }
    public void deleteTravel(List<Place> places, int position) {
        String name = places.get(position).getName();
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_NAME + " = ?", new String[] { name });
        db.close();
    }

}
