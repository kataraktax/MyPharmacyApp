package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.animation.FadeInFadeOut;

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
    void initialize(){
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        fadeInFadeOut.makeFadeIn(rootAnchorPane);

        cancelAddMedicineButton.setOnMouseClicked(event -> rootAnchorPane.setVisible(false));

        closePanel.setOnMouseClicked(event -> rootAnchorPane.setVisible(false));


    }

}
