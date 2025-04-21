package com.tiktime.model;

public class TimerModel {
    private float time;
    public TimerModel(){
        time = 0;
    }
    public TimerModel(float seconds){
        this.time = seconds;
    }
    public TimerModel(float minutes, float second){
        this.time = 60*minutes + second;
    }

    boolean stopped(){
        return time == 0;
    }

    void pass(float dt){
        time = Math.max(0, time - dt);
    }

}
