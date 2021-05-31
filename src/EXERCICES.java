import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

            fw.write("first_name,last_name,gender,hire_date,birth_date");

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


        List<Employee> employees = new ArrayList<>();
        try {
            // 1.	Charger le driver JDBC pour MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2.	Établir la connexion à la base de données MySQL
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", "");

            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = bufferedReader.readLine();

            while (line != null) {

                // use string.split to load a string array with the values from  each line of
                // the file, using a comma as the delimiter
                 String[] attributes = line.split(",");

                Date hire_date= (Date) new SimpleDateFormat("YYYY-MM-DD").parse(attributes[3]);
                Date birth_date= (Date) new SimpleDateFormat("YYYY-MM-DD").parse(attributes[3]);

                 Employee employee = new Employee(attributes[0],attributes[1],attributes[2],hire_date,birth_date);
                // adding book into ArrayList
                employees.add(employee);
                // read next line before looping
                // if end of file reached, line would be null
                line = bufferedReader.readLine();
                }


            // 3.	Mise à jour des salaires
          /*  PreparedStatement ps = con.prepareStatement("INSERT INTO employees.employees( birth_date, first_name, last_name, gender, hire_date) VALUES ( ?, ?, ?, ?, ?)");

            ps.setDate(2, birth_date);
            ps.setString(3, first_name);
            ps.setString(4, last_name);
            ps.setString(5, gender);
            ps.setDate(6, hire_date);
            ps.execute();*/


            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Impossible de charger le connecteur JDBC");
        } catch (SQLException throwables) {
            System.out.println("Une erreur s'est produite pendant la connexion a la BDD");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

    }
}
