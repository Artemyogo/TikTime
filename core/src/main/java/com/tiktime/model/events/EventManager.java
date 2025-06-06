package com.tiktime.model.events;

import java.util.*;
import com.tiktime.model.events.EventListener;

public class EventManager {
    private static final EnumMap<GameEventType, List<EventListener>> listeners = new EnumMap<>(GameEventType.class);

    public static void subscribe(GameEventType type, EventListener listener) {
        listeners.computeIfAbsent(type, k -> new ArrayList<>()).add(listener);
    }

    public static void unsubscribe(GameEventType type, EventListener listener) {
        if (listeners.containsKey(type)) {
            listeners.get(type).remove(listener);
        }
    }

    public static void fireEvent(GameEvent event) {
        List<EventListener> typeListeners = listeners.get(event.type);
        if (typeListeners != null) {
            for (EventListener listener : new ArrayList<>(typeListeners)) {
                listener.onEvent(event);
            }
        }
    }
}
