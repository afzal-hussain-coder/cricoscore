package com.cricoscore.model.AuthModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthData {
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getIs_mobile_verified() {
        return is_mobile_verified;
    }

    public void setIs_mobile_verified(String is_mobile_verified) {
        this.is_mobile_verified = is_mobile_verified;
    }

    public String getIs_email_verified() {
        return is_email_verified;
    }

    public void setIs_email_verified(String is_email_verified) {
        this.is_email_verified = is_email_verified;
    }

    private int user_id;
    private String username = "";
    private String email = "";
    private String phone_number = "";
    private String status = "";
    private String first_name = "";
    private String last_name = "";
    private String avatar = "";
    private String cover = "";
    private String is_mobile_verified = "";
    private String is_email_verified = "";

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private int otp;
    private String token = "";

    public AuthData(JSONObject jsonObject) {
        if (jsonObject.has("user_id")) {
            try {
                this.user_id = jsonObject.getInt("user_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (jsonObject.has("username")) {
            try {
                this.username = jsonObject.getString("username");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("email")) {
            try {
                this.email = jsonObject.getString("email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("phone_number")) {
            try {
                this.phone_number = jsonObject.getString("phone_number");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("status")) {
            try {
                this.status = jsonObject.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("first_name")) {
            try {
                this.first_name = jsonObject.getString("first_name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("last_name")) {
            try {
                this.last_name = jsonObject.getString("last_name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("avatar")) {
            try {
                this.avatar = jsonObject.getString("avatar");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("cover")) {
            try {
                this.cover = jsonObject.getString("cover");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("is_mobile_verified")) {
            try {
                this.is_mobile_verified = jsonObject.getString("is_mobile_verified");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("is_email_verified")) {
            try {
                this.is_email_verified = jsonObject.getString("is_email_verified");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("otp")) {
            try {
                this.otp = jsonObject.getInt("otp");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("token")) {
            try {
                this.token = jsonObject.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
