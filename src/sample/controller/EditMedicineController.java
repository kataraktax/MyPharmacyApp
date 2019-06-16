package sample.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.animation.FadeInFadeOut;

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



    }

    public JFXTextField getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(JFXTextField medicineName) {
        this.medicineName = medicineName;
    }

    public JFXTextArea getMedicineDescription() {
        return medicineDescription;
    }

    public void setMedicineDescription(JFXTextArea medicineDescription) {
        this.medicineDescription = medicineDescription;
    }

    public JFXDatePicker getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(JFXDatePicker expireDate) {
        this.expireDate = expireDate;
    }
}
