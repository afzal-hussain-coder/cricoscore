package com.cricoscore.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Balled implements Serializable {
    public int getBallId() {
        return ballId;
    }

    public void setBallId(int ballId) {
        this.ballId = ballId;
    }

    public int getInningId() {
        return inningId;
    }

    public void setInningId(int inningId) {
        this.inningId = inningId;
    }

    public int getOverNumber() {
        return overNumber;
    }

    public void setOverNumber(int overNumber) {
        this.overNumber = overNumber;
    }

    public int getBallNumber() {
        return ballNumber;
    }

    public void setBallNumber(int ballNumber) {
        this.ballNumber = ballNumber;
    }

    public int getBowlerId() {
        return bowlerId;
    }

    public void setBowlerId(int bowlerId) {
        this.bowlerId = bowlerId;
    }

    public int getBatsmanId() {
        return batsmanId;
    }

    public void setBatsmanId(int batsmanId) {
        this.batsmanId = batsmanId;
    }

    public int getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public int getIsWicket() {
        return isWicket;
    }

    public void setIsWicket(int isWicket) {
        this.isWicket = isWicket;
    }

    public String getWicketType() {
        return wicketType;
    }

    public void setWicketType(String wicketType) {
        this.wicketType = wicketType;
    }

    public int getFielderId() {
        return fielderId;
    }

    public void setFielderId(int fielderId) {
        this.fielderId = fielderId;
    }

    public String getIsBoundary() {
        return isBoundary;
    }

    public void setIsBoundary(String isBoundary) {
        this.isBoundary = isBoundary;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    private int ballId;
    private int inningId;
    private int overNumber;
    private int ballNumber;
    private int bowlerId;
    private int batsmanId;
    private int runsScored;
    private int isWicket;
    private String wicketType;
    private int fielderId;
    private String isBoundary;
    private String extras;
    private String commentary;

    // Getters and setters

    public Balled(JSONObject jsonObject){
        if(jsonObject.has("ball_id")){
            try {
                this.ballId = jsonObject.getInt("ball_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("inning_id")){
            try {
                this.inningId = jsonObject.getInt("inning_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("over_number")){
            try {
                this.overNumber = jsonObject.getInt("over_number");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("ball_number")){
            try {
                this.ballNumber = jsonObject.getInt("ball_number");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("bowler_id")){
            try {
                this.bowlerId = jsonObject.getInt("bowler_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("batsman_id")){
            try {
                this.batsmanId = jsonObject.getInt("batsman_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("runs_scored")){
            try {
                this.runsScored = jsonObject.getInt("runs_scored");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("is_wicket")){
            try {
                this.isWicket = jsonObject.getInt("is_wicket");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("wicket_type")){
            try {
                this.wicketType = jsonObject.getString("wicket_type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("fielder_id")){
            try {
                this.fielderId = jsonObject.getInt("fielder_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("is_boundry")){
            try {
                this.isBoundary = jsonObject.getString("is_boundry");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("extras")){
            try {
                this.extras = jsonObject.getString("extras");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("commentary")){
            try {
                this.commentary = jsonObject.getString("commentary");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

