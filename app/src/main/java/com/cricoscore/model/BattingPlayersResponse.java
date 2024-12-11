package com.cricoscore.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BattingPlayersResponse {

    private boolean status;
    private String message;
    private ArrayList<Player> data;

    // Getters and Setters
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Player> getData() {
        return data;
    }

    public void setData(ArrayList<Player> data) {
        this.data = data;
    }

    // Inner class for Player
    public static class Player {
        private int player_id;
        private String name;
        private String avatar;
        private int wickets_taken;
        private int runs_scored;
        private int total_overs;

        public int getIs_over_completed() {
            return is_over_completed;
        }

        public void setIs_over_completed(int is_over_completed) {
            this.is_over_completed = is_over_completed;
        }

        private int is_over_completed;

        public String getIs_out() {
            return is_out;
        }

        public void setIs_out(String is_out) {
            this.is_out = is_out;
        }

        private String is_out="";

        public Player(JSONObject jsonObject){
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

            if(jsonObject.has("wickets_taken")){
                try {
                    this.wickets_taken = jsonObject.getInt("wickets_taken");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(jsonObject.has("runs_scored")){
                try {
                    this.runs_scored = jsonObject.getInt("runs_scored");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if(jsonObject.has("is_out")){
                try {
                    this.is_out = jsonObject.getString("is_out");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(jsonObject.has("is_over_completed")){
                try {
                    this.is_over_completed = jsonObject.getInt("is_over_completed");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        // Getters and Setters
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

        public int getWickets_taken() {
            return wickets_taken;
        }

        public void setWickets_taken(int wickets_taken) {
            this.wickets_taken = wickets_taken;
        }

        public int getRuns_scored() {
            return runs_scored;
        }

        public void setRuns_scored(int runs_scored) {
            this.runs_scored = runs_scored;
        }
    }
}

