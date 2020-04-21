package com.justice.smartbuyingmallmanagement.user;

import com.justice.smartbuyingmallmanagement.Rating;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserLogin implements Serializable {
    private String email;
    private String password;
    private String type;
    public  List<Rating> UserRatingList = new ArrayList<>();

    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

    public static List<Rating> currentUserRatingList = new ArrayList<>();

    public UserLogin(String email, String password, List<Rating> userRatingList) {
        this.email = email;
        this.password = password;
        this.currentUserRatingList = userRatingList;
    }

    public UserLogin() {

    }

    public List<Rating> getUserRatingList() {
        return UserRatingList;
    }

    public void setUserRatingList(List<Rating> userRatingList) {
        UserRatingList = userRatingList;
    }

    public static List<Rating> getCurrentUserRatingList() {
        return currentUserRatingList;
    }

    public static void setCurrentUserRatingList(List<Rating> currentUserRatingList) {
        UserLogin.currentUserRatingList = currentUserRatingList;
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
