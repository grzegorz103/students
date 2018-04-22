package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<Student> studList = new ArrayList<>();

    @FXML
    private TextField idField, nameField, surnameField, ageField;

    @FXML
    private ChoiceBox majorBox, semBox;

    @FXML
    private TableView<Student> tableView;

    @FXML
    private TableColumn<Student, Integer> colID, colSem;

    @FXML
    private TableColumn<Student, String> colName, colSurname, colAge, colMajor;


    public Controller() {
        try {
            readIn();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        majorBox.setItems(FXCollections.observableArrayList("IT", "Mathematics", "Chemistry"));
        majorBox.getSelectionModel().select(0);
        semBox.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7"));
        semBox.getSelectionModel().select(0);
        show();

        tableView.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Student clicked = row.getItem();
                    this.studList.remove(clicked);
                    try {
                        save();
                        readIn();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    show();
                }
            });
            return row;
        });
    }

    @FXML
    private void addNewStud() {
        if (idField.getText().length() == 0 || nameField.getText().length() == 0 || surnameField.getText().length() == 0 || ageField.getText().length() == 0) {
            Alert warning = new Alert(Alert.AlertType.INFORMATION);
            warning.setContentText(null);
            warning.setContentText("Please fill in all fields");
            warning.showAndWait();
            return;
        }
        Alert warning;
        try {
            this.studList.add(new Student(Long.valueOf(idField.getText()), nameField.getText(), surnameField.getText(), ageField.getText(), majorBox.getValue().toString(), Integer.valueOf(semBox.getValue().toString())));
        } catch (NumberFormatException e) {
            warning = new Alert(Alert.AlertType.INFORMATION);
            warning.setContentText(null);
            warning.setContentText("ID and age is numeric only!");
            warning.showAndWait();
            return;
        }

        warning = new Alert(Alert.AlertType.INFORMATION);
        warning.setHeaderText("Success");
        warning.setContentText("Student has been added!");
        warning.showAndWait();
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        show();
        reset();
    }

    private void reset() {
        this.idField.setText("");
        this.nameField.setText("");
        this.surnameField.setText("");
        this.ageField.setText("");
    }

    private void save() throws IOException {
        FileOutputStream fos = new FileOutputStream(new File("students.dat"));
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(this.studList);

        oos.close();
        fos.close();
    }

    private void readIn() throws IOException {
        File file = new File("students.dat");
        if (!file.exists())
            file.createNewFile();

        FileInputStream fis = new FileInputStream(new File("students.dat"));
        ObjectInputStream ois = new ObjectInputStream(fis);

        try {
            this.studList = (List<Student>) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ois.close();
        fis.close();
    }

    private void show() {
        colID.setCellValueFactory(new PropertyValueFactory<Student, Integer>("PID"));
        colName.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<Student, String>("surname"));
        colAge.setCellValueFactory(new PropertyValueFactory<Student, String>("age"));
        colMajor.setCellValueFactory(new PropertyValueFactory<Student, String>("major"));
        colSem.setCellValueFactory(new PropertyValueFactory<Student, Integer>("semester"));
        ObservableList<Student> data = FXCollections.observableArrayList(this.studList);
        tableView.setItems(data);
    }

}
