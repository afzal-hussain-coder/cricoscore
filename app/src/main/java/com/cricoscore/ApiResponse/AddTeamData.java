package com.cricoscore.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddTeamData {
     public boolean isTeam_id() {
          return team_id;
     }

     public void setTeam_id(boolean team_id) {
          this.team_id = team_id;
     }

     public boolean isName() {
          return name;
     }

     public void setName(boolean name) {
          this.name = name;
     }

     public boolean isTeam_logo() {
          return team_logo;
     }

     public void setTeam_logo(boolean team_logo) {
          this.team_logo = team_logo;
     }

     public boolean isCreated_by() {
          return created_by;
     }

     public void setCreated_by(boolean created_by) {
          this.created_by = created_by;
     }

     public boolean isCreated_at() {
          return created_at;
     }

     public void setCreated_at(boolean created_at) {
          this.created_at = created_at;
     }

     public boolean isUpdate_at() {
          return update_at;
     }

     public void setUpdate_at(boolean update_at) {
          this.update_at = update_at;
     }

     @SerializedName("team_id")
     @Expose
     private boolean team_id;

     @SerializedName("name")
     @Expose
     private boolean name;

     @SerializedName("team_logo")
     @Expose
     private boolean team_logo;

     @SerializedName("created_by")
     @Expose
     private boolean created_by;

     @SerializedName("created_at")
     @Expose
     private boolean created_at;

     @SerializedName("update_at")
     @Expose
     private boolean update_at;


}
