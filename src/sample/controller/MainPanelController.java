package sample.controller;

import com.jfoenix.controls.JFXListView;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.animation.FadeInFadeOut;
import sample.database.DatabaseHandler;
import sample.model.Medicine;
import sample.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainPanelController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    public JFXListView<Medicine> medicineList;

    private ObservableList<Medicine> medicines;

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

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        fadeInFadeOut.makeFadeIn(rootAnchorPane);

        String profileEditScene = "/sample/view/edit_profile.fxml";
        String addMedicineScene = "/sample/view/add_medicine.fxml";
        String editMedicineScene = "/sample/view/edit_medicine.fxml";

        databaseHandler = new DatabaseHandler();
        medicines = FXCollections.observableArrayList();
        ResultSet resultSet = databaseHandler.getMedicines();

        while (resultSet.next()) {
            Medicine medicine = new Medicine();
            medicine.setId(resultSet.getInt("medicineid"));
            medicine.setName(resultSet.getString("name"));
            medicine.setDescription(resultSet.getString("description"));
            medicine.setExpireDate(resultSet.getDate("expiredate"));

            medicines.add(medicine);
        }

        medicineList.setItems(medicines);
        medicineList.setCellFactory(CellController -> new CellController());


        updateProfilePanel();


        profileEditButton.setOnMouseClicked(event -> {
            try {
                fadeInFadeOut.popupPanel(popupProfilePanel, profileEditScene, 390);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        addMedicine.setOnMouseClicked(event -> {
            try {
                fadeInFadeOut.popupPanel(popupMedicinePanel, addMedicineScene, 479);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        refreshMedicineList.setOnMouseClicked(event -> {
            try {
                refreshList();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        editMedicine.setOnMouseClicked(event -> {

            Medicine selectedMedicine = medicineList.getSelectionModel().getSelectedItem();
            AnchorPane pane = popupMedicinePanel;
            AnchorPane formPane;
            pane.setVisible(true);

            try {
                formPane = FXMLLoader.load(getClass().getResource(editMedicineScene));
                pane.setVisible(true);
                formPane.translateYProperty().set(-350.00);
                pane.getChildren().setAll(formPane);

                Timeline timeline = new Timeline();
                timeline.setCycleCount(1);
                KeyValue keyValue = new KeyValue(formPane.translateYProperty(), 479, Interpolator.EASE_IN);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.5), keyValue);
                timeline.getKeyFrames().add(keyFrame);
                timeline.play();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource(editMedicineScene));
//
//            try {
//                loader.load();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Parent root = loader.getRoot();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//
//            EditMedicineController editMedicineController = loader.getController();
//            editMedicineController.getMedicineName().setPromptText(selectedMedicine.getName());
//            editMedicineController.getMedicineDescription().setPromptText(selectedMedicine.getDescription());
//            editMedicineController.getExpireDate().setPromptText(selectedMedicine.getExpireDate().toString());
//            stage.show();

        });

    }

    public MainPanelController() {
    }

    public void refreshList() throws SQLException, ClassNotFoundException {
        databaseHandler = new DatabaseHandler();
        medicines = FXCollections.observableArrayList();
        ResultSet resultSet = databaseHandler.getMedicines();

        while (resultSet.next()) {
            Medicine medicine = new Medicine();
            medicine.setId(resultSet.getInt("medicineid"));
            medicine.setName(resultSet.getString("name"));
            medicine.setDescription(resultSet.getString("description"));
            medicine.setExpireDate(resultSet.getDate("expiredate"));

            medicines.add(medicine);
            medicineList.setItems(medicines);
            medicineList.setCellFactory(CellController -> new CellController());
        }


    }

    private void updateProfilePanel() throws SQLException, ClassNotFoundException {
        databaseHandler = new DatabaseHandler();
        User currentUser = new User();
        ResultSet userRow = databaseHandler.getUserById(LoginController.userId);

        while (userRow.next()) {
            currentUser.setFirstName(userRow.getString("firstname"));
            currentUser.setLastName(userRow.getString("lastname"));
            currentUser.setUserName(userRow.getString("username"));

            profilePanelFullName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
            profilePanelUserName.setText(currentUser.getUserName());
        }
    }
}