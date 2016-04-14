package com.learninghorizon.adwise.loginsignup;

import android.app.Application;
import android.content.Context;

/**
 * Created by ramnivasindani on 4/13/16.
 */
public class AdWiseApplication extends Application {

    private static Application adwiseApplication;
    @Override
    public void onCreate(){
        super.onCreate();
        adwiseApplication = this;
    }

    public static Application getIntance(){

        return adwiseApplication;
    }
    public Context getContext(){
        return this.getApplicationContext();
    }
}
