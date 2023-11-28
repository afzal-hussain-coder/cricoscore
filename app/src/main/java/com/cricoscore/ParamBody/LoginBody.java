package com.cricoscore.ParamBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginBody {

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    public LoginBody(String username,String password){
        this.username = username;
        this.password = password;
    }

}
