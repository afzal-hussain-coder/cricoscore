package com.cricoscore.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREF_NAME ="CricoscoreSharedPrefs";
    private static SharedPreferences preferences=null;

    public static void inIt(Context context){
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void savePreferenceBoolean(String key, Boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public static boolean getPreferenceBoolean(String key, Boolean value) {
        return preferences.getBoolean(key,value);
    }

    public static void savePreferenceInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public static Integer getPreferenceInt(String key, int value) {
        return preferences.getInt(key,value);
    }

    public static void savePreferenceString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public static String getPreferenceString(String key) {
       return preferences.getString(key,"");
    }
    public static void clearPreferences() {
        preferences.edit().clear().apply();
    }


}
