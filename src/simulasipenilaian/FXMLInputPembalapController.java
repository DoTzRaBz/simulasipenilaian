package simulasipenilaian;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FXMLInputPembalapController {

    @FXML
    private TextField txtIdPembalap;
    @FXML
    private TextField txtNamaPembalap;
    @FXML
    private TextField txtTim;
    @FXML
    private TextField txtPoin;
    @FXML
    private TextField txtPosisi;
    @FXML
    private Button btnSave;
    @FXML
    private TextField txtMusim;
    @FXML
    private TextField txtRoundKe;

    private DBPembalap dbPembalap = new DBPembalap();

    @FXML
    private void handleSave() {
        PembalapModel pembalap = new PembalapModel();
        pembalap.setIdPembalap(txtIdPembalap.getText());
        pembalap.setNamaPembalap(txtNamaPembalap.getText());
        pembalap.setTim(txtTim.getText());
        pembalap.setPoin(Integer.parseInt(txtPoin.getText()));
        pembalap.setPosisi(Integer.parseInt(txtPosisi.getText()));

        dbPembalap.setPembalapModel(pembalap);
        if (dbPembalap.insert()) {
            showAlert("Success", "Pembalap berhasil ditambahkan.");
        } else {
            showAlert("Error", "Gagal menambahkan pembalap.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
