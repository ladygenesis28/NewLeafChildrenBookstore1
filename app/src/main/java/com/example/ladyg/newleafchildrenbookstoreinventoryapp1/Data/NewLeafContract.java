package com.example.ladyg.newleafchildrenbookstoreinventoryapp1.Data;

import android.provider.BaseColumns;

public class NewLeafContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private NewLeafContract() {}

    public static final class NewLeafEntry implements BaseColumns {

        //Name of database table for the books
        public final static String BOOK = "books";
        /**
         * Unique ID number for the book (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the book.
         *
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCT ="product_name";

        /**
         * Type: TEXT
         */
        public final static String COLUMN_PRICE = "price";

        /**
         * Type: INTEGER
         */
        public final static String COLUMN_QUANTITY = "quantity";

        /**
         * Type: INTEGER
         */
        public final static String COLUMN_SUPPLIER = "supplier_name";

        /**
         * Type: TEXT
         */
        public final static String COLUMN_PHONE = "supplier_phone_number";

        /**
         * Type: INTEGER
         */
    }

}