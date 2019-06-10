package sample.controller;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.animation.FadeInFadeOut;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainPanelController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private JFXListView<?> medicineList;

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

        profileEditButton.setOnMouseClicked(event -> {
            try {
                fadeInFadeOut.popupPanel(popupProfilePanel, profileEditScene, 390);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        addMedicine.setOnMouseClicked(event -> {
            try {
                fadeInFadeOut.popupPanel(popupMedicinePanel, addMedicineScene, 425);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


    }
}