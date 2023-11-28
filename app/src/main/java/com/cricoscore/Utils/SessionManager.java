package com.cricoscore.Utils;


import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by Pradeep on 1/31/2017.
 */

public class SessionManager {

    private static String TOKEN = "TOKEN";
    private static String SESSION_CHECK_LOGIN = "SESSION_CHECK_LOGIN";
    private static String check_agreement = "CHECK_AGREEMENT";
    private static String USERID="USERID";
    private static String OTP = "OTP";
    private static String USERNAME ="USERNAME";
    private static String EMAIL ="EMAIL";
    private static String PHONE_NUMBER ="PHONE";
    private static String FIRSTNAME ="FIRSTNAME";
    private static String LASTNAME ="LASTNAME";
    private static String USER_STATUS ="USER_STATUS";
    private static String USER_AVATAR ="USER_AVATAR";
    private static String USER_COVER ="USER_COVER";
    private static String IS_MOBILE_VERIFIED ="IS_MOBILE_VERIFIED";
    private static String IS_EMAIL_VERIFIED ="IS_EMAIL_VERIFIED";





    public static void savePreference(SharedPreferences prefs, String key, Boolean value) {
        Editor e = prefs.edit();
        e.putBoolean(key, value);
        e.commit();
    }

    public static void savePreference(SharedPreferences prefs, String key, int value) {
        Editor e = prefs.edit();
        e.putInt(key, value);
        e.commit();
    }

    public static void savePreference(SharedPreferences prefs, String key, String value) {
        Editor e = prefs.edit();
        e.putString(key, value);
        e.commit();
    }

    public static void clearPreferences(SharedPreferences prefs) {
        Editor e = prefs.edit();
        e.clear();
        e.commit();
    }

    public static void save_check_login(SharedPreferences prefs, Boolean value) {
        SessionManager.savePreference(prefs, SESSION_CHECK_LOGIN, value);
    }

    public static Boolean get_check_login(SharedPreferences prefs) {
        return prefs.getBoolean(SESSION_CHECK_LOGIN, false);
    }

    public static void saveToken(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, TOKEN, value);
    }

    public static String getToken(SharedPreferences prefs) {

        return prefs.getString(TOKEN, "");
    }

    public static void saveUserId(SharedPreferences prefs, Integer value) {
        SessionManager.savePreference(prefs, USERID, value);
    }

    public static int getUserId(SharedPreferences prefs) {

        return prefs.getInt(USERID, 0);
    }

    public static void saveOtp(SharedPreferences prefs, Integer value) {
        SessionManager.savePreference(prefs, OTP, value);
    }

    public static int getOtp(SharedPreferences prefs) {

        return prefs.getInt(OTP,0);
    }

    public static void saveUserName(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, USERNAME, value);
    }

    public static String getUserName(SharedPreferences prefs) {

        return prefs.getString(USERNAME, "");
    }

    public static void saveEmail(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, EMAIL, value);
    }

    public static String getEmail(SharedPreferences prefs) {

        return prefs.getString(EMAIL, "");
    }

    public static void savePhone(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, PHONE_NUMBER, value);
    }

    public static String getPhone(SharedPreferences prefs) {

        return prefs.getString(PHONE_NUMBER, "");
    }

    public static void saveFirstName(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, FIRSTNAME, value);
    }

    public static String getFirstName(SharedPreferences prefs) {

        return prefs.getString(FIRSTNAME, "");
    }

    public static void saveLastName(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, LASTNAME, value);
    }

    public static String getLastName(SharedPreferences prefs) {

        return prefs.getString(LASTNAME, "");
    }

    public static void saveUserStatus(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, USER_STATUS, value);
    }

    public static String getUserStatus(SharedPreferences prefs) {

        return prefs.getString(USER_STATUS, "");
    }

    public static void saveIsEmailVerified(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, IS_EMAIL_VERIFIED, value);
    }

    public static String getIsEmailVerified(SharedPreferences prefs) {

        return prefs.getString(IS_EMAIL_VERIFIED, "");
    }

    public static void saveIsPhoneVerified(SharedPreferences prefs, String value) {
        SessionManager.savePreference(prefs, IS_MOBILE_VERIFIED, value);
    }

    public static String getIsPhoneVerified(SharedPreferences prefs) {

        return prefs.getString(IS_MOBILE_VERIFIED, "");
    }
}