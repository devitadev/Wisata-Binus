package com.temp.wisatabinus;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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

    public ArrayList<Campus> getFavourites(Integer userID){
        ArrayList<Campus> favourites = new ArrayList<>();
        String selectQuery = String.format("SELECT * FROM %s INNER JOIN %s ON %s.CampusID=%s.CampusID WHERE UserID=%d",
                favourites_table_name, CampusHelper.campus_table_name, favourites_table_name, CampusHelper.campus_table_name, userID);
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        int n = cursor.getCount();
        if (n > 0){
            do{
                Integer campusID = cursor.getInt(cursor.getColumnIndexOrThrow("CampusID"));
                String campusName = cursor.getString(cursor.getColumnIndexOrThrow("CampusName"));
                String campusLocation = cursor.getString(cursor.getColumnIndexOrThrow("CampusLocation"));
                String campusAddress = cursor.getString(cursor.getColumnIndexOrThrow("CampusAddress"));
                Double campusLatitude = cursor.getDouble(cursor.getColumnIndexOrThrow("CampusLatitude"));
                Double campusLongitude = cursor.getDouble(cursor.getColumnIndexOrThrow("CampusLongitude"));
                String campusImage = cursor.getString(cursor.getColumnIndexOrThrow("CampusImage"));
                favourites.add(new Campus(campusID, campusName, campusLocation, campusAddress, campusLatitude, campusLongitude, campusImage));
                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }
        return favourites;
    }

}
