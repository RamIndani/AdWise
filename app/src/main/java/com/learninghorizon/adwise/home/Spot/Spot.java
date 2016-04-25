package com.learninghorizon.adwise.home.spot;

import com.learninghorizon.adwise.home.coupon.Coupon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramnivasindani on 4/23/16.
 */
public class Spot {

    private String beaconId;

    private String uuid;

    private String majorId;

    private String minorId;

    //location identifier in estimote sdk
    private String locationId;

    private String spotName;

    private List<Coupon> coupons = new ArrayList<Coupon>();

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public void addCoupon(Coupon coupon) {
        this.coupons.add(coupon);
    }
    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getMinorId() {
        return minorId;
    }

    public void setMinorId(String minorId) {
        this.minorId = minorId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
