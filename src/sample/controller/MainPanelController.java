package sample.controller;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import sample.animation.FadeInFadeOut;
import sample.database.DatabaseHandler;
import sample.model.Medicine;
import sample.model.Treatment;
import sample.model.User;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Optional;

public class MainPanelController {
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    public JFXListView<Medicine> medicineList;
    private ObservableList<Medicine> medicines;
    @FXML
    private Label profilePanelFullName;
    @FXML
    private Label profilePanelUserName;
    @FXML
    private ImageView profileEditButton;
    @FXML
    private ImageView changeUser;
    @FXML
    private Label dateLabel;
    @FXML
    private ImageView addMedicine;
    @FXML
    private ImageView editMedicine;
    @FXML
    private ImageView deleteMedicine;
    @FXML
    private AnchorPane popupProfilePanel;
    @FXML
    private AnchorPane popupMedicinePanel;
    @FXML
    private Label treatmentDateLabel;
    @FXML
    private Label treatmentDurationLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label daysToEndLabel;
    @FXML
    private ImageView addTreatment;
    @FXML
    private ImageView editTreatment;
    @FXML
    private ImageView deleteTreatment;
    @FXML
    private ComboBox<String> treatmentComboBox;
    @FXML
    private AnchorPane popupTreatmentPanel;
    @FXML
    private JFXListView<Medicine> quickList;

    private ObservableList<Medicine> medicinesQuickList;

    private DatabaseHandler databaseHandler;

    static Medicine selectedMedicine;

    static Treatment selectedTreatment;

    private static String selectedItemComboBox;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        medicinesQuickList = FXCollections.observableArrayList();

        // resource scene strings
        String profileEditScene = "/sample/view/edit_profile.fxml";
        String addMedicineScene = "/sample/view/add_medicine.fxml";
        String editMedicineScene = "/sample/view/edit_medicine.fxml";
        String addTreatmentScene = "/sample/view/add_treatment.fxml";
        String editTreatmentScene = "/sample/view/edit_treatment.fxml";
        String loginScene = "/sample/view/login.fxml";
        String mainPanelScene = "/sample/view/main_panel.fxml";

        // prepare main panel to work
        refreshMedicineList();
        updateProfilePanel();
        updateComboBox();
        if (selectedTreatment != null){
            updateQuickList();
        }

        LocalDate dateLabelText = LocalDate.now();
        dateLabel.setText("Today is: " + dateLabelText.toString());

