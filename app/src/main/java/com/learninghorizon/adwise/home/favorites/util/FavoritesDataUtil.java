package com.learninghorizon.adwise.home.favorites.util;

import com.learninghorizon.adwise.home.favorites.FavoritesFragment;
import com.learninghorizon.adwise.home.spot.Spot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ramnivasindani on 5/1/16.
 */
public class FavoritesDataUtil {
    private static final FavoritesDataUtil favoritesDataUtil = new FavoritesDataUtil();
    private final List<Spot> favorites = new ArrayList<>();
    public static FavoritesDataUtil getInstance(){
        return favoritesDataUtil;
    }

    public void addFavorite(final Spot spot){
        if(null != spot) {
            favorites.add(spot);
        }
    }

    public List<Spot> getFavorites(){
        return favorites;
    }

    public void removeFavorite(String beaconId) {
        Iterator itr = favorites.iterator();
        while(itr.hasNext()){
            if(itr.next().equals(beaconId)){
                itr.remove();
                break;
            }
        }
        FavoritesFragment.LoadFavorites();
        FavoritesFragment.notifyAdapter();
    }
}
