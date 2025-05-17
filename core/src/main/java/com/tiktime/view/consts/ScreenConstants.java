package com.tiktime.view.consts;

public final class  ScreenConstants {
    public static final float VIRTUAL_WIDTH = 1280;
    public static final float VIRTUAL_HEIGHT = 720;

    public static final float PPM = 32;

    public static final float VIEWPORT_WIDTH = VIRTUAL_WIDTH / PPM;
    public static final float VIEWPORT_HEIGHT = VIRTUAL_HEIGHT / PPM;

    private ScreenConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
