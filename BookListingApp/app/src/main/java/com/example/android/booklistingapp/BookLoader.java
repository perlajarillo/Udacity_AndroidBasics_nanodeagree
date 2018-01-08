package com.example.android.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by PerlaIvet on 18/12/2017.
 */

public class BookLoader extends AsyncTaskLoader {
    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<Book> result = QueryUtils.fetchBookData(mUrl);
        return result;
    }
}
