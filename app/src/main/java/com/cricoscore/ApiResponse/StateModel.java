package com.cricoscore.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class StateModel {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    int id;
    String stateName="";

    public StateModel(JSONObject jsonObject){
        if (jsonObject.has("id")) {
            try {
                this.id = jsonObject.getInt("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject.has("name")) {
            try {
                this.stateName = jsonObject.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
