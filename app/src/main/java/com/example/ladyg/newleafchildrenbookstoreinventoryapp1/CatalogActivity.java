package com.example.ladyg.newleafchildrenbookstoreinventoryapp1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ladyg.newleafchildrenbookstoreinventoryapp1.Data.NewLeafContract;
import com.example.ladyg.newleafchildrenbookstoreinventoryapp1.Data.NewLeafDbHelper;


public class CatalogActivity extends AppCompatActivity {
    //database helper that will provide us access to the database
    private NewLeafDbHelper newLeafDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
                // To access our database, we instantiate our subclass of SQLiteOpenHelper
                // and pass the context, which is the current activity.
                newLeafDbHelper = new NewLeafDbHelper(this);
            }

            @Override
            protected void onStart() {
                super.onStart();
                displayDatabaseInfo();
            }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the books database.
     */
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = newLeafDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                NewLeafContract.NewLeafEntry._ID,
                NewLeafContract.NewLeafEntry.COLUMN_PRODUCT,
                NewLeafContract.NewLeafEntry.COLUMN_PRICE,
                NewLeafContract.NewLeafEntry.COLUMN_QUANTITY,
                NewLeafContract.NewLeafEntry.COLUMN_SUPPLIER,
                NewLeafContract.NewLeafEntry.COLUMN_PHONE };


        // Perform a query on the books table
        Cursor cursor = db.query(
                NewLeafContract.NewLeafEntry.BOOK,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = findViewById(R.id.text_view_books);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The books table contains <number of rows in Cursor> books.
            // _id - name - price- quantity - supplier name - supplier phone number
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The books table contains " + cursor.getCount() + " books.\n\n");
            displayView.append(NewLeafContract.NewLeafEntry._ID + " - " +
                    NewLeafContract.NewLeafEntry.COLUMN_PRODUCT + " - " +
                    NewLeafContract.NewLeafEntry.COLUMN_PRICE + " - " +
                    NewLeafContract.NewLeafEntry.COLUMN_QUANTITY + " - " +
                    NewLeafContract.NewLeafEntry.COLUMN_SUPPLIER + " - " +
                    NewLeafContract.NewLeafEntry.COLUMN_PHONE + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(NewLeafContract.NewLeafEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(NewLeafContract.NewLeafEntry.COLUMN_PRODUCT);
            int priceColumnIndex = cursor.getColumnIndex(NewLeafContract.NewLeafEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(NewLeafContract.NewLeafEntry.COLUMN_QUANTITY);
            int suppliernameColumnIndex = cursor.getColumnIndex(NewLeafContract.NewLeafEntry.COLUMN_SUPPLIER);
            int supplierphonenumberColumnIndex = cursor.getColumnIndex(NewLeafContract.NewLeafEntry.COLUMN_PHONE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(suppliernameColumnIndex);
                int currentSupplierPhoneNumber = cursor.getInt(supplierphonenumberColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplierName + " - " +
                        currentSupplierPhoneNumber));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded book data into the database. For debugging purposes only.
     */
    private void insertBook() {
        // Gets the database in write mode
        SQLiteDatabase db = newLeafDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and book attributes are the values.
        ContentValues values = new ContentValues();
        values.put(NewLeafContract.NewLeafEntry.COLUMN_PRODUCT, "Adventures Into St. Everston");
        values.put(NewLeafContract.NewLeafEntry.COLUMN_PRICE, "1499");
        values.put(NewLeafContract.NewLeafEntry.COLUMN_QUANTITY,"5" );
        values.put(NewLeafContract.NewLeafEntry.COLUMN_SUPPLIER, "Erica");
        values.put(NewLeafContract.NewLeafEntry.COLUMN_PHONE, "8123421795");

        // Insert a new row for bookstore1 into the provider using the ContentResolver.
        // Use the {@link BookEntry#CONTENT_URI} to indicate that we want to insert
        // into the books database table.
        // Receive the new content URI that will allow us to access Bookstore1 data in the future.
        long newRowId = db.insert(NewLeafContract.NewLeafEntry.BOOK, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.CatA_Error_Save_Book), Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, getString(R.string.CatA_Book_Saved_Row_id) + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Helper method to delete all children books in the database.
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertBook();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}