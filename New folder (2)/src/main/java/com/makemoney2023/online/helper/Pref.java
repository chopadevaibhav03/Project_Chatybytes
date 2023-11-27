package com.makemoney2023.online.helper;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

public class Pref {

    Activity activity;

    public Pref(Activity activity) {
        this.activity = activity;
    }

    public void setData(String data, String type) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(type, MODE_PRIVATE).edit();
        editor.putString(type, data);
        editor.apply();
    }

    public String getData(String type) {
        SharedPreferences prefs = activity.getSharedPreferences(type, MODE_PRIVATE);
        return prefs.getString(type, "");
    }

    public void setCount(int data, String type) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(type, MODE_PRIVATE).edit();
        editor.putInt(type, data);
        editor.apply();
    }

    public int getCount(String type) {
        SharedPreferences prefs = activity.getSharedPreferences(type, MODE_PRIVATE);
        return prefs.getInt(type, 0);
    }


}
