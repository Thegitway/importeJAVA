import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Exercice2 {

    public  void copie(String source,String destination){

        List<String> strings = new ArrayList<>();
        try {
            String strCurrentLine;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(source));
            while ((strCurrentLine = bufferedReader.readLine()) != null){


                System.out.println(strCurrentLine);
                strings.add(strCurrentLine);
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(destination));
            for(String s :strings)
                bufferedWriter.write(s);

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
     public static void main(String[] args) {

    }
}
