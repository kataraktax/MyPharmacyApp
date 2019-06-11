package sample.controller;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.database.DatabaseHandler;
import sample.model.Medicine;

import java.io.IOException;
import java.sql.SQLException;

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

    @FXML
    private ImageView editButton;

    private FXMLLoader fxmlLoaderCell;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize(){

    }

    @Override
    protected void updateItem(Medicine medicine, boolean empty) {
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
            expireDate.setText(medicine.getExpireDate().toString());

            delateButton.setOnMouseClicked(event -> {
                databaseHandler = new DatabaseHandler();

                try {
                    databaseHandler.deleteMedicine(medicine.getId());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                getListView().getItems().remove(getItem());
            });

            setText(null);
            setGraphic(rootAnchorPane);

        }
    }
}
