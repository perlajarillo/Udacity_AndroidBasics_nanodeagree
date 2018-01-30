package com.example.android.cupboardinventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by PerlaIvet on 23/01/2018.
 */

public class ProductContract {
    //Content Authority which is used to help identify the Content Provider
    public static final String CONTENT_AUTHORITY = "com.example.android.cupboardinventoryapp";
    //usable URI
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    //This constant store the path for each of the tables which will be appended to the base content URI.
    public static final String PATH_INVENTORY = "inventory";

    public static abstract class ProductEntry implements BaseColumns {
        //The content URI to access the product data in the provider
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);
        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of products at the inventory.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single product.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;
        //Name of the table
        public static final String TABLE_NAME = "products";
        //Name of the columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
        public static final String COLUMN_PRODUCT_IMAGE = "image";
    }
}

