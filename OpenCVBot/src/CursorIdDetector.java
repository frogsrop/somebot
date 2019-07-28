

public class CursorIdDetector {
    public static void main(String[] args) {

        while (true) {
            String id = BotUtils.getCursorId();
            System.err.println(id);
            System.err.println();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
