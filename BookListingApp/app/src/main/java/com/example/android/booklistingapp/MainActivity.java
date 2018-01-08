package com.example.android.booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    /**
     * Adapter for the list of earthquakes
     */
    private BookAdapter mAdapter;
    /**
     * URL for Book data from the Google APIs
     */
    private static final String GOOGLEBOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private TextView empty_list;
    private ProgressBar bar;
    private EditText query;
    private String strQuery;
    private Button btnSearch;
    private static String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        empty_list = (TextView) findViewById(R.id.no_books);
        //At the begining the Progress Bar must not be there
        bar = (ProgressBar) findViewById(R.id.bar);
        bar.setVisibility(View.GONE);
        query = (EditText) findViewById(R.id.src_query);
        btnSearch = (Button) findViewById(R.id.btn_search);
        //this will help to keep the items of the present query
        if (url != "") {
            getLoaderManager().initLoader(0, null, MainActivity.this);
        }
        mAdapter = new BookAdapter(MainActivity.this, new ArrayList<Book>());
        ListView bookListView = (ListView) findViewById(R.id.list);
        bookListView.setEmptyView((TextView) findViewById(R.id.no_books));
        bookListView.setAdapter(mAdapter);
        //When the user select an item, the information about the books is showed
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book currentBook = mAdapter.getItem(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(currentBook.getUrl()));
                startActivity(i);
            }
        });
        //On Click Listener in the search button
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                //Monitoring the state of the network
                ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                //If there is Internet Connection we can continue
                if (isConnected) {
                    if (!mAdapter.isEmpty()) {
                        getLoaderManager().destroyLoader(0);
                    }
                    Log.v("Monitoring ", "Button click");
                    strQuery = query.getText().toString();
                    strQuery = strQuery.replace(" ", "+");
                    Log.v("Query", strQuery);
                    getLoaderManager().initLoader(0, null, MainActivity.this);

                } else {
                    bar.setVisibility(View.GONE);
                    empty_list.setText(R.string.no_connection);
                }
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        //strQuery= "android";
        url = GOOGLEBOOKS_REQUEST_URL + strQuery;
        return new BookLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        empty_list.setText(R.string.empty_view);
        // Clear the adapter of previous book data
        mAdapter.clear();
        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        } else {
            //If we have null results
            getLoaderManager().destroyLoader(0);
        }
        bar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}
