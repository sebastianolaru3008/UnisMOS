package project;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.ResourceBundle;


public class UserMenuController implements Initializable {

    private Exam currentExam;
    private byte[] encoded;
    private Student currentStudent;
    @FXML
    private ImageView logoImageView;
    @FXML
    private Label studentNameLabel;
    @FXML
    private Label studentFacultyLabel;
    @FXML
    private Button studentDataButton;
    @FXML
    private Button calendarButton;
    @FXML
    private Button taxsesButton;
    @FXML
    private Button logoutButton;
    @FXML
    private StackPane mainStackPane;
    @FXML
    private AnchorPane pnStudentData;
    @FXML
    private AnchorPane pnExamCalendar;
    @FXML
    private AnchorPane pnTaxes;
    @FXML
    private Label gpaLabel;
    @FXML
    private Label taxLabel;
    @FXML
    private VBox vboxStudentsList;
    @FXML
    private Button catalogButton;
    @FXML
    private AnchorPane pnCatalog;
    @FXML
    private TextField fnameTextField;
    @FXML
    private TextField lnameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField ibanTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private ComboBox<String> nationalityComboBox;
    @FXML
    private ComboBox<String> groupComboBox;
    @FXML
    private RadioButton taxRadio;
    @FXML
    private RadioButton bursaryRadio;
    @FXML
    private VBox vboxExamList;
    @FXML
    private ComboBox<Integer> studentGrade;
    @FXML
    private TextField examNameTextField;
    @FXML
    private DatePicker examDatePicker;
    @FXML
    private RadioButton examStatusRadio;
    @FXML
    private VBox vboxTaxesList;
    @FXML
    private Tab editTab;
    @FXML
    private Tab manageTab;
    @FXML
    private Button updateExamButton;
    @FXML
    private Button declarationButton;
    @FXML
    private AnchorPane pnDeclaration;
    @FXML
    private Label fileChooserLabel;
    @FXML
    private ImageView declarationPreview;
    @FXML
    private Button uploadButton;
    @FXML
    public VBox vboxAssignedTaxesList;
    @FXML
    public VBox vboxStudentGradesList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentFacultyLabel.setText("UTCN - Automatica si Calculatoare");
        studentNameLabel.setText(Main.mainController.user.getFirstName() + " " + Main.mainController.user.getLastName());

        logoutButton.setText("\uD83D\uDEAA");
        logoutButton.setStyle("-fx-font-size:25");

        File logoFile = new File("images/logotype.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);

        if (Main.mainController.user instanceof Student) {
            pnStudentData.toFront();
            Student student = (Student) Main.mainController.user;
            gpaLabel.setText(student.calculateGPA().toString());
            taxLabel.setText(student.isTaxStudent() ? "Taxa" : "Buget");
            showStudentGrades();
            studentDataButton.setText("\uD83D\uDCC8");
            studentDataButton.setStyle("-fx-font-size:25");
            calendarButton.setText("\uD83D\uDCC6");
            calendarButton.setStyle("-fx-font-size:25");
            taxsesButton.setText("\uD83D\uDCB8");
            taxsesButton.setStyle("-fx-font-size:25");

        } else if (Main.mainController.user instanceof Secretary) {
            pnCatalog.toFront();
            examDatePicker.setDisable(true);
            examDatePicker.setEditable(false);
            examNameTextField.setDisable(true);
            examNameTextField.setEditable(false);
            showStudents();
            showExams();

            nationalityComboBox.getItems().add("romanian - drunken");
            nationalityComboBox.getItems().add("hungarian - kurtos");
            nationalityComboBox.getItems().add("german - shepherd");

            groupComboBox.getItems().add("30421");
            groupComboBox.getItems().add("30422");
            groupComboBox.getItems().add("30423");
            groupComboBox.getItems().add("30424");

            calendarButton.setText("\uD83D\uDCC6");
            calendarButton.setStyle("-fx-font-size:25");
            taxsesButton.setText("\uD83D\uDCB8");
            taxsesButton.setStyle("-fx-font-size:25");
            catalogButton.setText("\uD83D\uDCD1");
            catalogButton.setStyle("-fx-font-size:25");

        } else {
            pnExamCalendar.toFront();
            studentGrade.getItems().add(0);
            studentGrade.getItems().add(1);
            studentGrade.getItems().add(2);
            studentGrade.getItems().add(3);
            studentGrade.getItems().add(4);
            studentGrade.getItems().add(5);
            studentGrade.getItems().add(6);
            studentGrade.getItems().add(7);
            studentGrade.getItems().add(8);
            studentGrade.getItems().add(9);
            studentGrade.getItems().add(10);

            updateExamButton.setDisable(true);
            showExams();
            calendarButton.setText("\uD83D\uDCC6");
            calendarButton.setStyle("-fx-font-size:25");
            catalogButton.setText("\uD83D\uDCD1");
            catalogButton.setStyle("-fx-font-size:25");
            declarationButton.setText("\uD83D\uDCCB");
            declarationButton.setStyle("-fx-font-size:25");
        }
    }

