package com.example.android.musicestructure;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This is a pretty cool example I found at
 * http://www.codigojavalibre.com/2015/10/crear-un-listview-con-imagenes-en-Android-Studio.html
 */
public class ArtistListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] itemname;
    private final Integer[] integers;

    public ArtistListAdapter(Activity context, String[] itemname, Integer[] integers) {
        super(context, R.layout.list_row, itemname);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.itemname = itemname;
        this.integers = integers;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_row, null, true);
        TextView txtArtist = (TextView) rowView.findViewById(R.id.artist_name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        txtArtist.setText(itemname[position]);
        imageView.setImageResource(integers[position]);
        return rowView;
    }
}