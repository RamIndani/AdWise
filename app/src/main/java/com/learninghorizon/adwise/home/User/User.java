package com.learninghorizon.adwise.home.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramnivasindani on 4/24/16.
 */
public class User {
    private String email;

    private String password;

    //list of favourite coupons
    private List<String> favorites = new ArrayList<String>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getFavourites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }
    public void setFavourites(List<String> favourites) {
        this.favorites = favourites;
    }
}
