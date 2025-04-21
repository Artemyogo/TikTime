package com.tiktime.model;

public class TimerModel {
    private float time;

    public TimerModel(){
        this.time = 0;
    }
    public TimerModel(float seconds){
        this.time = seconds;
    }
    public TimerModel(float minutes, float second){
        this.time = 60 * minutes + second;
    }

    boolean stopped(){
        return this.time == 0;
    }

    void pass(float dt){
        this.time = Math.max(0, this.time - dt);
    }

}
