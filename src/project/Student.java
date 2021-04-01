package project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private static DecimalFormat df2 = new DecimalFormat("#.##");
    Student(int id) {
        super(id);
    }



    public Float calculateGPA() {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String queryGrades = "SELECT * FROM grades WHERE studentID = '" + this.getId() + "'";
        int count = 0;
        float sum = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryGrades);
            while (result.next()) {
                sum += result.getFloat("grade");
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }
        }

        df2.setRoundingMode(RoundingMode.UP);
        return Float.parseFloat(df2.format(((float)sum)/count));
    }

    public boolean isTaxStudent() {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String queryScholarship = "SELECT * FROM persons WHERE id = '" + this.getId() + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryScholarship);
            result.next();
            return result.getBoolean("tax");
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }
        }
        return false;
    }


    public ArrayList<Exam> getUpcomingExams() {

        ArrayList<Exam> examsList = new ArrayList<>();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String queryExams = "SELECT * FROM exams WHERE status = 'approved'";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryExams);
            while(result.next()){
                examsList.add(new Exam(result.getString("subjectName"), result.getDate("date").toLocalDate(), result.getString("status"), result.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }
        }
        return examsList;
    }

    public ArrayList<Tax> getTaxes() {

        ArrayList<Tax> taxesList = new ArrayList<>();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String queryTaxes = "SELECT * FROM assignedtaxes WHERE studentID = '" + this.getId() + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryTaxes);
            while(result.next()){
                taxesList.add(new Tax(result.getInt("taxID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }
        }
        return taxesList;
    }

    public void makePayment(int idTax){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String payTax = "DELETE FROM assignedtaxes WHERE taxID = '" + idTax + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(payTax);

        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }
        }
    }

    public ArrayList<Grade> getGrades(){
        ArrayList<Grade> gradesList = new ArrayList<>();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String queryTaxes = "SELECT * FROM grades WHERE studentID = '" + this.getId() +"'";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryTaxes);
            while (result.next()) {
                gradesList.add(new Grade(result.getString("subject"), result.getFloat("grade")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }
        }
        return gradesList;
    }
}
