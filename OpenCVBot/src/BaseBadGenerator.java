import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BaseBadGenerator {
    public static void main(String[] args) {
        Robot robot = null;
        try {
            robot = new Robot();
            while (true) {
                String cursor = BotUtils.getCursor();
                if (cursor != null && !cursor.equals("Attack")) {
                    System.err.println("Example");
                    Point position = MouseInfo.getPointerInfo().getLocation();
                    int width = 50;
                    int height = 100;
                    BufferedImage sample = robot.createScreenCapture(new Rectangle(position.x - width / 2, position.y - height / 2, width, height));
                    Mat sampleMat = BotUtils.BufferedImage2Mat(sample);
                    Imgproc.cvtColor(sampleMat, sampleMat, Imgproc.COLOR_BGR2GRAY, CvType.CV_8U);
                    BotUtils.save_img(BotUtils.Mat2BufferedImage(sampleMat), "D:/Bad/");
                }
                Thread.sleep(10);
            }
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
