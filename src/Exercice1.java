import java.io.File;
import java.io.IOException;

public class Exercice1 {

    public void testFile(String path) {
        File file = new File(path);

        boolean x = false;
        if (file.exists()) {
            if (file.isFile()) {
                System.out.println("Its a file");
            } else {
                System.out.println("Its a directory");
                for(File f:file.listFiles())
                    System.out.println(f.getAbsolutePath());
            }
        } else {
            System.out.println("Not exist");
            try {
                x = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (x) System.out.println("File created successfully");
            else System.out.println("File created unsuccessfully");
        }


    }


    public static void main(String[] args) {

    }
}
