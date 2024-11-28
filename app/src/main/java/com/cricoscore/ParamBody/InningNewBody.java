package com.cricoscore.ParamBody;

public class InningNewBody {

    public int getSchedule_match_id() {
        return schedule_match_id;
    }

    public void setSchedule_match_id(int schedule_match_id) {
        this.schedule_match_id = schedule_match_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getInning_number() {
        return inning_number;
    }

    public void setInning_number(int inning_number) {
        this.inning_number = inning_number;
    }

    public int getCurrent_bowler_id() {
        return current_bowler_id;
    }

    public void setCurrent_bowler_id(int current_bowler_id) {
        this.current_bowler_id = current_bowler_id;
    }

    public int getCurrent_non_striker_id() {
        return current_non_striker_id;
    }

    public void setCurrent_non_striker_id(int current_non_striker_id) {
        this.current_non_striker_id = current_non_striker_id;
    }

    public int getCurrent_striker_id() {
        return current_striker_id;
    }

    public void setCurrent_striker_id(int current_striker_id) {
        this.current_striker_id = current_striker_id;
    }

    int schedule_match_id;
    int team_id;
    int inning_number;
    int current_bowler_id;
    int current_non_striker_id;
    int current_striker_id;

    public int getBowling_team_id() {
        return bowling_team_id;
    }

    public void setBowling_team_id(int bowling_team_id) {
        this.bowling_team_id = bowling_team_id;
    }

    int bowling_team_id;

    public InningNewBody(int schedule_match_id,
                         int team_id,
                         int inning_number,
                         int current_bowler_id,
                         int current_non_striker_id,
                         int current_striker_id,
                         int bowling_team_id) {
        this.schedule_match_id = schedule_match_id;
        this.team_id = team_id;
        this.inning_number = inning_number;
        this.current_bowler_id = current_bowler_id;
        this.current_non_striker_id = current_non_striker_id;
        this.current_striker_id = current_striker_id;
        this.bowling_team_id = bowling_team_id;

    }

}
