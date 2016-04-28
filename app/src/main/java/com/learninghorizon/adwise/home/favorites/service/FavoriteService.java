package com.learninghorizon.adwise.home.favorites.service;

import com.learninghorizon.adwise.home.favorites.util.FavoritesPresenter;
import com.learninghorizon.adwise.home.favorites.util.RestUtil;
import com.learninghorizon.adwise.home.spot.Spot;

/**
 * Created by ramnivasindani on 5/1/16.
 */
public class FavoriteService {

    public void saveFavorite(FavoritesPresenter favoritesPresenter, Spot spot, String userEmailId){
        RestUtil.saveFavorites(favoritesPresenter, spot, userEmailId);
    }
}
