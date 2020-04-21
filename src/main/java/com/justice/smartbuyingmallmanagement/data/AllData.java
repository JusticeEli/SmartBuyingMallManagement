package com.justice.smartbuyingmallmanagement.data;

import android.os.Environment;

import com.backendless.BackendlessUser;
import com.justice.smartbuyingmallmanagement.Rating;
import com.justice.smartbuyingmallmanagement.admin.AdminLogin;
import com.justice.smartbuyingmallmanagement.business.Business;
import com.justice.smartbuyingmallmanagement.business.BusinessOwner;
import com.justice.smartbuyingmallmanagement.business.BusinessOwnerLogin;
import com.justice.smartbuyingmallmanagement.user.UserLogin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AllData {

    public static BackendlessUser user;


    public static UserLogin currentUserLoggedIn;
    public static String path;

    public static List<BusinessOwner> businessOwnerList = new ArrayList<>();
    public static List<Business> businessList = new ArrayList<>();
    public static List<BusinessOwnerLogin> businessOwnerLoginList = new ArrayList<>();
    public static List<AdminLogin> adminLoginList = new ArrayList<>();
    public static List<UserLogin> userLoginList = new ArrayList<>();
    public static List<Rating> ratingList = new ArrayList<>();


    public   static void createDirectory() {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/smartBuyingMallManagement_directory";
        File dir = new File(path);
        dir.mkdirs();

    }

    public static void readAllDataFromFiles() {
        readBusinessOwner();
        readBusiness();
        readBusinessOwnerLogin();
        readAdminLogin();
        readUserLogin();
        readRating();
    }

    private static void readRating() {
        try {
            FileInputStream fileInputStream = new FileInputStream(path + "/rating.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ratingList = (ArrayList<Rating>) objectInputStream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void readUserLogin() {
        try {
            FileInputStream fileInputStream = new FileInputStream(path + "/user_login.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            userLoginList = (ArrayList<UserLogin>) objectInputStream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void readAdminLogin() {
        try {
            FileInputStream fileInputStream = new FileInputStream(path + "/admin_login.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            adminLoginList = (ArrayList<AdminLogin>) objectInputStream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void readBusinessOwnerLogin() {
        try {
            FileInputStream fileInputStream = new FileInputStream(path + "/business_owner_login.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            businessOwnerLoginList = (ArrayList<BusinessOwnerLogin>) objectInputStream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void readBusiness() {
        try {
            FileInputStream fileInputStream = new FileInputStream(path + "/business.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            businessList = (ArrayList<Business>) objectInputStream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void readBusinessOwner() {
        try {
            FileInputStream fileInputStream = new FileInputStream(path + "/business_owner.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            businessOwnerList = (ArrayList<BusinessOwner>) objectInputStream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void writeAllDataToFiles() {
        writeBusinessOwner();
        writeBusiness();
        writeBusinessOwnerLogin();
        writeAdminLogin();
        writeUserLogin();
        writeRating();
    }

    private static void writeRating() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/rating.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(ratingList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeUserLogin() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/user_login.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(userLoginList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void writeAdminLogin() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/admin_login.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(adminLoginList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeBusinessOwnerLogin() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/business_owner_login.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(businessOwnerLoginList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void writeBusiness() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/business.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(businessList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeBusinessOwner() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/business_owner.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(businessOwnerList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
