import com.sun.jna.platform.win32.WinDef;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BotUtils {
    public static Character c = 'a';
    public static Integer x = 0;
    public static Map<String, String> cursors = new HashMap<>();
    public static CascadeClassifier cascadeClassifier;

    static {
        cursors.put("native@0xffffffffaf6f0f11", "Attack");
        cursors.put("native@0x39d20361", "Default");
        cursors.put("native@0x21521", "Talk");
        cursors.put("native@0xce14a9", "Grab");
        String path = System.getProperty("java.library.path");
        System.loadLibrary("opencv_java346");
        cascadeClassifier = new CascadeClassifier(path + "\\grass.xml");
    }

    public static String getCursorId() {
        WinDef.HCURSOR cur = cursorIdEval();
        if (cur == null) {
            return "";
        }
        return cur.toString();
    }

    public static String getCursor() {
        WinDef.HCURSOR cur = cursorIdEval();
        if (cur == null) {
            return "";
        }
        return cursors.get(cur.toString());
    }

    public static WinDef.HCURSOR cursorIdEval() {
        WinDef.HCURSOR cur;
        int x = 5;
        while ((cur = CursorLib.getCurrentCursor()) == null && x > 0) {
            x--;
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return cur;
    }

    public static Mat BufferedImage2Mat(BufferedImage image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
    }


    public static BufferedImage Mat2BufferedImage(Mat matrix) {
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", matrix, mob);
        try {
            return ImageIO.read(new ByteArrayInputStream(mob.toArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] MatToByteArray(Mat mat) {
        byte[] arr = new byte[(int) (mat.total() * mat.channels())];
        mat.get(0, 0, arr);
        return arr;
    }

    public static void save_img(BufferedImage img, String path) {
        try {
            File name = File.createTempFile("sample", ".jpg", new File(path));
            File outputfile = new File(name.getAbsoluteFile().toString());
            ImageIO.write(img, "jpg", outputfile);
            x++;
            if (x == 1000) {
                c++;
                x = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MatOfRect runCascadeClassifier(Mat mat) {
        MatOfRect rects = new MatOfRect();
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2GRAY, CvType.CV_8U);
        cascadeClassifier.detectMultiScale(mat, rects, 10, 3, Objdetect.CASCADE_SCALE_IMAGE, new Size(1, 1), new Size(30, 60));
        return rects;
    }


}


