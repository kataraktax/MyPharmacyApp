package sample.controller;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import sample.animation.FadeInFadeOut;
import sample.database.DatabaseHandler;
import sample.model.Medicine;

import java.io.IOException;
import java.time.LocalDate;

public class QuickListCellController extends JFXListCell<Medicine> {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label medicineName;

    @FXML
    private Label status;

    private FXMLLoader fxmlLoaderCell;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize(){

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
                fxmlLoaderCell = new FXMLLoader(getClass().getResource("/sample/view/quickList_cell.fxml"));
                fxmlLoaderCell.setController(this);

                try {
                    fxmlLoaderCell.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            medicineName.setText(medicine.getName());
            LocalDate startDate = MainPanelController.selectedTreatment.getStartDate().toLocalDate();
            LocalDate endDate = startDate.plusDays(MainPanelController.selectedTreatment.getDuration());
            if (medicine.getExpireDate().compareTo(java.sql.Date.valueOf(endDate))  < 0){
                status.setText("Status: not enough");
                status.setTextFill(Color.RED);
            } else {
                status.setText("Status: OK");
                status.setTextFill(Color.BLACK);
            }

            setText(null);
            setGraphic(rootAnchorPane);

        }
    }
}

