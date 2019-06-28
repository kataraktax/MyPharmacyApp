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

        DatabaseHandler databaseHandler = new DatabaseHandler();

        loginError.setVisible(false);
        String mainPanelScene = "/sample/view/main_panel.fxml";

        medicineName.setPromptText(MainPanelController.selectedMedicine.getName());
        medicineDescription.setPromptText(MainPanelController.selectedMedicine.getDescription());
        expireDate.setPromptText(MainPanelController.selectedMedicine.getExpireDate().toString());
        if (MainPanelController.selectedMedicine.getHeadache() == 1){
            headacheBox.setSelected(true);
        }
        if (MainPanelController.selectedMedicine.getFever() == 1){
            feverBox.setSelected(true);
        }
        if (MainPanelController.selectedMedicine.getCold() == 1){
            coldBox.setSelected(true);
        }
        if (MainPanelController.selectedMedicine.getCough() == 1){
            coughBox.setSelected(true);
        }

        cancelEditMedicineButton.setOnMouseClicked(event -> fadeInFadeOut.makeFadeOut(rootAnchorPane, mainPanelScene));
        fadeInFadeOut.hoverOverIconEffects(cancelEditMedicineButton);

        closePanel.setOnMouseClicked(event -> fadeInFadeOut.makeFadeOut(rootAnchorPane, mainPanelScene));
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
            if (headacheBox.isSelected()){
                tempMedicine.setHeadache(1);
            } else {
                tempMedicine.setHeadache(0);
            }
            if (feverBox.isSelected()){
                tempMedicine.setFever(1);
            } else {
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

            try {
                int id = MainPanelController.selectedMedicine.getId();
                databaseHandler.updateMedicine(tempMedicine, id);
                fadeInFadeOut.makeFadeOut(rootAnchorPane, mainPanelScene);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        fadeInFadeOut.hoverOverIconEffects(confirmEditMedicineButton);
    }
}


