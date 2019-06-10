package sample.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.animation.FadeInFadeOut;
import sample.animation.Shaker;
import sample.database.DatabaseHandler;
import sample.model.Medicine;

import java.sql.SQLException;
import java.time.LocalDate;

public class AddMedicineController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private JFXTextField medicineName;

    @FXML
    private JFXTextArea medicineDescription;

    @FXML
    private JFXDatePicker expireDate;

    @FXML
    private ImageView closePanel;

    @FXML
    private ImageView confirmAddMedicineButton;

    @FXML
    private ImageView cancelAddMedicineButton;

    @FXML
    private Label loginError;

    @FXML
    void initialize(){
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        fadeInFadeOut.makeFadeIn(rootAnchorPane);

        loginError.setVisible(false);



        cancelAddMedicineButton.setOnMouseClicked(event -> rootAnchorPane.setVisible(false));

        closePanel.setOnMouseClicked(event -> rootAnchorPane.setVisible(false));

        confirmAddMedicineButton.setOnMouseClicked(event -> {
            if (!medicineName.getText().equals("") && !medicineDescription.getText().equals("") && !expireDate.getValue().equals("")) {
                try {
                    addMedicine();
                    rootAnchorPane.setVisible(false);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Shaker medicineNameShaker = new Shaker(medicineName);
                Shaker medicineDescriptionShaker = new Shaker(medicineDescription);
                Shaker expireDateShaker = new Shaker(expireDate);

                medicineNameShaker.shake();
                medicineDescriptionShaker.shake();
                expireDateShaker.shake();

                loginError.setVisible(true);
                fadeInFadeOut.hideLabel(loginError);
            }
        });


    }

    private void addMedicine() throws SQLException, ClassNotFoundException {

        DatabaseHandler databaseHandler = new DatabaseHandler();

        String name = medicineName.getText().trim();
        String description = medicineDescription.getText().trim();
        LocalDate date = expireDate.getValue();
        java.sql.Date expire = java.sql.Date.valueOf(date);

        Medicine medicine = new Medicine(name, description, expire);

        databaseHandler.addMedicine(medicine);

    }

}
