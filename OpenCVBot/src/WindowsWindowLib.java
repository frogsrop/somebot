import java.awt.*;
import java.util.Arrays;

import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef.HWND;
import org.w3c.dom.css.Rect;

public class WindowsWindowLib {

    static {
        Native.register("User32.dll");
    }

    public static native HWND FindWindowA(String lpClassName, String lpWindowName);

    public static native int GetWindowRect(HWND handle, int[] rect);

    public static Rectangle getRect(String windowName) throws WindowNotFoundException,
            GetWindowRectException {
        HWND hwnd = FindWindowA(null, windowName);
        if (hwnd == null) {
            throw new WindowNotFoundException("", windowName);
        }

        int[] rect = {0, 0, 0, 0};
        int result = GetWindowRect(hwnd, rect);
        if (result == 0) {
            throw new GetWindowRectException(windowName);
        }
        Rectangle ans = new Rectangle(rect[0], rect[1], rect[2] - rect[0], rect[3] - rect[1]);
        return ans;
    }

    @SuppressWarnings("serial")
    public static class WindowNotFoundException extends Exception {
        public WindowNotFoundException(String className, String windowName) {
            super(String.format("Window null for className: %s; windowName: %s",
                    className, windowName));
        }
    }

    @SuppressWarnings("serial")
    public static class GetWindowRectException extends Exception {
        public GetWindowRectException(String windowName) {
            super("Window Rect not found for " + windowName);
        }
    }

    public static void main(String[] args) {
        String windowName = "4game";
        Rectangle rect;
        try {
            rect = getRect(windowName);
            System.out.printf("The corner locations for the window \"%s\" are %s",
                    windowName, rect.toString());
        } catch (WindowNotFoundException e) {
            e.printStackTrace();
        } catch (GetWindowRectException e) {
            e.printStackTrace();
        }
    }
}