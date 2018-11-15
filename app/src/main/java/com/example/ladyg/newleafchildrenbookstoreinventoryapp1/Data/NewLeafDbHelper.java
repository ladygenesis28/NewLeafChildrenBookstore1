package com.example.ladyg.newleafchildrenbookstoreinventoryapp1.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.ladyg.newleafchildrenbookstoreinventoryapp1.Data.NewLeafContract.NewLeafEntry;

import static com.example.ladyg.newleafchildrenbookstoreinventoryapp1.Data.NewLeafContract.NewLeafEntry.BOOK;

public class NewLeafDbHelper extends SQLiteOpenHelper {

    // Name of the database file
    private static final String DATABASE_NAME = "book.db";

    //Database version. If I change the database schema, I must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public NewLeafDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        /**
         * This is called when the database is created for the first time.
         */
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the book table
        String SQL_CREATE_BOOK_TABLE = "CREATE TABLE " + BOOK + "("
                + NewLeafEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NewLeafEntry.COLUMN_PRODUCT + " TEXT, "
                + NewLeafEntry.COLUMN_PRICE + " INTEGER DEFAULT 0, "
                + NewLeafEntry.COLUMN_QUANTITY + " INTEGER DEFAULT 0, "
                + NewLeafEntry.COLUMN_SUPPLIER + " TEXT NOT NULL, "
                + NewLeafEntry.COLUMN_PHONE + " TEXT);";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_BOOK_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.

        // Note to reviewer; I know unused methods and variables are not allowed, but
        // since onUpgrade method is mention on the rubic, I assume its safe to leave on here.

    }
}

