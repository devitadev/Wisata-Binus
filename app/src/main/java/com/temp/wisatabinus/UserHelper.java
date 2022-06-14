package com.temp.wisatabinus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UserHelper {

    public static String user_table_name = "MsUser";
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public UserHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void close() throws SQLException{
        sqLiteDatabase.close();
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();

        String selectQuery = String.format("SELECT * FROM %s", user_table_name);
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        int n = cursor.getCount();
        if (n > 0){
            do{
                Integer userID = cursor.getInt(cursor.getColumnIndexOrThrow("UserID"));
                String userEmailAddress = cursor.getString(cursor.getColumnIndexOrThrow("UserEmailAddress"));
                String userPhoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("UserPhoneNumber"));
                String userPassword = cursor.getString(cursor.getColumnIndexOrThrow("UserPassword"));
                users.add(new User(userID, userEmailAddress,userPhoneNumber, userPassword));
                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }
        return users;
    }

    public Integer getUsersCount(){
        String selectQuery = String.format("SELECT * FROM %s", user_table_name);
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        return cursor.getCount();
    }

    public void registerUser(String emailAddress, String phoneNumber, String password){
        Integer userID = getUsersCount() + 1;
        String insertQuery = String.format("INSERT INTO %s VALUES (%d, '%s', '%s', '%s')", user_table_name, userID, emailAddress, phoneNumber, password);
        sqLiteDatabase.execSQL(insertQuery);
    }

    public User getUserByID(Integer userID){
        User user = null;
        String selectQuery = String.format("SELECT * FROM %s WHERE UserID=%d", user_table_name, userID);
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            String userEmailAddress = cursor.getString(cursor.getColumnIndexOrThrow("UserEmailAddress"));
            String userPhoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("UserPhoneNumber"));
            String userPassword = cursor.getString(cursor.getColumnIndexOrThrow("UserPassword"));
            user = new User(userID, userEmailAddress,userPhoneNumber, userPassword);
        }
        return user;
    }

    public void changePassword(Integer userID, String newPassword){
        String updateQuery = String.format("UPDATE %s SET UserPassword='%s' WHERE UserID=%d", user_table_name, newPassword, userID);
        sqLiteDatabase.execSQL(updateQuery);
    }
}
