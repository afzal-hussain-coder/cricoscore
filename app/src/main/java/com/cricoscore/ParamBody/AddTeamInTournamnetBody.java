package com.cricoscore.ParamBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.Path;
import retrofit2.http.Query;

public class AddTeamInTournamnetBody {
    public String getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(String tournament_id) {
        this.tournament_id = tournament_id;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    @SerializedName("tournament_id")
    @Expose
    String tournament_id="";
    @SerializedName("team_id")
    @Expose
    String team_id="";

    public AddTeamInTournamnetBody(String tournamentId,String teamId){
        this.tournament_id = tournamentId;
        this.team_id = teamId;
    }
}
