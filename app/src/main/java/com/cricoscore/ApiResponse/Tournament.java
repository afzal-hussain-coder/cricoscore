package com.cricoscore.ApiResponse;

import com.google.gson.annotations.SerializedName;

public class Tournament {

    @SerializedName("tournament_id")
    private int tournamentId;
    @SerializedName("created_by")
    private int createdBy;
    private String name;
    private String type;
    @SerializedName("ball_type")
    private String ballType;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
    private String state;
    private String city;
    private String location;
    private int fee;
    private String prize;
    private int discount;
    private String sponsor;
    @SerializedName("no_of_team")
    private int numberOfTeams;
    @SerializedName("tournament_logo")
    private String tournamentLogo;
    @SerializedName("tournament_banner")
    private String tournamentBanner;
    @SerializedName("org_contact_number")
    private String orgContactNumber;
    @SerializedName("org_contact_email")
    private String orgContactEmail;
    @SerializedName("org_contact_name")
    private String orgContactName;
    private String status;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("update_at")
    private String updatedAt;
    @SerializedName("ground_name")
    private String groundName;

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBallType() {
        return ballType;
    }

    public void setBallType(String ballType) {
        this.ballType = ballType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public String getTournamentLogo() {
        return tournamentLogo;
    }

    public void setTournamentLogo(String tournamentLogo) {
        this.tournamentLogo = tournamentLogo;
    }

    public String getTournamentBanner() {
        return tournamentBanner;
    }

    public void setTournamentBanner(String tournamentBanner) {
        this.tournamentBanner = tournamentBanner;
    }

    public String getOrgContactNumber() {
        return orgContactNumber;
    }

    public void setOrgContactNumber(String orgContactNumber) {
        this.orgContactNumber = orgContactNumber;
    }

    public String getOrgContactEmail() {
        return orgContactEmail;
    }

    public void setOrgContactEmail(String orgContactEmail) {
        this.orgContactEmail = orgContactEmail;
    }

    public String getOrgContactName() {
        return orgContactName;
    }

    public void setOrgContactName(String orgContactName) {
        this.orgContactName = orgContactName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getGroundName() {
        return groundName;
    }

    public void setGroundName(String groundName) {
        this.groundName = groundName;
    }
}
