package com.cricoscore.ParamBody;

public class PlayingSqudUpdateBody {
    public int getCurrent_striker_id() {
        return current_striker_id;
    }

    public void setCurrent_striker_id(int current_striker_id) {
        this.current_striker_id = current_striker_id;
    }

    public int getCurrent_non_striker_id() {
        return current_non_striker_id;
    }

    public void setCurrent_non_striker_id(int current_non_striker_id) {
        this.current_non_striker_id = current_non_striker_id;
    }

    public int getCurrent_bowler_id() {
        return current_bowler_id;
    }

    public void setCurrent_bowler_id(int current_bowler_id) {
        this.current_bowler_id = current_bowler_id;
    }

    public int getInning_id() {
        return inning_id;
    }

    public void setInning_id(int inning_id) {
        this.inning_id = inning_id;
    }

    int current_striker_id;
    int current_non_striker_id;
    int current_bowler_id;
    int inning_id;

    public String getPlayer_type() {
        return player_type;
    }

    public void setPlayer_type(String player_type) {
        this.player_type = player_type;
    }

    String player_type ="";


    public PlayingSqudUpdateBody(int current_striker_id,int current_non_striker_id,int current_bowler_id,
                                 int inning_id,String player_type){
        this.current_striker_id = current_striker_id;
        this.current_non_striker_id = current_non_striker_id;
        this.current_bowler_id = current_bowler_id;
        this.inning_id = inning_id;
        this.player_type =player_type;
    }
}
