package com.example.loginandsignup.helpers;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserDatabase";

    // Table name and column names
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULL_NAME = "fullName";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_YEAR_OF_BIRTH = "yearOfBirth";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    // Create table query
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_FULL_NAME + " TEXT,"
            + COLUMN_GENDER + " TEXT,"
            + COLUMN_YEAR_OF_BIRTH + " INTEGER,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists and create fresh table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Method to add a new user to the database
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULL_NAME, user.getFullName());
        values.put(COLUMN_GENDER, user.getGender());
        values.put(COLUMN_YEAR_OF_BIRTH, user.getYearOfBirth());
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());

        // Inserting Row
        long result = db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
        return result;
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Define the columns you want to fetch
            String[] columns = {COLUMN_ID};

            // Specify the selection criteria
            String selection = COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";

            // Specify the selection arguments
            String[] selectionArgs = {username, password};

            // Execute the query
            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

            // Check if any record matches the provided credentials
            return cursor.moveToFirst(); // If moveToFirst() returns true, it means a record exists
        } finally {
            if (cursor != null) {
                cursor.close(); // Close the cursor to release resources
            }
        }
    }

    @SuppressLint("Range")
    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID, COLUMN_FULL_NAME, COLUMN_GENDER, COLUMN_YEAR_OF_BIRTH, COLUMN_USERNAME},
                COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            user.setFullName(cursor.getString(cursor.getColumnIndex(COLUMN_FULL_NAME)));
            user.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
            user.setYearOfBirth(cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR_OF_BIRTH)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            cursor.close();
        }

        return user;
    }

}
