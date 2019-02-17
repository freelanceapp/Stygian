package com.infobite.stygian.model;

public class CouponModel {
    String id;
    String coupon_code;
    String coupon_exp_date;
    String coupon_detail;
    String coupon_amount;
    String coupon_min_amount;
    String coupon_max_amount;

    public CouponModel() {
    }

    public CouponModel(String id, String coupon_code, String coupon_exp_date, String coupon_detail, String coupon_amount, String coupon_min_amount, String coupon_max_amount) {
        this.id = id;
        this.coupon_code = coupon_code;
        this.coupon_exp_date = coupon_exp_date;
        this.coupon_detail = coupon_detail;
        this.coupon_amount = coupon_amount;
        this.coupon_min_amount = coupon_min_amount;
        this.coupon_max_amount = coupon_max_amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getCoupon_exp_date() {
        return coupon_exp_date;
    }

    public void setCoupon_exp_date(String coupon_exp_date) {
        this.coupon_exp_date = coupon_exp_date;
    }

    public String getCoupon_detail() {
        return coupon_detail;
    }

    public void setCoupon_detail(String coupon_detail) {
        this.coupon_detail = coupon_detail;
    }

    public String getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(String coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public String getCoupon_min_amount() {
        return coupon_min_amount;
    }

    public void setCoupon_min_amount(String coupon_min_amount) {
        this.coupon_min_amount = coupon_min_amount;
    }

    public String getCoupon_max_amount() {
        return coupon_max_amount;
    }

    public void setCoupon_max_amount(String coupon_max_amount) {
        this.coupon_max_amount = coupon_max_amount;
    }
}
