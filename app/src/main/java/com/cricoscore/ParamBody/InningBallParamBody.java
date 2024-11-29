package com.cricoscore.ParamBody;

import com.bumptech.glide.load.model.UriLoader;

public class InningBallParamBody {

    public int getInning_id() {
        return inning_id;
    }

    public void setInning_id(int inning_id) {
        this.inning_id = inning_id;
    }

    public int getOver_number() {
        return over_number;
    }

    public void setOver_number(int over_number) {
        this.over_number = over_number;
    }

    public int getBall_number() {
        return ball_number;
    }

    public void setBall_number(int ball_number) {
        this.ball_number = ball_number;
    }

    public int getBowler_id() {
        return bowler_id;
    }

    public void setBowler_id(int bowler_id) {
        this.bowler_id = bowler_id;
    }

    public int getBatsman_id() {
        return batsman_id;
    }

    public void setBatsman_id(int batsman_id) {
        this.batsman_id = batsman_id;
    }

    public int getRuns_scored() {
        return runs_scored;
    }

    public void setRuns_scored(int runs_scored) {
        this.runs_scored = runs_scored;
    }

    public String getIs_boundry() {
        return is_boundry;
    }

    public void setIs_boundry(String is_boundry) {
        this.is_boundry = is_boundry;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getIs_wicket() {
        return is_wicket;
    }

    public void setIs_wicket(String is_wicket) {
        this.is_wicket = is_wicket;
    }

    public String getWicket_type() {
        return wicket_type;
    }

    public void setWicket_type(String wicket_type) {
        this.wicket_type = wicket_type;
    }

    public int getFielder_id() {
        return fielder_id;
    }

    public void setFielder_id(int fielder_id) {
        this.fielder_id = fielder_id;
    }

    int inning_id;
    int over_number;
    int ball_number;
    int bowler_id;
    int batsman_id;
    int runs_scored;
    String is_boundry;
    String extras;
    String is_wicket;
    String wicket_type;
    int fielder_id;


    public InningBallParamBody(int inning_id, int over_number, int ball_number, int bowler_id,
                               int batsman_id, int runs_scored,
                               String is_boundry, String extras, String is_wicket,
                               String wicket_type, int fielder_id) {
        this.inning_id = inning_id;
        this.over_number = over_number;
        this.ball_number = ball_number;
        this.bowler_id = bowler_id;
        this.batsman_id = batsman_id;
        this.runs_scored = runs_scored;
        this.is_boundry = is_boundry;
        this.extras = extras;
        this.is_wicket = is_wicket;
        this.wicket_type = wicket_type;
        this.fielder_id = fielder_id;

    }

}