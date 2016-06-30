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
    private List<Coupon> activeCoupons = new ArrayList<Coupon>();

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

    public List<Coupon> getActiveCoupons() {
        return activeCoupons;
    }

    public void setActiveCoupons(List<Coupon> activeCoupons) {
        this.activeCoupons = activeCoupons;
    }

    public void addActiveCoupons(Coupon coupon) {
        activeCoupons.add(coupon);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spot spot = (Spot) o;

        if (beaconId != null ? !beaconId.equals(spot.beaconId) : spot.beaconId != null)
            return false;
        if (uuid != null ? !uuid.equals(spot.uuid) : spot.uuid != null) return false;
        if (majorId != null ? !majorId.equals(spot.majorId) : spot.majorId != null) return false;
        if (minorId != null ? !minorId.equals(spot.minorId) : spot.minorId != null) return false;
        if (locationId != null ? !locationId.equals(spot.locationId) : spot.locationId != null)
            return false;
        if (spotName != null ? !spotName.equals(spot.spotName) : spot.spotName != null)
            return false;
        if (coupons != null ? !coupons.equals(spot.coupons) : spot.coupons != null) return false;
        return activeCoupons != null ? activeCoupons.equals(spot.activeCoupons) : spot.activeCoupons == null;

    }

    @Override
    public int hashCode() {
        int result = beaconId != null ? beaconId.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (majorId != null ? majorId.hashCode() : 0);
        result = 31 * result + (minorId != null ? minorId.hashCode() : 0);
        result = 31 * result + (locationId != null ? locationId.hashCode() : 0);
        result = 31 * result + (spotName != null ? spotName.hashCode() : 0);
        result = 31 * result + (coupons != null ? coupons.hashCode() : 0);
        result = 31 * result + (activeCoupons != null ? activeCoupons.hashCode() : 0);
        return result;
    }

    public boolean contains(Coupon coupon) {
        for(Coupon activeCoupon : activeCoupons){
            if(activeCoupon.getCouponId().equalsIgnoreCase(coupon.getCouponId())){
                return true;
            }
        }
        return false;
    }
}
