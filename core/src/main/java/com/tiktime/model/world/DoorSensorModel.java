package com.tiktime.model.world;

public class DoorSensorModel {
    private int counter = 0;
    public void onDoorEntry(){
        counter++;
    }
    public void onDoorExit(){
        counter--;
    }
    public boolean isInDoor(){
        return counter > 0;
    }
    public void reset(){
        counter = 0;
    }


}
