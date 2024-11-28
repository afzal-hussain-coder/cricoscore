package com.cricoscore.ParamBody;

public class UpdateScheduleTeamPlayerBody {
    public String getTeam_b_player_ids() {
        return team_b_player_ids;
    }

    public void setTeam_b_player_ids(String team_b_player_ids) {
        this.team_b_player_ids = team_b_player_ids;
    }

    public int getSchedule_match_id() {
        return schedule_match_id;
    }

    public void setSchedule_match_id(int schedule_match_id) {
        this.schedule_match_id = schedule_match_id;
    }

    String team_b_player_ids="";

    public String getTeam_a_player_ids() {
        return team_a_player_ids;
    }

    public void setTeam_a_player_ids(String team_a_player_ids) {
        this.team_a_player_ids = team_a_player_ids;
    }

    private String team_a_player_ids = "";
    int schedule_match_id;

    public UpdateScheduleTeamPlayerBody(String team_b_player_ids,String team_a_player_ids, int schedule_match_id){
        this.schedule_match_id = schedule_match_id;
        this.team_b_player_ids = team_b_player_ids;
        this.team_a_player_ids = team_a_player_ids;
    }

}
