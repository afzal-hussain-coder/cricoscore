package com.cricoscore.ApiResponse;

import com.cricoscore.model.TournamentModel.AddTournamentData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddTournamentResponse {

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

    public AddTournamentData getData() {
        return data;
    }

    public void setData(AddTournamentData data) {
        this.data = data;
    }

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private AddTournamentData data;



}
