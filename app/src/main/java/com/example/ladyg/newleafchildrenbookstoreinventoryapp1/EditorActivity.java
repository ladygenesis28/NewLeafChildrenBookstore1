package com.example.ladyg.newleafchildrenbookstoreinventoryapp1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ladyg.newleafchildrenbookstoreinventoryapp1.Data.NewLeafContract;
import com.example.ladyg.newleafchildrenbookstoreinventoryapp1.Data.NewLeafDbHelper;

public class EditorActivity extends AppCompatActivity {
    
    //EditText field to enter the book name
    private EditText mNameEditText;

    //EditText field to enter the book price
    private EditText mPriceEditText;

    //EditText field to enter the book quantity
    private EditText mQuantityEditText;

    // EditText field to enter the supplier's name
    private EditText mSupplierNameEditText;

    //EditText field to enter the supplier's phone number
    private EditText mSupplierPhoneNumberEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_product_name);
        mPriceEditText = (EditText) findViewById(R.id.edit_book_price);
        mQuantityEditText = (EditText) findViewById(R.id.edit_book_quantity);
        mSupplierNameEditText = (EditText) findViewById(R.id.edit_supplier_name);
        mSupplierPhoneNumberEditText = (EditText) findViewById(R.id.edit_supplier_phone_number);

    }

    private void insertBook() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String suppliernameString = mSupplierNameEditText.getText().toString().trim();
        String supplierphonenumberString = mSupplierPhoneNumberEditText.getText().toString().trim();
        int priceInteger = Integer.parseInt(priceString);
        int quantityInteger = Integer.parseInt(quantityString);

        // Create database helper
        NewLeafDbHelper newLeafDbHelper = new NewLeafDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = newLeafDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and book attributes are the values.
        ContentValues values = new ContentValues();
        values.put(NewLeafContract.NewLeafEntry.COLUMN_PRODUCT_NAME, nameString);
        values.put(NewLeafContract.NewLeafEntry.COLUMN_PRICE, priceInteger);
        values.put(NewLeafContract.NewLeafEntry.COLUMN_QUANTITY,quantityInteger );
        values.put(NewLeafContract.NewLeafEntry.COLUMN_SUPPLIER_NAME, suppliernameString);
        values.put(NewLeafContract.NewLeafEntry.COLUMN_PHONE_NUMBER, supplierphonenumberString);

        // Insert a new row for bookstore1 into the provider using the ContentResolver.
        // Use the {@link BookEntry#CONTENT_URI} to indicate that we want to insert
        // into the books database table.
        // Receive the new content URI that will allow us to access Bookstore1 data in the future.
        long newRowId = db.insert(NewLeafContract.NewLeafEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving a book", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Book saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save book to database
                insertBook();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}