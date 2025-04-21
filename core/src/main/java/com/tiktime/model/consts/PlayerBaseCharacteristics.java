package com.tiktime.model.consts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

public class PlayerBaseCharacteristics {
    public static final int BASE_HP;
    public static final int BASE_SPEED;
    public static final int BASE_DAMAGE;
    public static final int BASE_REGEN;

    static {
        Json json = new Json();
        JSONData jsonData = json.fromJson(JSONData.class, Gdx.files.internal("config.json"));

        BASE_HP = jsonData.player.baseHP;
        BASE_SPEED = jsonData.player.baseSpeed;
        BASE_DAMAGE = jsonData.player.baseDamage;
        BASE_REGEN = jsonData.player.baseRegen;
    }

    private static class Player {
        public int baseHP;
        public int baseSpeed;
        public int baseDamage;
        public int baseRegen;
    }

    private static class JSONData {
        public Player player;
    }

    private PlayerBaseCharacteristics() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
