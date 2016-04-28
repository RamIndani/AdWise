package com.learninghorizon.adwise.home.offers;

import com.learninghorizon.adwise.home.spot.Spot;

/**
 * Created by ramnivasindani on 5/1/16.
 */
public class OffersDataUtil {
    private static final OffersDataUtil offersDataUtil = new OffersDataUtil();
    private Spot currentSpot;
    private OffersDataUtil(){

    }

    public static OffersDataUtil getInstance(){
        return offersDataUtil;
    }


    public Spot getCurrentSpot() {
        return currentSpot;
    }

    public void setCurrentSpot(Spot currentSpot) {
        this.currentSpot = currentSpot;
    }

    public void clear() {
        currentSpot = null;
    }
}
