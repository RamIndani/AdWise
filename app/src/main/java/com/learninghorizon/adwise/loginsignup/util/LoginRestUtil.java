package com.learninghorizon.adwise.loginsignup.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.learninghorizon.adwise.loginsignup.AdWiseApplication;
import com.learninghorizon.adwise.loginsignup.LoginSignUpPresenter;
import com.learninghorizon.adwise.util.VolleySingleton;

import org.json.JSONObject;

/**
 * Created by ramnivasindani on 4/10/16.
 */
public class LoginRestUtil {


    private static final String TAG = "LoginRestUtil";
    private static boolean isSuccessful = false;
    public static void networkCall(String url, JSONObject requestData, final LoginSignUpPresenter loginSignUpPresenter, final boolean isLogin){

        Context mContext = AdWiseApplication.getIntance();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "successful");
                if(isLogin) {
                    loginSignUpPresenter.updateLogin(response, true);
                }else{
                    loginSignUpPresenter.updatesignup(response, true);
                }
            }
        },
        new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.d(TAG, "un-successful");
                if(isLogin) {
                    loginSignUpPresenter.updateLogin(null,false);
                }else{
                    loginSignUpPresenter.updatesignup(null, false);
                }
            }
        });
        VolleySingleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest);
    }

}
