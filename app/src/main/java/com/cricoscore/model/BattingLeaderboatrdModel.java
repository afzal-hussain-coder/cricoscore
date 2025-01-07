package com.cricoscore.model;

import org.json.JSONException;
import org.json.JSONObject;

public class BattingLeaderboatrdModel {

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

    public int getTotal_matches() {
        return total_matches;
    }

    public void setTotal_matches(int total_matches) {
        this.total_matches = total_matches;
    }

    public int getTotal_inning() {
        return total_inning;
    }

    public void setTotal_inning(int total_inning) {
        this.total_inning = total_inning;
    }

    public int getTotal_runs() {
        return total_runs;
    }

    public void setTotal_runs(int total_runs) {
        this.total_runs = total_runs;
    }

    public int getTotal_balls_faced() {
        return total_balls_faced;
    }

    public void setTotal_balls_faced(int total_balls_faced) {
        this.total_balls_faced = total_balls_faced;
    }

    public int getTotal_fours() {
        return total_fours;
    }

    public void setTotal_fours(int total_fours) {
        this.total_fours = total_fours;
    }

    public int getTotal_sixes() {
        return total_sixes;
    }

    public void setTotal_sixes(int total_sixes) {
        this.total_sixes = total_sixes;
    }

    public float getStrike_rate() {
        return strike_rate;
    }

    public void setStrike_rate(float strike_rate) {
        this.strike_rate = strike_rate;
    }

    int player_id;
    String name = "";
    String avatar = "";
    int total_matches;
    int total_inning;
    int total_runs;
    int total_balls_faced;
    int total_fours;
    int total_sixes;
    float strike_rate;

    public int getHighest_score() {
        return highest_score;
    }

    public void setHighest_score(int highest_score) {
        this.highest_score = highest_score;
    }

    int highest_score;

    public int getTotal_50s() {
        return total_50s;
    }

    public void setTotal_50s(int total_50s) {
        this.total_50s = total_50s;
    }

    public int getTotal_100s() {
        return total_100s;
    }

    public void setTotal_100s(int total_100s) {
        this.total_100s = total_100s;
    }

    public float getAvg_run() {
        return avg_run;
    }

    public void setAvg_run(float avg_run) {
        this.avg_run = avg_run;
    }

    int total_50s;
    int total_100s;
    float avg_run;


    public BattingLeaderboatrdModel(JSONObject jsonObject) {

        if (jsonObject.has("player_id")) {
            try {
                this.player_id = jsonObject.getInt("player_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("name")) {
            try {
                this.name = jsonObject.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("avatar")) {
            try {
                this.avatar = jsonObject.getString("avatar");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("total_matches")) {
            try {
                this.total_matches = jsonObject.getInt("total_matches");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("total_inning")) {
            try {
                this.total_inning = jsonObject.getInt("total_inning");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("total_balls_faced")) {
            try {
                this.total_balls_faced = jsonObject.getInt("total_balls_faced");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("total_fours")) {
            try {
                this.total_fours = jsonObject.getInt("total_fours");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("total_sixes")) {
            try {
                this.total_sixes = jsonObject.getInt("total_sixes");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("strike_rate")) {
            try {
                this.strike_rate = jsonObject.getInt("strike_rate");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("total_50s")) {
            try {
                this.total_50s = jsonObject.getInt("total_50s");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("total_100s")) {
            try {
                this.total_100s = jsonObject.getInt("total_100s");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("avg_run")) {
            try {
                this.avg_run = jsonObject.getInt("avg_run");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (jsonObject.has("highest_score")) {
            try {
                this.highest_score = jsonObject.getInt("highest_score");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (jsonObject.has("total_runs")) {
            try {
                this.total_runs = jsonObject.getInt("total_runs");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




    }

}
