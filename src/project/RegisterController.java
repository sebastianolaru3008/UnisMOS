package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class RegisterController implements Initializable {

    @FXML
    private ImageView bannerRegister;
    @FXML
    private Button registerButton;
    @FXML
    private Button backButton;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField setPasswordField;
    @FXML
    private TextField confirmPasswordField;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        File regBannerFile = new File("images/register.png");
        Image regBannerImage = new Image(regBannerFile.toURI().toString());
        bannerRegister.setImage(regBannerImage);
    }

    public void registerAction(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        //TODO: Insert all data into Persons Table
        String insertUser = "INSERT INTO users(username, password, email) VALUES ('" + usernameTextField.getText() + "','" + setPasswordField.getText() + "','" + emailTextField.getText() + "')";
        String userIdDB = "SELECT id FROM users WHERE username ='" + usernameTextField.getText() + "'";
        String personQuery = "INSERT INTO persons(firstName, lastName, type, id) VALUES ('" + firstnameTextField.getText() + "','" + lastnameTextField.getText() + "','student','";
        try {

            if (firstnameTextField.getText().isEmpty() || lastnameTextField.getText().isEmpty() || usernameTextField.getText().isEmpty() ||
                    emailTextField.getText().isEmpty() || setPasswordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()) {

                messageLabel.setText("Fill in all the fields!");
            } else {
                int count = 0;
                Statement statement = connection.createStatement();
                try {
                    ResultSet resultSet = statement.executeQuery(userIdDB);
                    while (resultSet.next()) {
                        count++;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    e.getCause();
                }
                if (count != 0) {
                    messageLabel.setText("Username already taken!");
                } else if (setPasswordField.getText().equals(confirmPasswordField.getText())) {

                    try {
                        statement.executeUpdate(insertUser);
                        ResultSet queryUserId = statement.executeQuery(userIdDB);
                        queryUserId.next();
                        String id = queryUserId.getString("id");
//                    messageLabel.setText(queryUserId.getString(1));
                        String insertPerson = personQuery + id + "')";

                        statement.executeUpdate(insertPerson);

                        String insertNullGrade = "INSERT INTO grades(studentID,subject) VALUES('" + id + "','";

                        for(String course : Main.mainController.courses){
                            statement.executeUpdate(insertNullGrade + course + "')");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        e.getCause();
                    }

                    backToLogin(event);

                } else {
                    messageLabel.setText("Passwords must match!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }
    }

    public void backToLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
