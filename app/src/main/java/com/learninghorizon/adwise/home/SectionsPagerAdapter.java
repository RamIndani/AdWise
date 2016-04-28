package com.learninghorizon.adwise.home;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.learninghorizon.adwise.R;
import com.learninghorizon.adwise.home.favorites.FavorFragment;
import com.learninghorizon.adwise.home.favorites.FavoritesFragment;
import com.learninghorizon.adwise.home.offers.OffersMapFragment;

/**
 * Created by ramnivasindani on 4/14/16.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    Context mContext;
    public SectionsPagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }



    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new OffersMapFragment();
            case 1:
                return new FavoritesFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    String title;

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                title = mContext.getString(R.string.offers);
                break;
            case 1:
                title = mContext.getString(R.string.favorites);
                break;
            default:
                break;
        }

        return title;
    }
}
