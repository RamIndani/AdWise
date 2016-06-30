package com.learninghorizon.adwise.loginsignup;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.learninghorizon.adwise.R;
import com.learninghorizon.adwise.home.user.User;
import com.learninghorizon.adwise.home.user.UserDataUtil;
import com.learninghorizon.adwise.loginsignup.util.LoginRestUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ramnivasindani on 4/10/16.
 */
public class LoginSignUpService {
    public void login(String url, String userName, String password, LoginSignUpPresenter loginSignUpPresenter) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email",userName);
        jsonObject.put("password", password);
        LoginRestUtil.networkCall(url, jsonObject,loginSignUpPresenter, true);
    }

    public void signup(String url, String userName, String password, LoginSignUpPresenter loginSignUpPresenter) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email",userName);
        jsonObject.put("password", password);
        LoginRestUtil.networkCall(url, jsonObject,loginSignUpPresenter, false);
    }

    public void storeUserNamePassword(@NonNull String userName, @NonNull String password) {
        if(!userName.isEmpty() && !password.isEmpty()) {
            SharedPreferences adWisePreferences = AdWiseApplication.getIntance().getSharedPreferences(
                    AdWiseApplication.getIntance().getString(R.string.adwise_shared_preferences), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = adWisePreferences.edit();
            editor.putString("userName", userName);
            editor.putString("password", password);
            editor.commit();
        }
    }

    public boolean isAlreadyLogedIn(@NonNull  String userName, @NonNull  String password, @NonNull Context mContext) {
        boolean isLoggedIn = false;
        SharedPreferences adWisePreferences = AdWiseApplication.getIntance().getSharedPreferences(
                AdWiseApplication.getIntance().getString(R.string.adwise_shared_preferences), Context.MODE_PRIVATE);
        if(null != adWisePreferences) {
            String userNameShared = adWisePreferences.getString("userName", userName);
            String passwordShared = adWisePreferences.getString("password", password);
            if (userNameShared != null && passwordShared!= null && !userNameShared.equals(userName) && !passwordShared.equals(password)) {
                isLoggedIn = true;
            }else{
                removeSharedPreferences();
            }
        }
        return isLoggedIn;
    }

    public boolean isAlreadyLogedIn() {
        boolean isLoggedIn = false;
        SharedPreferences adWisePreferences = AdWiseApplication.getIntance().getSharedPreferences(
                AdWiseApplication.getIntance().getString(R.string.adwise_shared_preferences), Context.MODE_PRIVATE);
        if(null != adWisePreferences) {
            String userNameShared = adWisePreferences.getString("userName", "");
            String passwordShared = adWisePreferences.getString("password", "");
            if (userNameShared != null && passwordShared!= null && !userNameShared.isEmpty() && !passwordShared.isEmpty()) {
                isLoggedIn = true;
                User user = new User();
                user.setEmail(userNameShared);
                user.setPassword(passwordShared);
                UserDataUtil.getInstance().setUser(user);
            }else{
                removeSharedPreferences();
            }
        }
        return isLoggedIn;
    }

    private void removeSharedPreferences() {
        SharedPreferences adWisePreferences = AdWiseApplication.getIntance().getSharedPreferences(
                AdWiseApplication.getIntance().getString(R.string.adwise_shared_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = adWisePreferences.edit();
        editor.remove("userName");
        editor.remove("password");
    }
}
