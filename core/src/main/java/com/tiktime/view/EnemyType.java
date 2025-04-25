package com.tiktime.view;

public enum EnemyType {
    /// TODO just some names to begin, in the future should rename like to 'sniper' or smth else
    First(0),
    Second(1),
    Third(2);

    private int id;
    EnemyType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

