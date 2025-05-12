package com.tiktime.model.enums;

public enum Category {
    PLAYER   (0x0001),
    ENEMY    (0x0002),
    WALL     (0x0004),
    BULLET   (0x0008),
    DYNAMITE    (0x0010),
    DOOR     (0x0020);

    private final short bit;

    Category(int bit) {
        this.bit = (short) bit;
    }

    public short getBit() {
        return bit;
    }
    public boolean is(short mask) {
        return mask == (mask | bit);
    }

    public static short combine(Category... categories) {
        short mask = 0;
        for (Category c : categories) {
            mask |= c.getBit();
        }
        return mask;
    }
}
