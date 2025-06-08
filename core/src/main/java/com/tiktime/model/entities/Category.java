package com.tiktime.model.entities;

public enum Category {
    PLAYER(0x0001),
    FLOOR(0x0002),
    WALL(0x0004),
    BULLET(0x0008),
    DYNAMITE(0x0010),
    DOOR(0x0020),
    ENEMY_RUSHER(0x0040),
    ENEMY_ANIMAN(0x0080),
    ENEMY_MARKSMAN(0x0100),
    ENEMY_ATTACK(0x0200),
    ENEMY(combine(ENEMY_MARKSMAN, ENEMY_RUSHER, ENEMY_ANIMAN)),
    LIVING_ENTITY(combine(ENEMY, PLAYER)),
    OBSTACLE(combine(WALL, DOOR));

    private final short bits;

    Category(int bits) {
        this.bits = (short) bits;
    }

    public short getBits() {
        return bits;
    }
    public boolean is(short mask) {
        return (mask == (mask | bits));
    }
    public boolean intercept(short mask) {
        return (mask & bits) != 0;
    }
    public static Category fromBits(short bits) {
        for (Category c : values()) {
            if (c.getBits() == bits) return c;
        }
        throw new IllegalArgumentException("Unknown category bits: " + bits);
    }

    public static short combine(Category... categories) {
        short mask = 0;
        for (Category c : categories) {
            mask |= c.getBits();
        }
        return mask;
    }
}
