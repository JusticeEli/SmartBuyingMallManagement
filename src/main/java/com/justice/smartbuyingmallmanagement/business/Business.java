package com.justice.smartbuyingmallmanagement.business;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Business implements Serializable {

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Business(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessOwnerEmail() {
        return businessOwnerEmail;
    }

    public void setBusinessOwnerEmail(String businessOwnerEmail) {
        this.businessOwnerEmail = businessOwnerEmail;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    private String objectId;
    private String businessOwnerEmail;

    private int regId;
    private String entity;

    public int getRegId() {
        return regId;
    }

    public void setRegId(int regId) {
        this.regId = regId;
    }

    private String status;
    private String businessName;
    private String offer;
    private String productServiceDescription;
    private String offerUpto;
    private String address;
    private String city;
    private String locality;
    private String mobile;
    private String lat;

    public Business() {

    }


    public Business(int regId, String status, String businessName, String offer, String productServiceDescription, String offerUpto, String address, String city, String locality, String mobile, String lat, String longtide) {
        this.regId = regId;
        this.status = status;
        this.businessName = businessName;
        this.offer = offer;
        this.productServiceDescription = productServiceDescription;
        this.offerUpto = offerUpto;
        this.address = address;
        this.city = city;
        this.locality = locality;
        this.mobile = mobile;
        this.lat = lat;

        this.longtide = longtide;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getLongtide() {
        return longtide;
    }

    public void setLongtide(String longtide) {
        this.longtide = longtide;
    }

    private String longtide;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }


    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getProductServiceDescription() {
        return productServiceDescription;
    }

    public void setProductServiceDescription(String productServiceDescription) {
        this.productServiceDescription = productServiceDescription;
    }

    public String getOfferUpto() {
        return offerUpto;
    }

    public void setOfferUpto(String offerUpto) {
        this.offerUpto = offerUpto;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
