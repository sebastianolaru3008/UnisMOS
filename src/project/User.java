package project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String nationality;
    private String phone;
    private String IBAN;
    private String type;
    private boolean tax;
    private boolean bursary;
    private String code;

    User(int id) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        this.id = id;
        String initQuery = "SELECT * FROM users JOIN persons ON users.id = persons.id WHERE users.id = '" + this.id + "'";

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(initQuery);
            result.next();

            this.username = result.getString("username");
            this.email = result.getString("email");
            this.firstName = result.getString("firstName");
            this.lastName = result.getString("lastName");
            this.address = result.getString("address");
            this.nationality = result.getString("nationality");
            this.phone = result.getString("phone");
            this.IBAN = result.getString("IBAN");
            this.type = result.getString("type");
            this.tax = result.getBoolean("tax");
            this.bursary = result.getBoolean("bursary");
            this.code = result.getString("code");


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

    public boolean isBursary() {
        return bursary;
    }

    public void setBursary(boolean bursary) {
        this.bursary = bursary;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();


        String update = "UPDATE persons SET bursary = " + Boolean.toString(bursary) + " WHERE persons.id = '" + this.getId() + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);
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


    public void setUsername(String username) {
        this.username = username;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();


        String update = "UPDATE users SET username = '" + username + "' WHERE users.id = '" + this.getId() + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);
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

    public void setEmail(String email) {
        this.email = email;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();


        String update = "UPDATE users SET email = '" + email + "' WHERE users.id = '" + this.getId() + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();


        String update = "UPDATE persons SET firstName = '" + firstName + "' WHERE persons.id = '" + this.getId() + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);

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

    public void setLastName(String lastName) {
        this.lastName = lastName;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();


        String update = "UPDATE persons SET lastName = '" + lastName + "' WHERE persons.id = '" + this.getId() + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);

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

    public void setAddress(String address) {
        this.address = address;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();


        String update = "UPDATE persons SET address = '" + address + "' WHERE persons.id = '" + this.getId() + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);

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

    public void setNationality(String nationality) {
        this.nationality = nationality;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();


        String update = "UPDATE persons SET nationality = '" + nationality + "' WHERE persons.id = '" + this.getId() + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);

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

    public void setPhone(String phone) {
        this.phone = phone;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();


        String update = "UPDATE persons SET phone = '" + phone + "' WHERE persons.id = '" + this.getId() + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);

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

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();


        String update = "UPDATE persons SET IBAN = '" + IBAN + "' WHERE persons.id = '" + this.getId() + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);

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

    public void setType(String type) {
        this.type = type;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();


        String update = "UPDATE persons SET type = '" + type + "' WHERE persons.id = '" + this.getId() + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);

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

    public void setTax(boolean tax) {
        this.tax = tax;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();


        String update = "UPDATE persons SET tax = " + Boolean.toString(tax) + " WHERE persons.id = '" + this.getId() + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);

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

    public void setCode(String code) {
        this.code = code;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();


        String update = "UPDATE persons SET code = '" + code + "' WHERE persons.id = '" + this.getId() + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);

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

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPhone() {
        return phone;
    }

    public String getIBAN() {
        return IBAN;
    }

    public String getType() {
        return type;
    }

    public boolean isTax() {
        return tax;
    }

    public String getCode() {
        return code;
    }

    public ArrayList<Grade> getGrades() {
        ArrayList<Grade> gradesList = new ArrayList<>();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String queryTaxes = "SELECT * FROM grades WHERE studentID = '" + this.getId() + "'";
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