        profileEditButton.setOnMouseClicked(event -> {
            try {
                fadeInFadeOut.popupYPanel(popupProfilePanel, profileEditScene, 429);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        fadeInFadeOut.hoverOverIconEffects(profileEditButton);

        addMedicine.setOnMouseClicked(event -> {
            try {
                fadeInFadeOut.popupYPanel(popupMedicinePanel, addMedicineScene, 479);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fadeInFadeOut.hoverOverIconEffects(addMedicine);

        editMedicine.setOnMouseClicked(event -> {
            selectedMedicine = medicineList.getSelectionModel().getSelectedItem();
            if (selectedMedicine != null) {
                try {
                    fadeInFadeOut.popupYPanel(popupMedicinePanel, editMedicineScene, 479);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        fadeInFadeOut.hoverOverIconEffects(editMedicine);

        deleteMedicine.setOnMouseClicked(event -> {
            selectedMedicine = medicineList.getSelectionModel().getSelectedItem();
            if (selectedMedicine != null) {
                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Medicine from List Confirmation");
                    alert.setHeaderText("Please confirm do you want to continue and delete medicine: " + selectedMedicine.getName());
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setContentText("If you want to delete this Medicine from List press OK in other case please press CANCEL");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        databaseHandler.deleteMedicine(selectedMedicine.getId());
                        refreshMedicineList();
                    } else {
                        alert.close();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        fadeInFadeOut.hoverOverIconEffects(deleteMedicine);

        addTreatment.setOnMouseClicked(event -> {
            try {
                fadeInFadeOut.popupYPanel(popupTreatmentPanel, addTreatmentScene, 369);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fadeInFadeOut.hoverOverIconEffects(addTreatment);

        treatmentComboBox.setOnAction(event -> {
            databaseHandler = new DatabaseHandler();
            selectedTreatment = new Treatment();
            selectedItemComboBox = treatmentComboBox.getSelectionModel().getSelectedItem();
            ObservableList<Medicine> tempMedicinesQuickList = FXCollections.observableArrayList();
            if (!selectedItemComboBox.isEmpty()) {
                try {
                    ResultSet resultSet = databaseHandler.getTreatmentByName(selectedItemComboBox);
                    while (resultSet.next()) {
                        selectedTreatment.setId(resultSet.getInt("treatmentid"));
                        selectedTreatment.setName(selectedItemComboBox);
                        selectedTreatment.setStartDate(resultSet.getDate("startdate"));
                        selectedTreatment.setDuration(resultSet.getInt("duration"));
                        selectedTreatment.setHeadache(resultSet.getInt("headache"));
                        selectedTreatment.setFever(resultSet.getInt("fever"));
                        selectedTreatment.setCold(resultSet.getInt("cold"));
                        selectedTreatment.setCough(resultSet.getInt("cough"));
                        if (selectedTreatment != null){
                            treatmentDateLabel.setText(selectedTreatment.getStartDate().toString());
                            treatmentDurationLabel.setText(String.valueOf(selectedTreatment.getDuration()));
                            LocalDate startDate = selectedTreatment.getStartDate().toLocalDate();
                            LocalDate endDate = startDate.plusDays(selectedTreatment.getDuration());
                            endDateLabel.setText(endDate.toString());
                            LocalDate today = LocalDate.now();
                            String daysToEnd = String.valueOf(today.until(endDate, ChronoUnit.DAYS));
                            daysToEndLabel.setText(daysToEnd);
                        }
                        medicines.forEach(medicine -> {
                            if (medicine.getHeadache() == 1 && selectedTreatment.getHeadache() == 1){
                                tempMedicinesQuickList.add(medicine);
                            } else if (medicine.getFever() == 1 && selectedTreatment.getFever() == 1){
                                tempMedicinesQuickList.add(medicine);
                            } else if (medicine.getCold() == 1 && selectedTreatment.getCold() == 1){
                                tempMedicinesQuickList.add(medicine);
                            } else if (medicine.getCough() == 1 && selectedTreatment.getCough() == 1){
                                tempMedicinesQuickList.add(medicine);
                            }
                        });
                        medicinesQuickList = tempMedicinesQuickList;
                        SortedList<Medicine> sortedList = new SortedList<>(medicinesQuickList,
                                Comparator.comparing(o -> o.getName().toUpperCase()));
                        quickList.getItems().setAll(sortedList);
                        quickList.setCellFactory(CellController -> new QuickListCellController());
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                treatmentDateLabel.setText(selectedTreatment.getStartDate().toString());
                treatmentDurationLabel.setText(String.valueOf(selectedTreatment.getDuration()));
                LocalDate startDate = selectedTreatment.getStartDate().toLocalDate();
                LocalDate endDate = startDate.plusDays(selectedTreatment.getDuration());
                endDateLabel.setText(endDate.toString());
                LocalDate today = LocalDate.now();
                String daysToEnd = String.valueOf(today.until(endDate, ChronoUnit.DAYS));
                daysToEndLabel.setText(daysToEnd);
            }
        });

        editTreatment.setOnMouseClicked(event -> {
            if (selectedTreatment != null) {
                try {
                    fadeInFadeOut.popupYPanel(popupTreatmentPanel, editTreatmentScene, 369);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        fadeInFadeOut.hoverOverIconEffects(editTreatment);

        deleteTreatment.setOnMouseClicked(event -> {
            treatmentComboBox = new ComboBox<>();
            if (selectedTreatment != null) {
                try {
                    databaseHandler.deleteTreatment(selectedTreatment.getId());
                    selectedTreatment = null;
                    fadeInFadeOut.quickRefresh(rootAnchorPane, mainPanelScene);
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        fadeInFadeOut.hoverOverIconEffects(deleteTreatment);
        
        changeUser.setOnMouseClicked(event -> fadeInFadeOut.makeFadeOut(rootAnchorPane, loginScene));
        fadeInFadeOut.hoverOverIconEffects(changeUser);

    }
    private void refreshMedicineList() throws SQLException, ClassNotFoundException {
        databaseHandler = new DatabaseHandler();
        medicines = FXCollections.observableArrayList();
        ResultSet resultSet = databaseHandler.getMedicines();

        while (resultSet.next()) {
            Medicine medicine = new Medicine();
            medicine.setId(resultSet.getInt("medicineid"));
            medicine.setName(resultSet.getString("name"));
            medicine.setDescription(resultSet.getString("description"));
            medicine.setExpireDate(resultSet.getDate("expiredate"));
            medicine.setHeadache(resultSet.getInt("headache"));
            medicine.setFever(resultSet.getInt("fever"));
            medicine.setCold(resultSet.getInt("cold"));
            medicine.setCough(resultSet.getInt("cough"));

            medicines.add(medicine);
        }
        SortedList<Medicine> sortedList = new SortedList<>(medicines,
                Comparator.comparing(o -> o.getName().toUpperCase()));
        medicineList.setItems(sortedList);
        medicineList.setCellFactory(CellController -> new MedicinesCellController());
    }
    private void updateProfilePanel() throws SQLException, ClassNotFoundException {
        databaseHandler = new DatabaseHandler();
        User currentUser = new User();
        ResultSet userRow = databaseHandler.getUserById(LoginController.userId);

        while (userRow.next()) {
            currentUser.setFirstName(userRow.getString("firstname"));
            currentUser.setLastName(userRow.getString("lastname"));
            currentUser.setUserName(userRow.getString("username"));

            profilePanelFullName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
            profilePanelUserName.setText(currentUser.getUserName());
        }
    }
    private void updateComboBox() throws SQLException, ClassNotFoundException {
        databaseHandler = new DatabaseHandler();
        Treatment tempTreatment = new Treatment();
        ObservableList<String> comboBoxNames = FXCollections.observableArrayList();

        ResultSet resultSet = databaseHandler.getTreatmentsByUserId(LoginController.userId);

        while (resultSet.next()) {
            tempTreatment.setName(resultSet.getString("treatmentname"));
            if (!tempTreatment.getName().equals("")) {

                comboBoxNames.add(tempTreatment.getName());

            }
        }
        SortedList<String> sortedList = new SortedList<>(comboBoxNames, Comparator.comparing(String::toUpperCase));
        treatmentComboBox.setItems(sortedList);
    }
    private void updateQuickList() throws SQLException, ClassNotFoundException {
        Treatment tempSelectedTreatment = new Treatment();
        if (!selectedItemComboBox.isEmpty()){
            ResultSet resultSet = databaseHandler.getTreatmentByName(selectedItemComboBox);
            while (resultSet.next()){
                tempSelectedTreatment.setId(resultSet.getInt("treatmentid"));
                tempSelectedTreatment.setName(selectedItemComboBox);
                tempSelectedTreatment.setStartDate(resultSet.getDate("startdate"));
                tempSelectedTreatment.setDuration(resultSet.getInt("duration"));
                tempSelectedTreatment.setHeadache(resultSet.getInt("headache"));
                tempSelectedTreatment.setFever(resultSet.getInt("fever"));
                tempSelectedTreatment.setCold(resultSet.getInt("cold"));
                tempSelectedTreatment.setCough(resultSet.getInt("cough"));
            }
        }
        selectedTreatment = tempSelectedTreatment;
        ObservableList<Medicine> tempMedicinesQuickList = FXCollections.observableArrayList();
        treatmentDateLabel.setText(selectedTreatment.getStartDate().toString());
        treatmentDurationLabel.setText(String.valueOf(selectedTreatment.getDuration()));
        LocalDate startDate = selectedTreatment.getStartDate().toLocalDate();
        LocalDate endDate = startDate.plusDays(selectedTreatment.getDuration());
        endDateLabel.setText(endDate.toString());
        LocalDate today = LocalDate.now();
        String daysToEnd = String.valueOf(today.until(endDate, ChronoUnit.DAYS));
        daysToEndLabel.setText(daysToEnd);
        medicines.forEach(medicine -> {
            if (medicine.getHeadache() == 1 && selectedTreatment.getHeadache() == 1){
                tempMedicinesQuickList.add(medicine);
            } else if (medicine.getFever() == 1 && selectedTreatment.getFever() == 1){
                tempMedicinesQuickList.add(medicine);
            } else if (medicine.getCold() == 1 && selectedTreatment.getCold() == 1){
                tempMedicinesQuickList.add(medicine);
            } else if (medicine.getCough() == 1 && selectedTreatment.getCough() == 1){
                tempMedicinesQuickList.add(medicine);
            }
        });
        medicinesQuickList = tempMedicinesQuickList;
        SortedList<Medicine> sortedList = new SortedList<>(medicinesQuickList,
                Comparator.comparing(o -> o.getName().toUpperCase()));
        quickList.getItems().setAll(sortedList);
        quickList.setCellFactory(CellController -> new QuickListCellController());
        treatmentComboBox.getSelectionModel().select(selectedTreatment.getName());
    }
}

