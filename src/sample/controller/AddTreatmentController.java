package sample.controller;


import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.animation.FadeInFadeOut;
import sample.animation.Shaker;
import sample.database.DatabaseHandler;
import sample.model.Treatment;

import java.sql.SQLException;
import java.time.LocalDate;

public class AddTreatmentController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private JFXTextField treatmentName;

    @FXML
    private ImageView closePanel;

    @FXML
    private ImageView addTreatmentButton;

    @FXML
    private ImageView cancelButton;

    @FXML
    private Label loginError;

    @FXML
    private JFXTextField treatmentDuration;

    @FXML
    private JFXCheckBox headacheBox;

    @FXML
    private JFXCheckBox feverBox;

    @FXML
    private JFXCheckBox coldBox;

    @FXML
    private JFXCheckBox coughBox;

    @FXML
    void initialize() {
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        fadeInFadeOut.makeFadeIn(rootAnchorPane);

        loginError.setVisible(false);
        String mainPanelScene = "/sample/view/main_panel.fxml";

        cancelButton.setOnMouseClicked(event -> fadeInFadeOut.makeFadeOut(rootAnchorPane, mainPanelScene));
        fadeInFadeOut.hoverOverIconEffects(cancelButton);

        closePanel.setOnMouseClicked(event -> fadeInFadeOut.makeFadeOut(rootAnchorPane, mainPanelScene));
        fadeInFadeOut.hoverOverIconEffects(closePanel);

        addTreatmentButton.setOnMouseClicked(event -> {
            if (!treatmentName.getText().equals("") && !treatmentDuration.getText().equals("")) {
                String durationText = treatmentDuration.getText();
                int durationInt = Integer.parseInt(durationText);
                if ((durationInt) > 0){
                    try {
                        addTreatment();
                        fadeInFadeOut.makeFadeOut(rootAnchorPane, mainPanelScene);
                    } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                Shaker treatmentNameShaker = new Shaker(treatmentName);
                treatmentNameShaker.shake();
                loginError.setVisible(true);
                fadeInFadeOut.hideLabel(loginError);
            }
        });
        fadeInFadeOut.hoverOverIconEffects(addTreatmentButton);
    }

    private void addTreatment() throws SQLException, ClassNotFoundException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        Treatment tempTreatment = new Treatment();

        tempTreatment.setName(treatmentName.getText());
        LocalDate now = LocalDate.now().plusDays(1);
        tempTreatment.setStartDate(java.sql.Date.valueOf(now));
        tempTreatment.setDuration(Integer.parseInt(treatmentDuration.getText()));
        if (headacheBox.isSelected()){
            tempTreatment.setHeadache(1);
        } else {
            tempTreatment.setHeadache(0);
        }
        if (feverBox.isSelected()){
            tempTreatment.setFever(1);
        } else{
            tempTreatment.setFever(0);
        }
        if (coldBox.isSelected()){
            tempTreatment.setCold(1);
        } else {
            tempTreatment.setCold(0);
        }
        if (coughBox.isSelected()){
            tempTreatment.setCough(1);
        } else {
            tempTreatment.setCough(0);
        }
        databaseHandler.createTreatment(tempTreatment, LoginController.userId);
    }

}
