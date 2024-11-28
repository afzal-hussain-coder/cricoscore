package com.cricoscore.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Objects;

public class PlayerModel implements Serializable {


    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    int player_id;
    String name ="";
    String avatar="";

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    String teamId="";
    public PlayerModel(){

    }

    public PlayerModel(int id,String name,String avtar){
       this.player_id = id;
       this.name = name;
       this.avatar = avtar;
    }
    public PlayerModel(JSONObject  jsonObject){
        if(jsonObject.has("player_id")){
            try {
                this.player_id = jsonObject.getInt("player_id");
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
        if(jsonObject.has("avatar")){
            try {
                this.avatar = jsonObject.getString("avatar");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("teamId")){
            try {
                this.teamId = jsonObject.getString("teamId");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    // Override equals() and hashCode() for proper comparison in addList
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PlayerModel)) return false;
        PlayerModel other = (PlayerModel) obj;
        return this.name.equals(other.name) && this.teamId.equals(other.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, teamId); // Ensure unique identification
    }


}
