package com.learninghorizon.adwise.home.user;

/**
 * Created by ramnivasindani on 4/24/16.
 */
public class User {
    private String email;

    private String password;

    //list of favourite coupons
    private String[] favourites;

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
}
