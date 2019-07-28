import interception.java.binds.Interception;
import interception.java.binds.InterceptionImpl;

public class DeviceIdDetector {
    public static void main(String[] args) {
        Interception interception = new InterceptionImpl();
        interception.load();
        interception.enableDebug();
        while (true) {
        }
    }
}
