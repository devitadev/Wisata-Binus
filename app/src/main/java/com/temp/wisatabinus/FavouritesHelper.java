package com.temp.wisatabinus;

import android.content.Context;
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

}