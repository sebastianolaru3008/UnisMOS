package project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Secretary extends User {
    Secretary(int id) {
        super(id);
    }

    public ArrayList<Student> getStudents(){
        ArrayList<Student> studentsList = new ArrayList<>();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String queryStudents = "SELECT id FROM persons WHERE type = 'student'";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryStudents);
            while (result.next()) {
                studentsList.add(new Student(result.getInt("id")));
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
        return studentsList;
    }

    public ArrayList<Exam> getExams() {

        ArrayList<Exam> examsList = new ArrayList<>();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String queryExams = "SELECT * FROM exams";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryExams);
            while (result.next()) {
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

        String queryTaxes = "SELECT * FROM taxes";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryTaxes);
            while (result.next()) {
                taxesList.add(new Tax(result.getInt("id")));
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

    public ArrayList<Fee> getAssignedTaxes() {

        ArrayList<Fee> feesList = new ArrayList<>();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String queryTaxes = "SELECT * FROM assignedtaxes";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryTaxes);
            while (result.next()) {
                feesList.add(new Fee(new Tax(result.getInt("taxID")), new Student(result.getInt("studentID")), result.getInt("id")));
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
        return feesList;
    }


    void assignTax(Student student,Tax tax) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String queryTax = "SELECT COUNT(id) FROM assignedtaxes WHERE studentID = '" + student.getId() + "' AND taxID = '" + tax.getId() + "'";
        String addTax = "INSERT INTO assignedtaxes(studentID,taxID) VALUES ('" + student.getId() +"','" + tax.getId() + "');";

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryTax);
            result.next();
            if(result.getInt(1) == 0) {
                statement.executeUpdate(addTax);
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
    }

    void assignTaxToAll(Tax tax) {
        for(Student student : this.getStudents()){
            this.assignTax(student, tax);
        }
    }

    void addTax(String taxName, float amount) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String addTax = "INSERT INTO taxes(name,amount) VALUES ('" + taxName + "'," + amount + ");";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(addTax);

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

    public void removeTax(int idTax) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String removeTax = "DELETE FROM taxes WHERE id = '" + idTax + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(removeTax);

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

    public void removeFee(int idFee) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String removeTax = "DELETE FROM assignedtaxes WHERE id = '" + idFee + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(removeTax);

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


    public void removeExam(int idExam) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String removeExam = "DELETE FROM exams WHERE id = '" + idExam + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(removeExam);

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

    public void removeStudent(int idStudent) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String removePerson = "DELETE FROM persons WHERE id = '" + idStudent + "'";
        String removeUser = "DELETE FROM users WHERE id = '" + idStudent + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(removePerson);
            statement.executeUpdate(removeUser);

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

    public void approveExam(Integer idExam) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String approveExam = "UPDATE exams SET status = 'approved' WHERE id = '" + idExam + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(approveExam);

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

    public void disapproveExam(Integer idExam) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String approveExam = "UPDATE exams SET status = 'pending' WHERE id = '" + idExam + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(approveExam);

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
}

