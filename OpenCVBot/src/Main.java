
import com.sun.javafx.geom.Vec3d;
import interception.java.binds.Interception;
import interception.java.binds.InterceptionImpl;
import interception.java.binds.KeyStroke;
import interception.java.binds.key.binds.Keys;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.security.Key;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static java.lang.System.exit;


public class Main {
    static AtomicBoolean hit = new AtomicBoolean(true);
    static final String WINDOWNAME = "R2";

    private static void take(Interception interception) throws InterruptedException {
        interception.pressId(Keys.E);
        Thread.sleep(100);
        interception.releaseId(Keys.E);
        Thread.sleep(1500);
    }

    private static void atack(Interception interception) throws InterruptedException {
        interception.pressLeftMouseButton();
        Thread.sleep(20);
        interception.pressRightMouseButton();
        Thread.sleep(50);
        interception.releaseLeftMouseButton();
        Thread.sleep(20);
        interception.releaseRightMouseButton();
    }

    static void turnOnBuff(Interception interception) {
        Thread rebuff = new Thread(() -> {
            while (hit.get()) {
                try {
                    Thread.sleep(900000 * 2);
                    interception.pressId(Keys.One);
                    Thread.sleep(3000);
                    interception.releaseId(Keys.One);
                    Thread.sleep(100);
                    interception.pressId(Keys.Three);
                    Thread.sleep(3000);
                    interception.releaseId(Keys.Three);
                    Thread.sleep(100);
                    interception.pressId(Keys.Four);
                    Thread.sleep(3000);
                    interception.releaseId(Keys.Four);
                    Thread.sleep(100);
                    interception.pressId(Keys.Five);
                    Thread.sleep(3000);
                    interception.releaseId(Keys.Five);
                    Thread.sleep(100);
                    interception.pressId(Keys.Seven);
                    Thread.sleep(3000);
                    interception.releaseId(Keys.Seven);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        rebuff.setDaemon(true);
        rebuff.setPriority(10);
        rebuff.start();

    }

    static void turnOnPause(Interception interception) {
        Thread thread = new Thread(() -> {
            while (true) {
                if (interception.isKeyPressed(Keys.P)) {
                    hit.set(!hit.get());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.setDaemon(true);
        thread.setPriority(10);
        thread.start();
    }

    static void turnOnExit(Interception interception) {
        Thread keyHooker = new Thread(() -> {
            while (true) {
                if (interception.isKeyPressed(Keys.Escape)) {
                    exit(0);
                }
            }
        });
        keyHooker.setDaemon(true);
        keyHooker.setPriority(9);
        keyHooker.start();
    }

    static void turnOnAutocast(Interception interception) {
        Thread bomb = new Thread(() -> {
            try {
                while (hit.get()) {
                    interception.writeChar('3');
                    Thread.sleep(3 * 1000);
                    take(interception);
                    take(interception);
                    take(interception);
                    take(interception);
                    take(interception);
                    System.err.println("done");
                    //тут кулдаун подкрутить надо
                    Thread.sleep(3 * 1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        bomb.setDaemon(true);
        bomb.setPriority(10);
        bomb.start();
    }

    static boolean hpTest(Mat mat, double prcnthp) {
        Mat screen = mat.clone();
        Imgproc.cvtColor(screen, screen, Imgproc.COLOR_BGR2GRAY);

        Core.inRange(screen,
                new Scalar(40, 40, 40),
                new Scalar(155, 155, 155), screen);
        screen = screen.submat(749, 753, 438, 589);

        int sumhp = 0;

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j < screen.height(); j++) {
                double curhp = screen.width() * prcnthp + i;
                if (curhp > 0 && curhp < screen.width() - 1) {
                    sumhp += screen.get(j, (int) curhp)[0];
                } else {
                    sumhp += 255;
                }
            }
        }
        int eps = 3;
        if (sumhp / 255 < screen.height() * 3 - eps) {
            return true;
        }
        return false;
    }

    static boolean tpTest(Mat mat, double prcnttp) {
        Mat screen = mat.clone();
        Imgproc.cvtColor(screen, screen, Imgproc.COLOR_BGR2GRAY);

        Core.inRange(screen,
                new Scalar(40, 40, 40),
                new Scalar(155, 155, 155), screen);
        screen = screen.submat(749, 753, 438, 589);

        int sumtp = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = 0; j < screen.height(); j++) {
                double curtp = screen.width() * prcnttp + i;
                if (curtp > 0 && curtp < screen.width() - 1) {
                    sumtp += screen.get(j, (int) curtp)[0];
                } else {
                    sumtp += 255;
                }
            }
        }
        int eps = 3;
        if (sumtp / 255 < screen.height() * 3 - eps) {
            return true;
        }
        return false;
    }

    static public AtomicReference<Mat> atomicImg = new AtomicReference<>();

    static public void turnOnAI(Interception interception) {
        Thread scanner = new Thread(() -> {
            while (true) {
                System.err.println("WORKING");
                Rectangle current = rect.get();
                Mat mat = atomicImg.get();
                if (mat == null) {
                    continue;
                }
                MatOfRect rects = BotUtils.runCascadeClassifier(mat);
                for (Rect rect : rects.toArray()) {
                    int rectX = rect.x;
                    int rectY = rect.y;
                    int rectWidth = rect.width;
                    int rectHeight = rect.height;
                    for (int i = rectX; i < rectX + rectWidth; i++) {
                        mat.put(rectY, i, new byte[]{-1, 0, 0});
                        mat.put(rectY + rectHeight, i, new byte[]{-1, 0, 0});
                    }
                    for (int i = rectY; i < rectY + rectHeight; i++) {
                        mat.put(i, rectX, new byte[]{-1, 0, 0});
                        mat.put(i, rectX + rectWidth, new byte[]{-1, 0, 0});
                    }
                }
                try {
                    for (Rect rect : rects.toArray()) {
                        int rectX = rect.x + rect.width / 2;
                        int rectY = rect.y + rect.height / 2;
                        interception.moveMouseTo(current.x + rectX, current.y + rectY);
                        Thread.sleep(100);
                        if (BotUtils.getCursorId().equals(atackID)) {
                            System.err.println("ATACK");
                            atack(interception);
                            Thread.sleep(5000);
                            take(interception);

                            take(interception);

                            take(interception);

                            take(interception);

                            take(interception);
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        scanner.setDaemon(true);
        scanner.setPriority(10);
        scanner.start();
    }

    static void turnOnScreenScaner(JFrame form, Interception interception) {
        try {
            Robot robot = new Robot();
            JLabel image = new JLabel();
            form.add(image);
            while (hit.get()) {
                Rectangle current = rect.get();
                BufferedImage img = robot.createScreenCapture(current);
                Mat mat = BotUtils.BufferedImage2Mat(img);
                atomicImg.set(mat);
                boolean tp = tpTest(mat, 0.7);
                boolean hp = hpTest(mat, 0.9);
                if (tp) {
                    interception.writeChar('q');
                    exit(0);
                }
                if (hp) {
                    interception.writeChar('1');
                }
                Icon icon = new ImageIcon(HighGui.toBufferedImage(mat));
                image.setIcon(icon);
                Thread.sleep(50);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static void turnOnRectUpdater() {
        Thread rectUpdater = new Thread(() -> {
            try {
                while (true) {
                    rect.lazySet(WindowsWindowLib.getRect(WINDOWNAME));
                    Thread.sleep(5000);
                }
            } catch (WindowsWindowLib.WindowNotFoundException e) {
                e.printStackTrace();
            } catch (WindowsWindowLib.GetWindowRectException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        rectUpdater.setDaemon(true);
        rectUpdater.setPriority(10);
        rectUpdater.start();
    }

    public static AtomicReference<Rectangle> rect = new AtomicReference<>();
    public static String atackID;

    public static void main(String[] args) {
        try {
            rect.lazySet(WindowsWindowLib.getRect(WINDOWNAME));
        } catch (WindowsWindowLib.WindowNotFoundException e) {
            e.printStackTrace();
        } catch (WindowsWindowLib.GetWindowRectException e) {
            e.printStackTrace();
        }
//        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        turnOnRectUpdater();
        Interception interception = new InterceptionImpl();
        interception.load();
        turnOnExit(interception);
        turnOnPause(interception);
        System.err.println("On mob and E");
        while (!interception.isKeyPressed(Keys.E)) {
        }
        atackID = BotUtils.getCursorId();
        JFrame form = new JFrame();
        Rectangle currect = rect.get();
        form.setSize(currect.width, currect.height + 30);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setLocationRelativeTo(null);
        form.setLocation(currect.x + currect.width, 0);
        form.setVisible(true);
        turnOnAI(interception);
        turnOnScreenScaner(form, interception);
        interception.unload();
    }
}
