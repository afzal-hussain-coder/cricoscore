package com.cricoscore.ParamBody;

public class RemovePlayerBody {
    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    int team_id;
    String player_id="";

    public RemovePlayerBody(int teamId,String playerid){
        this.team_id = teamId;
        this.player_id = playerid;
    }
}
