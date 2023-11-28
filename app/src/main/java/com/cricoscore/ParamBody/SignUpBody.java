package com.cricoscore.ParamBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpBody {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone_number")
    @Expose
    private String phone_number;

    @SerializedName("password")
    @Expose
    private String password;

    public SignUpBody(String username,String email,String phone_number, String password){
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.password =password;
    }
}
