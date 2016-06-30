package com.learninghorizon.adwise.home.coupon;

/**
 * Created by ramnivasindani on 4/24/16.
 */

public class Coupon implements Comparable{

    private String couponId;


    private int discount;


    private String code;


    private int couponStartTime;

    private long couponActiveTime;
    private int duration;


    private String description;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCouponStartTime() {
        return couponStartTime;
    }

    public void setCouponStartTime(int couponStartTime) {
        this.couponStartTime = couponStartTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Object coupon) {
        if( null != coupon && coupon instanceof Coupon){
            return this.couponStartTime > ((Coupon) coupon).getCouponStartTime() ? 1 : 0;
        }
        return 0;
    }

    public long getCouponActiveTime() {
        return couponActiveTime;
    }

    public void setCouponActiveTime(long couponActiveTime) {
        this.couponActiveTime = couponActiveTime;
    }
}

