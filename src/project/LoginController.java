package project;

import com.sun.glass.ui.CommonDialogs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView bannerImageView;
    @FXML
    private ImageView logoImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File bannerFile = new File("images/banner.png");
        Image bannerImage = new Image(bannerFile.toURI().toString());
        bannerImageView.setImage(bannerImage);

        File logoFile = new File("images/logo.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);

    }

    @FXML
    public void loginButtonAction(ActionEvent event) {

        if (!usernameTextField.getText().isEmpty() && !enterPasswordField.getText().isEmpty()) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Please enter username and password");
        }
    }

    @FXML
    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT COUNT(username),password FROM users where username = '" + usernameTextField.getText() + "'";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryAuth = statement.executeQuery(verifyLogin);
            queryAuth.next();
            if (queryAuth.getInt(1) == 1) {
                if (queryAuth.getString(2).equals(enterPasswordField.getText())) {
                    loginMessageLabel.setText("Congrats! Bravo ba! Te-ai logat!");

                    String selectType = "SELECT users.id, persons.type FROM persons JOIN users ON users.id=persons.id WHERE users.username = '" + usernameTextField.getText() + "'";
                    ResultSet querryUserType = statement.executeQuery(selectType);
                    querryUserType.next();

                    int id = querryUserType.getInt(1);
                    if (querryUserType.getString(2).equals("student")) {
                        Main.mainController.user = new Student(id);
                        userInterfaceForm("../fxml/studentMenu.fxml");
                    } else if (querryUserType.getString(2).equals("professor")) {
                        Main.mainController.user = new Professor(id);
                        userInterfaceForm("../fxml/professorMenu.fxml");
                    } else {
                        Main.mainController.user = new Secretary(id);
                        userInterfaceForm("../fxml/secretaryMenu.fxml");
                    }
                } else
                    loginMessageLabel.setText("Invalid password. Try again.");
            } else {
                loginMessageLabel.setText("Invalid username. Try again.");
            }


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        } finally {
            try {
                connectDB.close();
            } catch (SQLException e) {
            }
        }
    }

    public void userInterfaceForm(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
//            root.setPadding(new Insets(10,10,10,10));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            registerStage.setScene(scene);
            registerStage.show();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createAccountFormAction() {
        try {
            Region root = FXMLLoader.load(getClass().getResource("../fxml/register.fxml"));
            root.setPadding(new Insets(10,10,10,10));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