    public void uploadDeclarationAction(ActionEvent event) throws IOException {
        if (Main.mainController.user instanceof Professor) {
            ((Professor) Main.mainController.user).uploadDeclaration(((Professor) Main.mainController.user).getFirstName() + ((Professor) Main.mainController.user).getLastName() + ".png", encoded);
        }
        showDeclaration();
    }

    @FXML
    public void backToLoginAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));
//            root.setPadding(new Insets(10,10,10,10));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void getMenuPanel(ActionEvent event) throws IOException {

        if (event.getSource() == studentDataButton) {
            pnStudentData.toFront();
            showStudentGrades();
        } else if (event.getSource() == calendarButton) {
            pnExamCalendar.toFront();
            showExams();
        } else if (event.getSource() == taxsesButton) {
            pnTaxes.toFront();
            showTaxes();
            if (vboxAssignedTaxesList != null)
                showAssignedTaxes();
        } else if (event.getSource() == catalogButton) {
            pnCatalog.toFront();
            showStudents();
        } else if (event.getSource() == declarationButton) {
            pnDeclaration.toFront();
            showDeclaration();
        }
    }

    @FXML
    public void getTaxesPanel(Event event) {
        if (event.getSource() == editTab) {
            showTaxes();
        } else if (event.getSource() == manageTab) {

        }
    }

    @FXML
    public void addTaxAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/dialogAddTax.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setScene(new Scene(root));
            dialogStage.setOnHiding(windowEvent -> showTaxes());
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.showAndWait();
        } catch (Exception except) {
            except.printStackTrace();
            except.getCause();
        }
    }

    @FXML
    public void assignTaxAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/dialogAssignTax.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setScene(new Scene(root));
            dialogStage.setOnHiding(windowEvent -> showAssignedTaxes());
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.showAndWait();
        } catch (Exception except) {
            except.printStackTrace();
            except.getCause();
        }
    }

    private void showAssignedTaxes() {
        vboxAssignedTaxesList.getChildren().clear();
        ArrayList<Fee> fees = new ArrayList<>();
        if (Main.mainController.user instanceof Secretary) {
            fees = ((Secretary) Main.mainController.user).getAssignedTaxes();
        }
        for (Fee fee : fees) {
            Label feeName = new Label(fee.getTax().getName() + " " + fee.getTax().getAmount() + " - " + fee.getStudent().getFirstName() + " " + fee.getStudent().getLastName());
            Button remove = new Button("✕");
            remove.setPrefHeight(40);
            remove.setPrefWidth(40);
            remove.setOnAction(e -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../fxml/deleteEntry.fxml"));
                    Stage dialogStage = new Stage();
                    dialogStage.initStyle(StageStyle.UNDECORATED);
                    dialogStage.setScene(new Scene(root));
                    dialogStage.setOnHiding(windowEvent -> showAssignedTaxes());
                    dialogStage.setUserData(fee);
                    dialogStage.initModality(Modality.APPLICATION_MODAL);
                    dialogStage.showAndWait();
                } catch (Exception except) {
                    except.printStackTrace();
                    except.getCause();
                }
            });
            BorderPane taxEntry = new BorderPane();
            taxEntry.setLeft(feeName);
            taxEntry.setRight(remove);
            taxEntry.setPrefHeight(40);
            taxEntry.getStyleClass().add("student-entry");
            taxEntry.setOnMouseClicked(e -> {
            });
            vboxAssignedTaxesList.getChildren().add(taxEntry);
        }
    }

    private void showTaxes() {
        vboxTaxesList.getChildren().clear();
        ArrayList<Tax> taxes = new ArrayList<Tax>();

        if (Main.mainController.user instanceof Secretary) {
            taxes = ((Secretary) Main.mainController.user).getTaxes();
        } else if (Main.mainController.user instanceof Student) {
            taxes = ((Student) Main.mainController.user).getTaxes();
        }

        for (Tax tax : taxes) {
            Label taxName = new Label(tax.getName() + " " + tax.getAmount());
            System.out.println(taxName.getText());
            Button remove = new Button(Main.mainController.user instanceof Student ? "Pay" : "✕");
            remove.setPrefHeight(40);
            remove.setPrefWidth(40);
            remove.setStyle("-fx-background-color: #ff7871");
            remove.setTextFill(Color.web("#ffffff"));
            remove.setOnAction(e -> {
                if (Main.mainController.user instanceof Student) {
                    payTaxAction(tax);
                } else {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../fxml/deleteEntry.fxml"));
                        Stage dialogStage = new Stage();
                        dialogStage.initStyle(StageStyle.UNDECORATED);
                        dialogStage.setScene(new Scene(root));
                        dialogStage.setOnHiding(windowEvent -> showTaxes());
                        dialogStage.setUserData(tax);
                        dialogStage.initModality(Modality.APPLICATION_MODAL);
                        dialogStage.showAndWait();
                    } catch (Exception except) {
                        except.printStackTrace();
                        except.getCause();
                    }
                }

            });
            BorderPane taxEntry = new BorderPane();
            taxEntry.setLeft(taxName);
            taxEntry.setRight(remove);
            taxEntry.setPrefHeight(40);
            taxEntry.getStyleClass().add("student-entry");
            taxEntry.setOnMouseClicked(e -> {
            });
            vboxTaxesList.getChildren().add(taxEntry);
        }
    }

    private void payTaxAction(Tax tax) {
        ((Student) Main.mainController.user).makePayment(tax.getId());
        showTaxes();
    }

    @FXML
    public void updateStudentAction(ActionEvent event) {
        currentStudent.setFirstName(fnameTextField.getText());
        currentStudent.setLastName(lnameTextField.getText());
        currentStudent.setAddress(addressTextField.getText());
        currentStudent.setNationality(nationalityComboBox.getValue());
        currentStudent.setPhone(phoneTextField.getText());
        currentStudent.setIBAN(ibanTextField.getText());
        currentStudent.setTax(taxRadio.isSelected());
        currentStudent.setBursary(bursaryRadio.isSelected());
        currentStudent.setCode(groupComboBox.getValue());
        showStudents();
    }

    private void showStudents() {
        vboxStudentsList.getChildren().clear();
        ArrayList<Student> students = new ArrayList<Student>();
        if (Main.mainController.user instanceof Secretary) {
            students = ((Secretary) Main.mainController.user).getStudents();
        } else if (Main.mainController.user instanceof Professor) {
            students = ((Professor) Main.mainController.user).getStudents();
        }
        for (Student student : students) {
            Label studentName = new Label(student.getFirstName() + " " + student.getLastName() + " - " + student.getCode());
            studentName.setPrefHeight(40);
            studentName.setPadding(new Insets(0, 0, 0, 10));


            BorderPane studentEntry = new BorderPane();

            studentEntry.setLeft(studentName);
            if (Main.mainController.user instanceof Secretary) {
                Button removeStudent = new Button("✕");
                removeStudent.setPrefHeight(40);
                removeStudent.setPrefWidth(40);
                removeStudent.setStyle("-fx-background-color: #ff7871");
                removeStudent.setTextFill(Color.web("#ffffff"));
                removeStudent.setOnAction(e -> {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../fxml/deleteEntry.fxml"));
                        Stage dialogStage = new Stage();
                        dialogStage.initStyle(StageStyle.UNDECORATED);
                        dialogStage.setScene(new Scene(root));
                        dialogStage.setOnHiding(windowEvent -> showStudents());
                        dialogStage.setUserData(student);
                        dialogStage.initModality(Modality.APPLICATION_MODAL);
                        dialogStage.showAndWait();
                    } catch (Exception except) {
                        except.printStackTrace();
                        except.getCause();
                    }
                });
                studentEntry.setRight(removeStudent);
            }

            studentEntry.setPrefHeight(40);
            studentEntry.getStyleClass().add("student-entry");
            studentEntry.setOnMouseClicked(e -> {
                currentStudent = student;
                if (Main.mainController.user instanceof Secretary) {
                    setStudentDataForm(student);
                } else if (Main.mainController.user instanceof Professor) {
                    studentGrade.setValue(getStudentGrade(student, (Professor) Main.mainController.user));
                }
            });
            vboxStudentsList.getChildren().add(studentEntry);
        }
    }

    private void setStudentDataForm(Student student) {
        fnameTextField.setText(student.getFirstName());
        lnameTextField.setText(student.getLastName());
        addressTextField.setText(student.getAddress());
        ibanTextField.setText(student.getIBAN());
        phoneTextField.setText(student.getPhone());
        taxRadio.setSelected(student.isTax());
        bursaryRadio.setSelected(student.isBursary());
        nationalityComboBox.setValue(student.getNationality());
        groupComboBox.setValue(student.getCode());
    }

    private Integer getStudentGrade(Student student, Professor professor) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        Integer grade = 0;

        String queryGrades = "SELECT * FROM grades WHERE studentID = '" + student.getId() + "' AND subject ='" + professor.getCode() + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryGrades);
            result.next();
            grade = result.getInt("grade");

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

        return grade;

    }

    @FXML
    private void submitGrade(ActionEvent event) {
        setStudentGrade(currentStudent, (Professor) Main.mainController.user);
    }

    private void setStudentGrade(Student student, Professor prof) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();


        String queryGrades = "UPDATE grades SET grade = " + studentGrade.getValue().toString() + " WHERE studentID = '" + student.getId() + "' AND subject = '" + prof.getCode() + "'";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryGrades);

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

    @FXML
    private void setApproval(ActionEvent event) {
        if (examStatusRadio.isSelected())
            ((Secretary) Main.mainController.user).approveExam(currentExam.getId());
        else
            ((Secretary) Main.mainController.user).disapproveExam(currentExam.getId());

        showExams();
    }

    public void showExams() {
        vboxExamList.getChildren().clear();
        ArrayList<Exam> exams = null;
        if (Main.mainController.user instanceof Secretary) {
            exams = ((Secretary) Main.mainController.user).getExams();
        } else if (Main.mainController.user instanceof Professor) {
            exams = ((Professor) Main.mainController.user).getExams();
        } else if (Main.mainController.user instanceof Student) {
            exams = ((Student) Main.mainController.user).getUpcomingExams();
        }
        for (Exam exam : exams) {
            Label examName = new Label(exam.getName());
            Label examDate = new Label(exam.getDate().toString());
            Label examStatus = new Label(exam.getStatus());
            Button removeExam = new Button("✕");

            BorderPane examEntry = new BorderPane();

            examName.setPrefHeight(40);
            examName.setPrefWidth(40);
            examName.setPadding(new Insets(0, 0, 0, 10));
            examDate.setPrefHeight(40);
            examStatus.setPrefHeight(40);
            examStatus.setPadding(new Insets(0, 10, 0, 0));
            removeExam.setPrefWidth(40);
            removeExam.setPrefHeight(40);
            removeExam.setStyle("-fx-background-color: #ff7871");
            removeExam.setTextFill(Color.web("#ffffff"));

            removeExam.setOnAction(e -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../fxml/deleteEntry.fxml"));
                    Stage dialogStage = new Stage();
                    dialogStage.initStyle(StageStyle.UNDECORATED);
                    dialogStage.setScene(new Scene(root));
                    dialogStage.setOnHiding(windowEvent -> showExams());
                    dialogStage.setUserData(exam);
                    dialogStage.initModality(Modality.APPLICATION_MODAL);
                    dialogStage.showAndWait();
                } catch (Exception except) {
                    except.printStackTrace();
                    except.getCause();
                }
            });

            examEntry.setLeft(examName);
            examEntry.setCenter(examDate);
            if (Main.mainController.user instanceof Professor) {
                if ((Main.mainController.user).getCode().equals(exam.getName()))
                    examEntry.setRight(removeExam);
            } else {
                examEntry.setRight(examStatus);
            }

            examEntry.setPrefHeight(40);
            examEntry.getStyleClass().add("exam-entry");

            if (Main.mainController.user instanceof Secretary) {
                if (exam.getStatus().equals("pending")) {
                    examEntry.getStyleClass().add("red-bg");
                    examName.setTextFill(Color.web("#7F1B10"));
                    examDate.setTextFill(Color.web("#7F1B10"));
                    examStatus.setTextFill(Color.web("#7F1B10"));
                } else if (exam.getStatus().equals("approved")) {
                    examEntry.getStyleClass().add("green-bg");
                    examName.setTextFill(Color.web("#176437"));
                    examDate.setTextFill(Color.web("#176437"));
                    examStatus.setTextFill(Color.web("#176437"));
                }
            } else {
                if (exam.getName().equals(Main.mainController.user.getCode())) {
                    if (exam.getStatus().equals("pending")) {
                        examEntry.getStyleClass().add("red-bg");
                        examName.setTextFill(Color.web("#7F1B10"));
                        examDate.setTextFill(Color.web("#7F1B10"));
                        examStatus.setTextFill(Color.web("#7F1B10"));
                    } else if (exam.getStatus().equals("approved")) {
                        examEntry.getStyleClass().add("green-bg");
                        examName.setTextFill(Color.web("#176437"));
                        examDate.setTextFill(Color.web("#176437"));
                        examStatus.setTextFill(Color.web("#176437"));
                    }
                } else {

                    examEntry.getStyleClass().add("blue-bg");
                    examName.setTextFill(Color.web("#155179"));
                    examDate.setTextFill(Color.web("#155179"));
                    examStatus.setTextFill(Color.web("#155179"));
                }
            }

            examEntry.setOnMouseClicked(e -> {
                if (!(Main.mainController.user instanceof Student)) {
                    if (Main.mainController.user instanceof Professor) {
                        if (!((Professor) Main.mainController.user).getCode().equals(exam.getName())) {
                            updateExamButton.setDisable(true);
                        } else {
                            updateExamButton.setDisable(false);
                        }
                    }
                    currentExam = exam;
//                System.out.println(e.getSource().getClass().toString());
                    if (Main.mainController.user instanceof Secretary) {
                        examNameTextField.setText(exam.getName());
                        examStatusRadio.setSelected(exam.getStatus().equals("approved"));
                    }
                    examDatePicker.setValue(exam.getDate());
                }
            });
            vboxExamList.getChildren().add(examEntry);
        }

    }

    @FXML
    public void updateExam(ActionEvent event) {
        ((Professor) Main.mainController.user).editExam(currentExam.getId(), examDatePicker.getValue());
        showExams();
    }

    @FXML
    public void showAddExamDialog(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/dialogAddExam.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setScene(new Scene(root));
            dialogStage.setOnHiding(windowEvent -> showExams());
            dialogStage.setUserData(root);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void singleFileChooser() throws IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        File f = fc.showOpenDialog(null);
        if (f != null) {
            fileChooserLabel.setText(f.getAbsolutePath());
            byte[] inFileBytes = Files.readAllBytes(Path.of(f.getAbsolutePath()));
            encoded = Base64.getEncoder().encode(inFileBytes);
        }
    }

    public void showDeclaration() throws IOException {
        byte[] decoded = java.util.Base64.getDecoder().decode(((Professor) Main.mainController.user).getDeclaration());
        FileOutputStream fos = new FileOutputStream("declarations/" + ((Professor) Main.mainController.user).getFirstName() + ((Professor) Main.mainController.user).getLastName() + ".png");
        fos.write(decoded);
        fos.flush();
        fos.close();

        File brandingFile = new File("declarations/" + ((Professor) Main.mainController.user).getFirstName() + ((Professor) Main.mainController.user).getLastName() + ".png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        declarationPreview.setImage(brandingImage);
        declarationPreview.setOnMouseClicked(e->{
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(brandingFile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void showStudentGrades() {
        vboxStudentGradesList.setPrefWidth(324);
        vboxStudentGradesList.getChildren().clear();
        ArrayList<Grade> grades = new ArrayList<>();
        if (Main.mainController.user instanceof Student) {
            grades = ((Student) Main.mainController.user).getGrades();
        }
        for (Grade grade : grades){
            Label gradeSubject = new Label(grade.getSubject());
            gradeSubject.setPrefHeight(40);
            gradeSubject.setPadding(new Insets( 0, 0, 0, 10));
            Label gradeValue = new Label(Integer.toString(grade.getGrade().intValue()));
            gradeValue.setPrefHeight(40);
            gradeValue.setPadding(new Insets( 0, 10, 0, 0));
            BorderPane gradeEntry = new BorderPane();
            gradeEntry.setLeft(gradeSubject);
            gradeEntry.setRight(gradeValue);
            gradeEntry.getStyleClass().add("blue-bg");
            gradeSubject.setTextFill(Color.web("#155179"));
            vboxStudentGradesList.getChildren().add(gradeEntry);
        }
    }
}
