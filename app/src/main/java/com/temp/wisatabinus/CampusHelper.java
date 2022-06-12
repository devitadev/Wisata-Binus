package com.temp.wisatabinus;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class CampusHelper {

    public static String campus_table_name = "MsCampus";
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public CampusHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void close() throws SQLException{
        sqLiteDatabase.close();
    }

    public ArrayList<Campus> getCampuses(){
        ArrayList<Campus> campuses = new ArrayList<>();

        String selectQuery = String.format("SELECT * FROM %s", campus_table_name);
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        Integer campusID;
        String campusName, campusLocation, campusAddress, campusImage;
        Double campusLatitude, campusLongitude;
        if(cursor.getCount() > 0){
            do{
                campusID = cursor.getInt(cursor.getColumnIndexOrThrow("CampusID"));
                campusName = cursor.getString(cursor.getColumnIndexOrThrow("CampusName"));
                campusLocation = cursor.getString(cursor.getColumnIndexOrThrow("CampusLocation"));
                campusAddress = cursor.getString(cursor.getColumnIndexOrThrow("CampusAddress"));
                campusLatitude = cursor.getDouble(cursor.getColumnIndexOrThrow("CampusLatitude"));
                campusLongitude = cursor.getDouble(cursor.getColumnIndexOrThrow("CampusLongitude"));
                campusImage = cursor.getString(cursor.getColumnIndexOrThrow("CampusImage"));

                campuses.add(new Campus(campusID, campusName, campusLocation, campusAddress, campusLatitude, campusLongitude, campusImage));

                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }

        return campuses;
    }

    public void insertCampuses(ArrayList<Campus> campuses){
        String insertQuery = String.format("INSERT INTO %s VALUES", campus_table_name);
        int n = campuses.size();
        for(int i = 0; i < n; i++){
            Campus campus = campuses.get(i);
            if(i > 0) insertQuery = insertQuery + ",";
            insertQuery = insertQuery + String.format(" (%d, '%s', '%s', '%s', %.14f, %.14f, '%s')", campus.getCampusID(), campus.getCampusName(), campus.getCampusLocation(), campus.getCampusAddress(), campus.getCampusLatitude(), campus.getCampusLongitude(), campus.getCampusImage());
        }
        sqLiteDatabase.execSQL(insertQuery);
    }

    public void insertCampus(Campus campus){
        String insertQuery = String.format("INSERT INTO %s VALUES (%d, '%s', '%s', '%s', %.14f, %.14f, '%s')", campus_table_name, campus.getCampusID(), campus.getCampusName(), campus.getCampusLocation(), campus.getCampusAddress(), campus.getCampusLatitude(), campus.getCampusLongitude(), campus.getCampusImage());
        sqLiteDatabase.execSQL(insertQuery);
    }
}
