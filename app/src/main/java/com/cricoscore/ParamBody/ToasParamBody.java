package com.cricoscore.ParamBody;

public class ToasParamBody {

    public int getToss_winner_team_id() {
        return toss_winner_team_id;
    }

    public void setToss_winner_team_id(int toss_winner_team_id) {
        this.toss_winner_team_id = toss_winner_team_id;
    }

    public int getBatting_team_id() {
        return batting_team_id;
    }

    public void setBatting_team_id(int batting_team_id) {
        this.batting_team_id = batting_team_id;
    }

    public int getSchedule_match_id() {
        return schedule_match_id;
    }

    public void setSchedule_match_id(int schedule_match_id) {
        this.schedule_match_id = schedule_match_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    int toss_winner_team_id;
    int batting_team_id;
    int  schedule_match_id;
    String message="";

    public ToasParamBody(int toss_winner_team_id,int batting_team_id,int  schedule_match_id,String message){
        this.toss_winner_team_id = toss_winner_team_id;
        this.batting_team_id = batting_team_id;
        this.schedule_match_id = schedule_match_id;
        this.message = message;
    }
}
