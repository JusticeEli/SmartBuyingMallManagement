package com.justice.smartbuyingmallmanagement;

import java.io.Serializable;
import java.util.Objects;

public class Rating implements Serializable {
    private String entity;


    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    private String flag = "original";
    private String businessName;
    private String email;
    private int qualityOfService = 3;
    private int politenessTheKindness = 3;
    private int valueForMoney = 3;
    private int discountPolicyAsset = 3;
    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Rating(String businessName) {
        this.businessName = businessName;
    }

    public Rating(String businessName, String email, int qualityOfService, int politenessTheKindness, int valueForMoney, int discountPolicyAsset) {
        this.businessName = businessName;
        this.email = email;
        this.qualityOfService = qualityOfService;
        this.politenessTheKindness = politenessTheKindness;
        this.valueForMoney = valueForMoney;
        this.discountPolicyAsset = discountPolicyAsset;
    }

    public Rating() {

    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getQualityOfService() {
        return qualityOfService;
    }

    public void setQualityOfService(int qualityOfService) {
        this.qualityOfService = qualityOfService;
    }

    public int getPolitenessTheKindness() {
        return politenessTheKindness;
    }

    public void setPolitenessTheKindness(int politenessTheKindness) {
        this.politenessTheKindness = politenessTheKindness;
    }

    public int getValueForMoney() {
        return valueForMoney;
    }

    public void setValueForMoney(int valueForMoney) {
        this.valueForMoney = valueForMoney;
    }

    public int getDiscountPolicyAsset() {
        return discountPolicyAsset;
    }

    public void setDiscountPolicyAsset(int discountPolicyAsset) {
        this.discountPolicyAsset = discountPolicyAsset;
    }
}
