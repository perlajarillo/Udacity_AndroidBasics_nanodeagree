package com.example.android.tulancingotourguideapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsFragment extends Fragment {
    public RestaurantsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_restaurants, container, false);
        final ArrayList<Topic> topics = new ArrayList<Topic>();
        topics.add(new Topic(getString(R.string.title_colonial), getString(R.string.short_desc_colonial)));
        topics.add(new Topic(getString(R.string.title_taho), getString(R.string.short_desc_taho)));
        topics.add(new Topic(getString(R.string.title_novo), getString(R.string.short_desc_novo)));
        topics.add(new Topic(getString(R.string.title_antigua), getString(R.string.short_desc_antigua)));
        topics.add(new Topic(getString(R.string.title_cafe), getString(R.string.short_desc_cafe)));
        TopicAdapter adapter = new TopicAdapter(getActivity(), topics, R.color.category_restaurants);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
        return rootView;
    }

}
