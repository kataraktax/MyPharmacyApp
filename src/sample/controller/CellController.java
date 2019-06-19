package sample.controller;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import sample.database.DatabaseHandler;
import sample.model.Medicine;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class CellController extends JFXListCell<Medicine> {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label medicineName;

    @FXML
    private Label medicineDescription;

    @FXML
    private Label expireDate;

    @FXML
    private ImageView delateButton;

    private FXMLLoader fxmlLoaderCell;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() throws SQLException{

    }

    @Override
    protected void updateItem(Medicine medicine, boolean empty) {

        databaseHandler = new DatabaseHandler();

        super.updateItem(medicine, empty);

        if (empty || medicine == null){
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoaderCell == null){
                fxmlLoaderCell = new FXMLLoader(getClass().getResource("/sample/view/cell.fxml"));
                fxmlLoaderCell.setController(this);

                try {
                    fxmlLoaderCell.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            medicineName.setText(medicine.getName());
            medicineDescription.setText(medicine.getDescription());

            LocalDate today = LocalDate.now();
            if (medicine.getExpireDate().compareTo(java.sql.Date.valueOf(today)) < 0){
                expireDate.setText(medicine.getExpireDate().toString());
                expireDate.setTextFill(Color.RED);
            } else if(medicine.getExpireDate().compareTo(java.sql.Date.valueOf(today)) == 0){
                expireDate.setText(medicine.getExpireDate().toString());
                expireDate.setTextFill(Color.YELLOW);
            } else {
                expireDate.setText(medicine.getExpireDate().toString());
            }


            delateButton.setOnMouseClicked(event -> {
                try {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Medicine from List Confirmation");
                    alert.setHeaderText("Please confirm do you want to continue and delete medicine: " + medicine.getName());
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setContentText("If you want to delete this Medicine from List press OK in other case please press CANCEL");

                    Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            databaseHandler.deleteMedicine(medicine.getId());
                            getListView().getItems().remove(getItem());
                        } else {
                            alert.close();
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
            });

            setText(null);
            setGraphic(rootAnchorPane);

        }
    }
}
