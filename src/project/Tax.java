package project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Tax {
    private int idTax;
    private float amount;
    private String name;

    Tax(int idTax){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        this.idTax = idTax;
        String initQuery = "SELECT * FROM taxes WHERE taxes.id = '" + this.idTax + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(initQuery);
            result.next();

            this.amount = result.getFloat("amount");
            this.name = result.getString("name");
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

    public float getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public int getId() { return idTax; }
}
