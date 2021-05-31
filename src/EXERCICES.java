import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EXERCICES {

    //Exercice 1
    public void testFile(String path) {
        File file = new File(path);

        boolean x = false;
        if (file.exists()) {
            if (file.isFile()) {
                System.out.println("Its a file");
            } else {
                System.out.println("Its a directory");
                for (File f : file.listFiles())
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

    //Exercice 2
    public void copie(String source, String destination) {

        List<String> strings = new ArrayList<>();
        try {
            String strCurrentLine;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(source));
            while ((strCurrentLine = bufferedReader.readLine()) != null) {


                System.out.println(strCurrentLine);
                strings.add(strCurrentLine);
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(destination));
            for (String s : strings)
                bufferedWriter.write(s);

            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //Exercice 3
    public void exportEmployees(String filePath, String dept_no) {
        System.out.println("creating csv file: " + filePath);

        try {
            // 1.	Charger le driver JDBC pour MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2.	Établir la connexion à la base de données MySQL
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", "");

            // 3.	Mise à jour des salaires
            PreparedStatement ps = con.prepareStatement("SELECT * FROM employees WHERE emp_no IN (SELECT DISTINCT emp_no FROM dept_emp WHERE dept_no = ?)");

            ps.setString(1, dept_no);
            ResultSet rs = ps.executeQuery();

            FileWriter fw = new FileWriter(filePath);

            fw.write("birth_date,first_name,last_name,gender,hire_date");

            //Get rows
            while (rs.next()) {


                String first_name, last_name, gender;
                Date hire_date, birth_date;
                first_name=rs.getString("first_name");
                last_name=rs.getString("last_name");
                gender=rs.getString("gender");
                hire_date=rs.getDate("hire_date");
                birth_date=rs.getDate("birth_date");

                String line = String.format("%s,%s,%s,%t,%t",first_name,last_name,gender,hire_date,birth_date);
                fw.write(line);
            }
            fw.flush();
            fw.close();
            System.out.println("CSV File is created successfully.");
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Impossible de charger le connecteur JDBC");
        } catch (SQLException throwables) {
            System.out.println("Une erreur s'est produite pendant la connexion a la BDD");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    
    public void importEmployees(String filePath) {


    }


    public static void main(String[] args) {

    }
}
