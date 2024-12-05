package com.cricoscore.retrofit;

import com.cricoscore.Activity.PlayingSquadActivity;
import com.cricoscore.ApiResponse.AddTeamResponse;
import com.cricoscore.ApiResponse.AddTournamentResponse;
import com.cricoscore.ApiResponse.SignUpResponse;
import com.cricoscore.ApiResponse.TournamentResponse;
import com.cricoscore.ApiResponse.UserProfileResponse;
import com.cricoscore.ApiResponse.VerifyEmailResponse;
import com.cricoscore.ParamBody.AddPlayerBody;
import com.cricoscore.ParamBody.AddPlayerIntoTeamBody;
import com.cricoscore.ParamBody.AddTeamInTournamnetBody;
import com.cricoscore.ParamBody.CreateScheduleBody;
import com.cricoscore.ParamBody.ForgetPasswordBody;
import com.cricoscore.ParamBody.InningBallParamBody;
import com.cricoscore.ParamBody.InningNewBody;
import com.cricoscore.ParamBody.LoginBody;
import com.cricoscore.ParamBody.LoginThroughPhoneNumberBody;
import com.cricoscore.ParamBody.PlayingSqudUpdateBody;
import com.cricoscore.ParamBody.RemovePlayerBody;
import com.cricoscore.ParamBody.ResetPasswordBody;
import com.cricoscore.ParamBody.SignUpBody;
import com.cricoscore.ParamBody.ToasParamBody;
import com.cricoscore.ParamBody.UpdateScheduleInningParamBody;
import com.cricoscore.ParamBody.UpdateScheduleTeamPlayerBody;
import com.cricoscore.ParamBody.VerifyOtpBody;
import com.cricoscore.Utils.Global;
import com.cricoscore.model.BattingPlayersResponse;
import com.cricoscore.model.TournamentModel.TournamentDetails;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET(Global.USER_PROFILE)
    Call<UserProfileResponse> getUserProfile(@Header("token") String token);

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
                                                         @Part("user_id") RequestBody user_id,
                                                         @Part MultipartBody.Part tournament_logo,
                                                         @Part MultipartBody.Part tournament_banner);


    @GET(Global.ALL_TOURNAMENT)
    Call<TournamentResponse> getAllTournament(@Header("token") String token);

    @GET(Global.GET_MY_TOURNAMENT + "{user_id}/{id}")
    Call<TournamentResponse> getMyTournament(@Header("token") String token, @Path("user_id") Integer userId);

    @Multipart
    @POST(Global.ADD_TEAM)
    Call<ResponseBody> getAddTeamResponse(@Header("token") String token,
                                          @Part("user_id") RequestBody user_id,
                                          @Part("city") RequestBody city,
                                          @Part("name") RequestBody name,
                                          @Part("tournament_id") RequestBody tournamentId,
                                          @Part MultipartBody.Part team_logo);

    @GET("tournament/getDetails/{tournamentId}")
        // Use {tournamentId} in the endpoint
    Call<ResponseBody> getTournamentDetails(
            @Header("token") String token,
            @Path("tournamentId") String tournamentId);

    @GET(Global.GET_MYTEAM)
    Call<ResponseBody> getMyTeam(
            @Header("token") String token);

    @POST(Global.ADD_TEAM_IN_TOURNAMENT)
    Call<ResponseBody> addTeamInTournamnet(@Header("token") String token,
                                           @Body AddTeamInTournamnetBody addTeamInTournamnetBody);

    @GET("team/getTeamDetails/{teamId}")
    Call<ResponseBody> getTeamDetails(@Header("token") String token,
                                      @Path("teamId") String teamId);

    @Multipart
    @POST(Global.ADD_PLAYER)
    Call<ResponseBody> addPayer(@Header("token") String token,
                                @Part("name") RequestBody name,
                                @Part("phone_number") RequestBody phone_number,
                                @Part MultipartBody.Part avatar);

    @GET(Global.MY_PLAYER_LIST)
    Call<ResponseBody> getMyPlayer(@Header("token") String token);


    @POST(Global.ADD_PLAYER_INTO_TEAM)
    Call<ResponseBody> addPlayerInTeam(@Header("token") String token,
                                       @Body AddPlayerIntoTeamBody addPlayerIntoTeamBody);

    @DELETE("team/removePlayersFromTeam/{team_id}/{player_id}")
    Call<ResponseBody> removePlayer(@Header("token") String token,
                                    @Path("team_id") int team_id,
                                    @Path("player_id") String player_id);

    @DELETE("team/delete/{team_id}")
    Call<ResponseBody> removeTeam(@Header("token") String token,
                                  @Path("team_id") int team_id);

    @DELETE("tournament/removeTeam/{tournament_id}/{team_id}")
    Call<ResponseBody> removeTeamFromTournament(@Header("token") String token,
                                                @Path("team_id") int team_id,
                                                @Path("tournament_id") int tournament_id);

    @POST(Global.CREATE_SCHEDULE)
    Call<ResponseBody> addSchedule(@Header("token") String token,
                                   @Body CreateScheduleBody createScheduleBody);

    @GET(Global.SCHEDULE_MATCH_LIST)
    Call<ResponseBody> getScheduleMatchList(@Header("token") String token);

    @GET("schedule/getDetails/{schedule_match_id}")
    Call<ResponseBody> getScheduleMatchDetails(@Header("token") String token,
                                               @Path("schedule_match_id") Integer schedule_match_id);

    @PUT(Global.UPDATE_SELECTED_TEAM_PLAYER)
    Call<ResponseBody> updateSelectedTeamPlayer(@Header("token") String token,
                                                @Body UpdateScheduleTeamPlayerBody createScheduleBody);

    @PUT(Global.UPDATE_SCHEDULE)
    Call<ResponseBody> updateSchedule(@Header("token") String token,
                                      @Body CreateScheduleBody createScheduleBody);

    @PUT(Global.TOSS_API)
    Call<ResponseBody> toss(@Header("token") String token,
                            @Body ToasParamBody createScheduleBody);

    @POST(Global.INNING_NEW)
    Call<ResponseBody> inningNew(@Header("token") String token,
                                 @Body InningNewBody inningNewBody);

    @GET("inning/getBattingPlayers")
    Call<ResponseBody> getBattingPlayers(@Header("token") String token,
                                         @Query("schedule_match_id") int scheduleMatchId,
                                         @Query("inning_id") int inningId,
                                         @Query("team_id") int teamId
    );

    @GET("inning/getDetails/{inning_id}")
    Call<ResponseBody> getInningDetails(@Header("token") String token,
                                        @Path("inning_id") int inningId
    );

    @PUT(Global.INNING_UPDATE)
    Call<ResponseBody> inningUpdate(@Header("token") String token,
                                    @Body PlayingSqudUpdateBody createScheduleBody);


    @POST(Global.INNING_BALL)
    Call<ResponseBody> inningBall(@Header("token") String token,
                                  @Body InningBallParamBody inningBallParamBody);

    @PUT(Global.UPDATE_SCHEDULE_INNING)
    Call<ResponseBody> updateScheduleInning(@Header("token") String token,
                                                @Body UpdateScheduleInningParamBody updateScheduleInningParamBody);


}
