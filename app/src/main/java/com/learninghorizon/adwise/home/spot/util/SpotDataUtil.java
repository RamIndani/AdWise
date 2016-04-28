package com.learninghorizon.adwise.home.spot.util;

import com.learninghorizon.adwise.home.spot.Spot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramnivasindani on 5/1/16.
 */
public class SpotDataUtil {
    private static final SpotDataUtil spotDataUtil = new SpotDataUtil();

    private List<Spot> currentSpots = new ArrayList<Spot>();
    private SpotDataUtil(){

    }

    public static SpotDataUtil getInstance(){
        return spotDataUtil;
    }

    public void addSpot(Spot spot){
        currentSpots.add(spot);
    }

    public List<Spot> getSpots(){
        return currentSpots;
    }

    public void setSpots(List<Spot> spots) {
        this.currentSpots.clear();
        this.currentSpots.addAll(spots);
    }
}
