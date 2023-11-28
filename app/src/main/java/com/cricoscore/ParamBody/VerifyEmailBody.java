package com.cricoscore.ParamBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyEmailBody {

    public VerifyEmailBody(int user_id,int otp){
        this.user_id = user_id;
        this.otp = otp;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("otp")
    @Expose
    private int otp;

}
