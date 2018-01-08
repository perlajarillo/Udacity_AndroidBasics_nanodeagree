package com.example.android.booklistingapp;

/**
 * Created by PerlaIvet on 18/12/2017.
 */

public class Book {
    private String mTitle;
    private String mSubtitle;
    private String mAuthor;
    private String mUrl;
    private String mSaleability;

    public Book(String title, String subtitle, String author, String url, String saleability) {
        mTitle = title;
        mSubtitle = subtitle;
        mAuthor = author;
        mUrl = url;
        mSaleability = saleability;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getSaleability() {
        return mSaleability;
    }
}
