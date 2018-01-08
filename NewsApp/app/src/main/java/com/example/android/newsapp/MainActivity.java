package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<New>> {
    private static final String GUARDIAN_REQUEST_URL = "http://content.guardianapis.com/search";
    private TextView empty_list;
    private ProgressBar bar;
    private NewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        empty_list = (TextView) findViewById(R.id.no_news);
        bar = (ProgressBar) findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        mAdapter = new NewAdapter(MainActivity.this, new ArrayList<New>());
        ListView newListView = (ListView) findViewById(R.id.list);
        newListView.setEmptyView((TextView) findViewById(R.id.no_news));
        newListView.setAdapter(mAdapter);
        ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        //If there is Internet Connection we can continue
        if (isConnected) {
            if (!mAdapter.isEmpty()) {
                getLoaderManager().destroyLoader(0);
            }
            getLoaderManager().initLoader(0, null, this);

        } else {
            mAdapter.clear();
            bar.setVisibility(View.GONE);
            empty_list.setText(R.string.no_connection);
        }
        newListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                New currentNew = mAdapter.getItem(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(currentNew.getWebUrl()));
                startActivity(i);
            }
        });

    }

    @Override
    public Loader<List<New>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String topic = sharedPrefs.getString(
                getString(R.string.settings_topic_key),
                getString(R.string.settings_topic_default));
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("q", topic);
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("api-key", "784d9a12-c616-4c7a-9235-319043576acf");
        return new NewLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<New>> loader, List<New> news) {
        empty_list.setText(R.string.empty_view);
        // Clear the adapter of previous new data
        mAdapter.clear();
        // If there is a valid list of {@link New}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        } else {
            //If we have null results
            getLoaderManager().destroyLoader(0);
        }
        bar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<New>> loader) {
        // Clear the adapter of previous new data
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

