package com.cricoscore.retrofit;

import com.cricoscore.ApiResponse.AddTournamentResponse;
import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ApiResponse.UserProfileResponse;
import com.cricoscore.ApiResponse.VerifyEmailResponse;
import com.cricoscore.ParamBody.ForgetPasswordBody;
import com.cricoscore.ParamBody.LoginBody;
import com.cricoscore.ParamBody.LoginThroughPhoneNumberBody;
import com.cricoscore.ParamBody.ResetPasswordBody;
import com.cricoscore.ParamBody.SignUpBody;
import com.cricoscore.ParamBody.SubmitUserProfileBody;
import com.cricoscore.ParamBody.VerifyOtpBody;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.model.AddTournamentData;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @Multipart
    @POST(Global.USER_SUBMIT_PROFILE)
    Call<SignUpResponse> getUserSubmitProfile(@Header("token") String token,
                                              @Part("username") RequestBody username,
                                              @Part("first_name") RequestBody first_name,
                                              @Part("last_name") RequestBody last_name,
                                              @Part("phone_number") RequestBody phone_number,
                                              @Part("email") RequestBody email,
                                              @Part("user_id") RequestBody user_id,
                                              @Part MultipartBody.Part avatar);

    @Multipart
    @POST(Global.USER_SUBMIT_PROFILE)
    Call<SignUpResponse> getUserSubmitProfileWithoutImage(@Header("token") String token,
                                                          @Part("username") RequestBody username,
                                                          @Part("first_name") RequestBody first_name,
                                                          @Part("last_name") RequestBody last_name,
                                                          @Part("phone_number") RequestBody phone_number,
                                                          @Part("email") RequestBody email,
                                                          @Part("user_id") RequestBody user_id);

    @Multipart
    @POST(Global.ADDTOURNAMENTURL)
    Call<AddTournamentResponse> getAddTournamentResponse(@Header("token") String token,
                                                         @Part("type") RequestBody type,
                                                         @Part("prize") RequestBody prize,
                                                         @Part("ball_type") RequestBody ball_type,
                                                         @Part("name") RequestBody name,
                                                         @Part("start_date") RequestBody start_date,
                                                         @Part("end_date") RequestBody end_date,
                                                         @Part("state") RequestBody state,
                                                         @Part("city") RequestBody city,
                                                         @Part("location") RequestBody location,
                                                         @Part("ground_name") RequestBody ground_name,
                                                         @Part("fee") RequestBody fee,
                                                         @Part("discount") RequestBody discount,
                                                         @Part("no_of_team") RequestBody no_of_team,
                                                         @Part("sponser") RequestBody sponser,
                                                         @Part("org_contact_name") RequestBody org_contact_name,
                                                         @Part("org_contact_number") RequestBody org_contact_number,
                                                         @Part("org_contact_email") RequestBody org_contact_email,
                                                         @Part("tournament_id") RequestBody tournament_id,
                                                         @Part("user_id") RequestBody user_id);
}
