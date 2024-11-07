package com.cricoscore.ParamBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddTeamReguestBody {

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam_logo() {
        return team_logo;
    }

    public void setTeam_logo(String team_logo) {
        this.team_logo = team_logo;
    }

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("team_logo")
    @Expose
    private String team_logo;

}
