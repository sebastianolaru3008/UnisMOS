package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class DialogWindow implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Button addExamButton;
    @FXML
    private DatePicker examDatePicker;
    @FXML
    private AnchorPane titlePane;
    @FXML
    public TextField taxNameTextField;
    @FXML
    public TextField taxPriceTextField;
    @FXML
    private Button addTaxButton;
    @FXML
    private ComboBox<String> studentComboBox;
    @FXML
    private ComboBox<String> taxComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (titlePane != null) {
            if (Main.mainController.user instanceof Professor) {
                titlePane.setStyle("-fx-background-color: #2ecc71");
            } else if (Main.mainController.user instanceof Student) {
                titlePane.setStyle("-fx-background-color: #e74c3c");
            } else if (Main.mainController.user instanceof Secretary) {
                titlePane.setStyle("-fx-background-color: #9b59b6");
            }
        }

        if (studentComboBox != null && taxComboBox != null)
            if (Main.mainController.user instanceof Secretary) {
                studentComboBox.getItems().add("Select All");
                for (Student student : ((Secretary) Main.mainController.user).getStudents()) {
                    studentComboBox.getItems().add(student.getFirstName() + " " + student.getLastName());
                }
                for (Tax tax : ((Secretary) Main.mainController.user).getTaxes()) {
                    taxComboBox.getItems().add(tax.getName());
                }
            }
    }

    @FXML
    public void goBackAction(ActionEvent event) {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void addExamAction(ActionEvent event) {
        if (examDatePicker.getValue() != null) {
            ((Professor) Main.mainController.user).addExam(examDatePicker.getValue());
            Stage currentStage = (Stage) addExamButton.getScene().getWindow();
            currentStage.close();
        }
    }

    @FXML
    public void addTaxAction(ActionEvent event) {
        if (!(taxNameTextField.getText().equals("") || taxPriceTextField.getText().equals(""))) {
            ((Secretary) Main.mainController.user).addTax(taxNameTextField.getText(), Float.parseFloat(taxPriceTextField.getText()));
            Stage currentStage = (Stage) addTaxButton.getScene().getWindow();
            currentStage.close();
        }
    }

    @FXML
    public void assignTaxAction(ActionEvent event) {
        if (studentComboBox.getValue() != null && taxComboBox.getValue() != null) {
            String[] names = studentComboBox.getValue().split(" ");
            Student taxedStudent = null;
            for (Student student : ((Secretary) Main.mainController.user).getStudents()) {
                if (student.getFirstName().equals(names[0]) && student.getLastName().equals(names[1])) {
                    taxedStudent = student;
                }
            }
            Tax choosenTax = null;
            for (Tax tax : ((Secretary) Main.mainController.user).getTaxes()) {
                if (tax.getName().equals(taxComboBox.getValue())) {
                    choosenTax = tax;
                }
            }
            if ((taxedStudent != null || studentComboBox.getValue().equals("Select All")) && choosenTax != null) {
                if(studentComboBox.getValue().equals("Select All")){
                    ((Secretary) Main.mainController.user).assignTaxToAll(choosenTax);
                } else {
                    ((Secretary) Main.mainController.user).assignTax(taxedStudent, choosenTax);
                }
                Stage currentStage = (Stage) addTaxButton.getScene().getWindow();
                currentStage.close();
            }
        }
    }

    @FXML
    public void deleteEntry(ActionEvent event) {
        if (Main.mainController.user instanceof Professor) {
            ((Professor) Main.mainController.user).removeExam(((Exam) (((Button) event.getSource()).getScene().getWindow()).getUserData()).getId());
        } else if (Main.mainController.user instanceof Secretary) {
            if ((((Button) event.getSource()).getScene().getWindow()).getUserData() instanceof Tax)
                ((Secretary) Main.mainController.user).removeTax(((Tax) (((Button) event.getSource()).getScene().getWindow()).getUserData()).getId());
            else if ((((Button) event.getSource()).getScene().getWindow()).getUserData() instanceof Fee)
                ((Secretary) Main.mainController.user).removeFee(((Fee) (((Button) event.getSource()).getScene().getWindow()).getUserData()).getId());
            else if((((Button) event.getSource()).getScene().getWindow()).getUserData() instanceof Student)
                ((Secretary) Main.mainController.user).removeStudent(((Student) (((Button) event.getSource()).getScene().getWindow()).getUserData()).getId());
        }
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }
}
