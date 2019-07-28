import com.sun.jna.*;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.W32APIOptions;

import java.util.*;
import java.util.List;

public class CursorLib {

    static {
        Native.register("User32.dll");
    }

    private static native int GetCursorInfo(CURSORINFO cursorinfo);

    public static WinDef.HCURSOR getCurrentCursor() {
        final CURSORINFO cursorinfo = new CURSORINFO();
        final int success = GetCursorInfo(cursorinfo);
        if (success != 1) {
            throw new Error("Could not retrieve cursor info: " + String.valueOf(Native.getLastError()));
        }
        return cursorinfo.hCursor;
    }

    public static class CURSORINFO extends Structure {

        public int cbSize;
        public int flags;
        public WinDef.HCURSOR hCursor;
        public WinDef.POINT ptScreenPos;

        public CURSORINFO() {
            this.cbSize = Native.getNativeSize(CURSORINFO.class, null);
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("cbSize", "flags", "hCursor", "ptScreenPos");
        }
    }
}
