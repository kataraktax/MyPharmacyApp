package sample.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.animation.FadeInFadeOut;
import sample.database.DatabaseHandler;
import sample.model.Treatment;

import java.sql.SQLException;

public class EditTreatmentController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private JFXTextField treatmentName;

    @FXML
    private ImageView closePanel;

    @FXML
    private ImageView editTreatmentButton;

    @FXML
    private ImageView cancelEditTreatmentButton;

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
    void initialize(){
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        fadeInFadeOut.makeFadeIn(rootAnchorPane);

        DatabaseHandler databaseHandler = new DatabaseHandler();

        String mainPanelScene = "/sample/view/main_panel.fxml";

        treatmentName.setPromptText(MainPanelController.selectedTreatment.getName());
        treatmentDuration.setPromptText(String.valueOf(MainPanelController.selectedTreatment.getDuration()));
        if (MainPanelController.selectedTreatment.getHeadache() == 1){
            headacheBox.setSelected(true);
        }
        if (MainPanelController.selectedTreatment.getFever() == 1){
            feverBox.setSelected(true);
        }
        if (MainPanelController.selectedTreatment.getCold() == 1){
            coldBox.setSelected(true);
        }
        if (MainPanelController.selectedTreatment.getCough() == 1){
            coughBox.setSelected(true);
        }

        cancelEditTreatmentButton.setOnMouseClicked(event -> fadeInFadeOut.makeFadeOut(rootAnchorPane, mainPanelScene));
        fadeInFadeOut.hoverOverIconEffects(cancelEditTreatmentButton);

        closePanel.setOnMouseClicked(event -> fadeInFadeOut.makeFadeOut(rootAnchorPane, mainPanelScene));
        fadeInFadeOut.hoverOverIconEffects(closePanel);

        editTreatmentButton.setOnMouseClicked(event -> {
            Treatment tempTreatment = new Treatment();
            tempTreatment.setId(MainPanelController.selectedTreatment.getId());
            if (!treatmentName.getText().equals("")){
                tempTreatment.setName(treatmentName.getText().trim());
            } else {
                tempTreatment.setName(MainPanelController.selectedTreatment.getName());
            }
            if (!treatmentDuration.getText().equals("")){
                tempTreatment.setDuration(Integer.parseInt(treatmentDuration.getText()));
            } else {
                tempTreatment.setDuration(MainPanelController.selectedTreatment.getDuration());
            }
            if (headacheBox.isSelected()){
                tempTreatment.setHeadache(1);
            } else {
                tempTreatment.setHeadache(0);
            }
            if (feverBox.isSelected()){
                tempTreatment.setFever(1);
            } else {
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

            try {
                int id = MainPanelController.selectedTreatment.getId();
                databaseHandler.updateTreatment(tempTreatment,id);
                fadeInFadeOut.makeFadeOut(rootAnchorPane, mainPanelScene);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        fadeInFadeOut.hoverOverIconEffects(editTreatmentButton);

    }


}
