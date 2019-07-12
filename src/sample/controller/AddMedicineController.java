package sample.controller;

import com.jfoenix.controls.JFXCheckBox;
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
    private JFXCheckBox headacheBox;

    @FXML
    private JFXCheckBox feverBox;

    @FXML
    private JFXCheckBox coldBox;

    @FXML
    private JFXCheckBox coughBox;

    @FXML
    void initialize(){
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        fadeInFadeOut.makeFadeIn(rootAnchorPane);

        loginError.setVisible(false);

        String mainPanelScene = "/sample/view/main_panel.fxml";


        cancelAddMedicineButton.setOnMouseClicked(event -> fadeInFadeOut.makeFadeOut(rootAnchorPane, mainPanelScene));
        fadeInFadeOut.hoverOverIconEffects(cancelAddMedicineButton);

        closePanel.setOnMouseClicked(event -> fadeInFadeOut.makeFadeOut(rootAnchorPane, mainPanelScene));
        fadeInFadeOut.hoverOverIconEffects(closePanel);

        confirmAddMedicineButton.setOnMouseClicked(event -> {
            if (!medicineName.getText().equals("") && !medicineDescription.getText().equals("") && !expireDate.getValue().toString().equals("")) {
                try {
                    addMedicine();
                    fadeInFadeOut.makeFadeOut(rootAnchorPane, mainPanelScene);
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
        fadeInFadeOut.hoverOverIconEffects(confirmAddMedicineButton);
    }

    private void addMedicine() throws SQLException, ClassNotFoundException {

        DatabaseHandler databaseHandler = new DatabaseHandler();
        Medicine tempMedicine = new Medicine();

        tempMedicine.setName(medicineName.getText().trim());
        tempMedicine.setDescription(medicineDescription.getText().trim());
        LocalDate date = expireDate.getValue();
        tempMedicine.setExpireDate(java.sql.Date.valueOf(date));

        if (headacheBox.isSelected()){
            tempMedicine.setHeadache(1);
        } else {
            tempMedicine.setHeadache(0);
        }
        if (feverBox.isSelected()){
            tempMedicine.setFever(1);
        } else{
            tempMedicine.setFever(0);
        }
        if (coldBox.isSelected()){
            tempMedicine.setCold(1);
        } else {
            tempMedicine.setCold(0);
        }
        if (coughBox.isSelected()){
            tempMedicine.setCough(1);
        } else {
            tempMedicine.setCough(0);
        }
        databaseHandler.addMedicine(tempMedicine);
    }

}
