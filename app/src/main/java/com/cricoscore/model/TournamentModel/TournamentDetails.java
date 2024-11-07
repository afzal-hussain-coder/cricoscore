package com.cricoscore.model.TournamentModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class TournamentDetails implements Serializable {

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

    public int tournament_id;
    public int created_by;
    public String name="";
    public String type="";
    public String ball_type="";
    public String start_date="";
    public String end_date="";
    public String state="";
    public String city="";
    public String location="";


    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public int fees;
    public String prize="";
    public int discount;
    public String sponser="";
    public int  no_of_team;
    public String tournament_logo="";
    public String tournament_banner ="";

    public TournamentDetails(JSONObject jsonObject){

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
        if(jsonObject.has("city")){
            try {
                this.city = jsonObject.getString("city");
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
        if(jsonObject.has("fees")){
            try {
                this.fees = jsonObject.getInt("fees");
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


    }


}
