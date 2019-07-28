import java.io.*;
import java.util.Objects;
import java.util.function.Function;

public class DatGenerator {

    static String path = "D:\\Dungeon";


    static void walker(final File folder, BufferedWriter writer, Function<File, String> prefix_modifier, String suffix) {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                walker(fileEntry, writer, prefix_modifier, suffix);
            } else {
//                System.out.println(fileEntry.getName());
                try {
                    writer.write(prefix_modifier.apply(fileEntry) + suffix);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        try (BufferedWriter goodWriter = new BufferedWriter(new FileWriter("D:\\Dungeon\\Good.dat"))) {
            try (BufferedWriter badWriter = new BufferedWriter(new FileWriter("D:\\Dungeon\\Bad.dat"))) {
                walker(new File(path + "\\Good"), goodWriter, file -> file.getParentFile().getName() + "/" + file.getName(), " 1 0 0 50 100\n");
                walker(new File(path + "\\Bad"), badWriter,  file ->  file.getParentFile().getName() + "/" + file.getName(), "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
