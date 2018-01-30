package com.example.android.cupboardinventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cupboardinventoryapp.data.ProductContract.ProductEntry;

/**
 * Created by PerlaIvet on 23/01/2018.
 */

public class ProductCursorAdapter extends CursorAdapter {
    private Uri uri;
    Context mContext;
    TextView txtQuantity;

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
        this.mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView txtName = (TextView) view.findViewById(R.id.product_name);
        txtQuantity = (TextView) view.findViewById(R.id.current_quantity);
        TextView txtPrice = (TextView) view.findViewById(R.id.price);
        //Changing the setFocusable attribute will allow to select the whole item in the view.
        ImageButton sellButton = (ImageButton) view.findViewById(R.id.btn_sell);
        sellButton.setFocusable(false);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                //Getting the button parent row
                View parentRow = (View) view.getParent();
                ListView listView = (ListView) parentRow.getParent();
                //Getting the position of the row
                int position = (Integer) listView.getPositionForView(parentRow);
                //Getting the item id at that position
                long id = listView.getItemIdAtPosition(position);
                //Getting the current quantity of the product.
                TextView quantityView = (TextView) parentRow.findViewById(R.id.current_quantity);
                int current_quantity = Integer.parseInt(quantityView.getText().toString());
                /*The sale can happen only when the product is in stock, that means,
                the quantity is more than 0 */
                if (current_quantity > 0) {
                    current_quantity--;
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, current_quantity);
                    uri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
                    int updateProduct = mContext.getContentResolver().update(uri, values, null, null);
                    if (updateProduct == 0) {
                        //if the updateProduct is equal to 0, then there was an error with the updating
                        Toast.makeText(mContext, view.getContext().getString(R.string.update_product_failed),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // Otherwise, the update was successful and we can display a toast.
                        Toast.makeText(mContext, view.getContext().getString(R.string.update_product_successful),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(view.getContext(), view.getContext().getString(R.string.no_stock), Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_NAME));
        String quantity = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUANTITY)));
        String price = String.valueOf(cursor.getFloat(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PRICE)));
        // Populate fields with extracted properties
        txtName.setText(name);
        txtPrice.setText(price);
        txtQuantity.setText(quantity);
    }
}
