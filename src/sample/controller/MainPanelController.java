package sample.controller;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.animation.FadeInFadeOut;
import sample.database.DatabaseHandler;
import sample.model.Medicine;
import sample.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainPanelController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private JFXListView<Medicine> medicineList;

    private ObservableList<Medicine> medicines;

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
    void initialize() throws SQLException, ClassNotFoundException {
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        fadeInFadeOut.makeFadeIn(rootAnchorPane);

        User currentUser = new User();

        String profileEditScene = "/sample/view/edit_profile.fxml";
        String addMedicineScene = "/sample/view/add_medicine.fxml";


        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet userRow = databaseHandler.getUserById(LoginController.userId);

        while (userRow.next()) {
            currentUser.setFirstName(userRow.getString("firstname"));
            currentUser.setLastName(userRow.getString("lastname"));
            currentUser.setUserName(userRow.getString("username"));

            profilePanelFullName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
            profilePanelUserName.setText(currentUser.getUserName());
        }

        medicines = FXCollections.observableArrayList();
        ResultSet resultSet = databaseHandler.getMedicines();

        while (resultSet.next()){
            Medicine medicine = new Medicine();
            medicine.setName(resultSet.getString("name"));
            medicine.setDescription(resultSet.getString("description"));
            medicine.setExpireDate(resultSet.getDate("expiredate"));

            medicines.add(medicine);
        }

        medicineList.setItems(medicines);

        profileEditButton.setOnMouseClicked(event -> {
            try {
                fadeInFadeOut.popupPanel(popupProfilePanel, profileEditScene, 390);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        addMedicine.setOnMouseClicked(event -> {
            try {
                fadeInFadeOut.popupPanel(popupMedicinePanel, addMedicineScene, 479);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


    }
}