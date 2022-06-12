package com.temp.wisatabinus;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FavouritesHelper {

    public static String favourites_table_name = "TrFavourites";
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public FavouritesHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void close() throws SQLException{
        sqLiteDatabase.close();
    }

    public boolean favouriteCheck(Integer userID, Integer campusID){
        String selectQuery = String.format("SELECT * FROM %s WHERE UserID=%d AND CampusID=%d", favourites_table_name, userID, campusID);
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) return false;
        else return true;
    }

    public void addFavourite(Integer userID, Integer campusID){
        String insertQuery = String.format("INSERT INTO %s VALUES (%d, %d)", favourites_table_name, userID, campusID);
        sqLiteDatabase.execSQL(insertQuery);
    }

    public void removeFavourite(Integer userID, Integer campusID){
        String deleteQuery = String.format("DELETE FROM %s WHERE UserID=%d AND CampusID=%d", favourites_table_name, userID, campusID);
        sqLiteDatabase.execSQL(deleteQuery);
    }

}
