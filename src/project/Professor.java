package project;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Professor extends User {

    Professor(int id) {
        super(id);
    }

    public ArrayList<Student> getStudents() {
        ArrayList<Student> studentsList = new ArrayList<>();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String queryStudents = "SELECT persons.id FROM persons JOIN grades on grades.studentID = persons.id WHERE persons.type = 'student' AND grades.subject = '" + this.getCode() + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryStudents);
            while (result.next()) {
                studentsList.add(new Student(result.getInt("persons.id")));
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

    void addGrade(Student student, float grade) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        String addGrade = "INSERT INTO grades(studentID,grade,subject) VALUES ('" + student.getId() + "','" + grade + "','" + this.getCode() + "');";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(addGrade);

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

    public void removeGrade(int idGrade) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String removeGrade = "DELETE FROM grades WHERE id = '" + idGrade + "' AND subject ='" + this.getCode() + "';";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(removeGrade);

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

    public void addExam(LocalDate date) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        String addExam = "INSERT INTO exams (subjectName, date, status) VALUES ('" + this.getCode() + "','" + date + "','pending');";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(addExam);

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

        String removeExam = "DELETE FROM exams WHERE id = '" + idExam + "' AND subjectName = '" + this.getCode() + "';";

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

    public void editExam(int idExam, LocalDate date) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String removeExam = "UPDATE exams SET date = '" + date + "', status = 'pending' WHERE id = '" + idExam + "' AND subjectName = '" + this.getCode() + "';";

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

    public void uploadDeclaration(String name, byte[] data) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        String removeDeclaration = "DELETE FROM files WHERE professorID =" + this.getId();
        String addDeclaration = "INSERT INTO files(professorID, name, data) VALUES (?,?,?)";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(removeDeclaration);

            PreparedStatement statement1 = connection.prepareStatement(addDeclaration);
            statement1.setInt(1, this.getId());
            statement1.setString(2, name);
            statement1.setBytes(3, data);
            statement1.executeUpdate();

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

    public byte[] getDeclaration() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        byte[] declaration = null;
        String getDeclaration = "SELECT data FROM files WHERE professorID = '" + this.getId() + "';";

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(getDeclaration);
            if(result.next()) {
                declaration = result.getBytes("data");
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
        return declaration;
    }
}
