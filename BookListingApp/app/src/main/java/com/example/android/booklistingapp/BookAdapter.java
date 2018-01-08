package com.example.android.booklistingapp;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PerlaIvet on 18/12/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //  return super.getView(position, convertView, parent);
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Book currentBook = getItem(position);
        //Title Text View
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
        titleTextView.setText(currentBook.getTitle());
        //Subtitle Text View
        TextView subtitleTextView = (TextView) listItemView.findViewById(R.id.subtitle_text_view);
        subtitleTextView.setText(currentBook.getSubtitle());
        //Author Text View
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author_text_view);
        authorTextView.setText(currentBook.getAuthor());
        GradientDrawable titleRectangle = (GradientDrawable) titleTextView.getBackground();
        int titleColor = getColor(currentBook.getSaleability());
        // Fetch the background from the TextView, which is a GradientDrawable.
        titleRectangle.setColor(titleColor);
        return listItemView;
    }

    private int getColor(String saleability) {
        int color;
        if (saleability.equals("FOR_SALE")) {
            color = R.color.forSale;
        } else {
            color = R.color.notForSale;
        }
        return ContextCompat.getColor(getContext(), color);
    }
}
