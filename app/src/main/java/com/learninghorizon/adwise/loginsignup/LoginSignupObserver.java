package com.learninghorizon.adwise.loginsignup;

import org.json.JSONObject;

/**
 * Created by ramnivasindani on 4/15/16.
 */
public interface LoginSignupObserver {

    public void updateLogin(JSONObject response, boolean isSuccessful);
    public void updatesignup(JSONObject response, boolean isSuccessful);
}
