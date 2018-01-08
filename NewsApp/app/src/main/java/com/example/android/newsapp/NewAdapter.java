package com.example.android.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PerlaIvet on 20/12/2017.
 */
public class NewAdapter extends ArrayAdapter<New> {
    public NewAdapter(Activity context, ArrayList<New> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        New currentNew = getItem(position);
        //Title Text View
        TextView titleTextView = (TextView) convertView.findViewById(R.id.title_text_view);
        titleTextView.setText(currentNew.getWebTitle());
        //Section Name Text View
        TextView sectionTextView = (TextView) convertView.findViewById(R.id.section_text_view);
        sectionTextView.setText(currentNew.getSectionName());
        //Date of publication Text View
        TextView dateTextView = (TextView) convertView.findViewById(R.id.date_text_view);
        dateTextView.setText(currentNew.getWebPublicationDate().substring(0, 10) + " " + currentNew.getWebPublicationDate().substring(11, 19));
        //Author Text View, if there is some
        TextView authorTextView = (TextView) convertView.findViewById(R.id.author_text_view);
        authorTextView.setText(currentNew.getAuthor());
        return convertView;
    }
}
