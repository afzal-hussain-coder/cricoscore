package com.cricoscore.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class TeamModel implements Serializable {


    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTeam_logo() {
        return team_logo;
    }

    public void setTeam_logo(String team_logo) {
        this.team_logo = team_logo;
    }

    public int team_id;
    public String name="";
    public String city="";
    public String team_logo="";

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected; // New property

    public TeamModel(JSONObject jsonObject){
        if(jsonObject.has("team_id")){
            try {
                this.team_id = jsonObject.getInt("team_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("name")){
            try {
                this.name = jsonObject.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("city")){
            try {
                this.city = jsonObject.getString("city");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("team_logo")){
            try {
                this.team_logo = jsonObject.getString("team_logo");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
