package com.cricoscore.model;

public class Player {
    private int playerId;
    private String name;
    private String avatar;

    public Player(int playerId, String name, String avatar) {
        this.playerId = playerId;
        this.name = name;
        this.avatar = avatar;
    }

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
