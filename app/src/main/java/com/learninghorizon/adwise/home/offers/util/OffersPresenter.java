package com.learninghorizon.adwise.home.offers.util;

import com.estimote.sdk.Beacon;

import org.json.JSONArray;

/**
 * Created by ramnivasindani on 4/23/16.
 */
public interface OffersPresenter {
     void updateLocationId(String locationId);

     void updateSpots(JSONArray response, boolean b);

     void updateOffersForSpot(String response, boolean b, Beacon beacon);
}
