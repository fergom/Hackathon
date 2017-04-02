package com.a480.fernando.hackathon;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Fernando on 29/03/2017.
 */

public class AppSharedPreferences {

    //this method will save the user once he or she has logged in
    public static void setUser(Context context, String nif) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.HACKATHON_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstant.USER, nif);
        editor.apply();
    }

    //this method will return the nif of the user
    public static String getUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.HACKATHON_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AppConstant.USER, null);
    }

    //this method removes the user from the session when he or she logs out
    public static void removeUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.HACKATHON_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(AppConstant.USER);
        editor.apply();
    }

}
