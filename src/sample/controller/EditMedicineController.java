package sample.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.animation.FadeInFadeOut;
import sample.database.DatabaseHandler;
import sample.model.Medicine;

import java.sql.SQLException;
import java.time.LocalDate;

public class EditMedicineController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private JFXTextField medicineName;

    @FXML
    private ImageView closePanel;

    @FXML
    private JFXTextArea medicineDescription;

    @FXML
    private JFXDatePicker expireDate;

    @FXML
    private ImageView confirmEditMedicineButton;

    @FXML
    private ImageView cancelEditMedicineButton;

    @FXML
    private Label loginError;

    @FXML
    void initialize(){
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        fadeInFadeOut.makeFadeIn(rootAnchorPane);

        DatabaseHandler databaseHandler = new DatabaseHandler();

        loginError.setVisible(false);

        medicineName.setPromptText(MainPanelController.selectedMedicine.getName());
        medicineDescription.setPromptText(MainPanelController.selectedMedicine.getDescription());
        expireDate.setPromptText(MainPanelController.selectedMedicine.getExpireDate().toString());

        cancelEditMedicineButton.setOnMouseClicked(event -> fadeInFadeOut.popupPanelFadeOut(rootAnchorPane));
        fadeInFadeOut.hoverOverIconEffects(cancelEditMedicineButton);

        closePanel.setOnMouseClicked(event -> fadeInFadeOut.popupPanelFadeOut(rootAnchorPane));
        fadeInFadeOut.hoverOverIconEffects(closePanel);

        confirmEditMedicineButton.setOnMouseClicked(event -> {
            Medicine tempMedicine = new Medicine();
            tempMedicine.setId(MainPanelController.selectedMedicine.getId());
            if (!medicineName.getText().equals("")){
                tempMedicine.setName(medicineName.getText().trim());
            } else {
                tempMedicine.setName(MainPanelController.selectedMedicine.getName());
            }

            if (!medicineDescription.getText().equals("")){
                tempMedicine.setDescription(medicineDescription.getText().trim());
            } else {
                tempMedicine.setDescription(MainPanelController.selectedMedicine.getDescription());
            }

            if (expireDate.getValue() != null){
                LocalDate date = expireDate.getValue().plusDays(1);
                tempMedicine.setExpireDate(java.sql.Date.valueOf(date));
            } else {
                tempMedicine.setExpireDate(MainPanelController.selectedMedicine.getExpireDate());
            }
            System.out.println(tempMedicine.toString());
            try {
                int id = MainPanelController.selectedMedicine.getId();
                databaseHandler.updateMedicine(tempMedicine, id);
                fadeInFadeOut.popupPanelFadeOut(rootAnchorPane);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        fadeInFadeOut.hoverOverIconEffects(confirmEditMedicineButton);
    }
}


