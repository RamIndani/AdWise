package com.learninghorizon.adwise.home.favorites.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.learninghorizon.adwise.R;
import com.learninghorizon.adwise.home.favorites.FavoritesFragment;
import com.learninghorizon.adwise.home.spot.Spot;
import com.learninghorizon.adwise.loginsignup.AdWiseApplication;
import com.learninghorizon.adwise.util.VolleySingleton;

import org.json.JSONArray;

/**
 * Created by ramnivasindani on 5/1/16.
 */
public class RestUtil {

    private static final String TAG = "Favorites/RestUtil";
    public static void saveFavorites(final FavoritesPresenter favoritesPresenter, final Spot spot,
                              final String userId){
        Context context = AdWiseApplication.getIntance();
        String url =context.getString(R.string.restapi_base_url).concat(
                context.getString(R.string.restapi_signup)+"/"+userId).concat(context.getString(R.string.rest_api_favorites))
                .concat(spot.getBeaconId());
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "successful");
                favoritesPresenter.updateFavorites(spot,true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                favoritesPresenter.updateFavorites(spot, false);
            }
        }
        );
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void removeFavorites(final Spot spot, final String userId) {

        final Context context = AdWiseApplication.getIntance();
        String url =context.getString(R.string.restapi_base_url).concat(
                context.getString(R.string.restapi_signup)+"/"+userId).concat(context.getString(R.string.rest_api_favorites))
                .concat(spot.getBeaconId());
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "successful");
                FavoritesDataUtil.getInstance().removeFavorite(spot.getBeaconId());
                FavoritesFragment.LoadFavorites();
                FavoritesFragment.notifyAdapter();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Unable to remove favorites", Toast.LENGTH_SHORT).show();
            }
        }
        );
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
