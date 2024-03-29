package interception.java.binds.key.binds;

public class InterceptionMouseState {
    public static int MOUSE_LEFT_BUTTON_DOWN = 0x001,
            MOUSE_LEFT_BUTTON_UP = 0x002,
            MOUSE_RIGHT_BUTTON_DOWN = 0x004,
            MOUSE_RIGHT_BUTTON_UP = 0x008,
            MOUSE_MIDDLE_BUTTON_DOWN = 0x010,
            MOUSE_MIDDLE_BUTTON_UP = 0x020,

    MOUSE_BUTTON_1_DOWN = MOUSE_LEFT_BUTTON_DOWN,
            MOUSE_BUTTON_1_UP = MOUSE_LEFT_BUTTON_UP,
            MOUSE_BUTTON_2_DOWN = MOUSE_RIGHT_BUTTON_DOWN,
            MOUSE_BUTTON_2_UP = MOUSE_RIGHT_BUTTON_UP,
            MOUSE_BUTTON_3_DOWN = MOUSE_MIDDLE_BUTTON_DOWN,
            MOUSE_BUTTON_3_UP = MOUSE_MIDDLE_BUTTON_UP,

    MOUSE_BUTTON_4_DOWN = 0x040,
            MOUSE_BUTTON_4_UP = 0x080,
            MOUSE_BUTTON_5_DOWN = 0x100,
            MOUSE_BUTTON_5_UP = 0x200,

    MOUSE_WHEEL = 0x400,
            MOUSE_HWHEEL = 0x800;
}
