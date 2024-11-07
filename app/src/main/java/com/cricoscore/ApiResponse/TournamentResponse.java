package com.cricoscore.ApiResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TournamentResponse {
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

    public List<Tournament> getData() {
        return data;
    }

    public void setData(List<Tournament> data) {
        this.data = data;
    }

    private boolean status;
    private String message;
    private List<Tournament> data;

    // Getters and setters
}
