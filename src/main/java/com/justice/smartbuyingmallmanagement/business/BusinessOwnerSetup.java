package com.justice.smartbuyingmallmanagement.business;

public class BusinessOwnerSetup {
    private String name;
    private String email;
    private String mobile;
    private String userType;
    private String address;
    private String aadharNo;

    public BusinessOwnerSetup() {
    }

    public BusinessOwnerSetup(String name, String email, String mobile, String userType, String address, String aadharNo) {
        this.name = name;
        this.email = email;
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
