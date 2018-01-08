package com.example.android.tulancingotourguideapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by PerlaIvet on 19/11/2017.
 */

public class TouristFragmentAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[4];

    public TouristFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        tabTitles[0] = context.getResources().getString(R.string.category_info);
        tabTitles[1] = context.getResources().getString(R.string.category_places);
        tabTitles[2] = context.getResources().getString(R.string.category_around);
        tabTitles[3] = context.getResources().getString(R.string.category_restaurants);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new InfoFragment();
        } else if (position == 1) {
            return new PlacesFragment();
        } else if (position == 2) {
            return new AroundFragment();
        } else {
            return new RestaurantsFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
