package com.cricoscore.ParamBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordBody {

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("password")
    @Expose
    private String password;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirm() {
        return password_confirm;
    }

    public void setPassword_confirm(String password_confirm) {
        this.password_confirm = password_confirm;
    }

    @SerializedName("password_confirm")
    @Expose
    private String password_confirm;

    public ResetPasswordBody(int userId, String password, String password_confirm){
        this.user_id = userId;
        this.password = password;
        this.password_confirm = password_confirm;
    }


}
