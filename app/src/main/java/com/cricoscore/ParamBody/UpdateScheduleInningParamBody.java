package com.cricoscore.ParamBody;

public class UpdateScheduleInningParamBody {
    public int getBatting_team_id() {
        return batting_team_id;
    }

    public void setBatting_team_id(int batting_team_id) {
        this.batting_team_id = batting_team_id;
    }

    public int getBowlling_team_id() {
        return bowlling_team_id;
    }

    public void setBowlling_team_id(int bowlling_team_id) {
        this.bowlling_team_id = bowlling_team_id;
    }

    public int getSchedule_match_id() {
        return schedule_match_id;
    }

    public void setSchedule_match_id(int schedule_match_id) {
        this.schedule_match_id = schedule_match_id;
    }

    int batting_team_id;
    int bowlling_team_id;
    int schedule_match_id;

    public UpdateScheduleInningParamBody(int batting_team_id, int bowlling_team_id, int schedule_match_id) {
        this.batting_team_id = batting_team_id;
        this.bowlling_team_id = bowlling_team_id;
        this.schedule_match_id=schedule_match_id;

    }
}
