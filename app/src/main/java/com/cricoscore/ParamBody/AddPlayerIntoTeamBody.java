package com.cricoscore.ParamBody;

public class AddPlayerIntoTeamBody {

    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    String player_id ="";
    int team_id ;

    public AddPlayerIntoTeamBody(String player_id,int team_id){
        this.player_id = player_id;
        this.team_id = team_id;
    }


}
