package com.tiktime.common;

public enum EnemyType {
    MARKSMAN(0),
    ANIMAN(1),
    RUSHER(2);

    private final int id;
    EnemyType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

