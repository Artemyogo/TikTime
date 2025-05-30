package com.tiktime.model.events;

public class GameEvent {
    public final GameEventType type;
    public final Object data;

    public GameEvent(GameEventType type, Object data) {
        this.type = type;
        this.data = data;
    }
}
