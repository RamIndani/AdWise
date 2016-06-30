package com.learninghorizon.adwise.loginsignup;

import android.content.Context;
import android.content.res.AssetFileDescriptor;

import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.Gson;
import com.learninghorizon.adwise.R;
import com.learninghorizon.adwise.home.user.User;
import com.learninghorizon.adwise.home.user.UserDataUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by ramnivasindani on 4/10/16.
 */
public class LoginSignUpPresenter implements LoginSignupObserver{
    private final LoginSignupInterface loginSignupinterface;
    private final LoginSignUpService loginSignUpService;

    public LoginSignUpPresenter(final LoginSignupInterface loginSignupinterface, final LoginSignUpService loginSignUpService) {
        this.loginSignupinterface = loginSignupinterface;
        this.loginSignUpService = loginSignUpService;
    }

    public void login() {
        Context mContext = AdWiseApplication.getIntance();
        String userEmailId = null;
        try {
            userEmailId = UserDataUtil.getInstance().getUserEmailId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != userEmailId) {
            if (!userEmailId.isEmpty()) {
                try {
                    loginSignUpService.login(
                            mContext.getString(R.string.restapi_base_url).
                                    concat(mContext.getString(R.string.restapi_login)),
                            userEmailId,
                            UserDataUtil.getInstance().getPassword(), this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
                String userName = loginSignupinterface.getUserName();
                if (userName.isEmpty()) {
                    loginSignupinterface.setUserNameEmptyError(R.string.user_name_empty);
                    return;
                }

                String password = loginSignupinterface.getPassword();
                if (password.isEmpty()) {
                    loginSignupinterface.setPasswordEmptyError(R.string.password_empty);
                    return;
                }

                //if(!loginSignUpService.isAlreadyLogedIn(userName, password, mContext)) {
                try {
                    loginSignUpService.login(
                            mContext.getString(R.string.restapi_base_url).concat(mContext.getString(R.string.restapi_login)), userName, password, this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //}else{
                //loginSignupinterface.startHomeAcitivity();
                //}
            }

    }


    public void signup() throws JSONException {
        String userName = loginSignupinterface.getUserName();
        if(userName.isEmpty()){
            loginSignupinterface.setUserNameEmptyError(R.string.user_name_empty);
            return;
        }

        String password = loginSignupinterface.getPassword();
        if(password.isEmpty()){
            loginSignupinterface.setPasswordEmptyError(R.string.password_empty);
            return;
        }
        Context mContext = AdWiseApplication.getIntance();
        loginSignUpService.signup(
                mContext.getString(R.string.restapi_base_url).concat(mContext.getString(R.string.restapi_signup)),userName, password,this);

    }

    @Override
    public void updateLogin(JSONObject jsonObject, boolean isSuccess) {
        if(isSuccess){
            User user = new Gson().fromJson(jsonObject.toString(), User.class);
            UserDataUtil.getInstance().setUser(user);
            loginSignUpService.storeUserNamePassword(loginSignupinterface.getUserName(), loginSignupinterface.getPassword());
            loginSignupinterface.startHomeAcitivity();

        }else{
            loginSignupinterface.showLoginError(R.string.login_failed);
        }
    }

    @Override
    public void updatesignup(JSONObject response, boolean isSuccess) {
        if(isSuccess){
            try {
                login();
            } catch (Exception e) {
                loginSignupinterface.showLoginError(R.string.login_failed);
            }
        }else{
            loginSignupinterface.showLoginError(R.string.login_failed);
        }
    }
}
