package com.cricoscore.retrofit;

import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ApiResponse.UserProfileResponse;
import com.cricoscore.ApiResponse.VerifyEmailResponse;
import com.cricoscore.ParamBody.ForgetPasswordBody;
import com.cricoscore.ParamBody.LoginBody;
import com.cricoscore.ParamBody.LoginThroughPhoneNumberBody;
import com.cricoscore.ParamBody.ResetPasswordBody;
import com.cricoscore.ParamBody.SignUpBody;
import com.cricoscore.ParamBody.VerifyOtpBody;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {

    @POST(Global.SIGNUP)
    Call<SignUpResponse> getSignUp(@Body SignUpBody signUpBody);

    @POST(Global.VERIFY_OTP)
    Call<SignUpResponse> getVerifyOtp(@Body VerifyOtpBody verifyOtpBody);

    @POST(Global.VERIFY_EMAIL_OTP)
    Call<SignUpResponse> getEmailVerifyOtp(@Body VerifyOtpBody verifyOtpBody);

    @POST(Global.LOGIN)
    Call<SignUpResponse> getLogin(@Body LoginBody loginBody);


    @POST(Global.LOGIN_THROUGH_OTP)
    Call<SignUpResponse> getLoginThroughOtp(@Body LoginThroughPhoneNumberBody loginThroughPhoneNumberBody);

    @POST(Global.RESET_PASSWORD)
    Call<SignUpResponse> getResetPassword(@Body ResetPasswordBody resetPasswordBody);

    @POST(Global.FORGET_PASSWORD)
    Call<SignUpResponse> getForgotPassword(@Body ForgetPasswordBody forgetPasswordBody);

    @POST(Global.VERIFY_EMAIL_OTP)
    Call<VerifyEmailResponse> getVerifyEmailOtp(@Body VerifyOtpBody verifyOtpBody);

    @GET(Global.USER_PROFILE + "{user_id}/{id}")
    Call<UserProfileResponse> getUserProfile(@Header("token") String token, @Path("user_id") Integer userId, @Path("id") Integer userid);
}
