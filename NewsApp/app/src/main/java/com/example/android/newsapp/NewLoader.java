package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by PerlaIvet on 20/12/2017.
 */

public class NewLoader extends AsyncTaskLoader {
    private String mUrl;

    public NewLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<New> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<New> result = QueryUtils.fetchNewData(mUrl);
        return result;
    }
}
