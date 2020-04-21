package com.justice.smartbuyingmallmanagement.admin;

import java.io.Serializable;

public class AdminLogin implements Serializable {
    private String email;
    private String password;

    public AdminLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AdminLogin() {

    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
