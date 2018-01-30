package com.example.android.cupboardinventoryapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.cupboardinventoryapp.data.ProductContract.ProductEntry;
import com.example.android.cupboardinventoryapp.data.ProductDbHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;


public class ProductEditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int PRODUCT_LOADER = 0;
    private boolean mProductHasChanged = false;
    private boolean name_is_null = true;
    private boolean price_is_null = true;
    private boolean quantity_is_null = true;
    private boolean image_is_null = true;
    ProductDbHelper mDbHelper;
    String[] projection = null;
    /**
     * EditText field to enter the product's name
     */
    private EditText mNameEditText;
    /**
     * EditText field to enter the product's quantity
     */
    private EditText mQuantityEditText;
    /**
     * EditText field to enter the product's price
     */
    private EditText mPriceEditText;
    /**
     * ImageView representing the product's image
     */
    private ImageView mImageView;
    /**
     * Button that allows the user to explore the image gallery in order to pick an image
     */
    private Button mExploreButton;
    /**
     * Button that allows the user to decrease the quantity by one
     */
    private Button mMinusButton;
    /**
     * Button that allows the user to increase the quantity by one
     */
    private Button mPlusButton;
    /**
     * Button that allows the user to order supplies
     */
    private ImageButton mOrderButton;
    private static final int READ_REQUEST_CODE = 42;
    private Uri uri;
    ContentValues values = new ContentValues();
    int current_quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_editor);
        // Find all views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_product_name);
        mQuantityEditText = (EditText) findViewById(R.id.edit_product_quantity);
        mPriceEditText = (EditText) findViewById(R.id.edit_product_price);
        mExploreButton = (Button) findViewById(R.id.btn_explore);
        mMinusButton = (Button) findViewById(R.id.btn_minus);
        mPlusButton = (Button) findViewById(R.id.btn_plus);
        mImageView = (ImageView) findViewById(R.id.product_img);
        mOrderButton = findViewById(R.id.btn_order);
        //setting an onTouchListener at the views, so we can know when there are changes
        mNameEditText.setOnTouchListener(mTouchListener);
        mQuantityEditText.setOnTouchListener(mTouchListener);
        mPriceEditText.setOnTouchListener(mTouchListener);
        mExploreButton.setOnTouchListener(mTouchListener);
        mMinusButton.setOnTouchListener(mTouchListener);
        mPlusButton.setOnTouchListener(mTouchListener);
        //In case the user type a quantity instead using the buttons
        mQuantityEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0)
                    current_quantity = Integer.parseInt(mQuantityEditText.getText().toString());
                else
                    current_quantity = 0;
            }
        });
        mOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(getString(R.string.title_inputDialog));
                // Set up the input
                final EditText input = new EditText(view.getContext());
                // Specifying the type of input is an integer number
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int units = Integer.parseInt(input.getText().toString());
                        String message = getString(R.string.mail_message) + "\n \n" +
                                getString(R.string.products_supplie) +
                                mNameEditText.getText().toString() + "\n" +
                                getString(R.string.units_supplie) + units + "\n \n" +
                                getString(R.string.mail_end);
                        String[] TO = {getString(R.string.mail_to)};
                        Intent orderSupplierIntent = new Intent(Intent.ACTION_SEND);
                        orderSupplierIntent.setData(Uri.parse("mailto:"));
                        orderSupplierIntent.setType("text/plain");
                        orderSupplierIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        orderSupplierIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject));
                        orderSupplierIntent.putExtra(Intent.EXTRA_TEXT, message);

                        try {
                            startActivity(Intent.createChooser(orderSupplierIntent, "Sending order..."));
                            finish();
                            Log.v("Finished sending mail.", "");
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });
        //Listener for the onClick event at the button to explore at the gallery
        mExploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
                // browser.
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                // Filter to only show results that can be "opened", such as a
                // file (as opposed to a list of contacts or timezones)
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                // Filter to show only images, using the image MIME data type.
                intent.setType("image/*");
                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });

        //Listener for the onClick event at the btn_minus to decrease the quantity
        mMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_quantity > 0) {
                    current_quantity--;
                }
                setCurrentQuantity();
            }
        });
        //Listener for the onClick event at the btn_plus to increase the quantity
        mPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_quantity < 100) {
                    current_quantity++;
                }
                setCurrentQuantity();
            }
        });
        mDbHelper = new ProductDbHelper(this);
        uri = getIntent().getData();
        if (uri == null) {
            invalidateOptionsMenu();
            setTitle(getString(R.string.add_product));
        } else {
            setTitle(getString(R.string.edit_product));
            getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
        }
    }

    public void setCurrentQuantity() {
        mQuantityEditText.setText(String.valueOf(current_quantity));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE.
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // We pull the URI that represents the image selected using resultData.getData().
            Uri imageUri = null;
            if (resultData != null) {
                imageUri = resultData.getData();
                Log.i("Editor Activity", "Uri: " + imageUri.toString());
                showImage(imageUri);
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri imageUri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(imageUri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public void showImage(Uri imageUri) {
        Bitmap image = null;
        try {
            image = getBitmapFromUri(imageUri);
            if (image != null) {
                //Preparing image to be inserted in the database
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, out);
                values.put(ProductEntry.COLUMN_PRODUCT_IMAGE, out.toByteArray());
                mImageView.setImageBitmap(image);
                image_is_null = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_product_editor.xml file.
        getMenuInflater().inflate(R.menu.menu_product_editor, menu);
        // If this is a new product, hide the "Delete" menu item.
        if (uri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    //Method to save a product
    private int saveProduct() {
        if (!mNameEditText.getText().toString().equals("")) {
            String product_name = mNameEditText.getText().toString().trim();
            values.put(ProductEntry.COLUMN_PRODUCT_NAME, product_name);
            name_is_null = false;
        } else {
            Toast.makeText(this, getString(R.string.name_requiered),
                    Toast.LENGTH_SHORT).show();
            name_is_null = true;
        }
        if (!mPriceEditText.getText().toString().equals("")) {
            float product_price = Float.parseFloat(mPriceEditText.getText().toString().trim());
            values.put(ProductEntry.COLUMN_PRODUCT_PRICE, product_price);
            price_is_null = false;
        } else {
            Toast.makeText(this, getString(R.string.price_required),
                    Toast.LENGTH_SHORT).show();
            price_is_null = true;
        }
        String quantityEditText = mQuantityEditText.getText().toString();
        if (!quantityEditText.equals("")) {
            if (Integer.parseInt(quantityEditText) <= 0 || Integer.parseInt(quantityEditText) > 100) {
                Toast.makeText(this, getString(R.string.allowed_quantity_required),
                        Toast.LENGTH_SHORT).show();
                quantity_is_null = true;
            } else {
                int product_quantity = Integer.parseInt(mQuantityEditText.getText().toString().trim());
                values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, product_quantity);
                quantity_is_null = false;
            }
        } else {
            Toast.makeText(this, getString(R.string.quantity_required),
                    Toast.LENGTH_SHORT).show();
            quantity_is_null = true;

        }

        if (!name_is_null && !price_is_null && !quantity_is_null) {
            if (uri == null) {
                if (!image_is_null) {
                    Uri newUri = getContentResolver().insert(ProductEntry.CONTENT_URI, values);
                    Log.v("CatalogActivity", "New Row ID:" + newUri);
                    // Show a toast message depending on whether or not the insertion was successful
                    if (newUri == null) {
                        // If the new content URI is null, then there was an error with insertion.
                        Toast.makeText(this, getString(R.string.insert_product_failed),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // Otherwise, the insertion was successful.
                        Toast.makeText(this, getString(R.string.insert_product_successful),
                                Toast.LENGTH_SHORT).show();
                    }

                    return 1;
                } else
                    Toast.makeText(this, getString(R.string.image_required),
                            Toast.LENGTH_SHORT).show();
                return 0;
            } else {
                // Otherwise this is an EXISTING product and we want to update it.
                int updateProduct = getContentResolver().update(uri, values, null, null);
                if (updateProduct == 0) {
                    //if the updateProduct is equal to 0, then there was an error with the updating
                    Toast.makeText(this, getString(R.string.update_product_failed),
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the update was successful and we can display a toast.
                    Toast.makeText(this, getString(R.string.update_product_successful),
                            Toast.LENGTH_SHORT).show();
                }
                return 1;
            }
        } else
            return 0;
    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mProductHasChanged = true;
            return false;
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Saving product
                if (saveProduct() == 1) {
                    finish();
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                //showing the delete confirmation dialog
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the product hasn't changed, continue with navigating up to parent activity
                if (!mProductHasChanged) {
                    NavUtils.navigateUpFromSameTask(ProductEditorActivity.this);
                    return true;
                }
                // If there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(ProductEditorActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Method that shows a dialog message to alert about unsaved changes in the form
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Continue editing
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        //If there are no changes, go back
        if (!mProductHasChanged) {
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };
        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Calling method to delete the product.
                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the product.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //Delete the product from the database
    private void deleteProduct() {
        if (uri != null) {
            int deletedProduct = getContentResolver().delete(uri, null, null);
            if (deletedProduct == 0) {
                //Something fails and the product was not deleted.
                Toast.makeText(this, getString(R.string.delete_product_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // the deleted was successful.
                Toast.makeText(this, getString(R.string.delete_product_successful),
                        Toast.LENGTH_SHORT).show();
                // Close the activity
                finish();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.v("actual URI", " : " + uri);
        return new CursorLoader(this, uri,
                projection, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            // Extract properties from cursor
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_NAME));
            float price = cursor.getFloat(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PRICE));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUANTITY));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_IMAGE));
            // Populate fields with extracted properties
            mNameEditText.setText(name);
            mQuantityEditText.setText(String.valueOf(quantity));
            current_quantity = quantity;
            mPriceEditText.setText(String.valueOf(price));
            /*Decoding the image bytes in a bitmap so we can show it in the ImageView*/
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            mImageView.setImageBitmap(bmp);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNameEditText.setText("");
        mQuantityEditText.setText("");
        mPriceEditText.setText("");
        mImageView.setImageResource(R.drawable.ic_image);
    }
}
