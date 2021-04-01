package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static MainController mainController = new MainController();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Region root = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));
//        root.setPadding(new Insets(10,10,10,10));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
