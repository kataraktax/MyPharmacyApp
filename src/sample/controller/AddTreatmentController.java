package sample.controller;


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
    void initialize() {
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        fadeInFadeOut.makeFadeIn(rootAnchorPane);

        loginError.setVisible(false);

        cancelButton.setOnMouseClicked(event -> fadeInFadeOut.popupPanelFadeOut(rootAnchorPane));
        fadeInFadeOut.hoverOverIconEffects(cancelButton);

        closePanel.setOnMouseClicked(event -> fadeInFadeOut.popupPanelFadeOut(rootAnchorPane));
        fadeInFadeOut.hoverOverIconEffects(closePanel);

        addTreatmentButton.setOnMouseClicked(event -> {
            if (!treatmentName.getText().equals("") && !treatmentDuration.getText().equals("")) {
                String durationText = treatmentDuration.getText();
                if (Integer.parseInt(durationText) > 0){
                    try {
                        addTreatment();
                        fadeInFadeOut.popupPanelFadeOut(rootAnchorPane);
                    } catch (SQLException | ClassNotFoundException e) {
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

        String name = treatmentName.getText();
        LocalDate now = LocalDate.now().plusDays(1);
        java.sql.Date startDate = java.sql.Date.valueOf(now);
        int duration = Integer.parseInt(treatmentDuration.getText());

        Treatment tempTreatment = new Treatment(name, startDate, duration);

        databaseHandler.createTreatment(tempTreatment, LoginController.userId);
    }

}
