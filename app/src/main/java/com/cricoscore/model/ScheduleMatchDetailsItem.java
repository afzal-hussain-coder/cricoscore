package com.cricoscore.model;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScheduleMatchDetailsItem implements Serializable{
    public int getScheduleMatchId() {
        return scheduleMatchId;
    }

    public void setScheduleMatchId(int scheduleMatchId) {
        this.scheduleMatchId = scheduleMatchId;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getTeamA() {
        return teamA;
    }

    public void setTeamA(int teamA) {
        this.teamA = teamA;
    }

    public int getTeamB() {
        return teamB;
    }

    public void setTeamB(int teamB) {
        this.teamB = teamB;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGround() {
        return ground;
    }

    public void setGround(String ground) {
        this.ground = ground;
    }

    public String getBallType() {
        return ballType;
    }

    public void setBallType(String ballType) {
        this.ballType = ballType;
    }

    public String getPitchType() {
        return pitchType;
    }

    public void setPitchType(String pitchType) {
        this.pitchType = pitchType;
    }

    public String getMatch_type() {
        return match_type;
    }

    public void setMatch_type(String match_type) {
        this.match_type = match_type;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getIsMatchStatus() {
        return isMatchStatus;
    }

    public void setIsMatchStatus(String isMatchStatus) {
        this.isMatchStatus = isMatchStatus;
    }

    public String getTeamAPlayerIds() {
        return teamAPlayerIds;
    }

    public void setTeamAPlayerIds(String teamAPlayerIds) {
        this.teamAPlayerIds = teamAPlayerIds;
    }

    public String getTeamBPlayerIds() {
        return teamBPlayerIds;
    }

    public void setTeamBPlayerIds(String teamBPlayerIds) {
        this.teamBPlayerIds = teamBPlayerIds;
    }

    public int getNoOfOver() {
        return noOfOver;
    }

    public void setNoOfOver(int noOfOver) {
        this.noOfOver = noOfOver;
    }

    public int getNoOfOverBowler() {
        return noOfOverBowler;
    }

    public void setNoOfOverBowler(int noOfOverBowler) {
        this.noOfOverBowler = noOfOverBowler;
    }

    public TeamInfo getTeamAInfo() {
        return teamAInfo;
    }

    public void setTeamAInfo(TeamInfo teamAInfo) {
        this.teamAInfo = teamAInfo;
    }

    public TeamInfo getTeamBInfo() {
        return teamBInfo;
    }

    public void setTeamBInfo(TeamInfo teamBInfo) {
        this.teamBInfo = teamBInfo;
    }

    private int scheduleMatchId;
    private int tournamentId;
    private int teamA;
    private int teamB;
    private String location;
    private String ground;
    private String ballType;
    private String pitchType;
    private String match_type;
    private String startDateTime;
    private String isMatchStatus;
    private String teamAPlayerIds;
    private String teamBPlayerIds;
    private int noOfOver;
    private int noOfOverBowler;
    private TeamInfo teamAInfo;
    private TeamInfo teamBInfo;

    public int getInning_no() {
        return inning_no;
    }

    public void setInning_no(int inning_no) {
        this.inning_no = inning_no;
    }

    int inning_no;

    // New fields for selected players
    private List<PlayerModel> selectedTeamAPlayer;
    private List<PlayerModel> selectedTeamBPlayer;

    // Getters and Setters for the new fields
    public List<PlayerModel> getSelectedTeamAPlayer() {
        return selectedTeamAPlayer;
    }

    public void setSelectedTeamAPlayer(List<PlayerModel> selectedTeamAPlayer) {
        this.selectedTeamAPlayer = selectedTeamAPlayer;
    }

    public List<PlayerModel> getSelectedTeamBPlayer() {
        return selectedTeamBPlayer;
    }

    public void setSelectedTeamBPlayer(List<PlayerModel> selectedTeamBPlayer) {
        this.selectedTeamBPlayer = selectedTeamBPlayer;
    }

    // Constructor that accepts JSONObject
    public ScheduleMatchDetailsItem(JSONObject jsonObject) {
        this.scheduleMatchId = jsonObject.optInt("schedule_match_id");
        this.tournamentId = jsonObject.optInt("tournament_id");
        this.teamA = jsonObject.optInt("team_a");
        this.teamB = jsonObject.optInt("team_b");
        this.location = jsonObject.optString("location");
        this.ground = jsonObject.optString("ground");
        this.ballType = jsonObject.optString("ball_type");
        this.pitchType = jsonObject.optString("pitch_type");
        this.startDateTime = jsonObject.optString("start_date_time");
        this.isMatchStatus = jsonObject.optString("is_match_status");
        this.teamAPlayerIds = jsonObject.optString("team_a_player_ids");
        this.teamBPlayerIds = jsonObject.optString("team_b_player_ids");
        this.noOfOver = jsonObject.optInt("no_of_over");
        this.noOfOverBowler = jsonObject.optInt("no_of_over_bowler");
        this.match_type = jsonObject.optString("match_type");
        this.inning_no = jsonObject.optInt("inning_no");

        // Parsing nested objects for team info
        JSONObject teamAInfoJson = jsonObject.optJSONObject("team_a_info");
        if (teamAInfoJson != null) {
            this.teamAInfo = new TeamInfo(teamAInfoJson);
        }

        JSONObject teamBInfoJson = jsonObject.optJSONObject("team_b_info");
        if (teamBInfoJson != null) {
            this.teamBInfo = new TeamInfo(teamBInfoJson);
        }

        // Parsing selected players for Team A
        JSONArray selectedTeamAPlayersArray = jsonObject.optJSONArray("selected_team_a_player");
        if (selectedTeamAPlayersArray != null) {
            this.selectedTeamAPlayer = new ArrayList<>();
            for (int i = 0; i < selectedTeamAPlayersArray.length(); i++) {
                JSONObject playerJson = selectedTeamAPlayersArray.optJSONObject(i);
                if (playerJson != null) {
                    this.selectedTeamAPlayer.add(new PlayerModel(playerJson));
                }
            }
        }

        // Parsing selected players for Team B
        JSONArray selectedTeamBPlayersArray = jsonObject.optJSONArray("selected_team_b_player");
        if (selectedTeamBPlayersArray != null) {
            this.selectedTeamBPlayer = new ArrayList<>();
            for (int i = 0; i < selectedTeamBPlayersArray.length(); i++) {
                JSONObject playerJson = selectedTeamBPlayersArray.optJSONObject(i);
                if (playerJson != null) {
                    this.selectedTeamBPlayer.add(new PlayerModel(playerJson));
                }
            }
        }
    }

    // Nested TeamInfo class
    public static class TeamInfo {
        private int teamId;
        private String name;
        private String teamLogo;
        private String city;
        private List<PlayerModel> players;

        // Constructor that accepts JSONObject
        public TeamInfo(JSONObject jsonObject) {
            this.teamId = jsonObject.optInt("team_id");
            this.name = jsonObject.optString("name");
            this.teamLogo = jsonObject.optString("team_logo");
            this.city = jsonObject.optString("city");

            // Parsing players array (if any)
            this.players = new ArrayList<>();
            JSONArray playersArray = jsonObject.optJSONArray("players");
            if (playersArray != null) {
                for (int i = 0; i < playersArray.length(); i++) {
                    JSONObject playerJson = playersArray.optJSONObject(i);
                    if (playerJson != null) {
                        this.players.add(new PlayerModel(playerJson));
                    }
                }
            }
        }

        // Getters and Setters for TeamInfo
        public int getTeamId() {
            return teamId;
        }

        public String getName() {
            return name;
        }

        public String getTeamLogo() {
            return teamLogo;
        }

        public String getCity() {
            return city;
        }

        public List<PlayerModel> getPlayers() {
            return players;
        }
    }

    // Nested PlayerModel class
    public static class PlayerModel implements Serializable {
        private int playerId;
        private String name;
        private String avatar;

        // Constructor that accepts JSONObject
        public PlayerModel(JSONObject jsonObject) {
            this.playerId = jsonObject.optInt("player_id");
            this.name = jsonObject.optString("name");
            this.avatar = jsonObject.optString("avatar");
        }

        // Getters and Setters
        public int getPlayerId() {
            return playerId;
        }

        public String getName() {
            return name;
        }

        public String getAvatar() {
            return avatar;
        }
    }
}
