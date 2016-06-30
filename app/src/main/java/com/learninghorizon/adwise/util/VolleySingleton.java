package com.learninghorizon.adwise.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ramnivasindani on 4/11/16.
 */
public class VolleySingleton {
    private static VolleySingleton volleySingleton = null;
    private RequestQueue mRequestQueue;
    private static Context mContext;
    private VolleySingleton(Context mContext){
        this.mContext = mContext;
        mRequestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public static VolleySingleton getInstance(Context mContext){
        if(null == volleySingleton){
            volleySingleton = new VolleySingleton(mContext);
        }
        return volleySingleton;
    }


    public void addToRequestQueue(Request request){
        mRequestQueue.add(request);
    }

    public void cancelRequest(String tag){
        mRequestQueue.cancelAll(tag);
    }
}
