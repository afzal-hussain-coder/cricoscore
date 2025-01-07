package com.cricoscore.model;

import org.json.JSONException;
import org.json.JSONObject;

public class BowlingLeaderboatrdModel {

    private int playerId;
    private String name;
    private String avatar;
    private int totalMatches;
    private int totalInning;
    private int totalRunsConceded;
    private int totalWicketsTaken;
    private int totalMaidens;
    private double bowlingAverage;
    private double economyRate;

    // Constructor to initialize from a JSONObject
    public BowlingLeaderboatrdModel(JSONObject jsonObject) {
        this.playerId = jsonObject.optInt("player_id", 0);
        this.name = jsonObject.optString("name", "");
        this.avatar = jsonObject.optString("avatar", "");
        this.totalMatches = jsonObject.optInt("total_matches", 0);
        this.totalInning = jsonObject.optInt("total_inning", 0);
        this.totalRunsConceded = jsonObject.optInt("total_runs_conceded", 0);
        this.totalWicketsTaken = jsonObject.optInt("total_wickets_taken", 0);
        this.totalMaidens = jsonObject.optInt("total_maidens", 0);
        this.bowlingAverage = jsonObject.optDouble("bowling_average", 0.0);
        this.economyRate = jsonObject.optDouble("economy_rate", 0.0);
    }

    // Getters and Setters
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
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

    public int getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(int totalMatches) {
        this.totalMatches = totalMatches;
    }

    public int getTotalInning() {
        return totalInning;
    }

    public void setTotalInning(int totalInning) {
        this.totalInning = totalInning;
    }

    public int getTotalRunsConceded() {
        return totalRunsConceded;
    }

    public void setTotalRunsConceded(int totalRunsConceded) {
        this.totalRunsConceded = totalRunsConceded;
    }

    public int getTotalWicketsTaken() {
        return totalWicketsTaken;
    }

    public void setTotalWicketsTaken(int totalWicketsTaken) {
        this.totalWicketsTaken = totalWicketsTaken;
    }

    public int getTotalMaidens() {
        return totalMaidens;
    }

    public void setTotalMaidens(int totalMaidens) {
        this.totalMaidens = totalMaidens;
    }

    public double getBowlingAverage() {
        return bowlingAverage;
    }

    public void setBowlingAverage(double bowlingAverage) {
        this.bowlingAverage = bowlingAverage;
    }

    public double getEconomyRate() {
        return economyRate;
    }

    public void setEconomyRate(double economyRate) {
        this.economyRate = economyRate;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", totalMatches=" + totalMatches +
                ", totalInning=" + totalInning +
                ", totalRunsConceded=" + totalRunsConceded +
                ", totalWicketsTaken=" + totalWicketsTaken +
                ", totalMaidens=" + totalMaidens +
                ", bowlingAverage=" + bowlingAverage +
                ", economyRate=" + economyRate +
                '}';
    }
}
