package com.actofit.actofitdemo;

import android.content.Context;
import android.content.SharedPreferences;

import static com.actofit.actofitdemo.Constants.LOCATION_NEVER_ASKED;


public class SharedPreferencesHelper {

    private static SharedPreferencesHelper sharedPreferencesHelper;

    private static SharedPreferences sharedPreferences;

    private SharedPreferencesHelper() {
    }

    public static synchronized void initDefault(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesHelper getInstance() {
        if (sharedPreferencesHelper == null) {
            sharedPreferencesHelper = new SharedPreferencesHelper();
        }

        return sharedPreferencesHelper;
    }

    public void setName(String name) {
        sharedPreferences.edit().putString(Constants.USER_NAME, name).apply();
    }

    public String getName() {
        return sharedPreferences.getString(Constants.USER_NAME, "");
    }


    public void setContact(String contact) {
        sharedPreferences.edit().putString(Constants.USER_CONTACT_NUMBER, contact).apply();
    }

    public String getContact() {
        return sharedPreferences.getString(Constants.USER_CONTACT_NUMBER, "");
    }


    public void save(String username, String contact) {
        sharedPreferences.edit()
                .putString(Constants.USER_NAME, username)
                .putString(Constants.USER_CONTACT_NUMBER, contact).apply();
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    public void setLocationshowratrionale(boolean neverAsked){
        sharedPreferences.edit().putBoolean(LOCATION_NEVER_ASKED, neverAsked).commit();
    }

    public boolean isLocationshowRationale(){
        return  sharedPreferences.getBoolean(LOCATION_NEVER_ASKED, false);
    }
}
