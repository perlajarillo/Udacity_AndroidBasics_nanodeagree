package com.example.android.newsapp;

/**
 * Created by PerlaIvet on 20/12/2017.
 */

public class New {
    String mSectionName;
    String mWebPublicationDate;
    String mWebTitle;
    String mWebUrl;
    String mAuthor;

    public New(String sectionName, String webPublicationDate, String webTitle, String webUrl, String author) {
        mSectionName = sectionName;
        mWebPublicationDate = webPublicationDate;
        mWebTitle = webTitle;
        mWebUrl = webUrl;
        mAuthor = author;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getWebPublicationDate() {
        return mWebPublicationDate;
    }

    public String getWebTitle() {
        return mWebTitle;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public String getAuthor() {
        return mAuthor;
    }
}
