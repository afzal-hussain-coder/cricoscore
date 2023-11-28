package com.cricoscore.model;

import java.io.Serializable;

public class BallModel implements Serializable {

    public Float getBall() {
        return ball;
    }

    public void setBall(Float ball) {
        this.ball = ball;
    }

    float ball;
}
