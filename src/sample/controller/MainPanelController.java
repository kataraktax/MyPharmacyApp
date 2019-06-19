package sample.controller;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
import java.util.Optional;

public class MainPanelController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    public JFXListView<Medicine> medicineList;

    private ObservableList<Medicine> medicines;

    @FXML
    JFXListView<Treatment> treatmentList;

    private ObservableList<Treatment> treatments;

    @FXML
    private Label profilePanelFullName;

    @FXML
    private Label profilePanelUserName;

    @FXML
    private ImageView profileEditButton;

    @FXML
    private ImageView addMedicine;

    @FXML
    private ImageView refreshMedicineList;

    @FXML
    private ImageView editMedicine;

    @FXML
    private ImageView deleteMedicine;

    @FXML
    private AnchorPane popupProfilePanel;

    @FXML
    private AnchorPane popupMedicinePanel;

    @FXML
    private ImageView addTreatment;

    @FXML
    private ImageView refreshTreatmentList;

    @FXML
    private ImageView editTreatment;

    @FXML
    private ImageView deleteTreatment;

    @FXML
    private AnchorPane popupTreatmentPanel;

    private DatabaseHandler databaseHandler;

    static Medicine selectedMedicine;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        fadeInFadeOut.makeFadeIn(rootAnchorPane);
        // resource scene strings
        String profileEditScene = "/sample/view/edit_profile.fxml";
        String addMedicineScene = "/sample/view/add_medicine.fxml";
        String editMedicineScene = "/sample/view/edit_medicine.fxml";
        String addTreatmentScene = "/sample/view/add_treatment.fxml";

        // prepare main panel to work
        refreshMedicineList();
        updateProfilePanel();

        profileEditButton.setOnMouseClicked(event -> {
            try {
                fadeInFadeOut.popupYPanel(popupProfilePanel, profileEditScene, 429);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        addMedicine.setOnMouseClicked(event -> {
            try {
                fadeInFadeOut.popupYPanel(popupMedicinePanel, addMedicineScene, 479);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        refreshMedicineList.setOnMouseClicked(event -> {
            try {
                refreshMedicineList();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
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
        addTreatment.setOnMouseClicked(event -> {
            try {
                fadeInFadeOut.popupYPanel(popupTreatmentPanel, addTreatmentScene, 369);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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

            medicines.add(medicine);
            medicineList.setItems(medicines);
            medicineList.setCellFactory(CellController -> new CellController());
        }
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
}