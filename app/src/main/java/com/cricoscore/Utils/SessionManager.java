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
    private static String USERID = "USERID";
    private static String OTP = "OTP";
    private static String USERNAME = "USERNAME";
    private static String EMAIL = "EMAIL";
    private static String PHONE_NUMBER = "PHONE";
    private static String FIRSTNAME = "FIRSTNAME";
    private static String LASTNAME = "LASTNAME";
    private static String USER_STATUS = "USER_STATUS";
    private static String USER_AVATAR = "USER_AVATAR";
    private static String USER_COVER = "USER_COVER";
    private static String IS_MOBILE_VERIFIED = "IS_MOBILE_VERIFIED";
    private static String IS_EMAIL_VERIFIED = "IS_EMAIL_VERIFIED";

    public static void save_check_login(Boolean value) {
        SharedPreferencesManager.savePreferenceBoolean(SESSION_CHECK_LOGIN, value);
    }

    public static Boolean get_check_login() {
        return SharedPreferencesManager.getPreferenceBoolean(SESSION_CHECK_LOGIN, false);
    }

    public static void saveToken(String value) {
        SharedPreferencesManager.savePreferenceString(TOKEN, value);
    }

    public static String getToken() {

        return SharedPreferencesManager.getPreferenceString(TOKEN);
    }

    public static void saveUserId(Integer value) {
        SharedPreferencesManager.savePreferenceInt(USERID, value);
    }

    public static Integer getUserId() {

        return SharedPreferencesManager.getPreferenceInt(USERID, 0);
    }

    public static void saveOtp(Integer value) {
        SharedPreferencesManager.savePreferenceInt(OTP, value);
    }

    public static Integer getOtp() {

        return SharedPreferencesManager.getPreferenceInt(OTP, 0);
    }

    public static void saveUserName(String value) {
        SharedPreferencesManager.savePreferenceString(USERNAME, value);
    }

    public static String getUserName() {
        return SharedPreferencesManager.getPreferenceString(USERNAME);
    }

    public static void saveEmail(String value) {
        SharedPreferencesManager.savePreferenceString(EMAIL, value);
    }

    public static String getEmail() {
        return SharedPreferencesManager.getPreferenceString(EMAIL);
    }

    public static void savePhone(String value) {
        SharedPreferencesManager.savePreferenceString(PHONE_NUMBER, value);
    }

    public static String getPhone() {

        return SharedPreferencesManager.getPreferenceString(PHONE_NUMBER);
    }

    public static void saveFirstName(String value) {
        SharedPreferencesManager.savePreferenceString(FIRSTNAME, value);
    }

    public static String getFirstName() {

        return SharedPreferencesManager.getPreferenceString(FIRSTNAME);
    }

    public static void saveLastName(String value) {
        SharedPreferencesManager.savePreferenceString(LASTNAME, value);
    }

    public static String getLastName() {

        return SharedPreferencesManager.getPreferenceString(LASTNAME);
    }

    public static void saveUserStatus(String value) {
        SharedPreferencesManager.savePreferenceString(USER_STATUS, value);
    }

    public static String getUserStatus() {

        return SharedPreferencesManager.getPreferenceString(USER_STATUS);
    }

    public static void saveIsEmailVerified(String value) {
        SharedPreferencesManager.savePreferenceString(IS_EMAIL_VERIFIED, value);
    }

    public static String getIsEmailVerified() {

        return SharedPreferencesManager.getPreferenceString(IS_EMAIL_VERIFIED);
    }

    public static void saveIsPhoneVerified(String value) {
        SharedPreferencesManager.savePreferenceString(IS_MOBILE_VERIFIED, value);
    }

    public static String getIsPhoneVerified() {

        return SharedPreferencesManager.getPreferenceString(IS_MOBILE_VERIFIED);
    }
}