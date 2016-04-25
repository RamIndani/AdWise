package com.learninghorizon.adwise.home.offers.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.JsonArray;
import com.learninghorizon.adwise.R;
import com.learninghorizon.adwise.loginsignup.AdWiseApplication;
import com.learninghorizon.adwise.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ramnivasindani on 4/23/16.
 */
public class RestUtil {
    private static final String TAG = "RestUtil";

    public static void getAllSpots(String locationId, final OffersPresenter offersPresenter) {

        Context context = AdWiseApplication.getIntance();
        String url =context.getString(R.string.restapi_base_url).concat(
                context.getString(R.string.restapi_map)+locationId).concat(context.getString(R.string.rest_api_spots));
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "successful");
                offersPresenter.updateSpots(response, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                offersPresenter.updateSpots(null, false);
            }
        }
        );
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void getLocationId(final String uuid, final OffersPresenter offersPresenter) {
        Context context = AdWiseApplication.getIntance();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, context.getString(R.string.restapi_base_url).concat(
                context.getString(R.string.get_location_id)).concat(uuid.toUpperCase()), new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "successful");
                offersPresenter.updateLocationId(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                offersPresenter.updateLocationId("");
            }
        }
        );
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void registerHistory(String beaconId, String userEmailId, final OffersPresenter offersPresenter, final Beacon beacon) {
        Context context = AdWiseApplication.getIntance();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                context.getString(R.string.restapi_base_url).
                        concat(context.getString(R.string.restapi_history)).
                        concat("/").
                        concat(userEmailId).
                        concat("/").
                        concat(beaconId),
                new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "successful");
                offersPresenter.updateOffersForSpot(response, true, beacon);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                offersPresenter.updateOffersForSpot("", false, beacon);
            }
        }
        );
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}

