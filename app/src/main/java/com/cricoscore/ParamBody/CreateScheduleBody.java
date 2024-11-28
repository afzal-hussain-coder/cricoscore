package com.cricoscore.ParamBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateScheduleBody {

    public Integer getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(Integer tournament_id) {
        this.tournament_id = tournament_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGround() {
        return ground;
    }

    public void setGround(String ground) {
        this.ground = ground;
    }

    public String getBall_type() {
        return ball_type;
    }

    public void setBall_type(String ball_type) {
        this.ball_type = ball_type;
    }

    public String getPitch_type() {
        return pitch_type;
    }

    public void setPitch_type(String pitch_type) {
        this.pitch_type = pitch_type;
    }

    public String getNo_of_over() {
        return no_of_over;
    }

    public void setNo_of_over(String no_of_over) {
        this.no_of_over = no_of_over;
    }

    public String getNo_of_over_bowler() {
        return no_of_over_bowler;
    }

    public void setNo_of_over_bowler(String no_of_over_bowler) {
        this.no_of_over_bowler = no_of_over_bowler;
    }

    public Integer getTeam_a() {
        return team_a;
    }

    public void setTeam_a(Integer team_a) {
        this.team_a = team_a;
    }

    public Integer getTeam_b() {
        return team_b;
    }

    public void setTeam_b(Integer team_b) {
        this.team_b = team_b;
    }

    public String getStart_date_time() {
        return start_date_time;
    }

    public void setStart_date_time(String start_date_time) {
        this.start_date_time = start_date_time;
    }

    public String getTeam_a_player_ids() {
        return team_a_player_ids;
    }

    public void setTeam_a_player_ids(String team_a_player_ids) {
        this.team_a_player_ids = team_a_player_ids;
    }

    public String getTeam_b_player_ids() {
        return team_b_player_ids;
    }

    public void setTeam_b_player_ids(String team_b_player_ids) {
        this.team_b_player_ids = team_b_player_ids;
    }

    @SerializedName("tournament_id")
    @Expose
    Integer tournament_id;
    @SerializedName("location")
    @Expose
    String location="";
    @SerializedName("ground")
    @Expose
    String ground="";
    @SerializedName("ball_type")
    @Expose
    String ball_type="";
    @SerializedName("pitch_type")
    @Expose
    String pitch_type="";
    @SerializedName("no_of_over")
    @Expose
    String no_of_over="";
    @SerializedName("no_of_over_bowler")
    @Expose
    String no_of_over_bowler="";
    @SerializedName("team_a")
    @Expose
    Integer team_a;
    @SerializedName("team_b")
    @Expose
    Integer team_b;
    @SerializedName("start_date_time")
    @Expose
    String start_date_time="";
    @SerializedName("team_a_player_ids")
    @Expose
    String team_a_player_ids="";
    @SerializedName("team_b_player_ids")
    @Expose
    String team_b_player_ids="";

    public int getSchedule_match_id() {
        return schedule_match_id;
    }

    public void setSchedule_match_id(int schedule_match_id) {
        this.schedule_match_id = schedule_match_id;
    }

    @SerializedName("schedule_match_id")
    @Expose
    int schedule_match_id;

    public String getMatch_type() {
        return match_type;
    }

    public void setMatch_type(String match_type) {
        this.match_type = match_type;
    }

    @SerializedName("match_type")
    @Expose
    String match_type="";

    public CreateScheduleBody(Integer tournament_id,
                              String location,
                              String ground,
                              String ball_type,
                              String pitch_type,
                              String no_of_over,
                              String no_of_over_bowler,
                              Integer team_a,
                              Integer team_b,
                              String start_date_time,
                              String team_a_player_ids,
                              String team_b_player_ids,
                              String match_type,
                              int schedule_match_id
                              ){
        this.tournament_id = tournament_id;
        this.ground = ground;
        this.ball_type = ball_type;
        this.no_of_over_bowler = no_of_over_bowler;
        this.location = location;
        this.pitch_type = pitch_type;
        this.start_date_time = start_date_time;
        this.team_a = team_a;
        this.team_b = team_b;
        this.team_a_player_ids = team_a_player_ids;
        this.team_b_player_ids = team_b_player_ids;
        this.no_of_over = no_of_over;
        this.match_type = match_type;
        this.schedule_match_id = schedule_match_id;

    }
}
