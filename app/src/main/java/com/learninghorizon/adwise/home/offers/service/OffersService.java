package com.learninghorizon.adwise.home.offers.service;

import android.support.annotation.NonNull;

import com.estimote.sdk.Beacon;
import com.learninghorizon.adwise.home.offers.util.OffersPresenter;
import com.learninghorizon.adwise.home.offers.util.RestUtil;
import com.learninghorizon.adwise.home.spot.Spot;

import java.util.List;

/**
 * Created by ramnivasindani on 4/23/16.
 */
public class OffersService {

    public void getAllSpots(String locationId, OffersPresenter offersPresenter){
        RestUtil.getAllSpots(locationId, offersPresenter);
    }

    public void getLocationId(String UUID, OffersPresenter offersPresenter){
        RestUtil.getLocationId(UUID, offersPresenter);
    }

    public void recordUserHistory(@NonNull String currentBeaconId, String userEmailId,
                                  OffersPresenter offersPresenter, Beacon beacon) {
        RestUtil.registerHistory(currentBeaconId, userEmailId, offersPresenter, beacon);
    }

    public void getOfferDetails(@NonNull  Beacon currentBeacon) {
    }

    public void getOffer(@NonNull  Beacon currentBeacon) {
    }
}
