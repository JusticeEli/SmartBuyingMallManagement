package com.justice.smartbuyingmallmanagement.business;

import java.io.Serializable;

public class BusinessOwner implements Serializable {
    private String name;
    private String email;
    private String password;
    private String mobile;
    private String userType;
    private String address;
    private String aadharNo;

    public BusinessOwner() {

    }
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BusinessOwner(String name, String email, String password, String mobile, String userType, String address, String aadharNo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.userType = userType;
        this.address = address;
        this.aadharNo = aadharNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }
}
