package com.example.android.tulancingotourguideapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PerlaIvet on 19/11/2017.
 */

public class TopicAdapter extends ArrayAdapter<Topic> {
    public int mcolorResourceId;

    public TopicAdapter(Activity context, ArrayList<Topic> topics, int colorId) {
        super(context, 0, topics);
        mcolorResourceId = colorId;
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
        LinearLayout textContainer = (LinearLayout) listItemView.findViewById(R.id.text_container);
        textContainer.setBackgroundResource(mcolorResourceId);
        // Get the {@link topic} object located at this position in the list
        Topic currentTopic = getItem(position);
        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
        titleTextView.setText(currentTopic.getTitle());
        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.description_text_view);
        descriptionTextView.setText(currentTopic.getShortDescription());
        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.imageView);
        if (currentTopic.hasImage()) {
            // Get the image resource ID from the current word object and
            // set the image to iconView
            iconView.setImageResource(currentTopic.getImageResourceId());
            iconView.setVisibility(View.VISIBLE);

        } else {
            iconView.setVisibility(View.GONE);
        }
        // Return the whole list item layout (containing 2 TextViews and an ImageView when it applies)
        // so that it can be shown in the ListView
        return listItemView;
    }

}
