package com.temp.wisatabinus;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "Wisata Binus", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableUser = String.format("CREATE TABLE %s (UserID, UserEmailAddress, UserPhoneNumber, UserPassword)");
        String createTableCampuses = String.format("CREATE TABLE %s (CampusID, CampusName, CampusLocation, CampusAddress, CampusLatitude, CampusLongitude, CampusImage)");
        String createTableFavorites = String.format("CREATE TABLE %s (UserID, CampusID)");

        // fetch campus data


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // drop table if exist
    }
}
