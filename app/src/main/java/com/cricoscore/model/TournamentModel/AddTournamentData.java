package com.cricoscore.model.TournamentModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class AddTournamentData {


    public int getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(int tournament_id) {
        this.tournament_id = tournament_id;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
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

    public String getBall_type() {
        return ball_type;
    }

    public void setBall_type(String ball_type) {
        this.ball_type = ball_type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
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

    public String getSponser() {
        return sponser;
    }

    public void setSponser(String sponser) {
        this.sponser = sponser;
    }

    public int getNo_of_team() {
        return no_of_team;
    }

    public void setNo_of_team(int no_of_team) {
        this.no_of_team = no_of_team;
    }

    public String getTournament_logo() {
        return tournament_logo;
    }

    public void setTournament_logo(String tournament_logo) {
        this.tournament_logo = tournament_logo;
    }

    public String getTournament_banner() {
        return tournament_banner;
    }

    public void setTournament_banner(String tournament_banner) {
        this.tournament_banner = tournament_banner;
    }

    public String getOrg_contact_number() {
        return org_contact_number;
    }

    public void setOrg_contact_number(String org_contact_number) {
        this.org_contact_number = org_contact_number;
    }

    public String getOrg_contact_email() {
        return org_contact_email;
    }

    public void setOrg_contact_email(String org_contact_email) {
        this.org_contact_email = org_contact_email;
    }

    public String getOrg_contact_name() {
        return org_contact_name;
    }

    public void setOrg_contact_name(String org_contact_name) {
        this.org_contact_name = org_contact_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getGround_name() {
        return ground_name;
    }

    public void setGround_name(String ground_name) {
        this.ground_name = ground_name;
    }

    private int tournament_id;
    private int created_by;
    private String name="";
    private String type="";
    private String ball_type="";
    private String start_date="";
    private String end_date="";
    private String state="";
    private String location="";
    private float fee;
    private String prize="";
    private int  discount;
    private String sponser="";
    private int no_of_team;
    private String tournament_logo="";
    private String tournament_banner="";
    private String org_contact_number;
    private String org_contact_email="";
    private String org_contact_name="";
    private String status="";
    private String created_at="";
    private String update_at="";
    private String ground_name="";

    public AddTournamentData(JSONObject jsonObject){
        if(jsonObject.has("tournament_id")){
            try {
                this.tournament_id = jsonObject.getInt("tournament_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("created_by")){
            try {
                this.created_by = jsonObject.getInt("created_by");
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

        if(jsonObject.has("type")){
            try {
                this.type = jsonObject.getString("type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("ball_type")){
            try {
                this.ball_type = jsonObject.getString("ball_type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("start_date")){
            try {
                this.start_date = jsonObject.getString("start_date");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("end_date")){
            try {
                this.end_date = jsonObject.getString("end_date");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("state")){
            try {
                this.state = jsonObject.getString("state");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("location")){
            try {
                this.location = jsonObject.getString("location");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("fee")){
            try {
                this.fee = jsonObject.getInt("fee");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("prize")){
            try {
                this.prize = jsonObject.getString("prize");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("discount")){
            try {
                this.discount = jsonObject.getInt("discount");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("sponser")){
            try {
                this.sponser = jsonObject.getString("sponser");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("no_of_team")){
            try {
                this.no_of_team = jsonObject.getInt("no_of_team");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("tournament_logo")){
            try {
                this.tournament_logo = jsonObject.getString("tournament_logo");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("tournament_banner")){
            try {
                this.tournament_banner = jsonObject.getString("tournament_banner");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("org_contact_number")){
            try {
                this.org_contact_number = jsonObject.getString("org_contact_number");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("org_contact_email")){
            try {
                this.org_contact_email = jsonObject.getString("org_contact_email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("org_contact_name")){
            try {
                this.org_contact_name = jsonObject.getString("org_contact_name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("status")){
            try {
                this.status = jsonObject.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("created_at")){
            try {
                this.created_at = jsonObject.getString("created_at");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("update_at")){
            try {
                this.update_at = jsonObject.getString("update_at");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has("ground_name")){
            try {
                this.ground_name = jsonObject.getString("ground_name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
