package com.learninghorizon.adwise.loginsignup;

/**
 * Created by ramnivasindani on 4/15/16.
 */
public interface LoginSignupObserver {

    public void updateLogin(boolean isSuccessful);
    public void updatesignup(boolean isSuccessful);
}
