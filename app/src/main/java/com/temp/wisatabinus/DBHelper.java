package com.temp.wisatabinus;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "WisataBinus", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableUser = String.format("CREATE TABLE %s (UserID, UserEmailAddress, UserPhoneNumber, UserPassword)", UserHelper.user_table_name);
        sqLiteDatabase.execSQL(createTableUser);

        String createTableCampuses = String.format("CREATE TABLE %s (CampusID, CampusName, CampusLocation, CampusAddress, CampusLatitude, CampusLongitude, CampusImage)", CampusHelper.campus_table_name);
        sqLiteDatabase.execSQL(createTableCampuses);

        String createTableFavorites = String.format("CREATE TABLE %s (UserID, CampusID)", FavouritesHelper.favourites_table_name);
        sqLiteDatabase.execSQL(createTableFavorites);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserHelper.user_table_name);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CampusHelper.campus_table_name);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouritesHelper.favourites_table_name);
        onCreate(sqLiteDatabase);
    }
}
