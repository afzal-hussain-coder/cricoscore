package com.cricoscore.ParamBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginThroughPhoneNumberBody {

    public LoginThroughPhoneNumberBody(String phoneNumber){
        this.phoneNumber =phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber="";
}
