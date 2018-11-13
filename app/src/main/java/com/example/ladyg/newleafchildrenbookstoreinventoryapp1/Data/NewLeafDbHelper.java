package com.example.ladyg.newleafchildrenbookstoreinventoryapp1.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.ladyg.newleafchildrenbookstoreinventoryapp1.Data.NewLeafContract.NewLeafEntry;

import static com.example.ladyg.newleafchildrenbookstoreinventoryapp1.Data.NewLeafContract.NewLeafEntry.TABLE_NAME;

public class NewLeafDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = NewLeafDbHelper.class.getSimpleName();

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
        String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + NewLeafEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NewLeafEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + NewLeafEntry.COLUMN_PRICE + " INTEGER NOT NULL DEFAULT 0, "
                + NewLeafEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + NewLeafEntry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL, "
                + NewLeafEntry.COLUMN_PHONE_NUMBER + " TEXT NOT NULL);";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_BOOKS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.

    }
}

