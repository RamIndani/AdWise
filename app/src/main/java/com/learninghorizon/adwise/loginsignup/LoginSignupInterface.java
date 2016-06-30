package com.learninghorizon.adwise.loginsignup;

import android.content.Context;

/**
 * Created by ramnivasindani on 4/10/16.
 */
public interface LoginSignupInterface {
    void setUserNameEmptyError(int resId);

    void setPasswordEmptyError(int resId);

    void startHomeAcitivity();

    void showLoginError(int resId);

    void showSignupError(int resId);

    String getUserName();

    String getPassword();

    Context getContext();
}
